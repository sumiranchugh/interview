package com.orb.interview.model.exchange;

import com.orb.interview.controller.exceptions.PriceNotFoundException;
import com.orb.interview.infrastructure.dtos.ExchangePrice;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExchangeServiceImpl implements ExchangeService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${interview.exchange.apiKey}")
	String apiKey;
	@Value("${interview.exchange.timeThreshold}")
	String timeThreshold;
	@Value("${interview.exchange.url}")
	String url;

	Map<String, ExchangePrice> priceMap;

	@PostConstruct
	public void init(){
		priceMap = new ConcurrentHashMap<>();
	}

	@Override
	public ExchangePrice getQuote(String from, String to) throws PriceNotFoundException {
		String pair = from.concat(to);
		ExchangePrice exchangePrice = priceMap.get(pair);
		if(Objects.nonNull(exchangePrice)) {
			Instant instant = Instant.ofEpochSecond(exchangePrice.getTimestamp());
			long until = Instant.now().until(instant, ChronoUnit.SECONDS);
			if(until> Long.valueOf(timeThreshold)){
				getExchangePriceFromAPI(pair);
			} else
				return exchangePrice;
		}
		return getExchangePriceFromAPI(pair);
	}

	private ExchangePrice getExchangePriceFromAPI(String pair) throws PriceNotFoundException {
		ResponseEntity<ExchangePrice[]> responseEntity = restTemplate
				.getForEntity(url, ExchangePrice[].class, pair, apiKey);
		if( responseEntity.getStatusCode().is2xxSuccessful() &&
			    responseEntity.getBody().length == 1 ) {
			priceMap.put(pair,responseEntity.getBody()[0]);
			return responseEntity.getBody()[0];
		}
		throw new PriceNotFoundException(HttpStatus.SERVICE_UNAVAILABLE,"com.orb.interview.forex.priceNotFound");
	}
}
