package com.arem.core.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity()
@Table
(
	name = "customer",
	uniqueConstraints =
	{
		@UniqueConstraint(columnNames={"first_name", "last_name", "pick_name"}, name = "UniqueFirstNameAndLastNameAndPickName"),
		@UniqueConstraint(columnNames={"phone_number"}, name = "UniquePhoneNumber"),
	}
)
public class Customer extends User
{

	private static final long serialVersionUID = -5366369304341171036L;
	public static final String CacheName = "core:customer";
	
	
	public Customer()
	{
		super();
	}
	
	public Customer(long id)
	{
		super(id);
	}
	
	public Customer(long id, String firstName, String lastName)
	{
		super(id, firstName, lastName);
	}
		
	public Customer(long id, String firstName, String lastName, String phoneNumber)
	{
		super(id, firstName, lastName, phoneNumber);
	}
	
	public long getGroupId()
	{
		return 0;
	}
	
	public void trimAll()
	{
		super.trimAll();
	}
}