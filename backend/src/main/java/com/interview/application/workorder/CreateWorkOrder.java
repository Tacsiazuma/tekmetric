package com.interview.application.workorder;

import com.interview.application.WorkOrderRepository;
import com.interview.domain.WorkOrder;
import com.interview.domain.WorkOrderStatus;

import java.time.Instant;
import java.util.UUID;

public class CreateWorkOrder {

    private final WorkOrderRepository repository;

    public CreateWorkOrder(WorkOrderRepository repository) {
        this.repository = repository;
    }

    public WorkOrder execute(UUID customerId, UUID vehicleId, String description) {
        WorkOrder workOrder = new WorkOrder(
                UUID.randomUUID(),
                customerId,
                vehicleId,
                description,
                WorkOrderStatus.OPEN,
                Instant.now()
        );
        return repository.save(workOrder);
    }
}
