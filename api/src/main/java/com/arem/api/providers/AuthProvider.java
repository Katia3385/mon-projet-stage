package com.arem.api.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.arem.core.auth.SellerDetails;
import com.arem.core.model.Seller;
import com.arem.dataservice.services.ISellerService;

@Component
public class AuthProvider implements UserDetailsService
{
	
	@Autowired
	private ISellerService sellerService;
	
	
	@Override
	public SellerDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Seller seller = sellerService.getSellerByUserName(username);
		if (seller == null)
		{
			return null;
		}
		else
		{
			return new SellerDetails(seller);
		}
	}
	
	public Seller getCurrentUser()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SellerDetails userDetails = (SellerDetails) auth.getPrincipal();
		return userDetails.getSeller();
	}

}
