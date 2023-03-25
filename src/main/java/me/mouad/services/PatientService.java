package me.mouad.services;

import me.mouad.models.Patient;
import me.mouad.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    @Async
    public CompletableFuture<List<Patient>> fetch() {
        return CompletableFuture.supplyAsync(repository::findAll);
    }

    @Async
    public CompletableFuture<Optional<Patient>> get(Long id) {
        return CompletableFuture.supplyAsync(() -> repository.findById(id));
    }

    @Async
    public CompletableFuture<Patient> get(String cin) {
        return CompletableFuture.supplyAsync(() -> repository.findByCin(cin));
    }

    @Async
    public CompletableFuture<Patient> save(Patient trigger) {
        return CompletableFuture.supplyAsync(() -> repository.save(trigger));
    }

    @Async
    public CompletableFuture<Patient> update(Patient entity) {
        return CompletableFuture.supplyAsync(() -> repository.save(entity));
    }

    @Async
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.runAsync(() -> repository.deleteById(id));
    }

    @Async
    public CompletableFuture<Void> delete(Patient trigger) {
        return CompletableFuture.runAsync(() -> repository.delete(trigger));
    }

    @Async
    public CompletableFuture<Page<Patient>> list(Pageable pageable) {
        return CompletableFuture.supplyAsync(() -> repository.findAll(pageable));
    }

    @Async
    public CompletableFuture<Page<Patient>> list(Pageable pageable, Specification<Patient> filter) {
        return CompletableFuture.supplyAsync(() -> repository.findAll(filter, pageable));
    }

    @Async
    public CompletableFuture<Integer> count() {
        return CompletableFuture.supplyAsync(() -> (int) repository.count());
    }

}
