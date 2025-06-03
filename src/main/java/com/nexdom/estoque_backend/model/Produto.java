package com.nexdom.estoque_backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produtos")
@Data
@Builder
public class Produto {			
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;	
	
	@NotBlank(message = "Produto é obrigatório.")
	private String name;	
	
	@Positive(message = "O preço do produtor deve ter valor positivo.")
	private BigDecimal preco;		
	
	@Min(value = 0, message = "Quantidade em estoque não pode ser negativa.")
	@Column(name = "qtd_estoque")
	private Integer qtdEstoque;	
	
	private String description;
	
	@Column(name = "data_expiracao")
	private LocalDateTime dataExpiracao;
	
	@Column(name = "criado_em")
	private final LocalDateTime criadoEm = LocalDateTime.now();
	
	@Column(name = "imagem_url")
	private String imagemUrl;
	
	@ManyToOne
	@JoinColumn(name = "tipo_id")
	private TipoProduto tipoProduto;

	@Override
	public String toString() {
		return "Produto [id=" + id + 
				", name=" + name + 				
				", preco=" + preco + 
				", qtdEstoque=" + qtdEstoque + 
				", description=" + description + 
				", dataExpiracao=" + dataExpiracao + 
				", criadoEm=" + criadoEm + 
				", imagemUrl=" + imagemUrl + "]";
	}	

}



