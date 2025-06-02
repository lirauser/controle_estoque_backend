package com.nexdom.estoque_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nexdom.estoque_backend.enums.TransactionStatus;
import com.nexdom.estoque_backend.enums.TipoMovimentacao;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {	
	
	private long id;
	
	private Integer totalProdutos;
	
	private BigDecimal totalPreco;	
	
	private TipoMovimentacao tipoMovimentacao;	
	
	private TransactionStatus status;
	
	private String descricao;
	private String nota;
	
	private LocalDateTime criadoEm;
	private LocalDateTime atualizadoEm;	
	
	private ProdutoDTO produtoDTO;			
	
	private FornecedorDTO fornecedorDTO;

}
