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

@Entity()
@Table(name = "price")
public class Price implements Serializable, ICachable {

	
	private static final long serialVersionUID = 2687255343937073934L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private long id;
	
	
	@Column(name = "price")
    private double price;
	
	@Column(name = "measure")
    private Measure measure;
	
	@Column(name = "side")
    private Side side;
    
	
	@Column(name = "start_date")
    private LocalDateTime startDate;
	
	
	@Column(name = "end_date")
    private LocalDateTime endDate;
    
	
	@Column(name = "creation_date")
    private LocalDateTime creationDate;
	
	
	@Column(name = "modif_date")
    private LocalDateTime modifDate;
    
	
    @ManyToOne
    private Seller modifSeller;
    
        
    @ManyToOne
    private Seller createSeller;
    
    
    @Column(name = "product_id")
	private long productId;
    
    
    @Column(name = "version")
    private int version;
    
    
    public Price()
    {
    	
    }
    
    public Price(long id)
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
	
	public double getPrice()
	{
		return price;
	}
	
	public void setPrice(double price)
	{
		this.price = price;
	}
	
	public Measure getMeasure()
	{
		return measure;
	}
	
	public void setMeasure(Measure measure) 
	{
		this.measure = measure;
	}
	
	public Side getSide()
	{
		return side;
	}
	
	public void setSide(Side side) 
	{
		this.side = side;
	}
	
	public LocalDateTime getStartDate()
	{
		return startDate;
	}
	
	public void setStartDate(LocalDateTime startDate)
	{
		this.startDate = startDate;
	}
	
	public LocalDateTime getEndDate()
	{
		return endDate;
	}
	
	public void setEndDate(LocalDateTime endDate)
	{
		this.endDate = endDate;
	}
	
	public LocalDateTime getCreationDate() 
	{
		return creationDate;
	}
	
	public void setCreationDate(LocalDateTime creationDate)
	{
		this.creationDate = creationDate;
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

	public Seller getCreateSeller() 
	{
		return createSeller;
	}

	public void setCreateSeller(Seller creationSeller)
	{
		this.createSeller = creationSeller;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version) 
	{
		this.version = version;
	}
	
	public long getProductId()
	{
		return productId;
	}
	
	public void setProductId(long productId)
	{
		this.productId = productId;
	}
	   
	public long getGroupId()
	{
		return productId;
	}
	
}
