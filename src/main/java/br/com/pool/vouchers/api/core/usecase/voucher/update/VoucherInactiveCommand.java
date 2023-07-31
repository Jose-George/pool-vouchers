package br.com.pool.vouchers.api.core.usecase.voucher.update;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;

public record VoucherInactiveCommand(String codeVoucher, String email) {
    public static VoucherInactiveCommand with(String codeVoucher, String email) {
        return new VoucherInactiveCommand(codeVoucher, email);
    }
}
