package com.not.that.company.domain;

import java.util.List;

public interface AdRepository {
    List<Ad> findAllAds();

    void save(Ad ad);

    List<Ad> findRelevantAds();

    List<Ad> findIrrelevantAds();
}
