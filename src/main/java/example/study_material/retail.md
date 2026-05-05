## Summary

You need changes in **5 places only**:

1. `AccountStatementDownloadServiceImpl`
2. `PeriodicStatementAsyncClass`
3. `DownloadPdfForAccountStatement`
4. `NotificationRequest`
5. `ErrorExceptionCodes`

Controller does **not** need changes.

---

# 1. Class: `AccountStatementDownloadServiceImpl`

## 1.1 Add Import

At the top of `AccountStatementDownloadServiceImpl`, add this import:

```java
import com.au.app.payments.domain.enums.EventType;
```

Also add this static import:

```java
import static com.au.app.payments.domain.enums.ErrorExceptionCodes.PAYMENT0870;
```

---

## 1.2 Add Retail Email Enrichment Method

Add this method inside `AccountStatementDownloadServiceImpl`, preferably near `getEmailId(...)`.

```java
private void enrichRetailEmailRequest(DownloadAccountStatementRequest request, UserProfile userProfile) {
    if (!AppIdentifier.RETAIL.equals(request.getAppIdentifier())
            || !Boolean.TRUE.equals(request.getSendEmail())) {
        return;
    }

    if (Objects.isNull(userProfile.getEmailAddress())
            || userProfile.getEmailAddress().isBlank()
            || !Boolean.TRUE.equals(userProfile.getIsEmailVerified())) {
        businessDeclinedElasticLabel();
        throw new AuBusinessException(PAYMENT0870.getHttpStatus(),
                PAYMENT0870.getCode(),
                PAYMENT0870.getMessage());
    }

    request.setFormat("PDF");
    request.setEmailAddress(Collections.singleton(userProfile.getEmailAddress().trim()));
    request.setEventType(EventType.APP_AS_EMAIL);
}
```

---

## 1.3 Call This Method In `downloadAccountStatement(...)`

In this method:

```java
public ApiResponse<String> downloadAccountStatement(DownloadAccountStatementRequest request, UserProfile userProfile)
```

You currently have:

```java
setElasticRequestLabel(BusinessJourney.DOWNLOAD_ACCOUNT_STATEMENT);
JsonUtil.checkUserValidAccounts(request.getAccountStatementRequestDetail().getAccountNumber(), userProfile);

List<PeriodicStatementAsync> periodicStatementAsyncData =
```

Change it to:

```java
setElasticRequestLabel(BusinessJourney.DOWNLOAD_ACCOUNT_STATEMENT);
JsonUtil.checkUserValidAccounts(request.getAccountStatementRequestDetail().getAccountNumber(), userProfile);

enrichRetailEmailRequest(request, userProfile);

List<PeriodicStatementAsync> periodicStatementAsyncData =
```

This means retail email will automatically use logged-in user email.

---

## 1.4 Make `getEmailId(...)` Null Safe

Replace your current method:

```java
private Set<String> getEmailId(DownloadAccountStatementRequest request, UserProfile userProfile) {
    if (request.getEmailAddress().isEmpty()) {
        return getEmailUsingMerchantId(Collections.singleton(userProfile.getActiveMerchantId()));
    } else {
        return request.getEmailAddress();
    }
}
```

With this:

```java
private Set<String> getEmailId(DownloadAccountStatementRequest request, UserProfile userProfile) {
    if (Objects.isNull(request.getEmailAddress()) || request.getEmailAddress().isEmpty()) {
        return getEmailUsingMerchantId(Collections.singleton(userProfile.getActiveMerchantId()));
    }
    return request.getEmailAddress();
}
```

This is safe for merchant also.

---

# 2. Class: `ErrorExceptionCodes`

## Add New Error Code

In `ErrorExceptionCodes`, near other `PAYMENT08xx` codes, add:

```java
PAYMENT0870("PAYMENT0870", "No verified email ID is registered with this account", BAD_REQUEST.value()),
```

Example placement:

