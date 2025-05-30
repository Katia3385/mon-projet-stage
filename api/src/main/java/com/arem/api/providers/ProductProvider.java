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
import com.arem.framework.differ.ProductDiffer;
import com.arem.productInput.contracts.PriceContract;
import com.arem.productInput.contracts.ProductContract;
import com.arem.productInput.roles.UserRole;
import com.arem.productInput.rules.ProductRule;

@Component
public class ProductProvider
{

	@Autowired
	private IProductService productService;
	
	@Autowired
	private IPriceService priceService;
	
	@Autowired
	private ProductRule productRule;
	
	@Autowired
	private AuthProvider authProvider;
	
	
	public List<ProductContract> getProducts()
	{
		List<Product> all = productService.getProducts();
		List<ProductContract> result = new ArrayList<ProductContract>();
		for(Product product : all)
		{
			ProductContract contract = new ProductContract(product);
			result.add(contract);
		}
		return result;
	}
	
	public ProductContract getProductById(long id)
	{
		Product product = productService.getProductById(id);
		if (product == null)
		{
			return null;
		}
		List<Price> prices = priceService.getPricesByProductId(product.getId());
		List<PriceContract> priceContracts = new ArrayList<PriceContract>();
		prices.forEach(p -> priceContracts.add(new PriceContract(p)));
		ProductContract contract = new ProductContract(product);
		contract.setPrices(priceContracts);
		return contract;
	}
	
	public void save(ProductContract contract) throws Exception
	{
		if (contract == null)
		{
		   throw new IllegalArgumentException("product could not be null");	
		}
		
		Seller currentUser = authProvider.getCurrentUser();
		
		List<String> errors = new ArrayList<String>();
		
		if (UserRole.CanUpdateProducts(currentUser))
		{
			if (productRule.validate(contract, errors))
			{
				Product product = contract.getModel();
				
				product.setModifDate(LocalDateTime.now());
				product.setModifSeller(currentUser);
				
				if (product.getId() == 0)
				{
					product.setCreationDate(LocalDateTime.now());
					product.setCreateSeller(currentUser);
					product.setVersion(1);
					if (check(product, errors))
					{
						productService.save(product);
					}
					else
					{
						throw new IllegalArgumentException(String.join(";", errors));
					}
				}
				else
				{
					Product oldProduct = productService.getProductById(product.getId());
					if (oldProduct == null)
					{
						throw new IllegalArgumentException("the product id specified is not valid : " + product.getId());
					}
					product.setCreationDate(oldProduct.getCreationDate());
					product.setCreateSeller(oldProduct.getCreateSeller());
					int version = oldProduct.getVersion() + 1;
					product.setVersion(version);
					
					if (ProductDiffer.getDifferences(oldProduct, product).size() > 0)
					{
						if (check(product, errors))
						{
							productService.save(product);
						}
						else
						{
							throw new IllegalArgumentException(String.join(";", errors));
						}
					}
				}
			}
			else
			{
				throw new IllegalArgumentException(String.join(";", errors));
			}
		}
		else
		{
			throw new IllegalArgumentException("You are not authorized to save or update products");
		}
	}

	public void delete(long id) throws Exception
	{
		Seller currentUser = authProvider.getCurrentUser();
		if (UserRole.CanUpdateProducts(currentUser))
		{
			Product product = productService.getProductById(id);
			if (product != null)
			{
				List<Price> prices = priceService.getPricesByProductId(product.getId());
				priceService.delete(prices);
				productService.delete(product);
			}
		}
	}
	
	private Boolean check(Product product, List<String> errors)
	{
		Product oldProduct = productService.getProductByReference(product.getReference());
		if (oldProduct != null && oldProduct.getId() != product.getId())
		{
			errors.add("Another product with same reference already exists");
		}
		
		oldProduct = productService.getProductByNameAndMarque(product.getName(), product.getMarque());
		if (oldProduct != null && oldProduct.getId() != product.getId())
		{
			errors.add("Another product with same name and marque already exists");
		}
		
		return errors.isEmpty();
	}
}
