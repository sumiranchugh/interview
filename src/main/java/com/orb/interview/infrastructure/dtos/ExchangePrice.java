package com.orb.interview.infrastructure.dtos;

import lombok.Data;

@Data
public class ExchangePrice {

	String symbol;
	Float bid;
	Float price;
	Float ask;
	long timestamp;
}
