package com.nexdom.estoque_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resposta {
	
	//genérico
	private int status;
	private String mensagem;
	
	//para login	
	private String horaExpiracao;
	
	//para paginação
	private Integer totalPaginas;
	private Long totalElementos;	
	
	private FornecedorDTO fornecedor;
	private List<FornecedorDTO> fornecedores;
	
	private TipoProdutoDTO tipoProduto;
	private List<TipoProdutoDTO> tipoProdutos;
	
	private ProdutoDTO produto;
	private List<ProdutoDTO> produtos;
	
	private TransactionDTO transacao;
	private List<TransactionDTO> transacoes;
	
	private final LocalDateTime timestamp = LocalDateTime.now();

}
