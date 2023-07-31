package br.com.pool.vouchers.api.core.usecase.voucher.create;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateVoucherCommand(String id, String name, String nameVoucher,
                                   String email, BigDecimal fixedPercentageDiscount,
                                   Instant expirationDate, String specialOffer) {

    public static CreateVoucherCommand with(String id, String name, String nameVoucher,
                                            String email, BigDecimal fixedPercentageDiscount,
                                            Instant expirationDate, String specialOffer) {

        return new CreateVoucherCommand(id, name, nameVoucher, email, fixedPercentageDiscount, expirationDate, specialOffer);
    }

}
