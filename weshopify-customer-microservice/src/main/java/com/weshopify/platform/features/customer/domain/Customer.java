package com.weshopify.platform.features.customer.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name ="customer")
@Data
public class Customer  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id ;
	
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String mobileNo;
	private String password;	
	private String isSelfReg = "false";
}
