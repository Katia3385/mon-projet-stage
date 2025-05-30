package com.arem.api.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.arem.api.providers.SellerProvider;
import com.arem.productInput.contracts.SellerContract;

@RestController
@CrossOrigin
public class SellerController
{
	
	@Autowired
	private SellerProvider sellerProvider;
	
			
	@GetMapping("/sellers")
	public List<SellerContract> getSellers()
	{
		return sellerProvider.getSellers();
	}
	
	@GetMapping("/seller/{id}")
	public SellerContract getSellerById(@PathVariable long id)
	{
		return sellerProvider.getSellerById(id);
	}
	
	@PostMapping("/seller")
	public long Save(@RequestBody SellerContract contract) throws Exception
	{
		return sellerProvider.save(contract);
	}

}
