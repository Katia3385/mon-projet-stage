package com.arem.dataservice.repositories;

import org.springframework.data.repository.CrudRepository;

import com.arem.core.model.Seller;

public interface ISellerRepository extends CrudRepository<Seller, Long>
{
	
	public Seller findById(long id);
	
}
