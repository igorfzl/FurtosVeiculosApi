package br.edu.utfpr.td.tsi.api.furtos.controller;

import br.edu.utfpr.td.tsi.api.furtos.modelo.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.api.furtos.service.BoletimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boletim")
@CrossOrigin("*")
public class BoletinController {

    @Autowired
    private BoletimService boletimService;

    @PostMapping("/registrar")
    public ResponseEntity<?> cadastrar(@RequestBody BoletimFurtoVeiculo boletim) {
        try {
            BoletimFurtoVeiculo boCadastrado = boletimService.cadastrar(boletim);
            return ResponseEntity.status(HttpStatus.CREATED).body(boCadastrado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<BoletimFurtoVeiculo>> listarTodos() {
        List<BoletimFurtoVeiculo> lista = boletimService.listarTodos();
        return ResponseEntity.ok().body(lista);
    }


}
