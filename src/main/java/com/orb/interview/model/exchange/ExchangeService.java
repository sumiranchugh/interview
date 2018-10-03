package com.orb.interview.model.exchange;

import com.orb.interview.controller.exceptions.PriceNotFoundException;
import com.orb.interview.infrastructure.dtos.ExchangePrice;
import org.springframework.stereotype.Service;

@Service
public interface ExchangeService {

	ExchangePrice getQuote(String from, String to) throws PriceNotFoundException;
}
