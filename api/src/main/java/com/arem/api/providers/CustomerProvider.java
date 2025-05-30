package com.arem.api.providers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arem.core.model.Customer;
import com.arem.core.model.Seller;
import com.arem.dataservice.services.ICustomerService;
import com.arem.productInput.contracts.CustomerContract;
import com.arem.productInput.rules.CustomerRule;

@Component
public class CustomerProvider
{

	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private AuthProvider authProvider;
	
	@Autowired
	private CustomerRule rule;
	
	public List<CustomerContract> getCustomers()
	{
		Iterable<Customer> all = customerService.getCustomers();
		List<CustomerContract> result = new ArrayList<CustomerContract>();
		for(Customer customer : all)
		{
			CustomerContract contract = new CustomerContract(customer);
			result.add(contract);
		}
		return result;
	}
	
	public CustomerContract getCustomerById(long id)
	{
		Customer customer = customerService.getCustomerById(id);
		if (customer == null)
		{
			return null;
		}
		return new CustomerContract(customer);
	}
	
	public long save(CustomerContract contract) throws Exception
	{
		if (contract == null)
		{
		   throw new IllegalArgumentException("customer could not be null");	
		}
		
		Seller currentUser = authProvider.getCurrentUser();	
		List<String> errors = new ArrayList<String>();
		
		contract.getModel().trimAll();
		
		if (rule.validate(contract, errors))
		{
			Customer customer = contract.getModel();
			customer.setModifDate(LocalDateTime.now());
			customer.setModifSeller(currentUser);
			
			if (customer.getId() == 0)
			{
				customer.setCreationDate(LocalDateTime.now());
				customer.setCreateSeller(currentUser);
				customer.setVersion(1);
			}
			else
			{
				Customer oldCustomer = customerService.getCustomerById(customer.getId());
				if (oldCustomer == null)
				{
					throw new IllegalArgumentException(String.format("customer with id %s is not found", customer.getId()));
				}
				
				int version = oldCustomer.getVersion() + 1;
				customer.setVersion(version);
				customer.setCreateSeller(oldCustomer.getCreateSeller());
				customer.setCreationDate(oldCustomer.getCreationDate());
			}
			
			if (this.chekCustomer(customer, errors))
			{
				return customerService.save(customer);
			}
			else
			{
				throw new IllegalArgumentException(String.join(";", errors));
			}
		}
		
		else
		{
			throw new IllegalArgumentException(String.join(";", errors));
		}
	}
	
	private Boolean chekCustomer(Customer customer, List<String> errors)
	{
		Customer oldCustomer = customerService.getCustomerByFirstNameAndLastNameAndPickName(customer.getFirstName(), customer.getLastName(), customer.getPickName());
		if (oldCustomer != null && oldCustomer.getId() != customer.getId())
		{
			errors.add("Another customer with same first name and last name already exists");
		}
		
		oldCustomer = customerService.getCustomerByPhoneNumber(customer.getPhoneNumber());
		if (oldCustomer != null && oldCustomer.getId() != customer.getId())
		{
			errors.add("Another customer with same phone number already exists");
		}
		
		oldCustomer = customerService.getCustomerByPickName(customer.getPickName());
		if (oldCustomer != null && oldCustomer.getId() != customer.getId())
		{
			errors.add("Another customer with same pick name already exists");
		}
		
		return errors.isEmpty();
	}
}






