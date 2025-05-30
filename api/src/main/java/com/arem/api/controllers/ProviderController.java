package com.arem.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.arem.api.providers.UserProvider;
import com.arem.productInput.contracts.ProviderContract;


@RestController
@CrossOrigin
public class ProviderController
{

	@Autowired
	private UserProvider userProvider;
		
	@GetMapping("/providers")
	public List<ProviderContract> getProviders()
	{
		return userProvider.getProviders();
	}
	
	@GetMapping("/provider/{id}")
	public ProviderContract getProviderById(@PathVariable long id)
	{
		return userProvider.getProviderById(id);
	}
	
	@PostMapping("/provider")
	public long Save(@RequestBody ProviderContract contract) throws Exception
	{
		return userProvider.save(contract);
	}
	
}
