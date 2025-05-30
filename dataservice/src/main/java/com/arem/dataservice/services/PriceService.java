package com.arem.dataservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.arem.core.model.Price;
import com.arem.dataservice.repositories.IPriceRepository;
import com.arem.framework.runtime.Prices;

@Component
public class PriceService implements IPriceService
{
	
	@Autowired
	private IPriceRepository priceRepository;
	
	@Autowired
	private Prices cache;
	
	private final Object sync = new Object();

	private void enableCache()
	{
		if (!cache.isEnabled())
		{
			synchronized(sync)
			{
				if (!cache.isEnabled())
				{
					Iterable<Price> all = priceRepository.findAll();
					cache.putAll(all);
				}
			}
		}
	}
	
	@Override
	public List<Price> getPrices()
	{
		enableCache();
		return cache.findAll();
	}

	@Override
	public List<Price> getPricesByProductId(long productId)
	{
		enableCache();
		return cache.findByProductId(productId);
	}

	@Override
	public void save(Price price) throws Exception
	{
		priceRepository.save(price);
		if (cache.isEnabled())
		{
			try
			{
				cache.save(price);
			}
			catch(Exception ex)
			{
				desable();
			}
		}
	}

	@Override
	public void save(List<Price> prices) throws Exception
	{
		priceRepository.saveAll(prices);
		if (cache.isEnabled())
		{
			try
			{
				cache.save(prices);
			}
			catch(Exception ex)
			{
				desable();
			}
		}
	}

	@Override
	public void delete(Price price) throws Exception
	{
		priceRepository.delete(price);
		if (cache.isEnabled())
		{
			try
			{
				cache.delete(price);
			}
			catch(Exception ex)
			{
				desable();
			}
		}
	}

	@Override
	public void delete(List<Price> prices) throws Exception
	{
		priceRepository.deleteAll(prices);
		if (cache.isEnabled())
		{
			try
			{
				cache.delete(prices);
			}
			catch(Exception ex)
			{
				desable();
			}
		}
	}
	
	@Override
	public Price getPriceById(long productId, long priceId)
	{
		enableCache();
		return cache.getPriceById(productId, priceId);
	}

	@Override
	public void desable()
	{
		cache.desable();
	}
	
}
