package com.epam.rd.autocode.assessment.appliances.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Appliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="category")
    @Enumerated(EnumType.STRING)
    private Category category;


    @Column(name="model")
    private String model;

    @ManyToOne
    private Manufacturer manufacturer;

    @Column(name="power_type")
    @Enumerated(EnumType.STRING)
    private PowerType powerType;

    private String characteristic;

    private String description;

    private Integer power;

    private BigDecimal price;


}
