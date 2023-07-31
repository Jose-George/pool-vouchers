package br.com.pool.vouchers.api.dataprovider.repository;

import br.com.pool.vouchers.api.dataprovider.entity.VoucherEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends MongoRepository<VoucherEntity, String> {

    Optional<VoucherEntity> findByCodeVoucherAndEmail(String code, String email);

    Optional<List<VoucherEntity>> findByEmail(String email);

}
