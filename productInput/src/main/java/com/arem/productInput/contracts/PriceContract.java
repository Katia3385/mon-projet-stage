package com.arem.productInput.contracts;

import java.time.LocalDateTime;

import com.arem.core.model.Measure;
import com.arem.core.model.Price;
import com.arem.core.model.Seller;
import com.arem.core.model.Side;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PriceContract
{
	
	private Price model;
	
	public PriceContract()
	{
		model = new Price();
	}
	
	public PriceContract(Price model)
	{
		this.model = model;
	}
	
	public long getId()
	{
		return model.getId();
	}
	
	public void setId(long id)
	{
		model.setId(id);
	}
	
	public double getPrice()
	{
		return model.getPrice();
	}
	
	public void setPrice(double price)
	{
		model.setPrice(price);
	}
	
	public Measure getMeasure()
	{
		return model.getMeasure();
	}
	
	public void setMeasure(Measure measure)
	{
		model.setMeasure(measure);
	}
	
	public Side getSide()
	{
		return model.getSide();
	}
	
	public void setSide(Side side)
	{
		model.setSide(side);
	}
	
	public LocalDateTime getStartDate()
	{
		return model.getStartDate();
	}
	
	public void setStartDate(LocalDateTime startDate)
	{
		model.setStartDate(startDate);
	}
	
	public LocalDateTime getEndDate()
	{
		return model.getEndDate();
	}
	
	public void setEndDate(LocalDateTime endDate)
	{
		model.setEndDate(endDate);
	}
	
	public int getVersion() 
	{
		return model.getVersion();
	}
	
	public LocalDateTime getModifDate() 
	{
		return model.getModifDate();
	}
                  
	public LocalDateTime getCreationDate() 
	{
		return model.getCreationDate();
	}
	
	public long getModifSellerId()
	{
		if (model.getModifSeller() != null)
		{
			return model.getModifSeller().getId();
		}
		return 0;
	}
	
	public void setModifSellerId(long modifSellerId)
	{
		if (model.getModifSeller() == null || model.getModifSeller().getId() != modifSellerId)
		{
			Seller modifSeller = new Seller();
			modifSeller.setId(modifSellerId);
			model.setModifSeller(modifSeller);
		}
	}

	public long getCreateSellerId()
	{
		if (model.getCreateSeller() != null)
		{
			return model.getCreateSeller().getId();
		}
		return 0;
	}
	
	@JsonIgnore
    public Price getModel()
    {
    	return model;
    }
	
    public void setModel(Price model)
    {
    	this.model = model;
    }
}
