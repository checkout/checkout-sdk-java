package com.checkout;

import com.google.gson.FieldNamingPolicy;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to create form URL encoded content from request objects
 */
public final class FormUrlEncodedHelper {

    private FormUrlEncodedHelper() {
        // Utility class
    }

    /**
     * Creates form URL encoded content from a request object using reflection
     * Converts field names to snake_case and handles boolean values properly
     * 
     * @param request The request object to convert
     * @return UrlEncodedFormEntity containing the form data
     * @throws UnsupportedEncodingException if encoding fails
     */
    public static UrlEncodedFormEntity createFormUrlEncodedContent(Object request) throws UnsupportedEncodingException {
        if (request == null) {
            throw new IllegalArgumentException("Request object cannot be null");
        }

        List<NameValuePair> data = new ArrayList<>();
        
        // Get all fields from the class (including inherited fields if needed)
        Field[] fields = request.getClass().getDeclaredFields();
        
        for (Field field : fields) {
            try {
                // Make the field accessible
                field.setAccessible(true);
                
                // Get the field value
                Object value = field.get(request);
                
                if (value != null) {
                    // Convert field name to snake_case
                    String fieldName = toSnakeCase(field.getName());
                    
                    // Convert value to string, handling booleans specially
                    String stringValue;
                    if (value instanceof Boolean) {
                        stringValue = ((Boolean) value).toString().toLowerCase();
                    } else {
                        stringValue = value.toString();
                    }
                    
                    data.add(new BasicNameValuePair(fieldName, stringValue));
                }
                
            } catch (IllegalAccessException e) {
                // Skip fields that cannot be accessed
                continue;
            }
        }
        
        return new UrlEncodedFormEntity(data);
    }
    
    /**
     * Alternative method that uses Gson's FieldNamingPolicy for consistent naming
     */
    public static UrlEncodedFormEntity createFormUrlEncodedContentWithGson(Object request) throws UnsupportedEncodingException {
        if (request == null) {
            throw new IllegalArgumentException("Request object cannot be null");
        }

        List<NameValuePair> data = new ArrayList<>();
        Field[] fields = request.getClass().getDeclaredFields();
        
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(request);
                
                if (value != null) {
                    // Use Gson's naming policy for consistency with the rest of the SDK
                    String fieldName = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field);
                    
                    String stringValue;
                    if (value instanceof Boolean) {
                        stringValue = ((Boolean) value).toString().toLowerCase();
                    } else {
                        stringValue = value.toString();
                    }
                    
                    data.add(new BasicNameValuePair(fieldName, stringValue));
                }
                
            } catch (IllegalAccessException e) {
                continue;
            }
        }
        
        return new UrlEncodedFormEntity(data);
    }
    
    /**
     * Converts camelCase to snake_case
     */
    private static String toSnakeCase(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}