package com.orb.interview.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.orb.interview.controller.exceptions.PriceNotFoundException;
import com.orb.interview.model.exchange.ExchangeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Forex.class)
public class ForexTest {

	@Autowired
	private Forex forex;

	@MockBean
	private ExchangeService exchangeService;

	@Test
	public void getQuote() throws PriceNotFoundException {
		forex.getQuote("JPY", "USD");
		verify(exchangeService).getQuote("JPY", "USD");
	}

	@Test
	public void getQuote_validation_check_fail() throws PriceNotFoundException {
		when(forex.getQuote("JY", "USD")).thenThrow(PriceNotFoundException.class);
	}

}