package br.com.pool.vouchers.api.core.usecase.voucher.create;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;
import br.com.pool.vouchers.api.core.usecase.utils.ValidateFieldsVoucherUtils;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateVoucherCommand(String id, String name, String nameVoucher,
                                   String email, BigDecimal fixedPercentageDiscount,
                                   Instant expirationDate, String specialOffer) {

    public static CreateVoucherCommand with(String id, String name, String nameVoucher,
                                            String email, BigDecimal fixedPercentageDiscount,
                                            Instant expirationDate, String specialOffer) {

        ValidateFieldsVoucherUtils.from(Voucher.newVoucher(id, name, nameVoucher, email, fixedPercentageDiscount, expirationDate, specialOffer));

        return new CreateVoucherCommand(id, name, nameVoucher, email, fixedPercentageDiscount, expirationDate, specialOffer);
    }

}
