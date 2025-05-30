package com.arem.productInput.roles;

import com.arem.core.model.Seller;

public class UserRole 
{

	public static Boolean CanUpdateSeller(Seller seller, Seller sellerToUpdate)
	{
		if (seller.getIsAdmin())
		{
			return true;
		}
		else
		{
			return seller.getId() == sellerToUpdate.getId();
		}
	}
	
	public static Boolean CanReadSeller(Seller seller, Seller sellerToRead)
	{
		if (seller.getIsAdmin())
		{
			return true;
		}
		else
		{
			return seller.getId() == sellerToRead.getId();
		}
	}
	
	public static Boolean CanReadSellers(Seller seller)
	{
		if (seller.getIsAdmin())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static Boolean CanReadProviders(Seller seller)
	{
		if (seller.getIsAdmin())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static Boolean CanUpdateProviders(Seller seller)
	{
		if (seller.getIsAdmin())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static Boolean CanUpdateProducts(Seller seller)
	{
		if (seller.getIsAdmin())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static Boolean CanUpdatePrices(Seller seller)
	{
		if (seller.getIsAdmin())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
