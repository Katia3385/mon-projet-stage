package com.arem.productInput.contracts;
import com.arem.core.model.Customer;

public class CustomerContract extends UserContract<Customer>
{

    public CustomerContract(Customer customer)
	{
    	super(customer);
	}
	
	public CustomerContract()
	{
		super(new Customer());
	}
	
}
