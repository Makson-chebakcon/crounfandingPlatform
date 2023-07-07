package ru.cft.shift.crowdfundingplatformapi.dto.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss:SSS")
    private LocalDateTime timestamp;

    private int code;

    private String path;

    private String method;

    private String message;

    private Map<String, List<String>> validationMessages;

    public ApiError(int code, String path, String method, String message) {
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.path = path;
        this.method = method;
        this.message = message;
    }

    public ApiError(int code, String path, String method, String message, Map<String, List<String>> validationMessages) {
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.path = path;
        this.method = method;
        this.message = message;
        this.validationMessages = validationMessages;
    }


}
