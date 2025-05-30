package com.arem.dataservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.arem.core.model.Customer;
import com.arem.dataservice.repositories.ICustomerRepository;
import com.arem.framework.StringHelper;
import com.arem.framework.runtime.Customers;


@Component
public class CustomerService implements ICustomerService 
{
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Autowired
	private Customers cache;
	
	private final Object sync = new Object();
	
	private void enableCache()
	{
		if (!cache.isEnabled())
		{
			synchronized(sync)
			{
				if (!cache.isEnabled())
				{
					Iterable<Customer> all = customerRepository.findAll();
					cache.putAll(all);
				}
			}
		}
	}
	
	@Override
	public List<Customer> getCustomers()
	{
		enableCache();
		return cache.findAll();
	}

	@Override
	public Customer getCustomerById(long id)
	{
		enableCache();
		return cache.findById(id);
	}
	
	@Override
	public Customer getCustomerByPickName(String pickName)
	{
		enableCache();
		if (StringHelper.isNullOrEmpty(pickName))
		{
			return null;
		}
		return cache.findOne(c -> StringHelper.isNotNullOrEmpty(c.getPickName()) && c.getPickName().trim().toUpperCase().equals(pickName.trim().toUpperCase()));
	}
	
	@Override
	public Customer getCustomerByFirstNameAndLastNameAndPickName(String firstName, String lastName, String pickName)
	{
		enableCache();
		
		if (StringHelper.isNullOrEmpty(firstName) || StringHelper.isNullOrEmpty(lastName))
		{
			return null;
		}
		
		if (StringHelper.isNullOrEmpty(pickName))
		{
			return cache.findOne(s -> s.getFirstName().trim().toUpperCase().equals(firstName.trim().toUpperCase()) &&
					s.getLastName().trim().toUpperCase().equals(lastName.trim().toUpperCase()) && StringHelper.isNullOrEmpty(s.getPickName()));
		}
		
		else
		{
			return cache.findOne(s -> s.getFirstName().trim().toUpperCase().equals(firstName.trim().toUpperCase()) &&
					s.getLastName().trim().toUpperCase().equals(lastName.trim().toUpperCase()) && StringHelper.isNotNullOrEmpty(s.getPickName())
					&& s.getPickName().trim().toUpperCase().equals(pickName.trim().toUpperCase()));
		}
	}
	
	@Override
	public Customer getCustomerByPhoneNumber(String phoneNumber)
	{
		enableCache();
		if (StringHelper.isNullOrEmpty(phoneNumber))
		{
			return null;
		}
		return cache.findOne(c -> c.getPhoneNumber().trim().equals(phoneNumber.trim()));
	}
	
	
	@Override
	public long save(Customer customer) throws Exception
	{
		customerRepository.save(customer);
		if (cache.isEnabled())
		{
			try
			{
				cache.save(customer);
			}
			catch(Exception ex)
			{
				desable();
			}
		}
		return customer.getId();
	}
	
	@Override
	public void desable()
	{
		cache.desable();
	}

}
