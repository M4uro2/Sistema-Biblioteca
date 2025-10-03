package br.edu.ifpb.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.ifpb.academico.entity.Carteirinha;
import br.edu.ifpb.academico.service.CarteirinhaService;

@Controller
public class CarteirinhaController {

	@Autowired
	protected CarteirinhaService carteirinhaService;

	@GetMapping("/form")
	public String home(Model model) {
		model.addAttribute("carteirinha", new Carteirinha());
		return "cadastrarCarteirinha";
	}

	@PostMapping("save")
	public String saveCarteirinha(@ModelAttribute Carteirinha carteirinha, Model model) {	
		if (carteirinhaService.existsByNumero(carteirinha.getNumero())) {
			model.addAttribute("mensagemErro", "carteirinha " + carteirinha.getNumero() + " já cadastrado.");
			return "cadastrarCarteirinha";
		} else {			
			carteirinhaService.save(carteirinha);
		}
		model.addAttribute("mensagemSucesso", "carteirinha " + carteirinha.getNumero() + " cadastrado com sucesso!");
		return "cadastrarCarteirinha";
	}

	@GetMapping("/edit/{id}")
	public String editEmprestimo(@PathVariable Long id, Model model) {
		Carteirinha carteirinha = carteirinhaService.findById(id);
		model.addAttribute("carteirinha", carteirinha);
		return "editarCarteirinha";
	}

	@GetMapping("list")
	public String listCarteirinha(Model model) {
		model.addAttribute("carteirinha", carteirinhaService.findAll());
		return "listarCarteirinha";
	}

	
	@PostMapping("/edit")
	public String editCarteirinhaPost(@ModelAttribute Carteirinha carteirinha, Model model) {
		if((!carteirinhaService.findById(carteirinha.getId()).getNumero().equals(carteirinha.getNumero())) && carteirinhaService.existsByNumero(carteirinha.getNumero())) {
			model.addAttribute("mensagemErro", "carteirinha do numero" + carteirinha.getNumero() + " já existe.");
			return listCarteirinha(model);
		}else {
			carteirinhaService.save(carteirinha);
		}

		model.addAttribute("mensagemSucesso", "Carteirinha do numero" + carteirinha.getNumero() + " atualizado com sucesso!");
		return listCarteirinha(model);
	}


	@GetMapping("/delete/{id}")
	public String deleteCarteirinha(@PathVariable Long id, Model model) {
		carteirinhaService.deleteById(id);
		model.addAttribute("mensagemSucesso",  "deletado com sucesso!");
		return listCarteirinha(model);
	}
}
