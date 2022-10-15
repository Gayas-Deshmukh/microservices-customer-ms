package com.weshopify.platform.features.customer.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.weshopify.platform.features.customer.domain.Customer;

//public interface CustomerRepo extends CrudRepository<Customer, Integer>
//public interface CustomerRepo extends PagingAndSortingRepository<Customer, Integer>
public interface CustomerRepo extends JpaRepository<Customer, Integer>
{
	// added when extending JpaRepository
	@Query("from Customer c where c.email like %?1%")
	public List<Customer> searchCustomerByEmail(@Param("email") String email);
	
	@Query("from Customer c where c.userName like %?1%")
	public List<Customer> searchCustomerByUserName(@Param("userName") String userName);
	
	@Query("from Customer c where c.mobileNo like %?1%")
	public List<Customer> searchCustomerByMobile(@Param("mobileNo") String mobileNo);
	
}


// PagingAndSortingRepository is a subInterface of CrudRepository
// JpaRepository is a subInterface of PagingAndSortingRepository