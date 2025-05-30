package com.arem.api.security;

import java.io.Serializable;

import com.arem.productInput.contracts.SellerContract;

public class AuthResponse implements Serializable
{
	
	private static final long serialVersionUID = -8091879091924046844L;
	
	private final String jwttoken;
	
	private SellerContract seller;
	
	
	public AuthResponse(String jwttoken, SellerContract sellerContract)
	{
		this.jwttoken = jwttoken;
		this.seller = sellerContract;
	}
	
	
	public String getToken() 
	{
		return this.jwttoken;
	}
	
	public SellerContract getSeller() 
	{
		return this.seller;
	}
	
}