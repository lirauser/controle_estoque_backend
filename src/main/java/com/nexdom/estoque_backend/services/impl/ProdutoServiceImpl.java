package com.nexdom.estoque_backend.services.impl;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nexdom.estoque_backend.dto.ProdutoDTO;
import com.nexdom.estoque_backend.dto.Resposta;
import com.nexdom.estoque_backend.exception.NotFoundException;
import com.nexdom.estoque_backend.model.Produto;
import com.nexdom.estoque_backend.model.TipoProduto;
import com.nexdom.estoque_backend.repository.ProdutoRepository;
import com.nexdom.estoque_backend.repository.TipoProdutoRepository;
import com.nexdom.estoque_backend.services.ProdutoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutoServiceImpl implements ProdutoService {
	
	private final ProdutoRepository produtoRepository;
	private final ModelMapper modelMapper;
	private final TipoProdutoRepository tipoProdutoRepository;

    @Override
    public Resposta salvarProduto(ProdutoDTO produtoDTO) {

        TipoProduto tipoProduto = tipoProdutoRepository.findById(produtoDTO.getTipoProdutoId())
                .orElseThrow(() -> new NotFoundException("TipoProduto não encontrado."));

        //mapeia o DTO para a entidade Produto
        Produto produtoToSave = Produto.builder()
        		.codigo(produtoDTO.getCodigo())
                .name(produtoDTO.getName())          
                .preco(produtoDTO.getPreco())
                .qtdEstoque(produtoDTO.getQtdEstoque())
                .description(produtoDTO.getDescription())
                .tipoProduto(tipoProduto)
                .build();       

        //save the produto entity
        produtoRepository.save(produtoToSave);

        return Resposta.builder()
                .status(200)
                .mensagem("Produto salvo com sucesso.")
                .build();
    }

    @Override
    public Resposta atualizarProduto(ProdutoDTO produtoDTO) {

        //Verifica se um produto existe
        Produto existingProduto = produtoRepository.findById(produtoDTO.getProdutoId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));     

        //verifica se tipoProduto será mudado para os produtos
        if (produtoDTO.getTipoProdutoId() != null && produtoDTO.getTipoProdutoId() > 0) {
            TipoProduto tipoProduto = tipoProdutoRepository.findById(produtoDTO.getTipoProdutoId())
                    .orElseThrow(() -> new NotFoundException("TipoProduto não encontrado"));
            existingProduto.setTipoProduto(tipoProduto);
        }

        //verifica se os campos do produto serão mudados e atualizados
        if (produtoDTO.getCodigo() != null && !produtoDTO.getCodigo().isBlank()) {
        	existingProduto.setCodigo(produtoDTO.getCodigo());
        }
        
        if (produtoDTO.getName() != null && !produtoDTO.getName().isBlank()) {
            existingProduto.setName(produtoDTO.getName());
        }       

        if (produtoDTO.getDescription() != null && !produtoDTO.getDescription().isBlank()) {
            existingProduto.setDescription(produtoDTO.getDescription());
        }

        if (produtoDTO.getPreco() != null && produtoDTO.getPreco().compareTo(BigDecimal.ZERO) >= 0) {
            existingProduto.setPreco(produtoDTO.getPreco());
        }

        if (produtoDTO.getQtdEstoque() != null && produtoDTO.getQtdEstoque() >= 0) {
            existingProduto.setQtdEstoque(produtoDTO.getQtdEstoque());
        }
        //update the produto
        produtoRepository.save(existingProduto);

        //Build our response
        return Resposta.builder()
                .status(200)
                .mensagem("Produto atualizado com sucesso.")
                .build();
    }

    @Override
    public Resposta getAllProdutos() {

    	List<Produto> produtoList = produtoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<ProdutoDTO> produtoDTOList = modelMapper.map(produtoList, new TypeToken<List<ProdutoDTO>>() {
        }.getType());

        return Resposta.builder()
                .status(200)
                .mensagem("success")
                .produtos(produtoDTOList)
                .build();
    }

    @Override
    public Resposta getProdutoById(Long id) {

    	Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));

        return Resposta.builder()
                .status(200)
                .mensagem("sucesso")
                .produto(modelMapper.map(produto, ProdutoDTO.class))
                .build();
    }
    
    @Override
    public Resposta getProdutoByTipo(Long tipoId) {

    	Produto produto = produtoRepository.findById(tipoId)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));

        return Resposta.builder()
                .status(200)
                .mensagem("sucesso")
                .produto(modelMapper.map(produto, ProdutoDTO.class))
                .build();
    }

    @Override
    public Resposta deleteProduto(Long id) {

        produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));

        produtoRepository.deleteById(id);

        return Resposta.builder()
                .status(200)
                .mensagem("Produto removido com sucesso.")
                .build();
    }

    @Override
    public Resposta searchProduto(String input) {

        List<Produto> produtos = produtoRepository.findByNameContainingOrDescriptionContaining(input, input);

        if (produtos.isEmpty()) {
            throw new NotFoundException("Produto não encontrado.");
        }

        List<ProdutoDTO> produtoDTOList = modelMapper.map(produtos, new TypeToken<List<ProdutoDTO>>() {
        }.getType());

        return Resposta.builder()
                .status(200)
                .mensagem("success")
                .produtos(produtoDTOList)
                .build();
    }  
   
    
  }
