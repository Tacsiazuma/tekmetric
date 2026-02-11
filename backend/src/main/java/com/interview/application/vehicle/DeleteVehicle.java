package com.interview.application.vehicle;

import com.interview.application.ReferencedEntityException;
import com.interview.application.VehicleRepository;
import com.interview.application.WorkOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteVehicle {

    private final VehicleRepository repository;
    private final WorkOrderRepository workOrderRepository;

    public DeleteVehicle(VehicleRepository repository, WorkOrderRepository workOrderRepository) {
        this.repository = repository;
        this.workOrderRepository = workOrderRepository;
    }

    @Transactional
    public void execute(UUID id) {
        if (workOrderRepository.existsByVehicleId(id)) {
            throw new ReferencedEntityException("Cannot delete vehicle: it is referenced by one or more work orders.");
        }
        repository.findById(id).ifPresent(v -> repository.deleteById(id));
    }
}
