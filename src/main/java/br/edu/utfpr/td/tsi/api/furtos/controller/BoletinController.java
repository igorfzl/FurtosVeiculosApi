package br.edu.utfpr.td.tsi.api.furtos.controller;

import br.edu.utfpr.td.tsi.api.furtos.modelo.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.api.furtos.service.BoletimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boletins")
@CrossOrigin("*")
public class BoletinController {

    @Autowired
    private BoletimService boletimService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody BoletimFurtoVeiculo boletim) {
        try {
            BoletimFurtoVeiculo boCadastrado = boletimService.cadastrar(boletim);
            return ResponseEntity.status(HttpStatus.CREATED).body(boCadastrado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<BoletimFurtoVeiculo>> listarTodos(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String cor,
            @RequestParam(required = false) String tipoVeiculo) {

        List<BoletimFurtoVeiculo> lista = boletimService.listarTodos(placa, cor, tipoVeiculo);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable String id) {
        try {
            BoletimFurtoVeiculo bo = boletimService.buscarPorId(id);
            if (bo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Boletim não encontrado.");
            }
            return ResponseEntity.ok().body(bo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody BoletimFurtoVeiculo boletim) {
        try {
            boletim.setIdentificador(id);
            BoletimFurtoVeiculo boAtualizado = boletimService.atualizar(boletim);
            return ResponseEntity.ok().body(boAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable String id) {
        try {
            boolean removido = boletimService.remover(id);

            if (removido) {
                return ResponseEntity.ok().body("Boletim removido com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Boletim não encontrado para exclusão.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}