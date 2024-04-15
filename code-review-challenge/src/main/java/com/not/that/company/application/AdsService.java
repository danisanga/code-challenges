package com.not.that.company.application;

import com.not.that.company.infrastructure.api.PublicAd;
import com.not.that.company.infrastructure.api.QualityAd;

import java.util.List;

public interface AdsService {

    List<PublicAd> findPublicAds();
    List<QualityAd> findQualityAds();
    void calculateScores();
}
