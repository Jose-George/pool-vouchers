package br.com.pool.vouchers.api.core.gateway;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;
import br.com.pool.vouchers.api.dataprovider.repository.VoucherRepository;

import java.util.List;
import java.util.Optional;

public interface VoucherGateway {

    /**
     * Responsável por criar um voucher
     */
    Voucher create(Voucher voucher);

    /**
     * Responsável por validar que um voucher para um determinado e-mail existe e tá valido (data de expiração vigente)
     */
    Optional<Voucher> findByVoucherEmail(String voucher, String email);

    /**
     * Retorna uma lista de voucher com suas ofertas especiais
     */
    Optional<List<Voucher>> findAllSpecialOffer(String email);

    /**
     *
     * Seta como inativo um voucher, tal como sua hora de utilização
     */
    Optional<Voucher> redeemVoucher(Voucher voucher);


}
