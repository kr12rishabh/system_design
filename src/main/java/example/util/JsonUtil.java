package example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JsonUtil {

    private JsonUtil() {
        // not called
    }

    public static String getJsonString(Object response) {
        String responseString = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            responseString = objectMapper.writeValueAsString(responseString);

        } catch (JsonProcessingException e) {
            log.error("error while converting json to string" + e);
        }

        return responseString;
    }

    public static boolean isStringEmptyOrNull(String str) {
        return str == null || str.trim().isEmpty();
    }

}
