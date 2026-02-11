package com.interview.application.vehicle;

import com.interview.application.VehicleRepository;
import com.interview.domain.Vehicle;

import java.util.UUID;

public class CreateVehicle {

    private final VehicleRepository repository;

    public CreateVehicle(VehicleRepository repository) {
        this.repository = repository;
    }

    public Vehicle execute(String plateNumber, String model, UUID customerId) {
        Vehicle vehicle = new Vehicle(UUID.randomUUID(), plateNumber, model, customerId);
        return repository.save(vehicle);
    }
}