```java
PAYMENT0868("PAYMENT0868", "Payee group not present or not exist with current user", INTERNAL_SERVER_ERROR.value()),
PAYMENT0869("PAYMENT0869", "Payee group to mapping not present or not exist with current user", INTERNAL_SERVER_ERROR.value()),
PAYMENT0870("PAYMENT0870", "No verified email ID is registered with this account", BAD_REQUEST.value()),
```

Frontend can use this error code to show the “Add Email” screen.

---

# 3. Class: `PeriodicStatementAsyncClass`

This class needs the most important fixes.

---

## 3.1 Replace Filter Logic In `getAccountStatementString(...)`

Inside:

```java
public String getAccountStatementString(...)
```

You currently have this:

```java
if (Boolean.FALSE.equals(request.getSendEmail())) {
    if (AppIdentifier.RETAIL.equals(request.getAppIdentifier())) {
        retailFilterStatement(request, transactionStatements);
    } else {
        filterStatement(request, transactionStatements);
    }
}
```

Replace it with:

```java
applyFiltersIfRequired(request, transactionStatements);
```

---

## 3.2 Add New Helper Method

Add this method inside `PeriodicStatementAsyncClass`, preferably before `filterStatement(...)`.

```java
private void applyFiltersIfRequired(DownloadAccountStatementRequest request,
                                    PeriodicStatementResponseData transactionStatements) {
    if (AppIdentifier.RETAIL.equals(request.getAppIdentifier())) {
        retailFilterStatement(request, transactionStatements);
        return;
    }

    if (Boolean.FALSE.equals(request.getSendEmail())) {
        filterStatement(request, transactionStatements);
    }
}
```

Why this is needed:

```text
Retail download -> filters applied
Retail email -> filters applied
Merchant download -> existing behavior
Merchant email -> existing behavior
```

---

## 3.3 Add XLS/XLSX Support

Inside `getAccountStatementString(...)`, you have this switch:

```java
case "EXCEL" -> {
```

Change it to:

```java
case "EXCEL", "XLS", "XLSX" -> {
```

Full part should become:

```java
var base64String = switch (format.trim().toUpperCase(Locale.ROOT)) {
    case "EXCEL", "XLS", "XLSX" -> {
        log.info("Generating account statement in EXCEL format.");
        yield generateAccountStatementExcelBase64(request, transactionStatements);
    }
    case "CSV" -> {
        log.info("Generating account statement in CSV format.");
        yield generateAccountStatementCSVBase64(request, transactionStatements);
    }
    case "PDF" -> {
        log.info("Generating account statement in PDF format.");
        var accountStatementRequest = createRequestAccountStatement(request, transactionStatements);
        yield downloadPdfForAccountStatement.generateStatementPdf(request, accountStatementRequest, pdfDownloadAccountStatementReq);
    }
    default -> {
        log.warn("Unsupported account statement format='{}'.", format);
        throw new AuBusinessException(PAYMENT0400.getHttpStatus(), PAYMENT0400.getCode(), "Unsupported account statement format");
    }
};
```

---

## 3.4 Fix Retail Email Null Pointer In `sendEmail(...)`

Inside this private method:

```java
private void sendEmail(String startDate, String endDate, String emailAddress, String fileName, String fileData,
                       AppIdentifier appIdentifier, PdfDownloadAccountStatementReq pdfDownloadAccountStatementReq)
```

You currently have:

```java
NotificationRequest request = null;
//        = NotificationRequest.getNotificationRequestForAccountStatement(emailAddress,
//        fileName, CONTENT, fileData, appIdentifier, startDate, endDate);

if (AppIdentifier.RETAIL.equals(appIdentifier)) {
//    request = NotificationRequest.gerRetailAccountStatementNotification()
} else {
    request = NotificationRequest.getNotificationRequestForAccountStatement(emailAddress,
            fileName, CONTENT, fileData, appIdentifier, startDate, endDate);
}
```

Replace that complete block with:

