package com.epam.loans.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.loans.config.LoansConfigService;
import com.epam.loans.model.Customer;
import com.epam.loans.model.Loans;
import com.epam.loans.model.Properties;
import com.epam.loans.repository.LoansRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
public class LoansController {

	@Autowired
	private LoansRepository loansRepository;
	
	@Autowired
	private LoansConfigService loansConfigService;

	@PostMapping("/myLoans")
	public List<Loans> getLoansDetails(@RequestBody Customer customer) {
		List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
		System.out.println("invoking Loans Service............");
		if (loans != null) {
			return loans;
		} else {
			return null;
		}

	}

	@GetMapping("/loans/properties")
	public String getLoansConfigData() throws JsonProcessingException {
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(loansConfigService.getMsg(),loansConfigService.getBuildVersion(),
			loansConfigService.getMailDetails(),loansConfigService.getActiveBranches());
		String loansData = writer.writeValueAsString(properties);
		return loansData;
	}
	
}