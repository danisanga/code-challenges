package com.danisanga.infrastructure.converters;

import com.danisanga.domain.models.DispenserStatsModel;
import com.danisanga.domain.models.DispenserUsageModel;
import com.danisanga.application.responses.DispenserStatsResponse;
import com.danisanga.application.responses.DispenserUsageResponse;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DispenserStatsConverter extends AbstractConverter<DispenserStatsModel, DispenserStatsResponse> {
    @Override
    protected DispenserStatsResponse convert(final DispenserStatsModel dispenserStatsModel) {
        DispenserStatsResponse dispenserStatsResponse = new DispenserStatsResponse();
        dispenserStatsResponse.setAmount(dispenserStatsModel.getAmount());

        List<DispenserUsageResponse> dispenserUsagesResponse = new ArrayList<>();
        for (final DispenserUsageModel dispenserUsageModel : dispenserStatsModel.getUsages()) {
            DispenserUsageResponse dispenserUsageResponse = new DispenserUsageResponse();
            dispenserUsageResponse.setOpenedAt(dispenserUsageModel.getOpenedAt());
            dispenserUsageResponse.setClosedAt(dispenserUsageModel.getClosedAt());
            dispenserUsageResponse.setTotalSpent(dispenserUsageModel.getTotalSpent());
            dispenserUsagesResponse.add(dispenserUsageResponse);
        }
        dispenserStatsResponse.setUsages(dispenserUsagesResponse);

        return dispenserStatsResponse;
    }
}
