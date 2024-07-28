package com.masjid.crm.controller;

import com.masjid.crm.dto.request.FamilyDetailRequest;
import com.masjid.crm.dto.response.FamilyDetailListResponse;
import com.masjid.crm.dto.response.FamilyDetailResponse;
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
    public void saveFamilyDetails(@RequestBody @Valid FamilyDetailRequest request) {
        familyService.saveFamilyDetails(request);
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

}
