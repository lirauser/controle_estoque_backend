package com.nexdom.estoque_backend.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nexdom.estoque_backend.dto.Resposta;
import com.nexdom.estoque_backend.dto.TipoProdutoDTO;
import com.nexdom.estoque_backend.exception.NotFoundException;
import com.nexdom.estoque_backend.model.TipoProduto;
import com.nexdom.estoque_backend.repository.TipoProdutoRepository;
import com.nexdom.estoque_backend.services.TipoProdutoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TipoProdutoServiceImpl implements TipoProdutoService {
	
	private final TipoProdutoRepository tipoProdutoRepository;
	private final ModelMapper modelMapper;
	
	@Override
    public Resposta createTipoProduto(TipoProdutoDTO tipoProdutoDTO) {

		TipoProduto tipoProdutoToSave = modelMapper.map(tipoProdutoDTO, TipoProduto.class);

		tipoProdutoRepository.save(tipoProdutoToSave);

        return Resposta.builder()
                .status(200)
                .mensagem("Tipo de produto salvo com sucesso!")
                .build();
    }

    @Override
	public Resposta getAllTipoProduto() {
        List<TipoProduto> tipoProdutos = tipoProdutoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        tipoProdutos.forEach(tipoProduto -> tipoProduto.setProdutos(null));

        List<TipoProdutoDTO> tipoProdutoDTOList = modelMapper.map(tipoProdutos, new TypeToken<List<TipoProdutoDTO>>() {
        }.getType());

        return Resposta.builder()
                .status(200)
                .mensagem("success")
                .tipoProdutos(tipoProdutoDTOList)
                .build();
    }

    @Override
    public Resposta getTipoProdutoById(Long id) {

    	TipoProduto tipoProduto = tipoProdutoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de produto não encontrado."));

    	TipoProdutoDTO tipoProdutoDTO = modelMapper.map(tipoProduto, TipoProdutoDTO.class);

        return Resposta.builder()
                .status(200)
                .mensagem("sucesso")
                .tipoProduto(tipoProdutoDTO)
                .build();
    }

    @Override
    public Resposta updateTipoProduto(Long id, TipoProdutoDTO tipoProdutoDTO) {

        TipoProduto existingTipoProduto = tipoProdutoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de produto não encontrado."));

        existingTipoProduto.setNome(tipoProdutoDTO.getNome());

        tipoProdutoRepository.save(existingTipoProduto);

        return Resposta.builder()
                .status(200)
                .mensagem("Tipo de categoria foi atualizado com sucesso")
                .build();

    }

    @Override
    public Resposta deleteTipoProduto(Long id) {

    	tipoProdutoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de produto não encontrado"));

    	tipoProdutoRepository.deleteById(id);

        return Resposta.builder()
                .status(200)
                .mensagem("Tipo de categoria deletado.")
                .build();
    }
}


