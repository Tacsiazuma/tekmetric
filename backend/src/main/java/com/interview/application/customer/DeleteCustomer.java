package com.interview.application.customer;

import com.interview.application.CustomerRepository;
import com.interview.application.ReferencedEntityException;
import com.interview.application.VehicleRepository;
import com.interview.application.WorkOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteCustomer {

    private final CustomerRepository repository;
    private final VehicleRepository vehicleRepository;
    private final WorkOrderRepository workOrderRepository;

    public DeleteCustomer(CustomerRepository repository, VehicleRepository vehicleRepository,
                          WorkOrderRepository workOrderRepository) {
        this.repository = repository;
        this.vehicleRepository = vehicleRepository;
        this.workOrderRepository = workOrderRepository;
    }

    @Transactional
    public void execute(UUID id) {
        if (vehicleRepository.existsByCustomerId(id) || workOrderRepository.existsByCustomerId(id)) {
            throw new ReferencedEntityException("Cannot delete customer: it is referenced by vehicles or work orders.");
        }
        repository.findById(id).ifPresent(c -> repository.deleteById(id));
    }
}
