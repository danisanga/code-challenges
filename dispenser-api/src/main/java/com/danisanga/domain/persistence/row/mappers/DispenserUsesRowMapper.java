package com.danisanga.domain.persistence.row.mappers;

import com.danisanga.domain.models.DispenserUsageModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DispenserUsesRowMapper implements RowMapper<DispenserUsageModel> {
    @Override
    public DispenserUsageModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        DispenserUsageModel dispenserUsageModel = new DispenserUsageModel();
        dispenserUsageModel.setId(rs.getLong("id"));
        dispenserUsageModel.setDispenserId(UUID.fromString(rs.getString("dispenser_id")));
        dispenserUsageModel.setOpenedAt(rs.getTimestamp("opened_at"));
        dispenserUsageModel.setClosedAt(rs.getTimestamp("closed_at"));
        return dispenserUsageModel;
    }
}
