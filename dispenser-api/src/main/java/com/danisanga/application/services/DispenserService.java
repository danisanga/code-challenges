package com.danisanga.application.services;

import com.danisanga.domain.models.DispenserStatsModel;

import java.util.UUID;

public interface DispenserService {

    /**
     * Checks if a dispenser exists.
     *
     * @param dispenserId dispenser UUID
     */
    void checkIfDispenserExists(final UUID dispenserId);

    /**
     * Get dispenser stats.
     *
     * @param dispenserId   dispenser UUID
     * @return  dispenser stats.
     */
    DispenserStatsModel getDispenserStats(final UUID dispenserId);

    /**
     * Change dispenser status.
     *
     * @param dispenserId       dispenser UUID
     * @param dispenserStatus   dispenser status (open or close)
     */
    void changeDispenserStatus(final UUID dispenserId, final String dispenserStatus);

    /**
     * Creates a dispenser.
     *
     * @param flowVolume    dispenser flow volume
     * @return  created dispenser UUID.
     */
    UUID createDispenser(final Double flowVolume);
}
