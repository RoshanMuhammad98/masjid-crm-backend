package com.masjid.crm.controller;

import com.masjid.crm.dto.request.FamilyDetailRequest;
import com.masjid.crm.dto.request.MarriageDetailRequest;
import com.masjid.crm.dto.response.FamilyDetailResponse;
import com.masjid.crm.dto.response.MarriageDetailListResponse;
import com.masjid.crm.service.MarriageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This class handles routing of marriage related updates
 *
 * @author Roshan Muhammad
 * @version 1.0
 * @since 07-07-2024
 */
@RestController
@RequestMapping("/marriage")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MarriageController {

    @Autowired
    private MarriageService marriageService;

    /**
     * Save marriage details.
     *
     * @Request MarriageDetailRequest
     * @author Roshan Muhammad
     * @since 07-07-2024
     */
    @PostMapping("/save")
    public void saveMarriageDetails(@RequestBody @Valid MarriageDetailRequest request) {
        marriageService.saveMarriageDetails(request);
    }

    /**
     * filtered marriage details.
     *
     * @Request MarriageDetailRequest
     * @author Roshan Muhammad
     * @since 07-07-2024
     */
    @PostMapping("/filtered")
    public MarriageDetailListResponse filteredMarriageDetails(@RequestBody @Valid MarriageDetailRequest request) {
        return marriageService.filteredMarriageDetails(request);
    }

}
