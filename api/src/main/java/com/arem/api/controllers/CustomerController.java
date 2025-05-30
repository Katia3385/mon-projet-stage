package com.arem.api.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.arem.api.providers.CustomerProvider;
import com.arem.productInput.contracts.CustomerContract;

@RestController
@CrossOrigin
public class CustomerController {
	
	
	@Autowired
	private CustomerProvider customerProvider;
		
	@GetMapping("/customers")
	public List<CustomerContract> getCustomers()
	{
		return customerProvider.getCustomers();
	}
	
	@GetMapping("/customer/{id}")
	public CustomerContract getCustomerById(@PathVariable long id)
	{
		return customerProvider.getCustomerById(id);
	}
	
	@PostMapping("/customer")
	public long Save(@RequestBody CustomerContract contract) throws Exception
	{
		return customerProvider.save(contract);
	}
	
}
