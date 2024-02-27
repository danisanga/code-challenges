package com.danisanga.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DispenserStatsModel {

    private Double amount;
    private List<DispenserUsageModel> usages;

}
