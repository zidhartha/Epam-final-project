package com.epam.rd.autocode.assessment.appliances.Exceptions;

public class OrderNotFoundException extends RuntimeException{

    private static final String default_message = "Order you requested was not found.";
    public OrderNotFoundException(){
        super(default_message);
    }

    public OrderNotFoundException(String OtherInformation){
        super(default_message + " " + OtherInformation);

    }
}
