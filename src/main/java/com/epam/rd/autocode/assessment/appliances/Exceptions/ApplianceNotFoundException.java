package com.epam.rd.autocode.assessment.appliances.Exceptions;

public class ApplianceNotFoundException extends RuntimeException {
    private static final String default_message = "Appliance you requested was not found";

    public ApplianceNotFoundException(){
        super(default_message);
    }

    public ApplianceNotFoundException(String additionalInfo){
        super(default_message + "  " + additionalInfo);
    }
}
