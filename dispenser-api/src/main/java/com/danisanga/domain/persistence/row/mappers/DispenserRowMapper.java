package com.danisanga.domain.persistence.row.mappers;

import com.danisanga.domain.models.DispenserModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DispenserRowMapper implements RowMapper<DispenserModel> {
    @Override
    public DispenserModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        DispenserModel dispenserModel = new DispenserModel();
        dispenserModel.setId(UUID.fromString(rs.getString("id")));
        dispenserModel.setFlowVolume(rs.getDouble("flow_volume"));
        return dispenserModel;
    }
}
