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

import com.nexdom.estoque_backend.dto.FornecedorDTO;
import com.nexdom.estoque_backend.dto.Resposta;
import com.nexdom.estoque_backend.services.FornecedorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {
	
	private final FornecedorService fornecedorService;
	
	@PostMapping("/add")
	public ResponseEntity<Resposta> addFornecedor(@RequestBody @Valid FornecedorDTO fornecedorDTO) {
		return ResponseEntity.ok(fornecedorService.addFornecedor(fornecedorDTO));
	}
	
	@GetMapping("/all")
	public ResponseEntity<Resposta> getAllFornecedores() {
		return ResponseEntity.ok(fornecedorService.getAllFornecedor());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Resposta> getFornecedorById(@PathVariable Long id) {
		return ResponseEntity.ok(fornecedorService.getFornecedorById(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Resposta> updateFornecedor(@PathVariable Long id, @RequestBody @Valid FornecedorDTO fornecedorDTO) {
		return ResponseEntity.ok(fornecedorService.updateFornecedor(id, fornecedorDTO));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Resposta> deleteFornecedor(@PathVariable Long id) {
		return ResponseEntity.ok(fornecedorService.deleteFornecedor(id));
	}

}
