package com.loja.loja.MyExceptions;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(){
        super();
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
