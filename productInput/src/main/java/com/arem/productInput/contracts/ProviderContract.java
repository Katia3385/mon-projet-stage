package com.arem.productInput.contracts;
import com.arem.core.model.Provider;

public class ProviderContract extends UserContract<Provider>
{

	public ProviderContract(Provider provider)
	{
    	super(provider);
	}
	
	public ProviderContract()
	{
		super(new Provider());
	}
	
}
