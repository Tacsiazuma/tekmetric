package com.interview.application.vehicle;

import com.interview.application.VehicleRepository;
import com.interview.domain.Vehicle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListVehicles {

    private final VehicleRepository repository;

    public ListVehicles(VehicleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Vehicle> execute() {
        return repository.findAll();
    }
}
