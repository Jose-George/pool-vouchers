package br.com.pool.vouchers.api.core.usecase.voucher.create;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateVoucherOutput(String codeVoucher) {

    public static CreateVoucherOutput from(final Voucher voucher) {
        return new CreateVoucherOutput(voucher.getCodeVoucher());
    }
}
