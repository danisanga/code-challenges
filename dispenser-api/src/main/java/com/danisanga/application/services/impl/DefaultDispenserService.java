package com.danisanga.application.services.impl;

import com.danisanga.application.services.DispenserService;
import com.danisanga.domain.exceptions.DispenserNotFoundException;
import com.danisanga.domain.exceptions.DispenserWebServiceException;
import com.danisanga.domain.models.DispenserStatsModel;
import com.danisanga.domain.models.DispenserUsageModel;
import com.danisanga.domain.persistence.repositories.DispenserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class DefaultDispenserService implements DispenserService {

    private static final Logger LOGGER = LogManager.getLogger(DefaultDispenserService.class);

    private final DispenserRepository dispenserRepository;

    public DefaultDispenserService(final DispenserRepository dispenserRepository) {
        this.dispenserRepository = dispenserRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkIfDispenserExists(final UUID dispenserId) {
        try {
            dispenserRepository.getDispenser(dispenserId);
        } catch (final DataAccessException exception) {
            throw new DispenserNotFoundException(String.format("There is not a Dispenser associated with this UUID: %s", dispenserId));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispenserStatsModel getDispenserStats(final UUID dispenserId) {
        checkIfDispenserExists(dispenserId);
        return populateDispenserStats(dispenserId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeDispenserStatus(final UUID dispenserId, final String dispenserStatus) {
        checkIfDispenserExists(dispenserId);

        if ("open".equals(dispenserStatus) && dispenserRepository.isDispenserOpen(dispenserId)) {
            LOGGER.warn(() -> String.format("There is an open dispenser for this UUID: %s ", dispenserId));
            throw new DispenserWebServiceException(
                    String.format("There is an open dispenser for this UUID: %s ", dispenserId));
        }
        if ("close".equals(dispenserStatus) && !dispenserRepository.isDispenserClosed(dispenserId)) {
            LOGGER.warn("You cannot close something which is no opened!");
            throw new DispenserWebServiceException("You cannot close something which is no opened!");
        }

        dispenserRepository.updateDispenser(dispenserId, dispenserStatus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID createDispenser(final Double flowVolume) {
        return dispenserRepository.createDispenser(flowVolume);
    }

    private DispenserStatsModel populateDispenserStats(final UUID dispenserId) {
        return getCalculatedDispenserStats(dispenserId);
    }

    protected DispenserStatsModel getCalculatedDispenserStats(final UUID dispenserId) {
        DispenserStatsModel dispenserStatsModel = new DispenserStatsModel();
        final List<DispenserUsageModel> dispenserUsages = getDispenserUsages(dispenserId);
        dispenserStatsModel.setUsages(dispenserUsages);

        final double totalAmount = dispenserUsages
                .stream()
                .map(DispenserUsageModel::getTotalSpent)
                .mapToDouble(Double::doubleValue)
                .sum();
        dispenserStatsModel.setAmount(totalAmount);
        return dispenserStatsModel;
    }

    private List<DispenserUsageModel> getDispenserUsages(final UUID dispenserId) {
        final List<DispenserUsageModel> dispenserUsages = dispenserRepository.getDispenserUsages(dispenserId);
        for (final DispenserUsageModel dispenserUse : dispenserUsages) {
            final Long timeDifference = getTimeDifference(dispenserUse);
            final long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifference);
            final double amount = seconds * 12.25d;
            dispenserUse.setTotalSpent(amount);
        }
        return dispenserUsages;
    }

    private Long getTimeDifference(final DispenserUsageModel dispenserUsage) {
        final Long timeDifference = Optional.ofNullable(dispenserUsage)
                .filter(usage -> Objects.nonNull(usage.getClosedAt()))
                .map(use -> use.getClosedAt().getTime() - use.getOpenedAt().getTime())
                .orElse(0L);

        final Long timeDifferenceForNotClosedTaps = Optional.ofNullable(dispenserUsage)
                .filter(usage -> Objects.isNull(usage.getClosedAt()))
                .map(use -> new Date().getTime() - use.getOpenedAt().getTime())
                .orElse(0L);

        return timeDifference + timeDifferenceForNotClosedTaps;
    }
}
