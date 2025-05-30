package com.arem.dataservice.repositories;

import org.springframework.data.repository.CrudRepository;

import com.arem.core.model.Provider;

public interface IProviderRepository extends CrudRepository<Provider, Long> 
{

	public Provider findById(long Id);
	
}
