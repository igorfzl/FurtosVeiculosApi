package br.edu.utfpr.td.tsi.api.furtos.modelo;

public class Veiculo {
    private Integer anoFabricacao;
    private String cor;

    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public Emplacamento getEmplacamento() {
        return emplacamento;
    }

    public void setEmplacamento(Emplacamento emplacamento) {
        this.emplacamento = emplacamento;
    }

    private String marca;
    private TipoVeiculo tipoVeiculo;
    private Emplacamento emplacamento;
}
