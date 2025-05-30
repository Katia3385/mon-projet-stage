package com.arem.core.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table
(
	name = "provider",
	uniqueConstraints =
	{
		@UniqueConstraint(columnNames={"first_name", "last_name", "pick_name"}, name = "UniqueFirstNameAndLastNameAndPickName"),
		@UniqueConstraint(columnNames={"phone_number"}, name = "UniquePhoneNumber"),
	}
)
public class Provider extends User {

	private static final long serialVersionUID = 1859745615554L;
	public static final String CacheName = "core:provider";
	
	public Provider()
	{
		super();
	}
	
	public Provider(long id)
	{
		super(id);
	}
	
	public Provider(long id, String firstName, String lastName)
	{
		super(id, firstName, lastName);
	}
		
	public Provider(long id, String firstName, String lastName, String phoneNumber)
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
