package br.com.pool.vouchers.api.core.usecase.voucher.retrieve.list;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;

import java.math.BigDecimal;
import java.time.Instant;

public record VoucherSpecialOfferOutput(String codeVoucher, BigDecimal percentageDiscount,
                                        Instant expirationDate, String SpecialOffer) {
    public static VoucherSpecialOfferOutput from(Voucher voucher) {
        return new VoucherSpecialOfferOutput(voucher.getCodeVoucher(), voucher.getFixedPercentageDiscount(), voucher.getExpirationDate(), voucher.getSpecialOffer());
    }
}
