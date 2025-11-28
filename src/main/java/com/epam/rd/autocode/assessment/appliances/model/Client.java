package com.epam.rd.autocode.assessment.appliances.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
@Entity
public class Client extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;
    @Column(name="card")
    private String card;

    public Client(Long id, String firstName, String lastName, String email, String card) {
        super(id, firstName, lastName, email);
        this.card = card;
    }
}
