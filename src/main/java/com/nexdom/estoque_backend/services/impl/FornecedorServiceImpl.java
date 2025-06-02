package com.nexdom.estoque_backend.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nexdom.estoque_backend.dto.FornecedorDTO;
import com.nexdom.estoque_backend.dto.Resposta;
import com.nexdom.estoque_backend.exception.NotFoundException;
import com.nexdom.estoque_backend.model.Fornecedor;
import com.nexdom.estoque_backend.repository.FornecedorRepository;
import com.nexdom.estoque_backend.services.FornecedorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FornecedorServiceImpl implements FornecedorService {
	
	private final FornecedorRepository fornecedorRepository;
    private final ModelMapper modelMapper;

    @Override
    public Resposta addFornecedor(FornecedorDTO fornecedorDTO) {

        Fornecedor fornecedorToSave = modelMapper.map(fornecedorDTO, Fornecedor.class);

        fornecedorRepository.save(fornecedorToSave);

        return Resposta.builder()
                .status(200)
                .mensagem("Fornecedor salvo com sucesso.")
                .build();
    }

    @Override
    public Resposta updateFornecedor(Long id, FornecedorDTO fornecedorDTO) {

        Fornecedor existingFornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado."));

        if (fornecedorDTO.getNomeFornecedor() != null) existingFornecedor.setNomeFornecedor(fornecedorDTO.getNomeFornecedor());
        if (fornecedorDTO.getContato() != null) existingFornecedor.setContato(fornecedorDTO.getContato());
        if (fornecedorDTO.getEndereco() != null) existingFornecedor.setEndereco(fornecedorDTO.getEndereco());

        fornecedorRepository.save(existingFornecedor);

        return Resposta.builder()
                .status(200)
                .mensagem("Fornecedor foi atualizado com sucesso.")
                .build();
    }

    @Override
    public Resposta getAllFornecedor() {

        List<Fornecedor> fornecedores = fornecedorRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<FornecedorDTO> fornecedorDTOList = modelMapper.map(fornecedores, new TypeToken<List<FornecedorDTO>>() {
        }.getType());

        return Resposta.builder()
                .status(200)
                .mensagem("success")
                .fornecedores(fornecedorDTOList)
                .build();
    }

    @Override
    public Resposta getFornecedorById(Long id) {

        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado"));

        FornecedorDTO fornecedorDTO = modelMapper.map(fornecedor, FornecedorDTO.class);

        return Resposta.builder()
                .status(200)
                .mensagem("sucesso")
                .fornecedor(fornecedorDTO)
                .build();
    }

    @Override
    public Resposta deleteFornecedor(Long id) {

        fornecedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado."));

        fornecedorRepository.deleteById(id);

        return Resposta.builder()
                .status(200)
                .mensagem("Fornecedor foi apagado com sucesso.")
                .build();
    }
}
