package com.nexdom.estoque_backend.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexdom.estoque_backend.dto.ProdutoDTO;
import com.nexdom.estoque_backend.dto.Resposta;
import com.nexdom.estoque_backend.services.ProdutoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

	private final ProdutoService produtoService;

    @PostMapping("/add")
    public ResponseEntity<Resposta> saveProduto(            
            @RequestParam("codigo") String codigo,
            @RequestParam("name") String name,            
            @RequestParam("preco") BigDecimal preco,
            @RequestParam("qtdEstoque") Integer qtdEstoque,
            @RequestParam("tipoProdutoId") Long tipoProdutoId,
            @RequestParam(value = "description", required = false) String description
    ) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setCodigo(codigo);
        produtoDTO.setName(name);        
        produtoDTO.setPreco(preco);
        produtoDTO.setQtdEstoque(qtdEstoque);
        produtoDTO.setTipoProdutoId(tipoProdutoId);
        produtoDTO.setDescription(description);

        return ResponseEntity.ok(produtoService.salvarProduto(produtoDTO));

    }

    @PutMapping("/update")    
    public ResponseEntity<Resposta> updateProduto(           
            @RequestParam(value = "codigo", required = false) String codigo,
            @RequestParam(value = "name", required = false) String name,            
            @RequestParam(value = "preco", required = false) BigDecimal preco,
            @RequestParam(value = "qtdEstoque", required = false) Integer qtdEstoque,
            @RequestParam(value = "tipoProdutoId", required = false) Long tipoProdutoId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("produtoId") Long produtoId
    ) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setName(name);
        produtoDTO.setCodigo(codigo);
        produtoDTO.setPreco(preco);
        produtoDTO.setProdutoId(produtoId);
        produtoDTO.setQtdEstoque(qtdEstoque);
        produtoDTO.setTipoProdutoId(tipoProdutoId);
        produtoDTO.setDescription(description);

        return ResponseEntity.ok(produtoService.atualizarProduto(produtoDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<Resposta> getAllProdutos() {
        return ResponseEntity.ok(produtoService.getAllProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resposta> getProdutoById(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.getProdutoById(id));
    }    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Resposta> deleteProduto(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.deleteProduto(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Resposta> searchProduto(@RequestParam String input) {
        return ResponseEntity.ok(produtoService.searchProduto(input));
    }
}
