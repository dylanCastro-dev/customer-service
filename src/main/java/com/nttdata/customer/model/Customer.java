package com.nttdata.customer.model;

import com.nttdata.customer.model.Type.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa a un cliente del banco")
public class Customer {

    @Id
    @Schema(description = "ID único del cliente", example = "663022faca038d5f3b9e02bc", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez", required = true)
    private String name;

    @Schema(description = "Número de documento del cliente", example = "12345678", required = true)
    private String documentNumber;

    @Schema(description = "Tipo de cliente (PERSONAL o BUSINESS)", example = "PERSONAL", required = true)
    private CustomerType type;
}
