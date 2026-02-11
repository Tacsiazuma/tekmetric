package com.interview.application.vehicle;

import com.interview.application.EntityNotFoundException;
import com.interview.application.VehicleRepository;
import com.interview.domain.Vehicle;

import java.util.UUID;

public class UpdateVehicle {

    private final VehicleRepository repository;

    public UpdateVehicle(VehicleRepository repository) {
        this.repository = repository;
    }

    public Vehicle execute(UUID id, String plateNumber, String model, UUID customerId) {
        Vehicle existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found: " + id));
        Vehicle updated = new Vehicle(existing.getId(), plateNumber, model, customerId);
        return repository.save(updated);
    }
}
