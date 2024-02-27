package com.danisanga.infrastructure.repositories;

import com.danisanga.domain.models.DispenserModel;
import com.danisanga.domain.models.DispenserUsageModel;
import com.danisanga.domain.persistence.row.mappers.DispenserRowMapper;
import com.danisanga.domain.persistence.row.mappers.DispenserUsesRowMapper;
import com.danisanga.domain.persistence.repositories.DispenserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class PostgreSQLDispenserRepository implements DispenserRepository {

    private static final Logger LOGGER = LogManager.getLogger(PostgreSQLDispenserRepository.class);

    private static final String CREATE_DISPENSER_QUERY = "INSERT INTO dispensers (id, flow_volume) VALUES (?,?)";
    private static final String CREATE_OPEN_DISPENSER_USE_QUERY = "INSERT INTO uses (dispenser_id, opened_at) VALUES (?,?)";
    private static final String UPDATE_CLOSE_DISPENSER_USE_QUERY = "UPDATE uses SET closed_at = ? WHERE id = ?";
    private static final String SELECT_DISPENSER_BY_ID = "SELECT id, flow_volume FROM dispensers WHERE id = ?";
    private static final String SELECT_OPEN_DISPENSER_USE_QUERY = "SELECT id, dispenser_id, opened_at, closed_at FROM uses WHERE dispenser_id = ?";
    private static final String SELECT_LAST_CREATED_DISPENSER_QUERY =
            "SELECT id, dispenser_id, opened_at, closed_at FROM uses WHERE dispenser_id = ? ORDER BY created_at DESC";
    private static final String SELECT_DISPENSER_STATS = """
            SELECT USES.id, USES.dispenser_id, USES.opened_at, USES.closed_at
            FROM dispensers as DIS INNER JOIN uses as USES ON DIS.id = USES.dispenser_id
            WHERE DIS.id = ?;
            """;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgreSQLDispenserRepository(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispenserModel getDispenser(final UUID dispenserId) throws DataAccessException {
        return jdbcTemplate.queryForObject(SELECT_DISPENSER_BY_ID, new DispenserRowMapper(), dispenserId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID createDispenser(final Double flowVolume) {
        final UUID dispenserId = UUID.randomUUID();
        try {
            jdbcTemplate.update(CREATE_DISPENSER_QUERY, dispenserId, flowVolume);
            return dispenserId;
        } catch (final DataAccessException exception) {
            LOGGER.error("There were an error creating the dispenser into the DB!", exception);
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDispenser(final UUID dispenserId, final String status) {
        try {
            if (status.equals("open")) {
                performOpenChangeStatus(dispenserId);
            }
            if (status.equals("close")) {
                performCloseChangeStatus(dispenserId);
            }
        } catch (final DataAccessException exception) {
            LOGGER.error("There were an error creating dispenser use into the DB!", exception);
        }
    }

    @Override
    public List<DispenserUsageModel> getDispenserUsages(final UUID dispenserId) {
        return jdbcTemplate.query(SELECT_DISPENSER_STATS, new DispenserUsesRowMapper(), dispenserId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDispenserOpen(final UUID dispenserId) {
        final List<DispenserUsageModel> results =
                jdbcTemplate.query(SELECT_OPEN_DISPENSER_USE_QUERY, new DispenserUsesRowMapper(), dispenserId);
        return !CollectionUtils.isEmpty(results) && results.stream().anyMatch(result -> result.getClosedAt() == null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDispenserClosed(final UUID dispenserId) {
        final List<DispenserUsageModel> results =
                jdbcTemplate.query(SELECT_OPEN_DISPENSER_USE_QUERY, new DispenserUsesRowMapper(), dispenserId);
        return !CollectionUtils.isEmpty(results) && results.stream().anyMatch(result -> result.getOpenedAt() != null && result.getClosedAt() == null);
    }

    /**
     * Performs open change dispenser status action.
     *
     * @param dispenserId dispenser UUID
     */
    private void performOpenChangeStatus(final UUID dispenserId) {
        jdbcTemplate.update(CREATE_OPEN_DISPENSER_USE_QUERY, dispenserId, new Date());
    }

    /**
     * Performs close change dispenser status action.
     * First, get the last modified usage for the desired dispenser.
     *
     * @param dispenserId dispenser UUID
     */
    private void performCloseChangeStatus(final UUID dispenserId) {
        final List<DispenserUsageModel> lastCreatedDispenserUsages =
                jdbcTemplate.query(SELECT_LAST_CREATED_DISPENSER_QUERY, new DispenserUsesRowMapper(), dispenserId);
        if (!CollectionUtils.isEmpty(lastCreatedDispenserUsages)) {
            final DispenserUsageModel lastCreatedDispenserUsage = lastCreatedDispenserUsages
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (Objects.nonNull(lastCreatedDispenserUsage)) {
                jdbcTemplate.update(UPDATE_CLOSE_DISPENSER_USE_QUERY, new Date(), lastCreatedDispenserUsage.getId());
            }
        }
    }
}
