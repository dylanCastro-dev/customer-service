package com.nttdata.customer.service.impl;

import com.nttdata.customer.model.Customer;
import com.nttdata.customer.repository.CustomerRepository;
import com.nttdata.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Flux<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public Mono<Customer> createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Mono<Customer> updateCustomer(String id, Customer customer) {
        return customerRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(customer.getName());
                    existing.setDocumentNumber(customer.getDocumentNumber());
                    existing.setType(customer.getType());
                    existing.setProfile(customer.getProfile());
                    return customerRepository.save(existing);
                });
    }

    @Override
    public Mono<Void> deleteCustomer(String id) {
        return customerRepository.deleteById(id);
    }
}
