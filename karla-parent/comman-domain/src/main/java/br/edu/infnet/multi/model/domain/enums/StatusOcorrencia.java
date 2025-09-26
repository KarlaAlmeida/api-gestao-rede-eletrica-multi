package br.edu.infnet.multi.model.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusOcorrencia {
    REGISTRADA, ENCAMINHADA, CONCLUIDA;

    @JsonCreator
    public static StatusOcorrencia fromString(String value) {
        for (StatusOcorrencia status : StatusOcorrencia.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException(
                "Status " + value + " inválido. O status deve ser REGISTRADA, ENCAMINHADA ou CONCLUIDA ");
    }

}
