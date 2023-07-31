package br.com.pool.vouchers.voucher.core.domain;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;
import br.com.pool.vouchers.voucher.utils.RecordFactory;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

public class VoucherTest {

    private final EasyRandom easyRandom = new EasyRandom(new EasyRandomParameters().objectFactory(new RecordFactory()));
    @Test
    public void givenAValidParams_whenCallNewVoucher_thenInstantiateAVoucher() {

        final var expectedName = "Francesco Forgione";
        final var expectedNameVoucher = "Promoção dia dos pais";
        final var expectedEmail = "francescoforgione@email.com";
        final var expectedIsActive = true;
        final var expectedFixedPercentageDiscount = 20;
        final var expectedId = "1341341";
        final var expectedExpirationDate = Instant.now();
        final var specialOffer = "COMPRA DE CADEIRA R123";

        final var actualVoucher =
                Voucher.newVoucher(expectedId, expectedName, expectedNameVoucher, expectedEmail,
                        BigDecimal.valueOf(expectedFixedPercentageDiscount), expectedExpirationDate, specialOffer);

        Assertions.assertNotNull(actualVoucher);
        Assertions.assertNotNull(actualVoucher.getId());
        Assertions.assertNotNull(actualVoucher.getCodeVoucher());
        Assertions.assertNull(actualVoucher.getUseDate());
        Assertions.assertEquals(expectedName, actualVoucher.getName());
        Assertions.assertEquals(expectedNameVoucher, actualVoucher.getNameVoucher());
        Assertions.assertEquals(expectedEmail, actualVoucher.getEmail());
        Assertions.assertEquals(expectedIsActive, actualVoucher.isActive());

    }

    @Test
    public void givenAValidActiveVoucher_whenCallRedeemVoucher_thenInstantiateInativeVoucher() {
        final var voucherExpected = easyRandom.nextObject(Voucher.class);
        final var actualVoucher =
                Voucher.newVoucher(voucherExpected.getId(), voucherExpected.getName(), voucherExpected.getNameVoucher(), voucherExpected.getEmail(),
                        voucherExpected.getFixedPercentageDiscount(), voucherExpected.getExpirationDate(), voucherExpected.getSpecialOffer());

        actualVoucher.redeemVoucher();

        Assertions.assertNotNull(actualVoucher);
        Assertions.assertNotEquals(voucherExpected.isActive(), actualVoucher.isActive());
        Assertions.assertNotNull(actualVoucher.getUseDate());

    }

}
