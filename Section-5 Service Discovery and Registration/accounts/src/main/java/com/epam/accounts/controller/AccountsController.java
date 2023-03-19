package com.epam.accounts.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.accounts.config.AccountServiceConfig;
import com.epam.accounts.model.Accounts;
import com.epam.accounts.model.Cards;
import com.epam.accounts.model.Customer;
import com.epam.accounts.model.CustomerDetails;
import com.epam.accounts.model.Loans;
import com.epam.accounts.model.Properties;
import com.epam.accounts.repository.AccountsRepository;
import com.epam.accounts.service.clients.CardsFeignClient;
import com.epam.accounts.service.clients.LoansFeignClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


@RestController
public class AccountsController {
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private AccountServiceConfig accountServiceConfig;

	@Autowired 
	private CardsFeignClient cardsFeignClient;
	
	@Autowired
	private LoansFeignClient loansFeignClient;
	
	
	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {

		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}

	}
	
	@GetMapping("/accounts/properties")
	public String getAccountDetails() throws JsonProcessingException {
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(accountServiceConfig.getMsg(),accountServiceConfig.getBuildVersion()
				,accountServiceConfig.getMailDetails(),accountServiceConfig.getActiveBranches());
		String accountData = writer.writeValueAsString(properties);
		return accountData;
		
	}
	
	@GetMapping("/getCustomerDetails")
	public CustomerDetails getCustomerDetails(@RequestBody Customer customer) {
		
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
		List<Cards> cardDetails = cardsFeignClient.getCardDetails(customer);
		List<Loans> loanDetails = loansFeignClient.getLoanDetails(customer);
		
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccount(accounts);
		customerDetails.setCardDetails(cardDetails);
		customerDetails.setLoanDetails(loanDetails);
		
		return customerDetails;
	}

}
