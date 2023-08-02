package br.com.pool.vouchers.api.core.usecase.utils;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;
import br.com.pool.vouchers.api.core.exception.VoucherException;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.Optional;

public class  ValidateFieldsVoucherUtils {

    private final Voucher voucher;

    public ValidateFieldsVoucherUtils(Voucher voucher) {
        this.voucher = voucher;
    }

    public static ValidateFieldsVoucherUtils from(final Voucher voucher) {
        validateNotNull(voucher.getName(), "O campo 'name' do Voucher não pode ser nulo.");
        validateNotNull(voucher.getNameVoucher(), "O campo 'nameVoucher' do Voucher não pode ser nulo.");
        validateNotNull(voucher.getSpecialOffer(), "O campo 'SpecialOffer' do Voucher não pode ser nulo.");
        validateNotNull(voucher.getFixedPercentageDiscount(), "O campo 'fixedPercentageDiscount' do Voucher não pode ser nulo.");
        validateNotNull(voucher.getEmail(), "O campo 'email' do Voucher não pode ser nulo.");

        return new ValidateFieldsVoucherUtils(voucher);
    }

    private static void validateNotNull(Object field, String errorMessage) {
        Optional.ofNullable(field)
                .orElseThrow(() -> new VoucherException(errorMessage, HttpStatus.BAD_REQUEST));
    }

}
