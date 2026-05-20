package br.edu.utfpr.td.tsi.api.furtos.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PeriodoOcorrencia {
    @JsonProperty("A NOITE")
    A_NOITE,

    @JsonProperty("A TARDE")
    A_TARDE,

    @JsonProperty("A MANHÃ")
    A_MANHA,

    @JsonProperty("DE MADRUGADA")
    DE_MADRUGADA,

    @JsonProperty("EM HORA INCERTA")
    EM_HORA_INCERTA
}
