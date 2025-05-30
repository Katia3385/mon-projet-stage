package com.arem.productInput.contracts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.arem.core.model.Measure;
import com.arem.core.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductContract
{

	private Product model;
	
	private List<PriceContract> prices;
	
	public ProductContract()
	{
		model = new Product();
		prices = new ArrayList<>();
	}
	
	public ProductContract(Product model)
	{
		this.model = model;
		prices = new ArrayList<>();
	}
	
	public long getId()
	{
		return model.getId();
	}
	
	public void setId(long id)
	{
		model.setId(id);
	}
	
	public String getName()
	{
		return model.getName();
	}
	
	public void setName(String name)
	{
		model.setName(name);
	}
	
	public String getMarque() 
	{
		return this.model.getMarque();
	}

	public void setMarque(String marque)
	{
		this.model.setMarque(marque);
	}
	
	public String getReference()
	{
		return model.getReference();
	}
	
	public void setReference(String reference)
	{
		model.setReference(reference);
	}
	
	public String getDescription()
	{
		return model.getDescription();
	}
	
	public void setDescription(String description)
	{
		model.setDescription(description);
	}
	
	public String getComment()
	{
		return model.getComment();
	}
	
	public void setComment(String comment)
	{
		model.setComment(comment);
	}
	
	public long getModifSellerId()
	{
		if (model.getModifSeller() != null)
		{
			return model.getModifSeller().getId();
		}
		return 0;
	}
	
	public long getCreateSellerId()
	{
		if (model.getCreateSeller() != null)
		{
			return model.getCreateSeller().getId();
		}
		return 0;
	}

	public int getVersion() 
	{
		return model.getVersion();
	}
	
	public void setVersion(int version) 
	{
		model.setVersion(version);
	}
	
	public LocalDateTime getModifDate() 
	{
		return model.getModifDate();
	}
                  
	public LocalDateTime getCreationDate() 
	{
		return model.getCreationDate();
	}
	
	public double getQuantity() 
	{
		return this.model.getQuantity();
	}

	public void setQuantity(double quantity) 
	{
		this.model.setQuantity(quantity);
	}
	
	public Measure getMeasure() 
	{
		return this.model.getMeasure();
	}

	public void setMeasure(Measure measure) 
	{
		this.model.setMeasure(measure);
	}
		
	public List<PriceContract> getPrices()
	{
		return prices;
	}
	
	public void setPrices(List<PriceContract> prices)
	{
		this.prices = prices;
	}
	
	@JsonIgnore
    public Product getModel()
    {
    	return model;
    }
	
    public void setModel(Product model)
    {
    	this.model = model;
    }
}
