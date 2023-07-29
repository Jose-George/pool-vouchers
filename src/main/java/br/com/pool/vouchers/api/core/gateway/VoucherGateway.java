package br.com.pool.vouchers.api.core.gateway;

import br.com.pool.vouchers.api.core.domain.dto.DiscountDTO;
import br.com.pool.vouchers.api.core.domain.dto.SpecialOffer;
import br.com.pool.vouchers.api.core.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherGateway {

    Voucher create(Voucher voucher);

    Optional<DiscountDTO> validateVoucher(String voucher, String email);

    Optional<List<SpecialOffer>> findAllSpecialOffer(String email);

    Optional<Voucher> redeemVoucher(String codeVoucher, String email);

}
