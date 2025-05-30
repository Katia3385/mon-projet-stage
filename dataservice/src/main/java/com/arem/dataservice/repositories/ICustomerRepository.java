package com.arem.dataservice.repositories;

import org.springframework.data.repository.CrudRepository;
import com.arem.core.model.Customer;

public interface ICustomerRepository extends CrudRepository<Customer, Long>
{

	public Customer findById(long Id);
	
}
