package com.luxoft.proj.naceapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler
{
    private static final String LOG_MESSAGE_FORMAT = "ID: [{}] - CODE: [{}] - MESSAGE: [{}]";

    @ExceptionHandler({ Exception.class })
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDto> uncaughtError(final Exception ex)
    {
        return logInfoAndRespond(ex, ErrorCode.SYSTEM_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ NaceRecordNotFoundException.class })
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDto> naceNotFound(final Exception ex)
    {
        return logInfoAndRespond(ex, ErrorCode.NACE_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ NaceRecordNotSavedException.class })
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDto> naceNotSaved(final Exception ex)
    {
        return logInfoAndRespond(ex, ErrorCode.NACE_RECORD_NOT_SAVED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponseDto> logInfoAndRespond(Exception ex, String code, HttpStatus status)
    {
        ErrorResponseDto error = new ErrorResponseDto(code);
        log.info(LOG_MESSAGE_FORMAT, error.getId(), code, ex.getMessage(), ex);

        return ResponseEntity.status(status).body(error);
    }
}
