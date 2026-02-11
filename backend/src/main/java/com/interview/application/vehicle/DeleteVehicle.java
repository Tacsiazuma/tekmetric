package com.interview.application.vehicle;

import com.interview.application.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteVehicle {

    private final VehicleRepository repository;

    public DeleteVehicle(VehicleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(UUID id) {
        repository.findById(id).ifPresent(v -> repository.deleteById(id));
    }
}
