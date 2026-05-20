package br.edu.utfpr.td.tsi.api.furtos.modelo;

import java.time.LocalDate;
import java.util.List;

public class BoletimFurtoVeiculo {
    private String identificador;
    private LocalDate dataOcorrencia;
    private PeriodoOcorrencia periodoOcorrencia;

    private Endereco localOcorrencia;
    private Veiculo veiculoFurtado;

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public LocalDate getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDate dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public PeriodoOcorrencia getPeriodoOcorrencia() {
        return periodoOcorrencia;
    }

    public void setPeriodoOcorrencia(PeriodoOcorrencia periodoOcorrencia) {
        this.periodoOcorrencia = periodoOcorrencia;
    }

    public Endereco getLocalOcorrencia() {
        return localOcorrencia;
    }

    public void setLocalOcorrencia(Endereco localOcorrencia) {
        this.localOcorrencia = localOcorrencia;
    }

    public Veiculo getVeiculoFurtado() {
        return veiculoFurtado;
    }

    public void setVeiculoFurtado(Veiculo veiculoFurtado) {
        this.veiculoFurtado = veiculoFurtado;
    }

    public List<Parte> getPartes() {
        return partes;
    }

    public void setPartes(List<Parte> partes) {
        this.partes = partes;
    }

    // Uma lista, pois o relacionamento permite várias partes (vítimas, testemunhas)
    private List<Parte> partes;
}
