package br.com.dimdim.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.dimdim.model.Transaction;
import br.com.dimdim.model.User;
import br.com.dimdim.repository.TransactionRepository;
import br.com.dimdim.repository.UserRepository;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
	TransactionRepository repository;
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping()
	public ModelAndView index() {
		List<Transaction> history = repository.findAll();
		ModelAndView modelAndView = new ModelAndView("transaction");
		modelAndView.addObject("historico", history);
		return modelAndView;
	}
	
	@GetMapping("{id}")
	public ModelAndView get(@PathVariable Long id) {
		List<Transaction> history = repository.findByUser_Id(id);
		Optional<User> user = userRepo.findById(id);
		ModelAndView modelAndView = new ModelAndView("user-transaction");
		modelAndView.addObject("cliente", user.get());
		modelAndView.addObject("historico", history);
		return modelAndView;
	}
	
	@GetMapping("/new/{id}")
	public ModelAndView create(@PathVariable Long id, Transaction transaction) {
		Optional<User> user = userRepo.findById(id);
		transaction.setDate(new Date());
		transaction.setValue(new BigDecimal(0));
		System.out.println(transaction);
		ModelAndView modelAndView = new ModelAndView("transaction-form");
		modelAndView.addObject("userId", user.get().getId());
		modelAndView.addObject("nomeUsuario", user.get().getName());
		modelAndView.addObject("dataAtual", new Date());
		return modelAndView;
	}
	
	@PostMapping("/new/{id}")
	public String save(@PathVariable Long id, @Valid Transaction transaction, BindingResult result, Model model) {
		Optional<User> user = userRepo.findById(id);
		transaction.setUser(user.get());
		
		if (result.hasErrors()) {
			model.addAttribute("userId", transaction.getUser().getId());
			model.addAttribute("nomeUsuario", transaction.getUser().getName());
			return "transaction-form";
		}
		// WARNING: Tire as crianças da sala
		// para não verem esse null explícito
		transaction.setId(null);
		System.out.println(transaction);
		repository.save(transaction);
		List<Transaction> history = repository.findByUser_Id(transaction.getUser().getId());
		model.addAttribute("cliente", transaction.getUser());
		model.addAttribute("historico", history);
		return "redirect:/transaction/"+id;
	}
	
}
