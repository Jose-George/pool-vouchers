package br.com.pool.vouchers.voucher.usecase.create;

import br.com.pool.vouchers.api.core.gateway.VoucherGateway;
import br.com.pool.vouchers.api.core.usecase.voucher.create.CreateVoucherCommand;
import br.com.pool.vouchers.api.core.usecase.voucher.create.CreateVoucherUseCase;
import br.com.pool.vouchers.api.core.usecase.voucher.create.DefaultCreateCategoryUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class CreateVoucherUseCaseTest {

    // 1. Teste do caminho feliz
    // 2. Teste passando uma propriedade inválida (desconto)
    // 3. Teste simulando um erro generico vindo do gateway

    @Test
    public void givenAValidCommand_whenCallsCreateVoucher_shouldReturnVoucherId() {

        final var expectedName = "Francesco Forgione";
        final var expectedNameVoucher = "Promoção dia dos pais";
        final var expectedEmail = "francescoforgione@email.com";
        final var expectedFixedPercentageDiscount = 20;
        final var expectedId = "1341341";
        final var expectedExpirationDate = Instant.now();

        final var command =
                CreateVoucherCommand.with(expectedId, expectedName, expectedNameVoucher,
                        expectedEmail, BigDecimal.valueOf(expectedFixedPercentageDiscount), expectedExpirationDate);

        final VoucherGateway voucherGateway = Mockito.mock(VoucherGateway.class);

        Mockito.when(voucherGateway.create(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(voucherGateway);

        final var actualOutput = useCase.execute(command);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.voucherId());

        Mockito.verify(voucherGateway, Mockito.times(1))
                .create(Mockito.argThat(voucher ->
                        Objects.equals(expectedName, voucher.getName())
                                && Objects.equals(expectedNameVoucher, voucher.getNameVoucher())
                                && Objects.nonNull(voucher.getExpirationDate())
                                && Objects.equals(expectedEmail, voucher.getEmail())
                                && Objects.nonNull(voucher.getCreatedAt())
                                && Objects.equals(expectedId, voucher.getId())
                ));
    }

}
