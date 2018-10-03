package com.orb.interview.model.exchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.orb.interview.controller.config.ExchangeConfig;
import com.orb.interview.infrastructure.dtos.ExchangePrice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ExchangeConfig.class, ExchangeServiceImpl.class})
public class ExchangeServiceImplTest {

	@Autowired
	ExchangeServiceImpl exchangeService;

	@Mock
	RestTemplate restTemplate;

	@Test
	public void getQuote() throws Exception {
		exchangeService.restTemplate= restTemplate;
		ExchangePrice exchangePrice = new ExchangePrice();
		ExchangePrice[] exchangePrices = new ExchangePrice[]{exchangePrice};
		ResponseEntity<ExchangePrice[]> responseEntity = new ResponseEntity<>(exchangePrices,HttpStatus.OK);
		when(restTemplate.getForEntity(any(),any(ExchangePrice[].class.getClass()),any(), any())).thenReturn(responseEntity);
		ExchangePrice quote = exchangeService.getQuote("USD", "JPY");
		assertThat(quote).isEqualTo(exchangePrice);
	}
}