package com.masjid.crm.controller;

import com.masjid.crm.dto.response.DashboardInsightsResponse;
import com.masjid.crm.dto.response.WatchlistResponse;
import com.masjid.crm.model.FamilyStatus;
import com.masjid.crm.model.Gender;
import com.masjid.crm.service.DashboardInsightsService;
import com.masjid.crm.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private DashboardInsightsService dashboardInsightsService;

    @GetMapping("/watchlist")
    public WatchlistResponse getWatchlist(
            @RequestParam(required = false, defaultValue = "30") Integer overdueDays) {
        return watchlistService.getWatchlist(overdueDays);
    }

    @GetMapping("/insights")
    public DashboardInsightsResponse getInsights(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) FamilyStatus familyStatus,
            @RequestParam(required = false) Gender gender) {
        return dashboardInsightsService.getInsights(fromDate, toDate, familyStatus, gender);
    }
}
