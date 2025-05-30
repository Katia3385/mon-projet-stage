package com.arem.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.arem.api.providers.ProductProvider;
import com.arem.productInput.contracts.ProductContract;

@RestController
@CrossOrigin
public class ProductController 
{

	@Autowired
	private ProductProvider productProvider;
		
	@GetMapping("/products")
	public List<ProductContract> getProducts()
	{
		return productProvider.getProducts();
	}
	
	@GetMapping("/product/{id}")
	public ProductContract getProductById(@PathVariable long id)
	{
		return productProvider.getProductById(id);
	}
	
	@PostMapping("/product")
	public void save(@RequestBody ProductContract contract) throws Exception
	{
		productProvider.save(contract);
	}
	
	@DeleteMapping("/product/{id}")
	public void delete(@PathVariable long id) throws Exception
	{
		productProvider.delete(id);
	}
	
}
