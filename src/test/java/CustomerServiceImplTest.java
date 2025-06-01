
import com.nttdata.customer.model.Customer;
import com.nttdata.customer.model.Type.CustomerType;
import com.nttdata.customer.model.Type.ProfileType;
import com.nttdata.customer.repository.CustomerRepository;
import com.nttdata.customer.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId("1");
        customer.setName("Juan");
        customer.setDocumentNumber("12345678");
        customer.setType(CustomerType.PERSONAL);
        customer.setProfile(ProfileType.VIP);
    }

    /**
     * Verifica que getAllCustomers retorne correctamente una lista de clientes.
     */
    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Flux.just(customer));

        var result = customerService.getAllCustomers().collectList().block();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getName());
    }

    /**
     * Verifica que getCustomerById devuelva el cliente correcto cuando existe.
     */
    @Test
    void testGetCustomerById() {
        when(customerRepository.findById("1")).thenReturn(Mono.just(customer));

        var result = customerService.getCustomerById("1").block();

        assertNotNull(result);
        assertEquals("12345678", result.getDocumentNumber());
    }

    /**
     * Verifica que createCustomer almacene el cliente correctamente.
     */
    @Test
    void testCreateCustomer() {
        when(customerRepository.save(customer)).thenReturn(Mono.just(customer));

        var result = customerService.createCustomer(customer).block();

        assertNotNull(result);
        assertEquals("Juan", result.getName());
    }

    /**
     * Verifica que updateCustomer modifique los campos del cliente y los guarde.
     */
    @Test
    void testUpdateCustomer() {
        Customer updated = new Customer();
        updated.setName("Ana");
        updated.setDocumentNumber("87654321");
        updated.setType(CustomerType.BUSINESS);
        updated.setProfile(ProfileType.VIP);

        when(customerRepository.findById("1")).thenReturn(Mono.just(customer));
        when(customerRepository.save(any())).thenReturn(Mono.just(updated));

        var result = customerService.updateCustomer("1", updated).block();

        assertNotNull(result);
        assertEquals("Ana", result.getName());
        assertEquals("87654321", result.getDocumentNumber());
        assertEquals(CustomerType.BUSINESS, result.getType());
    }

    /**
     * Verifica que deleteCustomer elimine correctamente el cliente por ID.
     */
    @Test
    void testDeleteCustomer() {
        when(customerRepository.deleteById("1")).thenReturn(Mono.empty());

        var result = customerService.deleteCustomer("1").block();

        assertNull(result); // Mono<Void> devuelve null al bloquear
    }
}