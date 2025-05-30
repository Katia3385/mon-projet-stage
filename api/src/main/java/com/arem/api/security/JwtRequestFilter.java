package com.arem.api.security;


import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.arem.api.providers.AuthProvider;
import com.arem.core.auth.SellerDetails;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter 
{
	
	
	@Autowired
	private AuthProvider auhtProvider;
	
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	throws ServletException, IOException 
	{
		final String requestTokenHeader = request.getHeader("authorization");
		String userName = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("frf_rege!rgrenuy_ju4784-,frdfdz")) 
		{
			jwtToken = requestTokenHeader.substring(31);
			try
			{
				userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} 
			catch (IllegalArgumentException e)
			{
				System.out.println("Unable to get JWT Token");
			}
			catch (ExpiredJwtException e)
			{
				System.out.println("JWT Token has expired");
			}
		}
		else
		{
			logger.warn("JWT Token does not begin with Bearer String");
		}
				
		// Once we get the token validate it.
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			SellerDetails sellerDetails = this.auhtProvider.loadUserByUsername(userName);
			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, sellerDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						sellerDetails, null, sellerDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		chain.doFilter(request, response);
	}
}