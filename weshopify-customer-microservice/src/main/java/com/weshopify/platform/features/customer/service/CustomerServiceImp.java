package com.weshopify.platform.features.customer.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.weshopify.platform.features.customer.bean.CustomerBean;
import com.weshopify.platform.features.customer.commons.CustomerConstants;
import com.weshopify.platform.features.customer.commons.CustomerSearchOptions;
import com.weshopify.platform.features.customer.domain.Customer;
import com.weshopify.platform.features.customer.repo.CustomerRepo;

import lombok.extern.slf4j.Slf4j;

//Service layer always accept bean & return Bean

@Service
@Slf4j
public class CustomerServiceImp implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;
	
	//Annotation used at the field or method/constructor parameter level that indicates a default value expression for the annotated element. 
	//@Value("${weshopify.app.search.keys}")
	//private String searchKeys;
	
	@Override
	public CustomerBean saveCustomer(CustomerBean customerBean) {
		Customer customerDomain	=	new Customer();
		
		/**
		 * Convert the bean to domain as per the repository 
		 * design, it will only accesspts the domains which are 
		 * etities.
		 */
		BeanUtils.copyProperties(customerBean, customerDomain);
		
		customerRepo.save(customerDomain);
		
		BeanUtils.copyProperties(customerDomain, customerBean);

		return customerBean;
	}

	@Override
	public CustomerBean updateCustomer(CustomerBean customerBean) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerBean> getAllCustomers() 
	{
		 Iterator<Customer> 	customers		=	customerRepo.findAll().iterator();
		 List<CustomerBean>  	listCustomers	=	new ArrayList<>();
		 
		 while(customers.hasNext())
		 {
			 Customer 		customerDomain	=	customers.next();
			 CustomerBean	customerBean	=	new CustomerBean();
			 
			 BeanUtils.copyProperties(customerDomain, customerBean);
			 listCustomers.add(customerBean);
		 }
		
		 return listCustomers;
	}

	@Override
	public List<CustomerBean> getAllCustomers(int currentPage, int noOfRecPerPage) 
	{		
		PageRequest				pageRequest		=	PageRequest.of(currentPage, noOfRecPerPage);
		Iterator<Customer> 		customers		=	customerRepo.findAll(pageRequest).iterator();
		List<CustomerBean>  	listCustomers	=	new ArrayList<>();
		 
		 while(customers.hasNext())
		 {
			 Customer 		customerDomain	=	customers.next();
			 CustomerBean	customerBean	=	new CustomerBean();
			 
			 BeanUtils.copyProperties(customerDomain, customerBean);
			 listCustomers.add(customerBean);
		 }
		
		 return listCustomers;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation=Propagation.REQUIRED)
	@Override
	public void deleteCustomerById(int id) 
	{
		log.info("Executing Service Method");
		customerRepo.deleteById(id);
		
		// for to check rollBackFor & norollBackFor
		//getCustomerById(id);
	}

	@Override
	public List<CustomerBean> deleteCustomer(CustomerBean customerBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerBean getCustomerById(int id) 
	{
		Customer		customerDomain	=	customerRepo.findById(id).get();
		CustomerBean	customerBean	=	new CustomerBean();
		
		BeanUtils.copyProperties(customerDomain, customerBean);

		return customerBean;
	}

	@Override
	public List<CustomerBean> searchCustomers(String searchKey, String searchText) 
	{
		//step-1: if the incoming search key is matching with the configured search key then
	    //do the search based on the configured search key dynamically
		List<Customer> 		filteredCustomers 	= null;
		List<CustomerBean> 	customerBeanList 	= new ArrayList<>();
		
		if(CustomerSearchOptions.ByEmail.name().equals(searchKey))
		{
			filteredCustomers	=	customerRepo.searchCustomerByEmail(searchText);
		}
		else if(CustomerSearchOptions.ByUserName.name().equals(searchKey)) 
		{
			filteredCustomers	=	customerRepo.searchCustomerByUserName(searchText);
		}
		else if(CustomerSearchOptions.ByMobile.name().equals(searchKey)) 
		{
			filteredCustomers	=	customerRepo.searchCustomerByMobile(searchText);
		}
		else 
		{
			filteredCustomers	=	customerRepo.findAll();
		}
		
		
		filteredCustomers.forEach( customer ->{
			CustomerBean custBean = new CustomerBean();
			BeanUtils.copyProperties(customer, custBean);

			customerBeanList.add(custBean);
		});
		
		return customerBeanList;
	}

	@Override
	public List<CustomerBean> getAllCustomersBySort(String sortBy) {
		List<Customer> 		filteredCustomers 	= null;
		List<CustomerBean> 	customerBeanList 	= new ArrayList<>();
		
		Sort sort = Sort.by(Direction.ASC, sortBy);
		
		filteredCustomers	= customerRepo.findAll(sort);
		
		filteredCustomers.forEach( customer ->{
			CustomerBean custBean = new CustomerBean();
			BeanUtils.copyProperties(customer, custBean);

			customerBeanList.add(custBean);
		});
		
		return customerBeanList;
	}

}
