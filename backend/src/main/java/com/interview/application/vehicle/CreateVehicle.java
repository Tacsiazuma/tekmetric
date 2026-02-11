package com.interview.application.vehicle;

import com.interview.application.VehicleRepository;
import com.interview.domain.Vehicle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateVehicle {

    private final VehicleRepository repository;

    public CreateVehicle(VehicleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Vehicle execute(String plateNumber, String model, UUID customerId) {
        Vehicle vehicle = new Vehicle(UUID.randomUUID(), plateNumber, model, customerId);
        return repository.save(vehicle);
    }
}
