package br.com.dimdim.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    // Atualizar um usuário (Editar)
    @GetMapping("/user/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário inválido Id:" + id));
        model.addAttribute("user", user);
        return "user-form";
    }

    @PostMapping("/user/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id); // Certifica-se de que o ID não será perdido durante a atualização
            return "user-form";
        }
        repository.save(user); // Atualiza o usuário
        model.addAttribute("usuarios", repository.findAll());
        return "user"; // Redireciona para a lista de usuários
    }
    
    // Deletar um usuário
    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        User user = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário inválido Id:" + id));
        repository.delete(user);
        model.addAttribute("usuarios", repository.findAll());
        return "user"; // Redireciona para a lista de usuários
    }
}
