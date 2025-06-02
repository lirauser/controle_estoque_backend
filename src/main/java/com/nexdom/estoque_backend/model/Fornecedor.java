package com.nexdom.estoque_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fornecedores")
@Data
@Builder
public class Fornecedor {
			
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@NotBlank(message = "Nome do fornecedor é obrigatório.")
	@Column(name = "nome_fornecedor")
	private String nomeFornecedor;		
	
	@NotBlank(message = "Contato é obrigatório.")
	private String contato;
	
	private String endereco;

}

