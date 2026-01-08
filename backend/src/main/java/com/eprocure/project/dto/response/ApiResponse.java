package com.eprocure.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Generic API response wrapper.
 *
 * @param <T> the type of data in the response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private int status;
    private String message;
    private T data;
    private String requestId;
    private long timestamp;

    /**
     * Creates a successful response with data.
     *
     * @param data the response data
     * @param <T> the type of data
     * @return success response
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(200)
                .message("Success")
                .data(data)
                .requestId(UUID.randomUUID().toString())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * Creates a successful response with custom message.
     *
     * @param data the response data
     * @param message custom success message
     * @param <T> the type of data
     * @return success response
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .status(200)
                .message(message)
                .data(data)
                .requestId(UUID.randomUUID().toString())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * Creates an error response.
     *
     * @param status HTTP status code
     * @param message error message
     * @param <T> the type of data
     * @return error response
     */
    public static <T> ApiResponse<T> error(int status, String message) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .data(null)
                .requestId(UUID.randomUUID().toString())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * Creates a void success response (for delete operations).
     *
     * @return success response with no data
     */
    public static ApiResponse<Void> successVoid() {
        return ApiResponse.<Void>builder()
                .status(200)
                .message("Success")
                .data(null)
                .requestId(UUID.randomUUID().toString())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * Creates a void success response with custom message.
     *
     * @param message custom success message
     * @return success response with no data
     */
    public static ApiResponse<Void> successVoid(String message) {
        return ApiResponse.<Void>builder()
                .status(200)
                .message(message)
                .data(null)
                .requestId(UUID.randomUUID().toString())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
