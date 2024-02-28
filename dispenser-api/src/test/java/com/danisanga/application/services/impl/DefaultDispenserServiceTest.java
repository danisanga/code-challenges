package com.danisanga.application.services.impl;

import com.danisanga.dispensers.infrastructure.services.impl.DefaultDispenserService;
import com.danisanga.dispensers.domain.data.DispenserStatsData;
import com.danisanga.dispensers.domain.data.DispenserUsageData;
import com.danisanga.dispensers.domain.persistence.repositories.DispenserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    private final DispenserUsageData dispenserUsageDataStub = new DispenserUsageData();
    private final DispenserStatsData dispenserStatsDataStub = new DispenserStatsData();

    @Test
    void checkIfDispenserExists_shouldThrowException_whenDispenserNotExists() {

    }

}