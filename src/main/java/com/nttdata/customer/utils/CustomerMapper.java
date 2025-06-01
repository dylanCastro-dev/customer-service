package com.nttdata.customer.utils;


import com.nttdata.customer.model.Customer;
import com.nttdata.customer.model.Type.CustomerType;
import com.nttdata.customer.model.Type.ProfileType;
import org.openapitools.model.CustomerBody;
import org.openapitools.model.CustomerResponse;
import org.openapitools.model.CustomerResponse;
import org.openapitools.model.TemplateResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    /**
     * Convierte una entidad Customer a un CustomerResponse.
     */
    public static CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse()
                .id(customer.getId())
                .name(customer.getName())
                .documentNumber(customer.getDocumentNumber())
                .type(customer.getType().name())
                .profile(customer.getProfile().name());
    }

    /**
     * Convierte un CustomerBody recibido por la API a una entidad Customer.
     */
    public static Customer toCustomer(CustomerBody body) {
        return Customer.builder()
                .name(body.getName())
                .documentNumber(body.getDocumentNumber())
                .type(CustomerType.valueOf(body.getType()))
                .profile(ProfileType.valueOf(body.getProfile()))
                .build();
    }

    /**
     * Convierte una lista de Customer a una respuesta CustomerResponse.
     */
    public static TemplateResponse toResponse(List<Customer> customers, int status, String message) {
        List<CustomerResponse> customerBodies = customers.stream()
                .map(CustomerMapper::toCustomerResponse)
                .collect(Collectors.toList());

        return new TemplateResponse()
                .status(status)
                .message(message)
                .customers(customerBodies);
    }

    /**
     * Convierte un único Customer a una respuesta CustomerResponse con un solo item.
     */
    public static TemplateResponse toResponse(Customer customer, int status, String message) {
        return new TemplateResponse()
                .status(status)
                .message(message)
                .addCustomersItem(toCustomerResponse(customer));
    }

    /**
     * Genera una respuesta vacía con estado y mensaje.
     */
    public static TemplateResponse toResponse(int status, String message) {
        return new TemplateResponse()
                .status(status)
                .message(message)
                .customers(null);
    }
}