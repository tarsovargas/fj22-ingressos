package br.com.caelum.ingresso.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.helper.TokenHelper;
import br.com.caelum.ingresso.mail.EmailNovoUsuario;
import br.com.caelum.ingresso.mail.Mailer;
import br.com.caelum.ingresso.model.Token;

@Controller
public class UsuarioController {
	
	@Autowired
	private Mailer mailer;
	
	@Autowired
	private TokenHelper tokenhelper;
	
	
	
	@GetMapping("/usuario/request")
	public ModelAndView formSolicitacaoDeAcesso(){
		return new ModelAndView("usuario/form-email");
	}
	
	@PostMapping("/usuario/request")
	@Transactional
	public ModelAndView solicitacaoDeAcesso(String email) throws MessagingException{
		
		ModelAndView view = new ModelAndView("usuario/adicionado");
		
		Token token = tokenhelper.generateFrom(email);
		
		mailer.send(new EmailNovoUsuario(token));
		
		return view;
	}

}