```java
NotificationRequest request;

if (AppIdentifier.RETAIL.equals(appIdentifier)) {
    request = NotificationRequest.getRetailAccountStatementNotification(emailAddress,
            fileName, CONTENT, fileData, appIdentifier, startDate, endDate);
} else {
    request = NotificationRequest.getNotificationRequestForAccountStatement(emailAddress,
            fileName, CONTENT, fileData, appIdentifier, startDate, endDate);
}
```

Then replace this line:

```java
Map<String, Object> mapData = new HashMap<>(request.data());
```

With this:

```java
Map<String, Object> mapData = Objects.nonNull(request.data())
        ? new HashMap<>(request.data())
        : new HashMap<>();
```

This prevents retail email failure.

---

## 3.5 Separate Retail PDF Email Data From Merchant Data

Inside this method:

```java
private void sendEmail(DownloadAccountStatementRequest request, String base64String,
                       PdfDownloadAccountStatementReq pdfDownloadAccountStatementReq)
```

You currently have this inside PDF block:

```java
if (request.getFormat().equalsIgnoreCase("PDF") && Boolean.TRUE.equals(request.getSendEmail())) {
    pdfDownloadAccountStatementReq.setProtectedSuggestionImgInd("https://aubank-prod-merchant-static.au.bank.in/E_Statement_Mailer_02_147bf56edc.jpg");
    pdfDownloadAccountStatementReq.setProtectedSuggestionImgOth("https://aubank-prod-merchant-static.au.bank.in/E_Statement_Mailer_03_968cc190a0.jpg");
    pdfDownloadAccountStatementReq.setPhoneSign("https://aubank-prod-merchant-static.au.bank.in/E_Statement_Mailer_06_d4d8f583b6.png");
    pdfDownloadAccountStatementReq.setWebSign("https://aubank-prod-merchant-static.au.bank.in/E_Statement_Mailer_08_17e34adde5.png");
    pdfDownloadAccountStatementReq.setAccountNumber(accountNumber.substring(accountNumber.length() - 4));
    request.getEmailAddress().forEach(i -> {
```

Replace only the image-setting part with this:

```java
if (request.getFormat().equalsIgnoreCase("PDF") && Boolean.TRUE.equals(request.getSendEmail())) {
    preparePdfEmailData(request, pdfDownloadAccountStatementReq, accountNumber);

    request.getEmailAddress().forEach(i -> {
```

Then add these methods inside `PeriodicStatementAsyncClass`:

```java
private void preparePdfEmailData(DownloadAccountStatementRequest request,
                                 PdfDownloadAccountStatementReq pdfDownloadAccountStatementReq,
                                 String accountNumber) {
    if (AppIdentifier.RETAIL.equals(request.getAppIdentifier())) {
        prepareRetailPdfEmailData(pdfDownloadAccountStatementReq, accountNumber);
        return;
    }

    prepareMerchantPdfEmailData(pdfDownloadAccountStatementReq, accountNumber);
}

private void prepareMerchantPdfEmailData(PdfDownloadAccountStatementReq pdfDownloadAccountStatementReq,
                                         String accountNumber) {
    pdfDownloadAccountStatementReq.setProtectedSuggestionImgInd("https://aubank-prod-merchant-static.au.bank.in/E_Statement_Mailer_02_147bf56edc.jpg");
    pdfDownloadAccountStatementReq.setProtectedSuggestionImgOth("https://aubank-prod-merchant-static.au.bank.in/E_Statement_Mailer_03_968cc190a0.jpg");
    pdfDownloadAccountStatementReq.setPhoneSign("https://aubank-prod-merchant-static.au.bank.in/E_Statement_Mailer_06_d4d8f583b6.png");
    pdfDownloadAccountStatementReq.setWebSign("https://aubank-prod-merchant-static.au.bank.in/E_Statement_Mailer_08_17e34adde5.png");
    pdfDownloadAccountStatementReq.setAccountNumber(accountNumber.substring(accountNumber.length() - 4));
}

private void prepareRetailPdfEmailData(PdfDownloadAccountStatementReq pdfDownloadAccountStatementReq,
                                       String accountNumber) {
    pdfDownloadAccountStatementReq.setAccountNumber(accountNumber.substring(accountNumber.length() - 4));
}
```

