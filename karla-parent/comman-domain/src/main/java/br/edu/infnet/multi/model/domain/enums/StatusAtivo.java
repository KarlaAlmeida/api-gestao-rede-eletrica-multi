package br.edu.infnet.multi.model.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusAtivo {
    ATIVO, INATIVO, EM_MANUTENCAO;

    @JsonCreator
    public static StatusAtivo fromString(String value) {
        for (StatusAtivo statusAtivo : StatusAtivo.values()) {
            if (statusAtivo.name().equalsIgnoreCase(value)) {
                return statusAtivo;
            }
        }
        throw new IllegalArgumentException(
                "Status " + value + " inválido. O Status deve ser ATIVO, INATIVO ou EM_MANUTENCAO.");
    }
}
