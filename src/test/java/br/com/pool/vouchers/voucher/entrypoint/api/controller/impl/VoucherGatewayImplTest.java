package br.com.pool.vouchers.voucher.entrypoint.api.controller.impl;

import br.com.pool.vouchers.api.core.usecase.voucher.create.CreateVoucherOutput;
import br.com.pool.vouchers.api.core.usecase.voucher.create.CreateVoucherUseCase;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get.GetVoucherEmailUseCase;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get.VoucherEmailCommand;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get.VoucherEmailOutput;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.list.ListSpecialOfferUseCase;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.list.VoucherSpecialOfferOutput;
import br.com.pool.vouchers.api.core.usecase.voucher.update.UpdateVoucherInactiveUseCase;
import br.com.pool.vouchers.api.core.usecase.voucher.update.VoucherInactiveCommand;
import br.com.pool.vouchers.api.core.usecase.voucher.update.VoucherInactiveOutput;
import br.com.pool.vouchers.api.entrypoint.api.controller.impl.VoucherControllerImpl;
import br.com.pool.vouchers.api.entrypoint.api.model.input.VoucherCreateInput;
import br.com.pool.vouchers.voucher.utils.RecordFactory;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherGatewayImplTest {

    private CreateVoucherUseCase createVoucherUseCase;
    private GetVoucherEmailUseCase getVoucherEmailUseCase;
    private ListSpecialOfferUseCase listSpecialOfferUseCase;
    private UpdateVoucherInactiveUseCase updateVoucherInactiveUseCase;
    private VoucherControllerImpl voucherController;

    private final EasyRandom easyRandom = new EasyRandom(new EasyRandomParameters().objectFactory(new RecordFactory()));

    @BeforeEach
    public void setUp() {
        createVoucherUseCase = mock(CreateVoucherUseCase.class);
        getVoucherEmailUseCase = mock(GetVoucherEmailUseCase.class);
        listSpecialOfferUseCase = mock(ListSpecialOfferUseCase.class);
        updateVoucherInactiveUseCase = mock(UpdateVoucherInactiveUseCase.class);
        voucherController = new VoucherControllerImpl(createVoucherUseCase, getVoucherEmailUseCase, listSpecialOfferUseCase, updateVoucherInactiveUseCase);
    }

    @Test
    public void testCreateVoucherController() {
        final var voucherCreateInput = easyRandom.nextObject(VoucherCreateInput.class);
        final var createVoucherOutput = easyRandom.nextObject(CreateVoucherOutput.class);
        when(createVoucherUseCase.execute(any())).thenReturn(createVoucherOutput);
        ResponseEntity<?> response = voucherController.createVoucher(voucherCreateInput);
        verify(createVoucherUseCase, only()).execute(any());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testRedeemVoucherController() {
        final var voucherInactiveCommand = easyRandom.nextObject(VoucherInactiveCommand.class);
        final var voucherInactiveOutput = easyRandom.nextObject(VoucherInactiveOutput.class);
        when(updateVoucherInactiveUseCase.execute(any())).thenReturn(voucherInactiveOutput);
        ResponseEntity<?> response = voucherController.redeemVoucher(voucherInactiveCommand.codeVoucher(), voucherInactiveCommand.email());
        verify(updateVoucherInactiveUseCase, only()).execute(any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetVoucherCodeEmailActiveController() {
        final var voucherEmailCommand = easyRandom.nextObject(VoucherEmailCommand.class);
        final var voucherEmailOutput = easyRandom.nextObject(VoucherEmailOutput.class);
        when(getVoucherEmailUseCase.execute(any())).thenReturn(voucherEmailOutput);
        ResponseEntity<?> response = voucherController.getVoucherCodeEmailActive(voucherEmailCommand.codeVoucher(), voucherEmailCommand.email());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllSpecialOfferController() {
        final var VoucherSpecialOfferOne = easyRandom.nextObject(VoucherSpecialOfferOutput.class);
        final var VoucherSpecialOfferTwo = easyRandom.nextObject(VoucherSpecialOfferOutput.class);
        when(listSpecialOfferUseCase.execute(any())).thenReturn(List.of(VoucherSpecialOfferOne, VoucherSpecialOfferTwo));
        final var output = voucherController.getAllSpecialOffers(anyString()).getBody();
        verify(listSpecialOfferUseCase, only()).execute(any());
        assertEquals(2, output.size());

    }


}
