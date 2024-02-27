package com.danisanga.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DispenserModel {

    private UUID id;
    private Double flowVolume;
}
