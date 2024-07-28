package com.masjid.crm.controller;

import com.masjid.crm.dto.request.MembershipDetailRequest;
import com.masjid.crm.dto.response.FamilyDetailResponse;
import com.masjid.crm.dto.response.MembershipDetailListResponse;
import com.masjid.crm.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This class handles routing of membership related updates
 *
 * @author Roshan Muhammad
 * @version 1.0
 * @since 07-07-2024
 */
@RestController
@RequestMapping("/membership")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    /**
     * Save membership details.
     *
     * @Request MembershipDetailRequest
     * @author Roshan Muhammad
     * @since 07-07-2024
     */
    @PostMapping("/save")
    public void saveMembershipDetails(@RequestBody @Valid MembershipDetailRequest request) {
        membershipService.saveMembershipDetails(request);
    }

    /**
     * filtered member details.
     *
     * @Request MembershipDetailRequest
     * @author Roshan Muhammad
     * @since 07-07-2024
     */
    @PostMapping("/filtered")
    public MembershipDetailListResponse filteredMembershipDetails(@RequestBody @Valid MembershipDetailRequest request) {
        return membershipService.filteredMembershipDetails(request);
    }

}
