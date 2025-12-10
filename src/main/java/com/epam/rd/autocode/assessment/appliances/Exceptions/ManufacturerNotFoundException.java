package com.epam.rd.autocode.assessment.appliances.Exceptions;

public class ManufacturerNotFoundException extends RuntimeException {
    private static final String default_message = "Manufacturer you requested was not found.";
    public ManufacturerNotFoundException(){
        super(default_message);
    }

    public ManufacturerNotFoundException(String OtherInformation){
        super(default_message + " " + OtherInformation);

    }
}
