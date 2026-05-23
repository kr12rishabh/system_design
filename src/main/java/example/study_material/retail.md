## Summary

Yes bro, if the same images are also required for **RETAIL**, then **do not add this condition**:

```java
if (!AppIdentifier.RETAIL.equals(request.getAppIdentifier())) {
```

Keep the image-setting code as it is.
Only add a small comment for future, not a method extraction.

Now these are the **exact final changes** you need.

---

# Final Classes Where You Need Changes

You need changes in these classes only:

```text
1. AccountStatementDownloadServiceImpl
2. ErrorExceptionCodes
3. PeriodicStatementAsyncClass
4. NotificationRequest
5. DownloadPdfForAccountStatement
```

No controller change.

---

# 1. `AccountStatementDownloadServiceImpl`

## 1.1 Add Import

Add this import:

```java
import com.au.app.payments.domain.enums.EventType;
```

Add static import:

```java
import static com.au.app.payments.domain.enums.ErrorExceptionCodes.PAYMENT0870;
```

---

## 1.2 Add This Method In Same Class

Add this method near `getEmailId(...)`.

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

This is for retail email only.

Merchant is untouched.

---

## 1.3 Call This Method In `downloadAccountStatement(...)`

Find this:

```java
setElasticRequestLabel(BusinessJourney.DOWNLOAD_ACCOUNT_STATEMENT);
JsonUtil.checkUserValidAccounts(request.getAccountStatementRequestDetail().getAccountNumber(), userProfile);

List<PeriodicStatementAsync> periodicStatementAsyncData =
```

Change to:

```java
setElasticRequestLabel(BusinessJourney.DOWNLOAD_ACCOUNT_STATEMENT);
JsonUtil.checkUserValidAccounts(request.getAccountStatementRequestDetail().getAccountNumber(), userProfile);

enrichRetailEmailRequest(request, userProfile);

List<PeriodicStatementAsync> periodicStatementAsyncData =
```

---

## 1.4 Make `getEmailId(...)` Null Safe

Replace existing method:

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

---

# 2. `ErrorExceptionCodes`

Add this code near other `PAYMENT08xx` codes:

```java
PAYMENT0870("PAYMENT0870", "No verified email ID is registered with this account", BAD_REQUEST.value()),
```

Example:

```java
PAYMENT0868("PAYMENT0868", "Payee group not present or not exist with current user", INTERNAL_SERVER_ERROR.value()),
PAYMENT0869("PAYMENT0869", "Payee group to mapping not present or not exist with current user", INTERNAL_SERVER_ERROR.value()),
PAYMENT0870("PAYMENT0870", "No verified email ID is registered with this account", BAD_REQUEST.value()),
```

Frontend can use this error code to show the “Add Email” screen.

---

# 3. `PeriodicStatementAsyncClass`

## 3.1 Change Filter Logic In `getAccountStatementString(...)`

Find this existing block:

```java
if (Boolean.FALSE.equals(request.getSendEmail())) {
    if (AppIdentifier.RETAIL.equals(request.getAppIdentifier())) {
        retailFilterStatement(request, transactionStatements);
    } else {
        filterStatement(request, transactionStatements);
    }
}
```

Replace with this:

```java
if (Boolean.FALSE.equals(request.getSendEmail())
        || AppIdentifier.RETAIL.equals(request.getAppIdentifier())) {
    if (AppIdentifier.RETAIL.equals(request.getAppIdentifier())) {
        retailFilterStatement(request, transactionStatements);
    } else {
        filterStatement(request, transactionStatements);
    }
}
```

This is important because currently retail email skips filters.

After this:

```text
Retail download -> filters applied
Retail email -> filters applied
Merchant download -> same as before
Merchant email -> same as before
```

---

## 3.2 Support XLS / XLSX

Inside same method, find:

```java
case "EXCEL" -> {
```

Change to:

```java
case "EXCEL", "XLS", "XLSX" -> {
```

Full block:

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

## 3.3 Fix Retail Email Notification Null Issue

Find this method:

```java
private void sendEmail(String startDate, String endDate, String emailAddress, String fileName, String fileData,
                       AppIdentifier appIdentifier, PdfDownloadAccountStatementReq pdfDownloadAccountStatementReq)
```

Inside it, replace this:

```java
NotificationRequest request = null;
//                = NotificationRequest.getNotificationRequestForAccountStatement(emailAddress,
//                fileName, CONTENT, fileData, appIdentifier, startDate, endDate);

if (AppIdentifier.RETAIL.equals(appIdentifier)) {
//            request = NotificationRequest.gerRetailAccountStatementNotification()
} else {
    request = NotificationRequest.getNotificationRequestForAccountStatement(emailAddress,
            fileName, CONTENT, fileData, appIdentifier, startDate, endDate);
}
```

