package com.nttdata.customer.service;

import com.nttdata.customer.model.Customer;
import com.nttdata.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Flux<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Mono<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public Mono<Customer> createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Mono<Customer> updateCustomer(String id, Customer customer) {
        return customerRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(customer.getName());
                    existing.setDocumentNumber(customer.getDocumentNumber());
                    existing.setType(customer.getType());
                    return customerRepository.save(existing);
                });
    }

    public Mono<Void> deleteCustomer(String id) {
        return customerRepository.deleteById(id);
    }
}
