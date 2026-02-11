package com.interview.application.vehicle;

import com.interview.application.EntityNotFoundException;
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
        if (!repository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Vehicle not found: " + id);
        }
        repository.deleteById(id);
    }
}
