package com.arem.dataservice.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.arem.core.model.Price;

@Service
public interface IPriceService
{
	
	public List<Price> getPrices();
	
	public Price getPriceById(long productId, long priceId);
	
	public List<Price> getPricesByProductId(long productId);
	
	public void save(Price price) throws Exception;
	
	public void save(List<Price> price) throws Exception;
	
	public void delete(Price price) throws Exception;
	
	public void delete(List<Price> price) throws Exception;
	
	public void desable();

}
