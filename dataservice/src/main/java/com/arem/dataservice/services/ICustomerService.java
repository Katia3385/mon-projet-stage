package com.arem.dataservice.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.arem.core.model.Customer;


@Service
public interface ICustomerService 
{

	public List<Customer> getCustomers();
	
	public Customer getCustomerById(long id);
	
	public Customer getCustomerByPickName(String pickName);
	
	public Customer getCustomerByFirstNameAndLastNameAndPickName(String firstName, String lastName, String pickName);
	
	public Customer getCustomerByPhoneNumber(String phone);
	
	public long save(Customer customer) throws Exception;
	
	public void desable();
	
}
