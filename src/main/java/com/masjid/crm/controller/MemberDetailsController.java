package com.masjid.crm.controller;

import com.masjid.crm.dto.request.MemberDetailRequest;
import com.masjid.crm.dto.response.FamilyDetailResponse;
import com.masjid.crm.dto.response.MemberDetailListResponse;
import com.masjid.crm.service.MemberDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This class handles routing of member related updates
 *
 * @author Roshan Muhammad
 * @version 1.0
 * @since 07-07-2024
 */
@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MemberDetailsController {

    @Autowired
    private MemberDetailsService memberDetailsService;

    /**
     * Save member details.
     *
     * @Request MemberDetailRequest
     * @author Roshan Muhammad
     * @since 07-07-2024
     */
    @PostMapping("/save")
    public void saveMemberDetails(@RequestBody @Valid MemberDetailRequest request) {
        memberDetailsService.saveMemberDetails(request);
    }

    /**
     * filtered member details.
     *
     * @Request MemberDetailRequest
     * @author Roshan Muhammad
     * @since 07-07-2024
     */
    @PostMapping("/filtered")
    public MemberDetailListResponse filteredMemberDetails(@RequestBody @Valid MemberDetailRequest request) {
        return memberDetailsService.filteredMemberDetails(request);
    }

}
