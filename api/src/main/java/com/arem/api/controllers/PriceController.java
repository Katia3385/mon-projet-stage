package com.arem.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.arem.api.providers.PriceProvider;
import com.arem.productInput.contracts.PriceContract;


@RestController
@CrossOrigin
public class PriceController
{

	@Autowired
	private PriceProvider priceProvider;
	
	@PostMapping("/prices/{id}")
	public void save(@PathVariable long id, @RequestBody List<PriceContract> contracts) throws Exception
	{
		priceProvider.save(id, contracts);
	}
}
