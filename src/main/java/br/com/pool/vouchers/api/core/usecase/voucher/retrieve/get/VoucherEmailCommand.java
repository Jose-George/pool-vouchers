package br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;

public record VoucherEmailCommand(String codeVoucher, String email) {
    public static VoucherEmailCommand with(String codeVoucher, String email) {
        return new VoucherEmailCommand(codeVoucher, email);
    }
}
