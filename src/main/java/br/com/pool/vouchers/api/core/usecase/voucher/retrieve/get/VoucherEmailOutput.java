package br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;

import java.math.BigDecimal;
import java.time.Instant;

public record VoucherEmailOutput(String codeVoucher, BigDecimal percentageDiscount,
                                 Instant expirationDate) {
    public static VoucherEmailOutput from(Voucher voucher) {
        return new VoucherEmailOutput(voucher.getCodeVoucher(), voucher.getFixedPercentageDiscount(), voucher.getExpirationDate());
    }
}
