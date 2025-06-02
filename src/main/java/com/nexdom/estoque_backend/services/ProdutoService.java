package com.nexdom.estoque_backend.services;

import org.springframework.web.multipart.MultipartFile;

import com.nexdom.estoque_backend.dto.ProdutoDTO;
import com.nexdom.estoque_backend.dto.Resposta;

public interface ProdutoService {
	
	Resposta salvarProduto(ProdutoDTO produtoDTO, MultipartFile imageFile);
	
	Resposta atualizarProduto(ProdutoDTO produtoDTO, MultipartFile imageFile);
	
	Resposta getAllProdutos();
	
	Resposta getProdutoById(Long id);
	
	Resposta deleteProduto(Long id);
	
	Resposta searchProduto(String input);

}
