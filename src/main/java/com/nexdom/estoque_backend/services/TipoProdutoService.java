package com.nexdom.estoque_backend.services;

import com.nexdom.estoque_backend.dto.Resposta;
import com.nexdom.estoque_backend.dto.TipoProdutoDTO;


public interface TipoProdutoService {
	
	Resposta createTipoProduto(TipoProdutoDTO tipoProdutoDTO);
	
	Resposta getAllTipoProduto();
	
	Resposta getTipoProdutoById(Long id);
	
	Resposta updateTipoProduto(Long id, TipoProdutoDTO tipoProdutoDTO);
	
	Resposta deleteTipoProduto(Long id);

}
