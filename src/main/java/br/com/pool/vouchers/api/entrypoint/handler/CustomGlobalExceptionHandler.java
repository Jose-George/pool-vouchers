package br.com.pool.vouchers.api.entrypoint.handler;

import br.com.pool.vouchers.api.core.exception.VoucherException;
import br.com.pool.vouchers.api.entrypoint.handler.model.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(VoucherException.class)
    public ResponseEntity<ResponseError> handleWishlistException(VoucherException ex) {

        var error = buildExceptionOutput(ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }

    private ResponseError buildExceptionOutput(final String message) {
        return ResponseError.builder()
                .message(message)
                .build();
    }
}
