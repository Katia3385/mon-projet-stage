package com.arem.api.providers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.arem.core.model.Price;
import com.arem.core.model.Product;
import com.arem.core.model.Seller;
import com.arem.dataservice.services.IPriceService;
import com.arem.dataservice.services.IProductService;
import com.arem.framework.Iterables;
import com.arem.framework.differ.PriceDiffer;
import com.arem.productInput.contracts.PriceContract;
import com.arem.productInput.roles.UserRole;
import com.arem.productInput.rules.PriceRule;

@Component
public class PriceProvider
{

	@Autowired
	private IProductService productService;
	
	@Autowired
	private IPriceService priceService;
	
	@Autowired
	private PriceRule priceRule;
	
	
	@Autowired
	private AuthProvider authProvider;
	
	
	public void save(long productId, List<PriceContract> prices) throws Exception
	{
		Seller currentUser = authProvider.getCurrentUser();
		if (UserRole.CanUpdatePrices(currentUser))
		{
			List<String> errors = new ArrayList<String>();
			if (priceRule.validate(prices, errors))
			{
				Product product = productService.getProductById(productId);
				if (product == null) 
				{
					throw new IllegalArgumentException("could not find product with id : " + productId);
				}
				save(product, prices);
			}
			else
			{
				throw new IllegalArgumentException(String.join(";", errors));
			}
		}
		else
		{
			throw new IllegalArgumentException("You are not authorized to update prices");
		}
	}
	
	private void save(Product product, List<PriceContract> contracts) throws Exception
	{
		List<Price> oldPrices = priceService.getPricesByProductId(product.getId());
		List<Price> toDelete = new ArrayList<>();
		
		oldPrices.forEach(oldPrice ->
		{
			if (Iterables.findOne(contracts, p -> p.getId() == oldPrice.getId()) == null)
			{
				toDelete.add(oldPrice);
			}
		});
						
		List<Price> toSave = new ArrayList<Price>();
		for(PriceContract priceContract : contracts)
		{
			Price price = priceContract.getModel();
			price.setModifDate(LocalDateTime.now());
			price.setProductId(product.getId());
			if (price.getId() == 0)
			{
				price.setCreationDate(LocalDateTime.now());
				price.setCreateSeller(price.getModifSeller());
				price.setVersion(1);
				toSave.add(price);
			}
			else
			{
				Price old = priceService.getPriceById(product.getId(), price.getId());
				if (old == null)
				{
					throw new IllegalArgumentException("the price id specified is not valid : " + price.getId());
				}
				price.setCreationDate(old.getCreationDate());
				price.setCreateSeller(old.getCreateSeller());
				int version = old.getVersion() + 1;
				price.setVersion(version);
				if (PriceDiffer.getDifferences(old, price).size() > 0)
				{
					toSave.add(price);
				}
			}
			
		}
		if (!toDelete.isEmpty())
		{
			priceService.delete(toDelete);
		}
		if (!toSave.isEmpty())
		{
			priceService.save(toSave);
		}
	}
	
}
