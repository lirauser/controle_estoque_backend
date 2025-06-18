package com.nexdom.estoque_backend.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransactionRequest {
	
	@Positive(message = "Id do produto é obrigatório.")
	private Long produtoId;
	
	@Positive(message = "Quantidade é obrigatória.")
	private Integer qtd;	
	
	private Long fornecedorId;
	
	private String descricao;	

}
