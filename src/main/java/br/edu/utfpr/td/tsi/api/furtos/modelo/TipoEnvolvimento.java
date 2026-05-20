package br.edu.utfpr.td.tsi.api.furtos.modelo;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum TipoEnvolvimento {
    @JsonProperty("Vítima")
    VITIMA,

    @JsonProperty("Testemunha")
    TESTEMUNHA,

    @JsonProperty("Autor")
    AUTOR
}
