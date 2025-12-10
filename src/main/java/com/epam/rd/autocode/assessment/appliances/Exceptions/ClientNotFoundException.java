package com.epam.rd.autocode.assessment.appliances.Exceptions;


public class ClientNotFoundException extends RuntimeException{
    private static final String default_message = "Client you requested was not found.";
    public ClientNotFoundException(){
        super(default_message);
    }

    public ClientNotFoundException(String OtherInformation){
        super(default_message + " " + OtherInformation);

    }

}
