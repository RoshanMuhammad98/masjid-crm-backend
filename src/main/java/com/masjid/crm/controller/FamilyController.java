package com.masjid.crm.controller;

import com.masjid.crm.dto.request.FamilyDetailRequest;
import com.masjid.crm.dto.request.SavedFamilyDetailRequest;
import com.masjid.crm.dto.response.FamilyDetailListResponse;
import com.masjid.crm.dto.response.FamilyOverviewResponse;
import com.masjid.crm.entity.FamilyDetail;
import com.masjid.crm.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This class handles routing of family related updates
 *
 * @author Roshan Muhammad
 * @version 1.0
 * @since 07-07-2024
 */
@RestController
@RequestMapping("/family")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    /**
     * Save family details.
     *
     * @Request FamilyDetailRequest
     * @author Roshan Muhammad
     * @since 07-07-2024
     */
    @PostMapping("/save")
    public FamilyDetail saveFamilyDetails(@RequestBody @Valid SavedFamilyDetailRequest request) {
        return familyService.saveFamilyDetails(request);
    }

    /**
     * filtered family details.
     *
     * @Request FamilyDetailRequest
     * @author Roshan Muhammad
     * @since 07-07-2024
     */
    @PostMapping("/filtered")
    public FamilyDetailListResponse filteredFamilyDetails(@RequestBody @Valid FamilyDetailRequest request) {
        return familyService.filteredFamilyDetails(request);
    }

    @GetMapping("/{id}/overview")
    public FamilyOverviewResponse getFamilyOverview(@PathVariable Long id) {
        return familyService.getFamilyOverview(id);
    }

}
