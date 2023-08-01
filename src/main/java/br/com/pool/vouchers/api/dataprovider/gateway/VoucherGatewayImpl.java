package br.com.pool.vouchers.api.dataprovider.gateway;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;
import br.com.pool.vouchers.api.core.gateway.VoucherGateway;
import br.com.pool.vouchers.api.dataprovider.entity.VoucherEntity;
import br.com.pool.vouchers.api.dataprovider.repository.VoucherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class VoucherGatewayImpl implements VoucherGateway {
    private final VoucherRepository voucherRepository;
    
    public VoucherGatewayImpl(final VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher create(Voucher voucher) {
        log.info("salvando voucher {}" , voucher.getId());
        voucherRepository.save(buildVoucherEntity(voucher));
        return voucher;
    }

    @Override
    public Optional<Voucher> findByVoucherEmail(String voucher, String email) {
        log.info("Recuperando vouchers por e-mail: {}", email);
        var voucherEntiy = voucherRepository.findByCodeVoucherAndEmail(voucher, email);
        return voucherEntiy.map(this::mapToVoucher);
    }

    @Override
    public Optional<List<Voucher>> findAllSpecialOffer(String email) {
        log.info("Recuperando ofertas especiais por e-mail: {}", email);
        Optional<List<VoucherEntity>> voucherEntities = voucherRepository.findByEmail(email);
        return voucherEntities.map(this::mapToVoucherList);
    }

    @Override
    public Optional<Voucher> redeemVoucher(Voucher voucher) {
        log.info("Resgatando voucher: {}", voucher.getEmail());
        var voucherReturn = voucherRepository.save(buildVoucherEntity(voucher));
        return Optional.ofNullable(mapToVoucher(voucherReturn));
    }

    private VoucherEntity buildVoucherEntity(final Voucher voucher) {
        return VoucherEntity.builder()
                .id(voucher.getId())
                .name(voucher.getName())
                .nameVoucher(voucher.getNameVoucher())
                .expirationDate(voucher.getExpirationDate())
                .fixedPercentageDiscount(voucher.getFixedPercentageDiscount())
                .specialOffer(voucher.getSpecialOffer())
                .active(voucher.isActive())
                .useDate(voucher.getUseDate())
                .createdAt(voucher.getCreatedAt())
                .codeVoucher(voucher.getCodeVoucher())
                .email(voucher.getEmail())
                .expirationDate(voucher.getExpirationDate())
                .build();

    }

    private Voucher mapToVoucher(final VoucherEntity voucherEntity) {
        return Voucher.builder()
                .id(voucherEntity.getId())
                .name(voucherEntity.getName())
                .nameVoucher(voucherEntity.getNameVoucher())
                .expirationDate(voucherEntity.getExpirationDate())
                .fixedPercentageDiscount(voucherEntity.getFixedPercentageDiscount())
                .specialOffer(voucherEntity.getSpecialOffer())
                .active(voucherEntity.isActive())
                .useDate(voucherEntity.getUseDate())
                .createdAt(voucherEntity.getCreatedAt())
                .codeVoucher(voucherEntity.getCodeVoucher())
                .email(voucherEntity.getEmail())
                .expirationDate(voucherEntity.getExpirationDate())
                .build();
    }

    private List<Voucher> mapToVoucherList(List<VoucherEntity> voucherEntities) {
        return voucherEntities.stream()
                .map(this::mapToVoucher)
                .toList();
    }
}
