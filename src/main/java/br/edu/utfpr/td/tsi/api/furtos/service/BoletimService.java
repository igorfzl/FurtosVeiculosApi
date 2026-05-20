package br.edu.utfpr.td.tsi.api.furtos.service;

import br.edu.utfpr.td.tsi.api.furtos.modelo.*;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BoletimService {

    // Lista que atuará como nossa "Base de Dados" em memória principal
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


    // Método que o Controller usará para buscar os dados
    public List<BoletimFurtoVeiculo> listarTodos() {
        return this.boletins;
    }

    // --- MÉTODOS AUXILIARES PARA CONVERSÃO SEGURA DE ENUMS ---

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

        // Faz a ponte entre os termos do sistema da Polícia e o seu Enum
        if (t.contains("AUTOMOVEL") || t.contains("CARRO")) return TipoVeiculo.CARRO;
        if (t.contains("MOTO")) return TipoVeiculo.MOTO;
        if (t.contains("CAMINHAO") || t.contains("REBOQUE")) return TipoVeiculo.CAMINHAO;

        return TipoVeiculo.OUTROS;
    }

    private PeriodoOcorrencia parsePeriodo(String periodoStr) {
        if (periodoStr == null || periodoStr.trim().isEmpty()) return null;
        String p = periodoStr.trim().toUpperCase();

        switch (p) {
            case "A NOITE":
                return PeriodoOcorrencia.A_NOITE;
            case "A TARDE":
                return PeriodoOcorrencia.A_TARDE;
            case "A MANHÃ":
            case "PELA MANHÃ":
                return PeriodoOcorrencia.A_MANHA;
            case "DE MADRUGADA":
                return PeriodoOcorrencia.DE_MADRUGADA;
            case "EM HORA INCERTA":
                return PeriodoOcorrencia.EM_HORA_INCERTA;
            default:
                return null;
        }

    }


}