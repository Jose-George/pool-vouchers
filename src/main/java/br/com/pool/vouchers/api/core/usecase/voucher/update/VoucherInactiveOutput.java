package br.com.pool.vouchers.api.core.usecase.voucher.update;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;

import java.time.Instant;

public record VoucherInactiveOutput(String id, String codeVoucher, Instant useDate) {
    public static VoucherInactiveOutput from(final Voucher voucher) {
        return new VoucherInactiveOutput(voucher.getId(), voucher.getCodeVoucher(), voucher.getUseDate());
    }
}
