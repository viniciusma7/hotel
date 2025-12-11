package br.ifs.cads.api.hotel.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String error) {
        super(error);
    }
}
