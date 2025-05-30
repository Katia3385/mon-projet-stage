package com.arem.dataservice.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.arem.core.model.Seller;
import com.arem.dataservice.repositories.ISellerRepository;
import com.arem.framework.StringHelper;
import com.arem.framework.runtime.Sellers;


@Component
public class SellerService implements ISellerService
{

	@Autowired
	private ISellerRepository sellerRepository;
	
	@Autowired
	private Sellers cache;
	
	private final Object sync = new Object();

	private void enableCache()
	{
		if (!cache.isEnabled())
		{
			synchronized(sync)
			{
				if (!cache.isEnabled())
				{
					Iterable<Seller> sellers = sellerRepository.findAll();
					cache.putAll(sellers);
				}
			}
		}
	}
	
	@Override
	public List<Seller> getSellers()
	{
		enableCache();
		return cache.findAll();
	}

	@Override
	public Seller getSellerById(long id)
	{
		enableCache();
		return cache.findById(id);
	}
	
	@Override
	public Seller getSellerByUserName(String email)
	{
		enableCache();
		if (StringHelper.isNullOrEmpty(email))
		{
			return null;
		}
		return cache.findOne(s -> s.getEmail().trim().toUpperCase().equals(email.trim().toUpperCase()));
	}
	
	@Override
	public Seller getSellerByPickName(String pickName)
	{
		enableCache();
		if (StringHelper.isNullOrEmpty(pickName))
		{
			return null;
		}
		return cache.findOne(s -> StringHelper.isNotNullOrEmpty(s.getPickName()) && s.getPickName().trim().toUpperCase().equals(pickName.trim().toUpperCase()));
	}
	
	@Override
	public Seller getSellerByFirstNameAndLastNameAndPickName(String firstName, String lastName, String pickName)
	{
		enableCache();
		
		if (StringHelper.isNullOrEmpty(firstName) || StringHelper.isNullOrEmpty(lastName))
		{
			return null;
		}
		
		if (StringHelper.isNullOrEmpty(pickName))
		{
			return cache.findOne(c -> c.getFirstName().trim().toUpperCase().equals(firstName.trim().toUpperCase()) &&
					c.getLastName().trim().toUpperCase().equals(lastName.trim().toUpperCase()) && StringHelper.isNullOrEmpty(c.getPickName()));
		}
		
		else
		{
			return cache.findOne(c -> c.getFirstName().trim().toUpperCase().equals(firstName.trim().toUpperCase()) &&
					c.getLastName().trim().toUpperCase().equals(lastName.trim().toUpperCase()) && StringHelper.isNotNullOrEmpty(c.getPickName())
					&& c.getPickName().trim().toUpperCase().equals(pickName.trim().toUpperCase()));
		}
	}
	
	@Override
	public Seller getSellerByPhoneNumber(String phoneNumber)
	{
		enableCache();
		if (StringHelper.isNullOrEmpty(phoneNumber))
		{
			return null;
		}
		return cache.findOne(s -> s.getPhoneNumber().trim().equals(phoneNumber.trim()));
	}

	@Override
	public long save(Seller seller) throws Exception
	{
		sellerRepository.save(seller);		
		if (cache.isEnabled())
		{
			try
			{
				cache.save(seller);
			}
			catch(Exception ex)
			{
				desable();
			}
		}		
		return seller.getId();
	}
	
	@Override
	public void desable()
	{
		cache.desable();
	}

}
