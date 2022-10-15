package com.weshopify.platform.features.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.weshopify.platform.features.customer.bean.CustomerBean;

public interface CustomerService 
{
	public CustomerBean saveCustomer(CustomerBean customerBean);
	public CustomerBean updateCustomer(CustomerBean customerBean);
	public List<CustomerBean> getAllCustomers();
	public List<CustomerBean> getAllCustomers(int currentPage, int noOfRecPerPage);
	public void deleteCustomerById(int id);
	public List<CustomerBean> deleteCustomer(CustomerBean customerBean);
	public CustomerBean getCustomerById(int id);
	public List<CustomerBean> searchCustomers(String searchKey, String searchText);
	public List<CustomerBean> getAllCustomersBySort(String sortBy);
}
