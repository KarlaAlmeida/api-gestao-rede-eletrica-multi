package br.edu.infnet.multi.model.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusOS {
    ABERTA, EM_ANDAMENTO, CONCLUIDA, CANCELADA;

    @JsonCreator
    public static StatusOS fromString(String value) {
        for (StatusOS status : StatusOS.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException(
                "Status " + value + " inválido. O status deve ser ABERTA, EM_ANDAMENTO, CONCLUIDA ou CANCELADA.");
    }
}
