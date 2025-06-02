package com.nexdom.estoque_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexdom.estoque_backend.dto.Resposta;
import com.nexdom.estoque_backend.dto.TipoProdutoDTO;
import com.nexdom.estoque_backend.services.TipoProdutoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tipoProdutos")
@RequiredArgsConstructor
public class TipoProdutoController {
	
	private final TipoProdutoService tipoProdutoService;

    @PostMapping("/add")    
    public ResponseEntity<Resposta> createTipoProduto(@RequestBody @Valid TipoProdutoDTO tipoProdutoDTO) {
        return ResponseEntity.ok(tipoProdutoService.createTipoProduto(tipoProdutoDTO));
    }


    @GetMapping("/all")
    public ResponseEntity<Resposta> getAllCategories() {
        return ResponseEntity.ok(tipoProdutoService.getAllTipoProduto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resposta> getTipoProdutoById(@PathVariable Long id) {
        return ResponseEntity.ok(tipoProdutoService.getTipoProdutoById(id));
    }

    @PutMapping("/update/{id}")    
    public ResponseEntity<Resposta> updateTipoProduto(@PathVariable Long id, @RequestBody @Valid TipoProdutoDTO tipoProdutoDTO) {
        return ResponseEntity.ok(tipoProdutoService.updateTipoProduto(id, tipoProdutoDTO));
    }

    @DeleteMapping("/delete/{id}")    
    public ResponseEntity<Resposta> deleteTipoProduto(@PathVariable Long id) {
        return ResponseEntity.ok(tipoProdutoService.deleteTipoProduto(id));
    }

}
