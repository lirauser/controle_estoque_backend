package com.nexdom.estoque_backend.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tipoProdutos")
@Data
@Builder
public class TipoProduto {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório.")
	private String nome;	
	
	@OneToMany(mappedBy = "tipoProduto", cascade = CascadeType.ALL)
	private List<Produto> produtos;

	@Override
	public String toString() {
		return "TipoProduto [id=" + id + ", nome=" + nome + "]";
	}
	
	

}
