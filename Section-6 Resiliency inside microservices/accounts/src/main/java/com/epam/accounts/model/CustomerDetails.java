package com.epam.accounts.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CustomerDetails {

	Accounts account;
	List<Cards> cardDetails;
	List<Loans> loanDetails;
	
}
