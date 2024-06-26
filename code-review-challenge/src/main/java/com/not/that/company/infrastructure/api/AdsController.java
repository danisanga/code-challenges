package com.not.that.company.infrastructure.api;

import com.not.that.company.application.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdsController {

    @Autowired
    private AdsService adsService;

    @GetMapping("/ads/quality")
    public ResponseEntity<List<QualityAd>> qualityListing() {
        return ResponseEntity.ok(adsService.findQualityAds());
    }

    @GetMapping("/ads/public")
    public ResponseEntity<List<PublicAd>> publicListing() {
        return ResponseEntity.ok(adsService.findPublicAds());
    }

    @GetMapping("/ads/score")
    public ResponseEntity<Void> calculateScore() {
        adsService.calculateScores();
        return ResponseEntity.accepted().build();
    }
}
