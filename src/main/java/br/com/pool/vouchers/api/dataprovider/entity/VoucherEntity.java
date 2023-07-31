package br.com.pool.vouchers.api.dataprovider.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@Document("vouchers")
public class VoucherEntity {

    @Id
    private String id;
    private String name;
    private String nameVoucher;
    private String email;
    private boolean active;
    private BigDecimal fixedPercentageDiscount;

    @Indexed
    private String codeVoucher;
    private Instant expirationDate;
    private Instant createdAt;
    private String specialOffer;
    private Instant useDate;

}
