package br.edu.ifpb.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.academico.entity.Aluno;
import br.edu.ifpb.academico.entity.Carteirinha;
import br.edu.ifpb.academico.service.AlunoService;
import br.edu.ifpb.academico.service.CarteirinhaService;

@Controller
@RequestMapping("/carteirinha")
public class CarteirinhaController {

	@Autowired
	protected CarteirinhaService carteirinhaService;
	@Autowired
	protected AlunoService alunoService;

	@GetMapping("/form")
	public String home(Model model) {
		carregarAlunosSemCarteirinha(model);
		model.addAttribute("carteirinha", new Carteirinha());
		return "cadastrarCarteirinha";
	}
	
	
	
	@PostMapping("save")
	public String saveCarteirinha(@ModelAttribute Carteirinha carteirinha, Model model) {
		// 1. Verifica se o número da carteirinha já existe
		if (carteirinhaService.existsByNumero(carteirinha.getNumero())) {
			model.addAttribute("mensagemErro", "Carteirinha " + carteirinha.getNumero() + " já cadastrada.");
        alunos(model); // Recarrega a lista de alunos para o formulário
        return "cadastrarCarteirinha";
    }
    // 2. Busca o aluno selecionado no formulário
    Aluno aluno = alunoService.findById(carteirinha.getAluno().getId());
    if (aluno.getCarteirinha() != null) {
		model.addAttribute("mensagemErro", "Este aluno já possui uma carteirinha.");
        alunos(model); // Recarrega a lista de alunos para o formulário
        return "cadastrarCarteirinha";
    }
    // 3. Salva a nova carteirinha primeiro
    // Isso garante que a carteirinha tenha um ID antes de ser associada ao aluno
    carteirinha.setAluno(aluno);
    carteirinhaService.save(carteirinha);
    // 4. Associa a carteirinha recém-salva ao aluno
    aluno.setCarteirinha(carteirinha);
    // 5. Atualiza o aluno. Como o 'aluno' já possui um ID, o JPA/Hibernate
    // executará um UPDATE, e não um INSERT, evitando a duplicação de chave primária.
    alunoService.save(aluno);
    model.addAttribute("mensagemSucesso", "Carteirinha do número " + carteirinha.getNumero() + " cadastrada com sucesso!");
    return "redirect:/carteirinha/list"; // Redireciona para a lista para evitar reenvio do formulário
    }



	@GetMapping("/edit/{id}")
	public String editCarterinha(@PathVariable("id") Long id, Model model) {
		alunos(model);
		Carteirinha carteirinha = carteirinhaService.findById(id);
		model.addAttribute("carteirinha", carteirinha);
		return "editarCarteirinha";
	}
	
	
	
	@GetMapping("list")
	public String listCarteirinha(Model model) {
		model.addAttribute("carteirinhas", carteirinhaService.findAll());
		return "listarCarteirinha";
	}
	
	
	
	@PostMapping("/edit")
	public String editCarteirinhaPost(@ModelAttribute Carteirinha carteirinha, Model model) {
		Carteirinha carteirinhaExistente = carteirinhaService.findById(carteirinha.getId());
		if (carteirinhaExistente == null) {
			model.addAttribute("mensagemErro", "Carteirinha não encontrada.");
			return listCarteirinha(model);
		}
		// Verifica se o número está sendo alterado para um já existente
		if (!carteirinhaExistente.getNumero().equals(carteirinha.getNumero()) && carteirinhaService.existsByNumero(carteirinha.getNumero())) {
			model.addAttribute("mensagemErro", "Carteirinha do número " + carteirinha.getNumero() + " já existe.");
			return listCarteirinha(model);
		}
		// Desvincula a carteirinha do aluno anterior, se necessário
		Aluno alunoAnterior = carteirinhaExistente.getAluno();
		if (alunoAnterior != null && !alunoAnterior.getId().equals(carteirinha.getAluno().getId())) {
			alunoAnterior.setCarteirinha(null);
			alunoService.save(alunoAnterior);
		}
		// Atualiza os campos da carteirinha
		carteirinhaExistente.setNumero(carteirinha.getNumero());
		carteirinhaExistente.setDataEmissao(carteirinha.getDataEmissao());
		carteirinhaExistente.setValidade(carteirinha.getValidade());
		carteirinhaExistente.setStatus(carteirinha.getStatus());
		// Atualiza o aluno vinculado
		Aluno novoAluno = alunoService.findById(carteirinha.getAluno().getId());
		carteirinhaExistente.setAluno(novoAluno);
		novoAluno.setCarteirinha(carteirinhaExistente);
		alunoService.save(novoAluno);
		carteirinhaService.save(carteirinhaExistente);
		model.addAttribute("mensagemSucesso", "Carteirinha do número " + carteirinhaExistente.getNumero() + " atualizada com sucesso!");
		return listCarteirinha(model);
	}
	
	
	
	@GetMapping("/delete/{id}")
	public String deleteAluno(@PathVariable("id") Long id, Model model) {
		Carteirinha carteirinha = carteirinhaService.findById(id);
		if (carteirinha != null && carteirinha.getAluno() != null) {
			Aluno aluno = carteirinha.getAluno();
			aluno.setCarteirinha(null); // desvincula a carteirinha do aluno
			carteirinha.setAluno(null); // desvincula o aluno da carteirinha
			alunoService.save(aluno);   // salva o aluno atualizado
		}
		carteirinhaService.deleteById(id); // deleta só a carteirinha
		return "redirect:/carteirinha/list";
	}



	private void alunos(Model model) {
		List<Aluno> alunos = alunoService.findAll();
		model.addAttribute("alunos", alunos);
	}


	// Este método agora carrega APENAS alunos sem carteirinha
	private void carregarAlunosSemCarteirinha(Model model) {
		List<Aluno> alunos = alunoService.findAlunosSemCarteirinha();
		model.addAttribute("alunos", alunos);
	}

}
