package com.danisanga.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class DispenserUsageModel {

    private long id;
    private UUID dispenserId;
    private Date openedAt;
    private Date closedAt;
    private Double totalSpent;
}
