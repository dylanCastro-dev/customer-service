package com.nttdata.customer.controller;

import com.nttdata.customer.model.Customer;
import com.nttdata.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Listar todos los clientes", description = "Devuelve todos los clientes registrados, tanto personales como empresariales")
    @GetMapping
    public Flux<Customer> getAll() {
        return customerService.getAllCustomers();
    }

    @Operation(summary = "Obtener un cliente por ID", description = "Devuelve la información de un cliente específico usando su ID")
    @GetMapping("/{id}")
    public Mono<Customer> getById(
            @Parameter(description = "ID del cliente a buscar", required = true)
            @PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @Operation(summary = "Crear un nuevo cliente", description = "Registra un nuevo cliente personal o empresarial en el sistema")
    @PostMapping
    public Mono<Customer> create(
            @Parameter(description = "Datos del cliente a registrar", required = true)
            @RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @Operation(summary = "Actualizar un cliente", description = "Modifica los datos de un cliente existente a partir de su ID")
    @PutMapping("/{id}")
    public Mono<Customer> update(
            @Parameter(description = "ID del cliente a actualizar", required = true)
            @PathVariable String id,
            @Parameter(description = "Nuevos datos del cliente", required = true)
            @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @Operation(summary = "Eliminar un cliente", description = "Elimina un cliente del sistema por su ID")
    @DeleteMapping("/{id}")
    public Mono<Void> delete(
            @Parameter(description = "ID del cliente a eliminar", required = true)
            @PathVariable String id) {
        return customerService.deleteCustomer(id);
    }
}
