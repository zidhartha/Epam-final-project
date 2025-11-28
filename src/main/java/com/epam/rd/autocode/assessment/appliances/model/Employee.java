package com.epam.rd.autocode.assessment.appliances.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends User{
    public Employee(Long id,String name,String email,String password,String department){
        super(id,name,email,password);
        this.department = department;
    }
    @Column(name="department")
    private String department;
}
