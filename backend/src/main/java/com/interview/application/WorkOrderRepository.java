package com.interview.application;

import com.interview.domain.WorkOrder;
import com.interview.domain.WorkOrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkOrderRepository {

    WorkOrder save(WorkOrder workOrder);

    Optional<WorkOrder> findById(UUID id);

    List<WorkOrder> findAll(Optional<WorkOrderStatus> status);

    void deleteById(UUID id);
}
