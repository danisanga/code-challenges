package com.danisanga.domain.persistence.repositories;

import com.danisanga.domain.models.DispenserModel;
import com.danisanga.domain.models.DispenserUsageModel;

import java.util.List;
import java.util.UUID;

public interface DispenserRepository {

    /**
     * Get dispenser data if exists.
     *
     * @param dispenserId   dispenser UUID
     * @return  dispenser data.
     */
    DispenserModel getDispenser(final UUID dispenserId);

    /**
     * Creates a new dispenser and store it into the DB.
     *
     * @param flowVolume    flow volume of the dispenser
     * @return  created dispenser UUID.
     */
    UUID createDispenser(final Double flowVolume);

    /**
     * Updates the status for a specific dispenser.
     *
     * @param dispenserId   dispenser UUID
     * @param status        dispenser status
     */
    void updateDispenser(final UUID dispenserId, final String status);

    List<DispenserUsageModel> getDispenserUsages(final UUID dispenserId);

    /**
     * Checks if a dispenser is open.
     *
     * @param dispenserId   dispenser UUID
     * @return  true if dispenser is open, false otherwise.
     */
    boolean isDispenserOpen(final UUID dispenserId);

    /**
     * Checks if a dispenser is close.
     *
     * @param dispenserId   dispenser UUID
     * @return  true id dispenser is close, false otherwise.
     */
    boolean isDispenserClosed(final UUID dispenserId);
}
