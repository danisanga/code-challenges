package com.danisanga.application.services.impl;

import com.danisanga.domain.exceptions.DispenserNotFoundException;
import com.danisanga.domain.exceptions.DispenserWebServiceException;
import com.danisanga.domain.models.DispenserStatsModel;
import com.danisanga.domain.models.DispenserUsageModel;
import com.danisanga.domain.persistence.repositories.DispenserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultDispenserServiceTest {

    private static final UUID dispenserId = UUID.randomUUID();
    private static final String STATUS_OPEN = "open";
    private static final String STATUS_CLOSE = "close";

    @InjectMocks
    @Spy
    private DefaultDispenserService testObj;

    @Mock
    private DispenserRepository dispenserRepositoryMock;

    private final DispenserUsageModel dispenserUsageModelStub = new DispenserUsageModel();
    private final DispenserStatsModel dispenserStatsModelStub = new DispenserStatsModel();

    @Test
    void checkIfDispenserExists_shouldThrowException_whenDispenserNotExists() {
        doThrow(DispenserNotFoundException.class).when(dispenserRepositoryMock).getDispenser(dispenserId);

        final Exception expectedException =
                assertThrows(DispenserNotFoundException.class, () -> testObj.checkIfDispenserExists(dispenserId));

        assertThat(expectedException).isNotNull();
    }

    @Test
    void getCalculatedDispenserStats_shouldReturnStats_whenDispenserExists() {
        dispenserStatsModelStub.setUsages(List.of(dispenserUsageModelStub));
        dispenserUsageModelStub.setOpenedAt(new Date());
        dispenserUsageModelStub.setClosedAt(new Date());
        doNothing().when(testObj).checkIfDispenserExists(dispenserId);
        when(dispenserRepositoryMock.getDispenserUsages(dispenserId))
                .thenReturn(List.of(dispenserUsageModelStub));

        final DispenserStatsModel result = testObj.getDispenserStats(dispenserId);

        assertThat(result.getUsages()).isEqualTo(List.of(dispenserUsageModelStub));
    }

    @Test
    void changeDispenserStatus_shouldUpdateStatus_whenEverythingIsValid() {
        doNothing().when(testObj).checkIfDispenserExists(dispenserId);
        when(dispenserRepositoryMock.isDispenserOpen(dispenserId)).thenReturn(true);

        final Exception expectedException =
                assertThrows(DispenserWebServiceException.class, () -> testObj.changeDispenserStatus(dispenserId, STATUS_OPEN));

        assertThat(expectedException).isNotNull();
    }

    @Test
    void changeDispenserStatus_shouldNotCloseDispenser_whenAllDispensersAreClosed() {
        doNothing().when(testObj).checkIfDispenserExists(dispenserId);
        when(dispenserRepositoryMock.isDispenserClosed(dispenserId)).thenReturn(false);

        final Exception expectedException =
                assertThrows(DispenserWebServiceException.class, () -> testObj.changeDispenserStatus(dispenserId, STATUS_CLOSE));

        assertThat(expectedException).isNotNull();
    }

}