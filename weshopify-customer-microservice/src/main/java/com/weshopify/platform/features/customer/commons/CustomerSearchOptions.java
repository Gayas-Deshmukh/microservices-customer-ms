package com.weshopify.platform.features.customer.commons;

public enum CustomerSearchOptions
{
	ByEmail("ByEmail","email"),
	ByUserName("ByUserName","userName"),
	ByMobile("ByMobile","mobileNo");

	String searchType;
	String searchField;

	CustomerSearchOptions(String searchType, String searchField)
	{
		this.searchType		=	searchType;
		this.searchField	=	searchField;
	}
	
}
