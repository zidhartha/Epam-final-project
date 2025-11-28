package com.epam.rd.autocode.assessment.appliances.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name="employee")
public class Employee extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    public Employee(Long id,String name,String email,String password,String department){
        super(id,name,email,password);
        this.department = department;
    }
    @Column(name="department")
    private String department;
}
