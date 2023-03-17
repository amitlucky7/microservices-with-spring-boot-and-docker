package com.epam.accounts.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.accounts.config.AccountServiceConfig;
import com.epam.accounts.model.Accounts;
import com.epam.accounts.model.Customer;
import com.epam.accounts.model.Properties;
import com.epam.accounts.repository.AccountsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


@RestController
public class AccountsController {
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private AccountServiceConfig accountServiceConfig;

	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {

		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}

	}
	
	@GetMapping("/accountDetails")
	public String getAccountDetails() throws JsonProcessingException {
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(accountServiceConfig.getMsg(),accountServiceConfig.getBuildVersion()
				,accountServiceConfig.getMailDetails(),accountServiceConfig.getActiveBranches());
		String accountData = writer.writeValueAsString(properties);
		return accountData;
		
	}

}
