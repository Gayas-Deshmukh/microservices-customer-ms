package com.weshopify.platform.features.customer.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weshopify.platform.features.customer.bean.CustomerBean;
import com.weshopify.platform.features.customer.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CustomerResource 
{
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/customer", method= RequestMethod.GET)
	public @ResponseBody List<CustomerBean> viewCustomerdashBoard()
	{
		List<CustomerBean>	allCustomers	=	customerService.getAllCustomers();
		
		//model.addAttribute("allCustomers", allCustomers);
		
		//return "customer-dashboard";
		
		return allCustomers;
	}
	
	@RequestMapping(value = {"/customer/{sortBy}"} , method = RequestMethod.GET)
	public @ResponseBody List<CustomerBean> viewCustomerDataBySorting(@PathVariable("sortBy") String SortBy)
	{
		List<CustomerBean>	allCustomers	=	customerService.getAllCustomersBySort(SortBy);
		
		//model.addAttribute("allCustomers", allCustomers);
		
		//return "customer-dashboard";
		
		return allCustomers;
	}
	
	/* Not Required
	@RequestMapping("/add-customer-view")
	public String addCustomerViewPage(Model model)
	{
		model.addAttribute("customerFormBean", new CustomerBean());
		return "add-customer";
	}*/
	
	@RequestMapping(value = "/customer",method = RequestMethod.POST)
	public @ResponseBody CustomerBean addCustomer(@RequestBody CustomerBean cutomerBean)
	{
		log.info(cutomerBean.toString());
		
		/*
		if(cutomerBean.getIsSelfReg() != null && Boolean.valueOf(cutomerBean.getIsSelfReg()))
		{
			customerService.saveCustomer(cutomerBean);
			
			if(cutomerBean != null && cutomerBean.getId() > 0)
			{
				String isSelfReg = "true";
				model.addAttribute("regMessage", isSelfReg);
				
				return "customer-self-reg";
			}
		}*/
		
		return customerService.saveCustomer(cutomerBean);
	
		//return "redirect:/view-customer-board";
	}
	
	
	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
	@RequestMapping(value="/customer/{id}",method = RequestMethod.DELETE)
	public @ResponseBody String deleteCustomerByid(@PathVariable("id") String customerId)
	{
		log.info("Executing Controller Method");

		customerService.deleteCustomerById(Integer.parseInt(customerId));
		
		return "deleted : ok";
	}
	
	/*
	 * it is used to check transaction concepts
	 * 
	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED,timeout = 3,
			readOnly = true, rollbackFor = RuntimeException.class)
	@RequestMapping(value="/delete-customer/{id}",method = RequestMethod.GET)
	public String deleteCustomerByid(@PathVariable("id") String customerId)
	{
		log.info("Executing Controller Method");

		customerService.deleteCustomerById(Integer.parseInt(customerId));
		
		return "redirect:/view-customer-board";
	}
	*/
	
	@RequestMapping(value = "/customer",method = RequestMethod.PUT)
	public @ResponseBody CustomerBean updateCustomer(@RequestBody CustomerBean cutomerBean)
	{
		CustomerBean customer	= customerService.saveCustomer(cutomerBean);
		//model.addAttribute("customerInfo", customer);
		
		return customer;
	}
	
	@RequestMapping(value="/customer/{currentPage}/{noOfRecPerPage}", method = RequestMethod.GET)
	public @ResponseBody  List<CustomerBean> viewCustomerDashboardWithPagination(@PathVariable("currentPage")String currentPage, @PathVariable("noOfRecPerPage") String noOfRecPerPage)
	{
		List<CustomerBean> 	customerBean = null;
		
		if(currentPage != null)
		{
			int pageNo	= Integer.parseInt(currentPage);
			
	    	customerBean		=	customerService.getAllCustomers(Integer.parseInt(currentPage) -1,Integer.parseInt(noOfRecPerPage));
			List<Integer> 		noOfRequiredPage	=	new ArrayList<>();
			
			int totalRecord	=	customerService.getAllCustomers().size();	
			int noOfPages	=	totalRecord/Integer.parseInt(noOfRecPerPage);
			
			for (int i=1 ; i<= noOfPages; i++)
			{
				noOfRequiredPage.add(i);
			}
			
			if(totalRecord % Integer.parseInt(noOfRecPerPage) != 0 && Integer.parseInt(noOfRecPerPage) < totalRecord)
			{
				noOfRequiredPage.add(noOfRequiredPage.size() + 1);
			}
			
			if(totalRecord < Integer.parseInt(noOfRecPerPage) )
			{
				noOfRequiredPage.add(noOfRequiredPage.size() + 1);
			}
			
			/*
			if (pageNo == 1)
			{
				model.addAttribute("previousPage", pageNo);
			}
			else if(pageNo > 1)
			{
				model.addAttribute("previousPage", pageNo - 1);
			}
			
			if(pageNo < noOfRequiredPage.size())
			{
				model.addAttribute("nextPage", pageNo + 1);
			}
			else 
			{
				model.addAttribute("nextPage", pageNo);
			}
			
			model.addAttribute("noOfRecPerPage", noOfRecPerPage);
			model.addAttribute("noOfPages", noOfRequiredPage);
			model.addAttribute("allCustomers", customerBean);
			*/
		}
		
		return customerBean;
	}
	
	@RequestMapping(value="/customers/search/{searchKey}/{searchText}", method = RequestMethod.GET)
	public @ResponseBody  List<CustomerBean> searchCustomers(@PathVariable("searchKey") String searchKey, @PathVariable("searchText") String searchText)
	{
		List<CustomerBean> 	customerBean	=	customerService.searchCustomers(searchKey, searchText);
		
		//model.addAttribute("allCustomers", customerBean);

		//return "customer-dashboard-paggination";
		
		return customerBean;
	}
}
