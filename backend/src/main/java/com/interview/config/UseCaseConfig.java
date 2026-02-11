package com.interview.config;

import com.interview.application.CustomerRepository;
import com.interview.application.VehicleRepository;
import com.interview.application.WorkOrderRepository;
import com.interview.application.customer.CreateCustomer;
import com.interview.application.customer.DeleteCustomer;
import com.interview.application.customer.GetCustomer;
import com.interview.application.customer.ListCustomers;
import com.interview.application.customer.UpdateCustomer;
import com.interview.application.vehicle.CreateVehicle;
import com.interview.application.vehicle.DeleteVehicle;
import com.interview.application.vehicle.GetVehicle;
import com.interview.application.vehicle.ListVehicles;
import com.interview.application.vehicle.UpdateVehicle;
import com.interview.application.workorder.CreateWorkOrder;
import com.interview.application.workorder.DeleteWorkOrder;
import com.interview.application.workorder.GetWorkOrder;
import com.interview.application.workorder.ListWorkOrders;
import com.interview.application.workorder.UpdateWorkOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateCustomer createCustomer(CustomerRepository repository) {
        return new CreateCustomer(repository);
    }

    @Bean
    public GetCustomer getCustomer(CustomerRepository repository) {
        return new GetCustomer(repository);
    }

    @Bean
    public UpdateCustomer updateCustomer(CustomerRepository repository) {
        return new UpdateCustomer(repository);
    }

    @Bean
    public ListCustomers listCustomers(CustomerRepository repository) {
        return new ListCustomers(repository);
    }

    @Bean
    public DeleteCustomer deleteCustomer(CustomerRepository customerRepository,
                                         VehicleRepository vehicleRepository,
                                         WorkOrderRepository workOrderRepository) {
        return new DeleteCustomer(customerRepository, vehicleRepository, workOrderRepository);
    }

    @Bean
    public CreateVehicle createVehicle(VehicleRepository repository) {
        return new CreateVehicle(repository);
    }

    @Bean
    public GetVehicle getVehicle(VehicleRepository repository) {
        return new GetVehicle(repository);
    }

    @Bean
    public UpdateVehicle updateVehicle(VehicleRepository repository) {
        return new UpdateVehicle(repository);
    }

    @Bean
    public ListVehicles listVehicles(VehicleRepository repository) {
        return new ListVehicles(repository);
    }

    @Bean
    public DeleteVehicle deleteVehicle(VehicleRepository vehicleRepository,
                                        WorkOrderRepository workOrderRepository) {
        return new DeleteVehicle(vehicleRepository, workOrderRepository);
    }

    @Bean
    public CreateWorkOrder createWorkOrder(WorkOrderRepository repository) {
        return new CreateWorkOrder(repository);
    }

    @Bean
    public GetWorkOrder getWorkOrder(WorkOrderRepository repository) {
        return new GetWorkOrder(repository);
    }

    @Bean
    public UpdateWorkOrder updateWorkOrder(WorkOrderRepository repository) {
        return new UpdateWorkOrder(repository);
    }

    @Bean
    public ListWorkOrders listWorkOrders(WorkOrderRepository repository) {
        return new ListWorkOrders(repository);
    }

    @Bean
    public DeleteWorkOrder deleteWorkOrder(WorkOrderRepository repository) {
        return new DeleteWorkOrder(repository);
    }
}
