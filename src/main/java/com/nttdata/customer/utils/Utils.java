package com.nttdata.customer.utils;

import org.openapitools.model.CustomerBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static void validateCustomerBody(CustomerBody body) {
        if (body == null) {
            throw new IllegalArgumentException("El cuerpo del cliente no puede ser nulo.");
        }

        if (body.getName() == null || body.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio.");
        }

        if (body.getDocumentNumber() == null || body.getDocumentNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de documento es obligatorio.");
        }

        if (!body.getDocumentNumber().matches("\\d{8}|\\d{11}")) {
            throw new IllegalArgumentException("El número de documento debe tener 8 o 11 dígitos numéricos.");
        }

        if (body.getType() == null || body.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de cliente es obligatorio.");
        }

        if (!body.getType().equalsIgnoreCase("PERSONAL") && !body.getType().equalsIgnoreCase("BUSINESS")) {
            throw new IllegalArgumentException("El tipo de cliente debe ser PERSONAL o BUSINESS.");
        }
    }
}
