package br.com.pool.vouchers.api.core.domain.voucher;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
@Builder
public class Voucher {

    private String id;
    private String name;
    private String nameVoucher;
    private String email;
    private boolean active;
    private BigDecimal fixedPercentageDiscount;
    private String codeVoucher;
    private Instant expirationDate;
    private Instant createdAt;

    public Voucher(String id, String name, String nameVoucher, String email, boolean active, BigDecimal fixedPercentageDiscount, String codeVoucher, Instant expirationDate, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.nameVoucher = nameVoucher;
        this.email = email;
        this.active = active;
        this.fixedPercentageDiscount = fixedPercentageDiscount;
        this.codeVoucher = codeVoucher;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
    }

    public static Voucher newVoucher(final String id, final String name, final String nameVoucher,
                                     final String email, final BigDecimal fixedPercentageDiscount,
                                     final Instant expirationDate) {

        final var codeVoucher = generateCodeVoucher();
        final var createdAt = Instant.now();
        final var isActive = true;

        return new Voucher(id, name, nameVoucher, email, isActive, fixedPercentageDiscount, codeVoucher, expirationDate, createdAt);
    }

    private static String generateCodeVoucher() {
        return UUID.randomUUID().toString().replaceAll("\\d{2}", "").substring(0, 8);
    }

}
