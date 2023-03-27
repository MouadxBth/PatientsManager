package me.mouad.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName,
            lastName,
            cin,
            address,
            caseDescription;
    //private LocalDateTime scheduledAt, lastVisit;

    public static boolean isValid(Patient patient) {
        return patient.getFirstName() != null && !patient.getFirstName().isBlank()
                && patient.getLastName() != null && !patient.getLastName().isBlank()
                && patient.getCin() != null && !patient.getCin().isBlank()
                && patient.getCaseDescription() != null && !patient.getCaseDescription().isBlank();
    }
}
