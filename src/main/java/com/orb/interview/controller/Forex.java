package com.orb.interview.controller;

import com.orb.interview.controller.exceptions.PriceNotFoundException;
import com.orb.interview.infrastructure.dtos.ExchangePrice;
import com.orb.interview.model.exchange.ExchangeService;
import javax.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Forex {

	@Autowired
	ExchangeService exchangeService;

	@GetMapping("/getQuote")
	public ExchangePrice getQuote(@Validated @RequestParam
	@Size(message = "{com.orb.interview.forex.validation}",min = 3, max = 3) String from,
			@Validated @RequestParam @Size(message = "{com.orb.interview.forex.validation}",min = 3, max = 3) String to) throws PriceNotFoundException {
		log.debug("received quote request for {1} to {2}");
		return exchangeService.getQuote(from,to);
	}
}
