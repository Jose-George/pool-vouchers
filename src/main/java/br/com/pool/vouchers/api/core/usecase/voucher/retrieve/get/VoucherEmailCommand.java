package br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;

public record VoucherEmailCommand(String codeVoucher, String email) {
    public static VoucherEmailCommand with(Voucher voucher) {
        return new VoucherEmailCommand(voucher.getCodeVoucher(), voucher.getEmail());
    }
}
