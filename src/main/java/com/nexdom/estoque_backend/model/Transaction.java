package com.nexdom.estoque_backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.nexdom.estoque_backend.enums.TransactionStatus;
import com.nexdom.estoque_backend.enums.TipoMovimentacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transacoes")
@Data
@Builder
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "total_produtos")
	private Integer totalProdutos;
	
	@Column(name = "total_preco")
	private BigDecimal totalPreco;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_movimentacao")
	private TipoMovimentacao tipoMovimentacao; //entrada, saída, retorno
	
	@Enumerated(EnumType.STRING)
	private TransactionStatus status; //pendente, concluído, processando
	
	private String descricao;
	private String nota;
	
	@Column(name = "criado_em")
	private final LocalDateTime criadoEm = LocalDateTime.now();
	
	@Column(name = "atualizado_em")
	private LocalDateTime atualizadoEm;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "produto_id")
	private Produto produto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fornecedor_id")
	private Fornecedor fornecedor;

	@Override
	public String toString() {
		return "Transacao [id=" + id + 
				", totalProdutos=" + totalProdutos +
				", totalPreco=" + totalPreco +
				", tipoMovimentacao=" + tipoMovimentacao + 
				", status=" + status + 
				", descricao=" + descricao + 
				", nota=" + nota + 
				", criadoEm=" + criadoEm + 
				", atualizadoEm=" + atualizadoEm + "]";
	}


}
