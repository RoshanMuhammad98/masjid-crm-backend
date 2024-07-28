package com.masjid.crm.service;

import com.masjid.crm.config.security.JwtTokenUtil;
import com.masjid.crm.dto.request.UserRequest;
import com.masjid.crm.dto.response.LoginResponse;
import com.masjid.crm.entity.LoginDetails;
import com.masjid.crm.entity.Permission;
import com.masjid.crm.entity.User;
import com.masjid.crm.repository.LoginDetailsRepository;
import com.masjid.crm.repository.RoleRepository;
import com.masjid.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpCookie;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jtu;

    @Autowired
    private LoginDetailsRepository loginDetailsRepository;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optional = userRepository.findByUsername(username);
        if (optional.isPresent()) {
            User user = optional.get();
            if (user.getActive()) {
                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
            }
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return null;
    }

    public void getClientIpAddr(final HttpServletRequest request, Long id) {
        final String unknown = "unknown";
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0
                || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if (ipAddress != null) {
            LoginDetails loginDetails = new LoginDetails();
            loginDetails.setIpAddress(ipAddress);
            loginDetails.setUserId(id);
            loginDetailsRepository.save(loginDetails);
        }
    }

    public LoginResponse authenticate(UserRequest userRequest) {

        UserDetails userDetails = null;
        Optional<User> userOptional = null;

        if (userRequest.getPhoneNumber() != null) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getPhoneNumber(),
                    userRequest.getPassword()));
            userDetails = loadUserByUsername(userRequest.getPhoneNumber());
            userOptional = userRepository.findByUsername(userRequest.getPhoneNumber());
        } else {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(),
                    userRequest.getPassword()));
            userDetails = loadUserByUsername(userRequest.getEmail());
            userOptional = userRepository.findByUsername(userRequest.getEmail());
        }
        User user = userOptional.get();
        HttpHeaders headers = new HttpHeaders();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jtu.generateToken(userDetails.getUsername()));
        loginResponse.setUsername(userDetails.getUsername());
        loginResponse.setName(user.getName());
        loginResponse.setId(user.getId());
        loginResponse.setCode(user.getCode());
        loginResponse.setRole(user.getRole().getName());
        loginResponse.setKareflowUrl(user.getRole().getKareflowUrl());

        Set<Permission> permissionSet = user.getRole().getPermissions();
        List<String> permissions = new ArrayList<>();
        for (Permission permission : permissionSet) {
            permissions.add(permission.getPermission());
        }
        loginResponse.setPermissions(permissions);

        HttpCookie cookie = new HttpCookie("SESSIONID", loginResponse.getToken());
        cookie.setPath("/");
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return loginResponse;
    }



}
