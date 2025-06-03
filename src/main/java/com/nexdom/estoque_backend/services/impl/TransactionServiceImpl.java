package com.nexdom.estoque_backend.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nexdom.estoque_backend.dto.Resposta;
import com.nexdom.estoque_backend.dto.TransactionDTO;
import com.nexdom.estoque_backend.dto.TransactionRequest;
import com.nexdom.estoque_backend.enums.TipoMovimentacao;
import com.nexdom.estoque_backend.enums.TransactionStatus;
import com.nexdom.estoque_backend.exception.NotFoundException;
import com.nexdom.estoque_backend.exception.ValorNomeRequiredException;
import com.nexdom.estoque_backend.model.Fornecedor;
import com.nexdom.estoque_backend.model.Produto;
import com.nexdom.estoque_backend.model.Transaction;
import com.nexdom.estoque_backend.repository.FornecedorRepository;
import com.nexdom.estoque_backend.repository.ProdutoRepository;
import com.nexdom.estoque_backend.repository.TransactionRepository;
import com.nexdom.estoque_backend.services.TransactionService;
import com.nexdom.estoque_backend.specification.FiltroTransaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;   
    private final ModelMapper modelMapper;

    @Override
    public Resposta darEntrada(TransactionRequest transactionRequest) {

        Long produtoId = transactionRequest.getProdutoId();
        Long fornecedorId = transactionRequest.getFornecedorId();
        Integer qtd = transactionRequest.getQtd();

        if (fornecedorId == null) throw new ValorNomeRequiredException("Id do fornecedor é obrigatório.");

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));

        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado."));        

        //update the stock quantity and re-save
        produto.setQtdEstoque(produto.getQtdEstoque() + qtd);
        produtoRepository.save(produto);

        //create a transaction
        Transaction transaction = Transaction.builder()
                .tipoMovimentacao(TipoMovimentacao.ENTRADA)
                .status(TransactionStatus.CONCLUÍDO)
                .produto(produto)                
                .fornecedor(fornecedor)
                .totalProdutos(qtd)
                .totalPreco(produto.getPreco().multiply(BigDecimal.valueOf(qtd)))
                .descricao(transactionRequest.getDescricao())
                .nota(transactionRequest.getNota())
                .build();

        transactionRepository.save(transaction);
        return Resposta.builder()
                .status(200)
                .mensagem("Compra executada com sucesso.")
                .build();

    }

    @Override
    public Resposta vender(TransactionRequest transactionRequest) {

        Long produtoId = transactionRequest.getProdutoId();
        Integer quantity = transactionRequest.getQtd();

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));      

        //update the stock quantity and re-save
        produto.setQtdEstoque(produto.getQtdEstoque() - quantity);
        produtoRepository.save(produto);


        //create a transaction
        Transaction transaction = Transaction.builder()
                .tipoMovimentacao(TipoMovimentacao.SAIDA)
                .status(TransactionStatus.CONCLUÍDO)
                .produto(produto)                
                .totalProdutos(quantity)
                .totalPreco(produto.getPreco().multiply(BigDecimal.valueOf(quantity)))
                .descricao(transactionRequest.getDescricao())
                .nota(transactionRequest.getNota())
                .build();

        transactionRepository.save(transaction);
        return Resposta.builder()
                .status(200)
                .mensagem("Venda de produto feita com sucesso.")
                .build();


    }

    @Override
    public Resposta returnToFornecedor(TransactionRequest transactionRequest) {

        Long produtoId = transactionRequest.getProdutoId();
        Long fornecedorId = transactionRequest.getFornecedorId();
        Integer quantity = transactionRequest.getQtd();

        if (fornecedorId == null) throw new ValorNomeRequiredException("Id do fornecedor é obrigatório.");

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));

        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado."));

        //update the stock quantity and re-save
        produto.setQtdEstoque(produto.getQtdEstoque() - quantity);
        produtoRepository.save(produto);


        //create a transaction
        Transaction transaction = Transaction.builder()
                .tipoMovimentacao(TipoMovimentacao.RETORNO_FORNECEDOR)
                .status(TransactionStatus.PROCESSANDO)
                .produto(produto)              
                .totalProdutos(quantity)
                .totalPreco(BigDecimal.ZERO)
                .descricao(transactionRequest.getDescricao())
                .nota(transactionRequest.getNota())
                .build();

        transactionRepository.save(transaction);

        return Resposta.builder()
                .status(200)
                .mensagem("Produto retornado em progresso.")
                .build();
    }   
   

    @Override
    public Resposta getAllTransactions(String filter) { 
    	
        Specification<Transaction> spec = FiltroTransaction.byFilter(filter);
        List<Transaction> transactionPage = transactionRepository.findAll(spec);

        List<TransactionDTO> transactionDTOS = modelMapper.map(transactionPage, new TypeToken<List<TransactionDTO>>() {
        }.getType());

        transactionDTOS.forEach(transactionDTO -> {            
            transactionDTO.setProdutoDTO(null);
            transactionDTO.setFornecedorDTO(null);
        });

        return Resposta.builder()
                .status(200)
                .mensagem("sucesso")
                .transacoes(transactionDTOS)                
                .build();
    }

    @Override
    public Resposta getAllTransactionById(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transação não encontrada."));

        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);

        return Resposta.builder()
                .status(200)
                .mensagem("sucesso")
                .transacao(transactionDTO)
                .build();
    }

    @Override
    public Resposta getAllTransactionByMonthAndYear(int month, int year) {
        List<Transaction> transactions = transactionRepository.findAll(FiltroTransaction.byMonthAndYear(month, year));

        List<TransactionDTO> transactionDTOS = modelMapper.map(transactions, new TypeToken<List<TransactionDTO>>() {
        }.getType());

        transactionDTOS.forEach(transactionDTO -> {
            transactionDTO.setProdutoDTO(null);
            transactionDTO.setFornecedorDTO(null);
        });

        return Resposta.builder()
                .status(200)
                .mensagem("sucesso")
                .transacoes(transactionDTOS)
                .build();
    }

    @Override
    public Resposta updateTransactionStatus(Long transactionId, TransactionStatus status) {

        Transaction existingTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transação não encontrada."));

        existingTransaction.setStatus(status);
        existingTransaction.setAtualizadoEm(LocalDateTime.now());

        transactionRepository.save(existingTransaction);

        return Resposta.builder()
                .status(200)
                .mensagem("Status da transação atualizado com sucesso.")
                .build();


    }
}
