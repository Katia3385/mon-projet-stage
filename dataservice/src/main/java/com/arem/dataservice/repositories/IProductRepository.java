package com.arem.dataservice.repositories;

import org.springframework.data.repository.CrudRepository;
import com.arem.core.model.Product;

public interface IProductRepository extends CrudRepository<Product, Long>
{

	public Product findById(long Id);
	
}
