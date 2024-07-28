package com.masjid.crm.config.security;

import com.masjid.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AppRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserService us;

	@Autowired
	private JwtTokenUtil jtu;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String un = null, jt = null;
		if (request.getHeader("Authorization") != null) {
			jt = request.getHeader("Authorization").substring(7);
			try {
				un = jtu.getUsernameFromToken(jt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (un != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails ud = this.us.loadUserByUsername(un);
			if (jtu.validateToken(jt, ud)) {
				UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(ud, null,
						ud.getAuthorities());
				upa.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(upa);
			}
		}
		chain.doFilter(request, response);
	}

}
