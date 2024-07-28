package com.masjid.crm.controller;

import com.masjid.crm.dto.request.DeathDetailRequest;
import com.masjid.crm.dto.response.DeathDetailListResponse;
import com.masjid.crm.dto.response.DeathDetailResponse;
import com.masjid.crm.service.DeathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This class handles routing of death related updates
 *
 * @author Roshan Muhammad
 * @version 1.0
 * @since 07-07-2024
 */
@RestController
@RequestMapping("/death")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DeathController {

    @Autowired
    private DeathService deathService;

    /**
     * Save death details.
     *
     * @Request DeathDetailRequest
     * @author Roshan Muhammad
     * @since 07-07-2024
     */
    @PostMapping("/save")
    public void saveDeathDetails(@RequestBody @Valid DeathDetailRequest request) {
        deathService.saveDeathDetails(request);
    }

    /**
     * filtered death details.
     *
     * @Request DeathDetailRequest
     * @author Roshan Muhammad
     * @since 07-07-2024
     */
    @PostMapping("/filtered")
    public DeathDetailListResponse filteredDeathDetails(@RequestBody @Valid DeathDetailRequest request) {
        return deathService.filteredDeathDetails(request);
    }

}
