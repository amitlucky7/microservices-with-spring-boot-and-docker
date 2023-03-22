package com.epam.cards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.cards.config.CardsConfigService;
import com.epam.cards.model.Cards;
import com.epam.cards.model.Customer;
import com.epam.cards.model.Properties;
import com.epam.cards.repository.CardsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
public class CardsController {

	@Autowired
	private CardsRepository cardsRepository;

	@Autowired
	private CardsConfigService cardConfigService;

	@PostMapping("/myCards")
	public List<Cards> getCardDetails(@RequestBody Customer customer) {
		List<Cards> cards = cardsRepository.findByCustomerId(customer.getCustomerId());
		if (cards != null) {
			return cards;
		} else {
			return null;
		}

	}

	@GetMapping("/cards/properties")
	public String getCardConfigDetails() throws JsonProcessingException {
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(cardConfigService.getMsg(), cardConfigService.getBuildVersion(),
				cardConfigService.getMailDetails(), cardConfigService.getActiveBranches());
		String cardConfigData = writer.writeValueAsString(properties);
		return cardConfigData;
	}
}