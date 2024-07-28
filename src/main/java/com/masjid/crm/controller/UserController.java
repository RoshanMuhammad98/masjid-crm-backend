package com.masjid.crm.controller;

import com.masjid.crm.dto.request.UserRequest;
import com.masjid.crm.dto.response.LoginResponse;
import com.masjid.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpCookie;


/**
 * This class handles routing of user related updates
 *
 * @author Roshan Muhammad
 * @version 1.0
 * @since 07-07-2024
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception {
        try {
            LoginResponse loginResponse = userService.authenticate(userRequest);

            userService.getClientIpAddr(request,loginResponse.getId());

            HttpHeaders headers = new HttpHeaders();
            HttpCookie cookie = new HttpCookie("SESSIONID", loginResponse.getToken());
            cookie.setPath("/");
            headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

            return new ResponseEntity<>(loginResponse, headers, HttpStatus.OK);
        } catch (InternalAuthenticationServiceException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}
