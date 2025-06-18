package com.nexdom.estoque_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoDTO {			
		
		private Long id;
		
		private Long tipoProdutoId;
		private Long produtoId;
		private Long fornecedorId;
		
		private String codigo;
		
		private String name;		
		
		private BigDecimal preco;		
		
		private Integer qtdEstoque;	
		
		private String description;
		
		private LocalDateTime dataExpiracao;
		private LocalDateTime criadoEm;
		
		private String imagemUrl;				

}



