package com.nttdata.customer.utils;


import com.nttdata.customer.model.Customer;
import com.nttdata.customer.model.Type.CustomerType;
import org.openapitools.model.CustomerBody;
import org.openapitools.model.CustomerResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    /**
     * Convierte una entidad Customer a un CustomerBody (sin ID).
     */
    public static CustomerBody toCustomerBody(Customer customer) {
        return new CustomerBody()
                .name(customer.getName())
                .documentNumber(customer.getDocumentNumber())
                .type(customer.getType().name());
    }

    /**
     * Convierte un CustomerBody recibido por la API a una entidad Customer.
     */
    public static Customer toCustomer(CustomerBody body) {
        return Customer.builder()
                .name(body.getName())
                .documentNumber(body.getDocumentNumber())
                .type(CustomerType.valueOf(body.getType()))
                .build();
    }

    /**
     * Convierte una lista de Customer a una respuesta CustomerResponse.
     */
    public static CustomerResponse toResponse(List<Customer> customers, int status, String message) {
        List<CustomerBody> customerBodies = customers.stream()
                .map(CustomerMapper::toCustomerBody)
                .collect(Collectors.toList());

        return new CustomerResponse()
                .status(status)
                .message(message)
                .customers(customerBodies);
    }

    /**
     * Convierte un único Customer a una respuesta CustomerResponse con un solo item.
     */
    public static CustomerResponse toResponse(Customer customer, int status, String message) {
        return new CustomerResponse()
                .status(status)
                .message(message)
                .addCustomersItem(toCustomerBody(customer));
    }

    /**
     * Genera una respuesta vacía con estado y mensaje.
     */
    public static CustomerResponse toResponse(int status, String message) {
        return new CustomerResponse()
                .status(status)
                .message(message)
                .customers(null);
    }
}