This keeps merchant assets untouched.

Retail template can work with only required fields. If retail template needs separate images, later add retail image URLs here from config.

---

# 4. Class: `NotificationRequest`

You did not paste this class, so add this method in the same class where this method already exists:

```java
getNotificationRequestForAccountStatement(...)
```

Add the new method just below the existing merchant method.

```java
public static NotificationRequest getRetailAccountStatementNotification(String emailAddress,
                                                                        String fileName,
                                                                        String mimeType,
                                                                        String fileData,
                                                                        AppIdentifier appIdentifier,
                                                                        String startDate,
                                                                        String endDate) {
    NotificationRequest request = getNotificationRequestForAccountStatement(emailAddress,
            fileName, mimeType, fileData, appIdentifier, startDate, endDate);

    return NotificationRequest.builder()
            .eventType(request.eventType())
            .data(request.data())
            .customerRequest(request.customerRequest())
            .idempotencyKey(request.idempotencyKey())
            .build();
}
```

This is the safest first version because it reuses the existing working notification payload.

But if notification service has a separate retail template event, change only this part:

```java
.eventType(request.eventType())
```

to the actual retail event:

```java
.eventType(EventType.APP_AS_EMAIL)
```

or:

```java
.eventType(EventType.RETAIL_AS_EMAIL)
```

depending on what notification service expects.

Important: do not modify existing `getNotificationRequestForAccountStatement(...)`.

---

# 5. Class: `DownloadPdfForAccountStatement`

## 5.1 Add Imports

Add these imports:

```java
import com.au.app.payments.domain.enums.AppIdentifier;
import java.util.Locale;
```

