package br.com.pool.vouchers.api.core.usecase.utils;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;

import java.time.Instant;

public class VoucherExpiredUtils {
    public static boolean isVoucherExpired(Voucher voucher) {
        Instant expirationDate = voucher.getExpirationDate();
        Instant now = Instant.now();
        return expirationDate.isBefore(now);
    }

}
