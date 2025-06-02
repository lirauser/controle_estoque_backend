package com.nexdom.estoque_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nexdom.estoque_backend.dto.Resposta;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Resposta> handleAllExceptions(Exception ex) {
		Resposta resposta = Resposta.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.mensagem(ex.getMessage())
				.build();
		
		return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Resposta> handleNotFoundException(NotFoundException ex) {
		Resposta resposta = Resposta.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.mensagem(ex.getMessage())
				.build();
		
		return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(ValorNomeRequiredException.class)
	public ResponseEntity<Resposta> handleValorNomeRequeridoException(ValorNomeRequiredException ex) {
		Resposta resposta = Resposta.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.mensagem(ex.getMessage())
				.build();
		
		return new ResponseEntity<>(resposta, HttpStatus.BAD_REQUEST);
		
	}	

}
