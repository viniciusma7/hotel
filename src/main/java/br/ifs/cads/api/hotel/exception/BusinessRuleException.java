package br.ifs.cads.api.hotel.exception;

public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException(String error) {
        super(error);
    }
}