If your retail DOB/date can come in different formats, also add:

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
```

---

## 5.2 Change PDF Encryption Branch

Inside:

```java
public String generateStatementPdf(...)
```

You currently have:

```java
if (Boolean.TRUE.equals(requestDownloadAccountStatement.getSendEmail())) {
    var passwordBytes = encryptPdf(pdfReq).getBytes(UTF_8);
    writerProperties.setStandardEncryption(
            passwordBytes,
            passwordBytes,
            EncryptionConstants.ALLOW_PRINTING,
            EncryptionConstants.ENCRYPTION_AES_256
    );
}
```

Replace it with:

```java
if (Boolean.TRUE.equals(requestDownloadAccountStatement.getSendEmail())) {
    String password = AppIdentifier.RETAIL.equals(requestDownloadAccountStatement.getAppIdentifier())
            ? encryptRetailPdf(requestDownloadAccountStatement, pdfReq)
            : encryptPdf(pdfReq);

    var passwordBytes = password.getBytes(UTF_8);

    writerProperties.setStandardEncryption(
            passwordBytes,
            passwordBytes,
            EncryptionConstants.ALLOW_PRINTING,
            EncryptionConstants.ENCRYPTION_AES_256
    );
}
```

Merchant still uses old `encryptPdf(...)`.

Retail uses new `encryptRetailPdf(...)`.

---

## 5.3 Add Retail Password Method

Add this method inside `DownloadPdfForAccountStatement`, preferably below existing `encryptPdf(...)`.

```java
private String encryptRetailPdf(DownloadAccountStatementRequest requestDownloadAccountStatement,
                                PdfDownloadAccountStatementReq pdfDownloadAccountStatementReq) {
    UserProfile userProfile = userProfileDetails.getUserProfileFromSessionDetails();

    log.info("Request received to encrypt retail pdf for user {}", userProfile.getUserId());

    ApiResponse<DedupeLiteResponse> dedupeLiteResponse =
            umsApiClient.dedupeLiteV2(DedupeRequestV2.builder().id(null).build());

    if (Objects.isNull(dedupeLiteResponse.getData())
            || Objects.isNull(dedupeLiteResponse.getData().getMatchFound())
            || Objects.isNull(dedupeLiteResponse.getData().getMatchFound().getCustomer())) {
        throw new AuBusinessException(CES0500.getHttpStatus(), CES0500.getCode(),
                "Customer data not found for statement password");
    }

    CustomerPdf customer = dedupeLiteResponse.getData().getMatchFound().getCustomer().stream()
            .filter(data -> userProfile.getCifNumber().equalsIgnoreCase(data.getCustomerID()))
            .findFirst()
            .orElseThrow(() -> new AuBusinessException(CES0500.getHttpStatus(), CES0500.getCode(),
                    "Customer data not found for statement password"));

    String rawName = Objects.toString(customer.getCustomerFullName(), "").trim().replaceAll("\\s+", " ");

    if (rawName.isBlank()) {
        throw new AuBusinessException(CES0500.getHttpStatus(), CES0500.getCode(),
                "Customer name not found for statement password");
    }

    String displayName = Arrays.stream(rawName.split("([\\s\\-']+)"))
            .map(w -> StringUtils.capitalize(StringUtils.lowerCase(w)))
            .collect(Collectors.joining(" "));

    pdfDownloadAccountStatementReq.setCustomerName(displayName);

    String firstName = rawName.split(" ")[0];
    String shortName = firstName.length() > 4 ? firstName.substring(0, 4) : firstName;

    String dateOfBirth = customer.getDateOfBirth();

    if (Objects.isNull(dateOfBirth) || dateOfBirth.isBlank()) {
        throw new AuBusinessException(CES0500.getHttpStatus(), CES0500.getCode(),
                "Customer date of birth not found for statement password");
    }

    String[] dob = dateOfBirth.split("-");

    return shortName.toLowerCase(Locale.ROOT) + dob[2] + dob[1];
}
```

This gives password:

```text
Prince Chawla + 1994-08-26 = prin2608
```

For non-individual retail, replace this line:

```java
String dateOfBirth = customer.getDateOfBirth();
```

with your actual incorporation-date field if available, for example:

```java
String dateOfBirth = customer.getDateOfIncorporation();
```

Only do that if `CustomerPdf` has that field.

---

# Final Exact Change Checklist

## `AccountStatementDownloadServiceImpl`

Add:

```text
1. EventType import
2. PAYMENT0870 static import
3. enrichRetailEmailRequest(...)
4. call enrichRetailEmailRequest(...) after checkUserValidAccounts(...)
5. make getEmailId(...) null-safe
```

---

## `ErrorExceptionCodes`

Add:

```text
PAYMENT0870
```

---

## `PeriodicStatementAsyncClass`

Add/change:

```text
1. replace old filter block with applyFiltersIfRequired(...)
2. add applyFiltersIfRequired(...)
3. support "XLS" and "XLSX"
4. implement retail notification branch
5. make request.data() null-safe
6. split merchant PDF email data and retail PDF email data
```

---

## `NotificationRequest`

Add:

```text
getRetailAccountStatementNotification(...)
```

Do not touch existing merchant method.

---

## `DownloadPdfForAccountStatement`

Add/change:

```text
1. AppIdentifier import
2. Locale import
3. change encryption branch to RETAIL vs merchant
4. add encryptRetailPdf(...)
5. keep existing encryptPdf(...) untouched
```

---

# Most Important Point

The current retail email journey will fail here:

```java
NotificationRequest request = null;

if (AppIdentifier.RETAIL.equals(appIdentifier)) {
    // request is never assigned
}

Map<String, Object> mapData = new HashMap<>(request.data());
```

So the first fix you should do is in:

```text
PeriodicStatementAsyncClass -> private sendEmail(...)
```

Then do retail email ID and password changes.
