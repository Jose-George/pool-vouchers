package br.com.pool.vouchers.api.core.usecase.voucher.retrieve.list;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;

public record VoucherSpecialOfferCommand(String email) {
    public static VoucherSpecialOfferCommand with(String email) {
        return new VoucherSpecialOfferCommand(email);
    }
}
