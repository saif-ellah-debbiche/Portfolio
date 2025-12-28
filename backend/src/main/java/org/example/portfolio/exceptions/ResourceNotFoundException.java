package org.example.portfolio.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public String message;

    public ResourceNotFoundException(String resource){
        message="no "+resource+" found ";

    }

    public ResourceNotFoundException(String resource,String searchParam ,String searchValue){
        message="no "+resource+" found with "+searchParam+"= "+searchParam ;
    }

}