With this:

```java
NotificationRequest request = null;
//                = NotificationRequest.getNotificationRequestForAccountStatement(emailAddress,
//                fileName, CONTENT, fileData, appIdentifier, startDate, endDate);

if (AppIdentifier.RETAIL.equals(appIdentifier)) {
    request = NotificationRequest.getRetailAccountStatementNotification(emailAddress,
            fileName, CONTENT, fileData, appIdentifier, startDate, endDate);
} else {
    request = NotificationRequest.getNotificationRequestForAccountStatement(emailAddress,
            fileName, CONTENT, fileData, appIdentifier, startDate, endDate);
}
```

This fixes the current retail NPE.

---

## 3.4 Keep Image Block Same For Retail And Merchant

Since you said these images are also for retail, keep the block mostly same.

Use this final version:

```java
if (request.getFormat().equalsIgnoreCase("PDF") && Boolean.TRUE.equals(request.getSendEmail())) {
    // Currently merchant and retail use the same email assets.
    // If retail needs different assets in future, split these four setters using AppIdentifier.RETAIL.
    pdfDownloadAccountStatementReq.setProtectedSuggestionImgInd("https://aubank-prod-merchant-static.au.bank.in/E_Statement_Mailer_02_147bf56edc.jpg");
    pdfDownloadAccountStatementReq.setProtectedSuggestionImgOth("https://aubank-prod-merchant-static.au.bank.in/E_Statement_Mailer_03_968cc190a0.jpg");
    pdfDownloadAccountStatementReq.setPhoneSign("https://aubank-prod-merchant-static.au.bank.in/E_Statement_Mailer_06_d4d8f583b6.png");
    pdfDownloadAccountStatementReq.setWebSign("https://aubank-prod-merchant-static.au.bank.in/E_Statement_Mailer_08_17e34adde5.png");
    pdfDownloadAccountStatementReq.setAccountNumber(accountNumber.substring(accountNumber.length() - 4));
    request.getEmailAddress().forEach(i -> {
        sendEmail(request.getAccountStatementRequestDetail().getStartDate(), request.getAccountStatementRequestDetail().getEndDate(), i, buildFormattedFileName("Account_Statement", accountNumber, ".pdf"), base64String,
                request.getAppIdentifier(), pdfDownloadAccountStatementReq);
        log.info("Email data for pdf added successfully to notification service");
    });
}
```

So do **not** add this:

```java
if (!AppIdentifier.RETAIL.equals(request.getAppIdentifier())) {
```

Because now both retail and merchant should get the same image fields.

---

# 4. `NotificationRequest`

You need to add this method in `NotificationRequest`.

Place it below existing:

```java
getNotificationRequestForAccountStatement(...)
```

Add:

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

This is the safest version because it reuses existing working payload structure.

If notification team gives a separate retail event type later, only change this line:

```java
.eventType(request.eventType())
```

Example future change:

```java
.eventType(EventType.RETAIL_AS_EMAIL)
```

For now, keep it as above.

---

# 5. `DownloadPdfForAccountStatement`

## 5.1 Add Imports

Add:

```java
import com.au.app.payments.domain.enums.AppIdentifier;
import java.util.Locale;
```

---

## 5.2 Change Encryption Branch

Find this inside `generateStatementPdf(...)`:

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

Replace with:

```java
if (Boolean.TRUE.equals(requestDownloadAccountStatement.getSendEmail())) {
    String password = AppIdentifier.RETAIL.equals(requestDownloadAccountStatement.getAppIdentifier())
            ? encryptRetailPdf(pdfReq)
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

Add this method below existing `encryptPdf(...)`.

```java
private String encryptRetailPdf(PdfDownloadAccountStatementReq pdfDownloadAccountStatementReq) {
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

This gives password like:

```text
Prince Chawla + 1994-08-26 = prin2608
```

---

# Final Minimum Change List

Do these exact changes:

```text
1. AccountStatementDownloadServiceImpl
   - add enrichRetailEmailRequest()
   - call it after checkUserValidAccounts()
   - make getEmailId() null-safe

2. ErrorExceptionCodes
   - add PAYMENT0870

3. PeriodicStatementAsyncClass
   - change filter condition so retail email also applies filters
   - add XLS/XLSX case
   - fix retail NotificationRequest creation
   - keep image block same, only add comment for future

4. NotificationRequest
   - add getRetailAccountStatementNotification()

5. DownloadPdfForAccountStatement
   - choose encryptRetailPdf() for RETAIL
   - add encryptRetailPdf()
```

Do **not** change:

```text
1. Controller
2. Merchant notification method
3. Merchant password method
4. Merchant image URLs
5. Async / 999 transaction logic
6. S3 upload logic
```
