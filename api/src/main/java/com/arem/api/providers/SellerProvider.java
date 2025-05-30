package com.arem.api.providers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.arem.core.model.Seller;
import com.arem.dataservice.services.ISellerService;
import com.arem.productInput.contracts.SellerContract;
import com.arem.productInput.roles.UserRole;
import com.arem.productInput.rules.SellerRule;

@Component
public class SellerProvider
{
	
	@Autowired
	private ISellerService sellerService;
	
	@Autowired
	private AuthProvider authProvider;
	
	
	public List<SellerContract> getSellers()
	{
		if (UserRole.CanReadSellers(authProvider.getCurrentUser()))
		{
			Iterable<Seller> all = sellerService.getSellers();
			List<SellerContract> result = new ArrayList<SellerContract>();
			for(Seller seller : all)
			{
				SellerContract contract = new SellerContract(seller);
				result.add(contract);
			}
			return result;
		}
		else
		{
			throw new IllegalArgumentException("You are not authorized to read sellers");
		}
		
	}
	
	public SellerContract getSellerById(long id)
	{
		Seller seller = sellerService.getSellerById(id);
		if (UserRole.CanReadSeller(authProvider.getCurrentUser(), seller))
		{
			if (seller == null)
			{
				return null;
			}
			return new SellerContract(seller);
		}
		else
		{
			throw new IllegalArgumentException("You are not authorized to read seller");
		}
	}
	
	
	public long save(SellerContract contract) throws Exception
	{
		if (contract == null)
		{
		   throw new IllegalArgumentException("seller could not be null");	
		}
		
		List<String> errors = new ArrayList<String>();
		SellerRule rule = new SellerRule();
		
		contract.getModel().trimAll();
		
		Seller currentUser = authProvider.getCurrentUser();
				
		if (rule.validate(contract, errors))
		{
			Seller seller = contract.getModel();
			seller.setModifDate(LocalDateTime.now());
			seller.setModifSeller(currentUser);
			
			if (UserRole.CanUpdateSeller(currentUser, seller))
			{
				if (seller.getId() == 0)
				{
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					String password = passwordEncoder.encode(seller.getPassword());					
					seller.setPassword(password);
					seller.setCreationDate(LocalDateTime.now());
					seller.setCreateSeller(currentUser);
					seller.setVersion(1);
				}
				
				else
				{
					Seller oldSeller = sellerService.getSellerById(seller.getId());
					if (oldSeller == null)
					{
						throw new IllegalArgumentException(String.format("seller with id %s is not found", seller.getId()));
					}
					
					int version = oldSeller.getVersion() + 1;
					
					seller.setVersion(version);
					seller.setCreationDate(oldSeller.getCreationDate());
					seller.setPassword(oldSeller.getPassword());
					seller.setCreateSeller(oldSeller.getCreateSeller());
					
					if (seller.getId() == seller.getId() && oldSeller.getIsAdmin() == false)
					{
						seller.setIsCredentialsNonExpired(oldSeller.getIsCredentialsNonExpired());
						seller.setIsAccountNonExpired(oldSeller.getIsAccountNonExpired());
						seller.setIsAccountNonLocked(oldSeller.getIsAccountNonLocked());
						seller.setIsEnabled(oldSeller.getIsEnabled());
						seller.setIsAdmin(false);						
					}
				}
				
				if (this.chekSeller(seller, errors))
				{
					return sellerService.save(seller);
				}
				else
				{
					throw new IllegalArgumentException(String.join(";", errors));
				}			
			}
			else
			{
				throw new IllegalArgumentException("You are not authorized to save or update seller");
			}	
		}
		
		else
		{
			throw new IllegalArgumentException(String.join(";", errors));
		}
	}
	
	private Boolean chekSeller(Seller seller, List<String> errors)
	{
		Seller oldSeller = sellerService.getSellerByUserName(seller.getEmail());
		if (oldSeller != null && oldSeller.getId() != seller.getId())
		{
			errors.add("email address already exists");
		}
		
		oldSeller = sellerService.getSellerByFirstNameAndLastNameAndPickName(seller.getFirstName(), seller.getLastName(), seller.getPickName());
		if (oldSeller != null && oldSeller.getId() != seller.getId())
		{
			errors.add("Another user with same first name and last name already exists");
		}
		
		oldSeller = sellerService.getSellerByPhoneNumber(seller.getPhoneNumber());
		if (oldSeller != null && oldSeller.getId() != seller.getId())
		{
			errors.add("Another user with same phone number already exists");
		}
		
		oldSeller = sellerService.getSellerByPickName(seller.getPickName());
		if (oldSeller != null && oldSeller.getId() != seller.getId())
		{
			errors.add("Another user with same pick name already exists");
		}
		
		return errors.isEmpty();
	}
	
}
