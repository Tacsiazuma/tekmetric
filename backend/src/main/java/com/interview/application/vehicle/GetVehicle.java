package com.interview.application.vehicle;

import com.interview.application.EntityNotFoundException;
import com.interview.application.VehicleRepository;
import com.interview.domain.Vehicle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetVehicle {

    private final VehicleRepository repository;

    public GetVehicle(VehicleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Vehicle execute(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found: " + id));
    }
}
