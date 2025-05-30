package com.arem.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;

import com.arem.api.providers.AuthProvider;
import com.arem.api.security.AuthResponse;
import com.arem.api.security.JwtTokenUtil;
import com.arem.core.auth.AuthRequest;
import com.arem.core.auth.SellerDetails;
import com.arem.productInput.contracts.SellerContract;

@RestController
@CrossOrigin
public class AuthenticationController
{


	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	
	@Autowired
	private AuthProvider authProvider;
	

	@PostMapping("/auth")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception
	{
		authenticate(authRequest.getUserName(), authRequest.getPassword());
		
		final SellerDetails sellerDetails = authProvider.loadUserByUsername(authRequest.getUserName());
		
		final String token = jwtTokenUtil.generateToken(sellerDetails);			
		return ResponseEntity.ok(new AuthResponse(token, new SellerContract(sellerDetails.getSeller())));
	}
	
	
	private void authenticate(String username, String password) throws Exception
	{
		try 
		{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} 
		catch (DisabledException e)
		{
			throw new Exception("USER_DISABLED", e);
		}
		catch (BadCredentialsException e)
		{
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
