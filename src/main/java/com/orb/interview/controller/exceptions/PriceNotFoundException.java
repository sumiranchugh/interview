package com.orb.interview.controller.exceptions;

import com.orb.interview.model.InterviewException;
import org.springframework.http.HttpStatus;

public class PriceNotFoundException extends InterviewException {
	public PriceNotFoundException(HttpStatus httpStatus, String messageKey) {
		super(httpStatus, messageKey);
	}
}
