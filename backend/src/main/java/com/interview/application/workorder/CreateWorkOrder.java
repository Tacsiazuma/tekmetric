package com.interview.application.workorder;

import com.interview.application.WorkOrderRepository;
import com.interview.domain.WorkOrder;
import com.interview.domain.WorkOrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class CreateWorkOrder {

    private final WorkOrderRepository repository;

    public CreateWorkOrder(WorkOrderRepository repository) {
        this.repository = repository;
    }

    @Transactional
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
