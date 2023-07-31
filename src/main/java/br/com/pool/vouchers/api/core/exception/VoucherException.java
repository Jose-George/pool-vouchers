package br.com.pool.vouchers.api.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class VoucherException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
}
