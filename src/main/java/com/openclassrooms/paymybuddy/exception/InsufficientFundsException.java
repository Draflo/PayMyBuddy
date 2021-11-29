package com.openclassrooms.paymybuddy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InsufficientFundsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 360992529070931284L;

	private final String errorCode;
	private final String defaultMessage;
}
