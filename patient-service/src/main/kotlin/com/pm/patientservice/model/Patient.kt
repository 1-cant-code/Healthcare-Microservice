package com.pm.patientservice.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.util.*

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null;

    @NotNull
    lateinit var name: String;

    @NotNull
    @Email
    @Column(unique = true)
    lateinit var email: String;

    @NotNull
    lateinit var address: String;

    @NotNull
    lateinit var dateOfBirth: LocalDate;

    @NotNull
    lateinit var registeredDate: LocalDate;


}
