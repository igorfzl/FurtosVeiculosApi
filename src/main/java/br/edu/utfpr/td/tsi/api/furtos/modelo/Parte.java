package br.edu.utfpr.td.tsi.api.furtos.modelo;

public class Parte {
    private String nome;
    private String email;
    private String telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TipoEnvolvimento getTipoEnvolvimento() {
        return tipoEnvolvimento;
    }

    public void setTipoEnvolvimento(TipoEnvolvimento tipoEnvolvimento) {
        this.tipoEnvolvimento = tipoEnvolvimento;
    }

    private TipoEnvolvimento tipoEnvolvimento;
}
