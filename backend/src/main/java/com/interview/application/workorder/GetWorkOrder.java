package com.interview.application.workorder;

import com.interview.application.EntityNotFoundException;
import com.interview.application.WorkOrderRepository;
import com.interview.domain.WorkOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetWorkOrder {

    private final WorkOrderRepository repository;

    public GetWorkOrder(WorkOrderRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public WorkOrder execute(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WorkOrder not found: " + id));
    }
}
