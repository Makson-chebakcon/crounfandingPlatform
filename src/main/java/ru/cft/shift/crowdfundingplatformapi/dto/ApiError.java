package ru.cft.shift.crowdfundingplatformapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss:SSS")
    private LocalDateTime timestamp;

    private int code;

    private String method;

    private String message;

    public ApiError(int code, String message) {
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.message = message;
    }

}
