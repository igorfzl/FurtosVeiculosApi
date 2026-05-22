package br.edu.utfpr.td.tsi.api.furtos.service;

import br.edu.utfpr.td.tsi.api.furtos.modelo.*;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BoletimService {

    private List<BoletimFurtoVeiculo> boletins = new ArrayList<>();

    @PostConstruct
    public void carregarDadosDoCsv() {
        String caminhoArquivo = "furtos.csv";

        DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue; // Pula o cabeçalho
                }

                String[] dados = linha.split("\t", -1);

                if (dados.length < 52) continue;

                try {
                    Emplacamento emplacamento = new Emplacamento();
                    emplacamento.setPlaca(dados[44].trim());
                    emplacamento.setEstado(parseEstado(dados[45]));
                    emplacamento.setCidade(dados[46].trim());

                    Veiculo veiculo = new Veiculo();
                    veiculo.setCor(dados[47].trim());
                    veiculo.setMarca(dados[48].trim());
                    veiculo.setTipoVeiculo(parseTipoVeiculo(dados[51]));

                    if (!dados[49].trim().isEmpty()) {
                        veiculo.setAnoFabricacao(Integer.parseInt(dados[49].trim()));
                    }
                    veiculo.setEmplacamento(emplacamento);

                    Endereco endereco = new Endereco();
                    endereco.setLogradouro(dados[13].trim());
                    endereco.setNumero(dados[14].trim());
                    endereco.setBairro(dados[15].trim());
                    endereco.setCidade(dados[16].trim());
                    endereco.setEstado(parseEstado(dados[17]));

                    BoletimFurtoVeiculo boletim = new BoletimFurtoVeiculo();
                    boletim.setIdentificador(UUID.randomUUID().toString());
                    boletim.setLocalOcorrencia(endereco);
                    boletim.setVeiculoFurtado(veiculo);
                    boletim.setPeriodoOcorrencia(parsePeriodo(dados[7]));

                    if (!dados[5].trim().isEmpty()) {
                        boletim.setDataOcorrencia(LocalDate.parse(dados[5].trim(), formatoBr));
                    }

                    boletins.add(boletim);

                } catch (Exception e) {

                    System.err.println("Erro ao processar linha do CSV. Placa: " + dados[44]);
                }
            }

            System.out.println("Carga inicial concluída com sucesso!");
            System.out.println("Total de boletins carregados na memória: " + boletins.size());

        } catch (IOException e) {
            System.err.println("Erro fatal ao tentar ler o arquivo furtos.csv: " + e.getMessage());
        }

    }


    public List<BoletimFurtoVeiculo> listarTodos() {
        return this.boletins;

    }


    public BoletimFurtoVeiculo cadastrar(BoletimFurtoVeiculo novoBoletim) {
        validarRegrasDeNegocio(novoBoletim);
        novoBoletim.setIdentificador(UUID.randomUUID().toString());
        this.boletins.add(novoBoletim);
        return novoBoletim;
    }


    private void validarRegrasDeNegocio(BoletimFurtoVeiculo bo) {

        if (bo.getVeiculoFurtado() == null || bo.getVeiculoFurtado().getEmplacamento() == null) {
            throw new IllegalArgumentException("Os dados do veículo e o emplacamento são obrigatórios.");
        }
        if (bo.getVeiculoFurtado().getEmplacamento().getPlaca() == null ||
                bo.getVeiculoFurtado().getEmplacamento().getPlaca().trim().isEmpty()) {
            throw new IllegalArgumentException("A placa do veículo é de preenchimento obrigatório.");
        }

        if (bo.getDataOcorrencia() != null && bo.getDataOcorrencia().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data da ocorrência não pode ser uma data futura.");
        }

        if (bo.getPartes() != null) {
            for (Parte parte : bo.getPartes()) {
                String email = parte.getEmail();
                // Uma validação simples: se o e-mail foi preenchido, ele TEM que ter um "@"
                if (email != null && !email.trim().isEmpty() && !email.contains("@")) {
                    throw new IllegalArgumentException("O formato do e-mail da vítima é inválido.");
                }
            }
        }
    }


    //Converter nos enums
    private Estado parseEstado(String sigla) {
        if (sigla == null || sigla.trim().isEmpty()) return null;
        try {
            return Estado.valueOf(sigla.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // Retorna nulo se o estado não for válido
        }
    }

    private TipoVeiculo parseTipoVeiculo(String tipoStr) {
        if (tipoStr == null || tipoStr.trim().isEmpty()) return null;
        String t = tipoStr.trim().toUpperCase();
        if (t.contains("AUTOMOVEL") || t.contains("CARRO")) return TipoVeiculo.CARRO;
        if (t.contains("MOTO")) return TipoVeiculo.MOTO;
        if (t.contains("CAMINHAO") || t.contains("REBOQUE")) return TipoVeiculo.CAMINHAO;
        return TipoVeiculo.OUTROS;
    }

    private PeriodoOcorrencia parsePeriodo(String periodoStr) {
        if (periodoStr == null || periodoStr.trim().isEmpty()) return null;
        String p = periodoStr.trim().toUpperCase();

        return switch (p) {
            case "A NOITE" -> PeriodoOcorrencia.A_NOITE;
            case "A TARDE" -> PeriodoOcorrencia.A_TARDE;
            case "A MANHÃ", "PELA MANHÃ" -> PeriodoOcorrencia.A_MANHA;
            case "DE MADRUGADA" -> PeriodoOcorrencia.DE_MADRUGADA;
            case "EM HORA INCERTA" -> PeriodoOcorrencia.EM_HORA_INCERTA;
            default -> null;
        };

    }
}