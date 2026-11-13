package com.pm.labservice.model

import java.time.LocalDateTime
import java.util.UUID

import jakarta.persistence.*

@Entity
@Table(name = "lab_tests")
class LabTest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null;

    @Column(nullable = false, unique = true)
    lateinit var requestId: String;


    @Column(nullable = false)
    lateinit var patientId: String;

    @Column(nullable = false)
    lateinit var practitionerId: String;

    @Column(nullable = false)
    lateinit var testType: String;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var status: LabTestStatus;

    @Column
    var result: String? = null;

    @Column
    var resultReference: String? = null;

    @Column(nullable = false)
    var requestedAt: LocalDateTime? = null;

    var completedAt: LocalDateTime? = null;
}
