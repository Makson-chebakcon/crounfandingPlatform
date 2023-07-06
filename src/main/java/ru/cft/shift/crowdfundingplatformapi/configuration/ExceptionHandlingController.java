package ru.cft.shift.crowdfundingplatformapi.configuration;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.cft.shift.crowdfundingplatformapi.dto.ApiError;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(HttpServletRequest request,
                                                    Exception exception
    ) {
        logError(request, exception);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiError error = new ApiError(
                httpStatus.value(),
                "Непредвиденная внутренняя ошибка сервера"
        );

        return new ResponseEntity<>(error, httpStatus);
    }

    private void logError(HttpServletRequest request, Exception exception) {
        log.error("Возникла ошибка при запросе: {} {}.",
                request.getMethod(),
                request.getRequestURL(),
                exception
        );
    }

}
