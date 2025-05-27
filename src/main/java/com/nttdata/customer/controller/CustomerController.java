package com.nttdata.customer.controller;

import com.nttdata.customer.service.CustomerService;
import com.nttdata.customer.utils.Constants;
import com.nttdata.customer.utils.CustomerMapper;
import com.nttdata.customer.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.CustomersApi;
import org.openapitools.model.CustomerBody;
import org.openapitools.model.TemplateResponse;
import org.openapitools.model.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomersApi {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService service;

    @Override
    public Mono<ResponseEntity<TemplateResponse>> getAllCustomers(ServerWebExchange exchange) {
        return service.getAllCustomers()
                .collectList()
                .map(customers -> CustomerMapper.toResponse(customers, 200, Constants.SUCCESS_FIND_LIST_CUSTOMER))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<TemplateResponse>> getCustomerById(String id, ServerWebExchange exchange) {
        return service.getCustomerById(id)
                .map(customer -> CustomerMapper.toResponse(customer, 200, Constants.SUCCESS_FIND_CUSTOMER))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(CustomerMapper.toResponse(404, Constants.ERROR_FIND_CUSTOMER)));
    }

    @Override
    public Mono<ResponseEntity<TemplateResponse>> createCustomer(
            @RequestBody Mono<CustomerBody> request, ServerWebExchange exchange) {

        return request
                .doOnNext(req -> log.debug("Request recibido: {}", req))
                .doOnNext(Utils::validateCustomerBody)
                .map(CustomerMapper::toCustomer)
                .flatMap(service::createCustomer)
                .map(customer -> CustomerMapper.toResponse(customer, 201, Constants.SUCCESS_CREATE_CUSTOMER))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<TemplateResponse>> updateCustomer(
            String id, @RequestBody Mono<CustomerBody> request, ServerWebExchange exchange) {

        return request
                .doOnNext(req -> log.debug("Request recibido: {}", req))
                .doOnNext(Utils::validateCustomerBody)
                .map(CustomerMapper::toCustomer)
                .flatMap(customer -> service.updateCustomer(id, customer))
                .map(updated -> CustomerMapper.toResponse(updated, 200, Constants.SUCCESS_UPDATE_CUSTOMER))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<TemplateResponse>> deleteCustomer(String id, ServerWebExchange exchange) {
        return service.deleteCustomer(id)
                .thenReturn(CustomerMapper.toResponse(200, Constants.SUCCESS_DELETE_CUSTOMER))
                .map(ResponseEntity::ok);
    }
}
