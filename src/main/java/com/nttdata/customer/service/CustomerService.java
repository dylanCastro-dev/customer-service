package com.nttdata.customer.service;

import com.nttdata.customer.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomerService {

    /**
     * Obtiene todos los clientes registrados.
     * @return una secuencia reactiva de clientes.
     */
    Flux<Customer> getAllCustomers();

    /**
     * Obtiene un cliente por su identificador.
     * @param id identificador del cliente.
     * @return un cliente si existe, de lo contrario un Mono vacío.
     */
    Mono<Customer> getCustomerById(String id);

    /**
     * Crea un nuevo cliente.
     * @param customer objeto cliente a crear.
     * @return el cliente creado.
     */
    Mono<Customer> createCustomer(Customer customer);

    /**
     * Actualiza un cliente existente por ID.
     * @param id identificador del cliente a actualizar.
     * @param customer objeto con los datos actualizados.
     * @return el cliente actualizado.
     */
    Mono<Customer> updateCustomer(String id, Customer customer);

    /**
     * Elimina un cliente por su ID.
     * @param id identificador del cliente a eliminar.
     * @return una señal de finalización.
     */
    Mono<Void> deleteCustomer(String id);
}