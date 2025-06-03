package com.nexdom.estoque_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexdom.estoque_backend.dto.Resposta;
import com.nexdom.estoque_backend.dto.TransactionRequest;
import com.nexdom.estoque_backend.enums.TransactionStatus;
import com.nexdom.estoque_backend.services.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
	
	private final TransactionService transactionService;

    @PostMapping("/compra")
    public ResponseEntity<Resposta> purchaseInventory(@RequestBody @Valid TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.darEntrada(transactionRequest));
    }

    @PostMapping("/venda")
    public ResponseEntity<Resposta> makeSale(@RequestBody @Valid TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.vender(transactionRequest));
    }

    @PostMapping("/retorna")
    public ResponseEntity<Resposta> returnToSupplier(@RequestBody @Valid TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.returnToFornecedor(transactionRequest));
    }
    
    @GetMapping("/all")
    public ResponseEntity<Resposta> getAllTransactions(@RequestParam(required = false) String filter) {
      System.out.println("O valor buscado é: " + filter);
      return ResponseEntity.ok(transactionService.getAllTransactions(filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resposta> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getAllTransactionById(id));
    }

    @GetMapping("/mes-ano")
    public ResponseEntity<Resposta> getTransactionByMonthAndYear(
            @RequestParam int month,
            @RequestParam int year) {

        return ResponseEntity.ok(transactionService.getAllTransactionByMonthAndYear(month, year));
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Resposta> updateTransactionStatus(
            @PathVariable Long transactionId,
            @RequestBody TransactionStatus status) {

        return ResponseEntity.ok(transactionService.updateTransactionStatus(transactionId, status));
    }

}
