package com.arem.productInput.contracts;

import com.arem.core.model.Seller;

public class SellerContract extends UserContract<Seller>
{
		
	public SellerContract(Seller seller)
	{
		super(seller);
	}
	
	public SellerContract()
	{
		super(new Seller());
	}

	public String getPassword()
	{
		return null;
	}

	public void setPassword(String password)
	{
		this.model.setPassword(password);
	}
	
	public Boolean getIsAdmin()
	{
		return this.model.getIsAdmin();
	}
	
	public void setIsAdmin(Boolean isAdmin)
	{
		this.model.setIsAdmin(isAdmin);
	}
	
	public Boolean getIsAccountNonExpired()
	{
		return this.model.getIsAccountNonExpired();
	}

	public void setIsAccountNonExpired(Boolean isAccountNonExpired)
	{
		this.model.setIsAccountNonExpired(isAccountNonExpired);
	}

	public Boolean getIsAccountNonLocked() 
	{
		return this.model.getIsAccountNonLocked();
	}

	public void setIsAccountNonLocked(Boolean isAccountNonLocked)
	{
		this.model.setIsAccountNonLocked(isAccountNonLocked);
	}

	public Boolean getIsCredentialsNonExpired()
	{
		return this.model.getIsCredentialsNonExpired();
	}

	public void setIsCredentialsNonExpired(Boolean isCredentialsNonExpired)
	{
		this.model.setIsCredentialsNonExpired(isCredentialsNonExpired);
	}

	public Boolean getIsEnabled()
	{
		return this.model.getIsEnabled();
	}

	public void setIsEnabled(Boolean isEnabled)
	{
		this.model.setIsEnabled(isEnabled);
	}
	
	public String getEmail()
	{
		return this.model.getEmail();
	}
	
	public void setEmail(String email)
	{
		this.model.setEmail(email);
	}
		
}
