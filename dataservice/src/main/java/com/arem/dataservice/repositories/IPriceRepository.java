package com.arem.dataservice.repositories;

import org.springframework.data.repository.CrudRepository;

import com.arem.core.model.Price;

public interface IPriceRepository extends CrudRepository<Price, Long>
{

	public Iterable<Price> findByProductId(long productId);
	
	public Price findById(long priceId);
	
}
