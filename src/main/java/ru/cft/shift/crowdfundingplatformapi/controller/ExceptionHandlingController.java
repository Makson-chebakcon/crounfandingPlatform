package ru.cft.shift.crowdfundingplatformapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.cft.shift.crowdfundingplatformapi.dto.api.ApiError;
import ru.cft.shift.crowdfundingplatformapi.exception.BadRequestException;
import ru.cft.shift.crowdfundingplatformapi.exception.ConflictException;
import ru.cft.shift.crowdfundingplatformapi.exception.NotFoundException;
import ru.cft.shift.crowdfundingplatformapi.exception.UnauthorizedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInvalidRequestBody(HttpServletRequest request,
                                                             MethodArgumentNotValidException exception
    ) {
        logError(request, exception);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Map<String, List<String>> messages = new HashMap<>();

        exception
                .getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();

                    if (message != null) {
                        if (messages.containsKey(fieldName)) {
                            messages.get(fieldName).add(message);
                        } else {
                            List<String> newMessageList = new ArrayList<>();
                            newMessageList.add(message);

                            messages.put(fieldName, newMessageList);
                        }
                    }

                });

        ApiError error = new ApiError(
                httpStatus.value(),
                request.getRequestURI(),
                request.getMethod(),
                "Тело запроса не прошло валидацию",
                messages
        );

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(HttpServletRequest request,
                                                              BadRequestException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        logError(request, exception);
        ApiError error = buildApiError(request, httpStatus, exception.getMessage());

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorizedException(HttpServletRequest request,
                                                                UnauthorizedException exception) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        logError(request, exception);
        ApiError error = buildApiError(request, httpStatus, exception.getMessage());

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(HttpServletRequest request,
                                                            NotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        logError(request, exception);
        ApiError error = buildApiError(request, httpStatus, exception.getMessage());

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflictException(HttpServletRequest request,
                                                            ConflictException exception) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        logError(request, exception);
        ApiError error = buildApiError(request, httpStatus, exception.getMessage());

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(HttpServletRequest request,
                                                    Exception exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        logError(request, exception);
        ApiError error = buildApiError(request, httpStatus, "Непредвиденная внутренняя ошибка сервера");

        return new ResponseEntity<>(error, httpStatus);
    }

    private ApiError buildApiError(HttpServletRequest request,
                                   HttpStatus status,
                                   String message) {
        return new ApiError(
                status.value(),
                request.getRequestURI(),
                request.getMethod(),
                message
        );
    }

    private void logError(HttpServletRequest request, Exception exception) {
        log.error("Возникла ошибка при запросе: {} {}.",
                request.getMethod(),
                request.getRequestURL(),
                exception
        );
    }

}
