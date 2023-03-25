package me.mouad.repositories;

import me.mouad.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PatientRepository extends
        JpaRepository<Patient, Long>,
        JpaSpecificationExecutor<Patient> {

    Patient findByCin(String cin);
}
