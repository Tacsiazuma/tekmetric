package com.interview.application.workorder;

import com.interview.application.WorkOrderRepository;
import com.interview.domain.WorkOrder;
import com.interview.domain.WorkOrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ListWorkOrders {

    private final WorkOrderRepository repository;

    public ListWorkOrders(WorkOrderRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<WorkOrder> execute(Optional<WorkOrderStatus> status) {
        return repository.findAll(status);
    }
}
