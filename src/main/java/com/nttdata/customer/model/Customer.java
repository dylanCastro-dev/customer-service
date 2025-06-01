package com.nttdata.customer.model;

import com.nttdata.customer.model.Type.CustomerType;
import com.nttdata.customer.model.Type.ProfileType;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String id;

    private String name;

    private String documentNumber;

    private CustomerType type;

    private ProfileType profile;
}
