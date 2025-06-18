package com.nexdom.estoque_backend.services;

import com.nexdom.estoque_backend.dto.ProdutoDTO;
import com.nexdom.estoque_backend.dto.Resposta;

public interface ProdutoService {
	
	Resposta salvarProduto(ProdutoDTO produtoDTO);
	
	Resposta atualizarProduto(ProdutoDTO produtoDTO);
	
	Resposta getAllProdutos();
	
	Resposta getProdutoById(Long id);
	
	Resposta getProdutoByTipo(Long id);
	
	Resposta deleteProduto(Long id);
	
	Resposta searchProduto(String input);

}
