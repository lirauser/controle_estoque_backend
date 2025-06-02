package com.nexdom.estoque_backend.services;

import com.nexdom.estoque_backend.dto.Resposta;
import com.nexdom.estoque_backend.dto.TransactionRequest;
import com.nexdom.estoque_backend.enums.TransactionStatus;

public interface TransactionService {
	
	Resposta darEntrada(TransactionRequest transactionRequest);

    Resposta vender(TransactionRequest transactionRequest);

    Resposta returnToFornecedor(TransactionRequest transactionRequest);

    Resposta getAllTransactions(String filter);

    Resposta getAllTransactionById(Long id);

    Resposta getAllTransactionByMonthAndYear(int month, int year);

    Resposta updateTransactionStatus(Long transactionId, TransactionStatus status);

}
