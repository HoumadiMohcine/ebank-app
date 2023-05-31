package com.ebankapp.controller;

import com.ebankapp.exception.BalanceInsufficientException;
import com.ebankapp.exception.BankAccountUnfoundException;
import com.ebankapp.exception.ClientUnfoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(ClientUnfoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIError handleClientNotFoundException(ClientUnfoundException ex) {
        return new APIError(ex.getMessage());
    }

    @ExceptionHandler(BankAccountUnfoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIError handleBankAccountNotFoundException(BankAccountUnfoundException ex) {
        return new APIError(ex.getMessage());
    }


    @ExceptionHandler(BalanceInsufficientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIError handleBalanceInsufficientException(BalanceInsufficientException ex) {
        return new APIError(ex.getMessage());
    }

}
