package com.nexdom.estoque_backend.services.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/imagens_produto/";	
		
	 //AFTER YOUR FRONTEND IS SETUP CHANGE THE IMAGE DIRECTORY TO THE FRONTEND YOU ARE USING
    private static final String IMAGE_DIRECTORY_2 = "C:\\Users\\lira\\imagens-estoque\\produtos";

    @Override
    public Resposta salvarProduto(ProdutoDTO produtoDTO, MultipartFile imageFile) {

        TipoProduto tipoProduto = tipoProdutoRepository.findById(produtoDTO.getTipoProdutoId())
                .orElseThrow(() -> new NotFoundException("TipoProduto não encontrado."));

        //map our dto to produto entity
        Produto produtoToSave = Produto.builder()
                .name(produtoDTO.getName())                
                .preco(produtoDTO.getPreco())
                .qtdEstoque(produtoDTO.getQtdEstoque())
                .description(produtoDTO.getDescription())
                .tipoProduto(tipoProduto)
                .build();

        if (imageFile != null && !imageFile.isEmpty()) {
            log.info("Image file exist");
//			            String imagePath = saveImage(imageFile); //use this when you haven't setup your frontend
            String imagePath = saveImage2(imageFile); //use this when you ave set up your frontend locally but haven't deployed to production

            System.out.println("IMAGE URL IS: " + imagePath);
            produtoToSave.setImagemUrl(imagePath);
        }

        //save the produto entity
        produtoRepository.save(produtoToSave);

        return Resposta.builder()
                .status(200)
                .mensagem("Produto salvo com sucesso.")
                .build();
    }

    @Override
    public Resposta atualizarProduto(ProdutoDTO produtoDTO, MultipartFile imageFile) {

        //check if produto exisit
        Produto existingProduto = produtoRepository.findById(produtoDTO.getProdutoId())
                .orElseThrow(() -> new NotFoundException("Produto Not Found"));

        //check if image is associated with the produto to update and upload
        if (imageFile != null && !imageFile.isEmpty()) {
//			            String imagePath = saveImage(imageFile); //use this when you haven't setup your frontend
            String imagePath = saveImage2(imageFile); //use this when you ave set up your frontend locally but haven't deployed to produiction

            System.out.println("IMAGE URL IS: " + imagePath);
            existingProduto.setImagemUrl(imagePath);
        }

        //check if tipoProduto is to be chanegd for the produtos
        if (produtoDTO.getTipoProdutoId() != null && produtoDTO.getTipoProdutoId() > 0) {
            TipoProduto tipoProduto = tipoProdutoRepository.findById(produtoDTO.getTipoProdutoId())
                    .orElseThrow(() -> new NotFoundException("TipoProduto Not Found"));
            existingProduto.setTipoProduto(tipoProduto);
        }

        //check if produto fields is to be changed and update
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


    //this save to the root of your project
    private String saveImage(MultipartFile imageFile) {
        //validate image and check if it is greater than 1GIB
        if (!imageFile.getContentType().startsWith("image/") || imageFile.getSize() > 1024 * 1024 * 1024) {
            throw new IllegalArgumentException("Permitidas imagens abaixo de 500 mega apenas");
        }

        //create the directory if it doesn't exist
        File directory = new File(IMAGE_DIRECTORY);

        if (!directory.exists()) {
            directory.mkdir();
            log.info("Directory was created");
        }
        //generate unique file name for the image
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        //Get the absolute path of the image
        String imagePath = IMAGE_DIRECTORY + uniqueFileName;

        try {
            File destinationFile = new File(imagePath);
            imageFile.transferTo(destinationFile); //we are writing the image to this folder
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving Image: " + e.getMessage());
        }
        return imagePath;

    }

    //This saved image to the public folder in your frontend
    //Use this if your have setup your frontend
    private String saveImage2(MultipartFile imageFile) {
        //validate image and check if it is greater than 1GIB
        if (!imageFile.getContentType().startsWith("image/") || imageFile.getSize() > 1024 * 1024 * 1024) {
            throw new IllegalArgumentException("Only image files under 1GIG is allowed");
        }

        //create the directory if it doesn't exist
        File directory = new File(IMAGE_DIRECTORY_2);

        if (!directory.exists()) {
            directory.mkdir();
            log.info("Directory was created");
        }
        //generate unique file name for the image
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        //Get the absolute path of the image
        String imagePath = IMAGE_DIRECTORY_2 + uniqueFileName;

        try {
            File destinationFile = new File(imagePath);
            imageFile.transferTo(destinationFile); //we are writing the image to this folder
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao salvar imagem: " + e.getMessage());
        }
        return "produtos/"+uniqueFileName;


    }
  }
