package com.epam.rd.autocode.assessment.appliances.Exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    private static final String default_message = "Employee you requested was not found.";
    public EmployeeNotFoundException(){
        super(default_message);
    }

    public EmployeeNotFoundException(String OtherInformation){
        super(default_message + " " + OtherInformation);

    }

}
