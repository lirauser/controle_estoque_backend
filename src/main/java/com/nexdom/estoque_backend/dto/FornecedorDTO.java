package com.nexdom.estoque_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FornecedorDTO {
			
		private Long id;
		
		@NotBlank(message = "Nome do fornecedor é obrigatório.")
		private String nomeFornecedor;	
		
		@NotBlank(message = "Nome do fornecedor é obrigatório.")
		private String contato;
		
		private String endereco;	

}



