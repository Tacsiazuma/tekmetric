package com.interview.application.workorder;

import com.interview.application.EntityNotFoundException;
import com.interview.application.WorkOrderRepository;
import com.interview.domain.WorkOrder;
import com.interview.domain.WorkOrderStatus;

import java.util.UUID;

public class UpdateWorkOrder {

    private final WorkOrderRepository repository;

    public UpdateWorkOrder(WorkOrderRepository repository) {
        this.repository = repository;
    }

    public WorkOrder execute(UUID id, UUID customerId, UUID vehicleId, String description, WorkOrderStatus status) {
        WorkOrder existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WorkOrder not found: " + id));
        WorkOrder updated = new WorkOrder(
                existing.getId(),
                customerId,
                vehicleId,
                description,
                status,
                existing.getCreatedAt()
        );
        return repository.save(updated);
    }
}
