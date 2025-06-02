package com.nexdom.estoque_backend.services;

import com.nexdom.estoque_backend.dto.FornecedorDTO;
import com.nexdom.estoque_backend.dto.Resposta;

public interface FornecedorService {

	Resposta addFornecedor(FornecedorDTO fornecedorDTO);

    Resposta updateFornecedor(Long id, FornecedorDTO fornecedorDTO);

    Resposta getAllFornecedor();

    Resposta getFornecedorById(Long id);

    Resposta deleteFornecedor(Long id);
}
