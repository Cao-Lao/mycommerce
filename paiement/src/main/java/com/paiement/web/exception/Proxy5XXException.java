package com.paiement.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class Proxy5XXException extends RuntimeException {

	private static final long serialVersionUID = 8645581560813659059L;

	public Proxy5XXException(final String message) {

		super(message);
	}

}