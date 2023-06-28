package br.com.dimdim.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.dimdim.model.User;
import br.com.dimdim.repository.TransactionRepository;
import br.com.dimdim.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@GetMapping("/")
	public ModelAndView home() {
		Long qtdUsuarios = repository.count();
		Long qtdTransacoes = transactionRepo.count();
		ModelAndView modelAndView = new ModelAndView("base");
		modelAndView.addObject("qtdUsuarios", qtdUsuarios);
		modelAndView.addObject("qtdTransacoes", qtdTransacoes);
		return modelAndView;
	}
	
	@GetMapping("/user")
	public ModelAndView index() {
		List<User> usuarios = repository.findAll();
		ModelAndView modelAndView = new ModelAndView("user");
		modelAndView.addObject("usuarios", usuarios);
		return modelAndView;
	}
	
	@GetMapping("/user/new")
	public String create(User user) {
		return "user-form";
	}
	
	@PostMapping("/user")
	public String save(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user-form";
		}
		repository.save(user);
		model.addAttribute("usuarios", repository.findAll());
		return "user";
	}
	
}
