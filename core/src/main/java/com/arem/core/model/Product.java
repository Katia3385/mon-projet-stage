package com.arem.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity()
@Table
(
	name = "product",
	uniqueConstraints =
	{
		@UniqueConstraint(columnNames={"name", "marque"}, name = "UniqueNameAndMarque"),
		@UniqueConstraint(columnNames={"reference"}, name = "UniqueReference"),
	}
)
public class Product implements Serializable, ICachable 
{

	private static final long serialVersionUID = -1532512514431347248L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private long id;
	
	@Column(name = "reference", nullable = false)
	private String reference;
	
	
	@Column(name = "name", nullable = false)
    private String name;
	
	
	@Column(name = "marque")
    private String marque;
	
	
	@Column(name = "description")
    private String description;
	
	
	@Column(name = "comment")
    private String comment;
	
	
	@Column(name = "version", nullable = false)
    private int version;
	
	
	@Column(name = "quantity", nullable = false)
    private double quantity;
	
	
	@Column(name = "measure")
    private Measure measure;
	
	
	@Column(name = "modif_date")
    private LocalDateTime modifDate;
    
    @ManyToOne
    private Seller modifSeller;
    
    
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
        
    @ManyToOne
    private Seller createSeller;

    public Product()
    {
    }
    
    public Product(long id)
    {
    	this.id = id;
    }

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getReference() 
	{
		return reference;
	}

	public void setReference(String reference)
	{
		this.reference = reference;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getComment() 
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}
	
	public String getMarque() 
	{
		return this.marque;
	}

	public void setMarque(String marque)
	{
		this.marque = marque;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	public LocalDateTime getModifDate()
	{
		return modifDate;
	}

	public void setModifDate(LocalDateTime modifDate)
	{
		this.modifDate = modifDate;
	}

	public Seller getModifSeller()
	{
		return modifSeller;
	}

	public void setModifSeller(Seller modifSeller) 
	{
		this.modifSeller = modifSeller;
	}

	public LocalDateTime getCreationDate() 
	{
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) 
	{
		this.creationDate = creationDate;
	}

	public Seller getCreateSeller() 
	{
		return createSeller;
	}

	public void setCreateSeller(Seller creationSeller) 
	{
		this.createSeller = creationSeller;
	}
	
	public double getQuantity() 
	{
		return this.quantity;
	}

	public void setQuantity(double quantity) 
	{
		this.quantity = quantity;
	}
	
	public Measure getMeasure() 
	{
		return this.measure;
	}

	public void setMeasure(Measure measure) 
	{
		this.measure = measure;
	}
	
	protected void trimAll()
	{
		if (this.reference != null)
		{
			this.reference = this.reference.trim();
		}
		
		if (this.name != null)
		{
			this.name = this.name.trim();
		}
		
		if (this.marque != null)
		{
			this.marque = this.marque.trim();
		}
	}
	
	public long getGroupId()
	{
		return 0;
	}
}
