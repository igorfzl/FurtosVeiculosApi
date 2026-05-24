package br.edu.utfpr.td.tsi.api.furtos.service;

import br.edu.utfpr.td.tsi.api.furtos.modelo.BoletimFurtoVeiculo;
import java.util.List;

public interface BoletimService {

    List<BoletimFurtoVeiculo> listarTodos(String placa, String cor, String tipoVeiculo);

    BoletimFurtoVeiculo buscarPorId(String id);

    BoletimFurtoVeiculo cadastrar(BoletimFurtoVeiculo novoBoletim);

    BoletimFurtoVeiculo atualizar(BoletimFurtoVeiculo boletimEditado);

    boolean remover(String id);
}