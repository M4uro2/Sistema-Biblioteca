package br.edu.ifpb.academico.controller;

import java.util.ArrayList;
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
import br.edu.ifpb.academico.entity.Emprestimo;
import br.edu.ifpb.academico.service.AlunoService;
import br.edu.ifpb.academico.service.EmprestimoService;

@Controller
@RequestMapping("/emprestimo")
public class EmprestimoController {	
	
		@Autowired
		protected EmprestimoService emprestimoService;

		@Autowired
		protected AlunoService alunoService;

		@GetMapping("/form")
		public String home(Model model) {
			alunos(model);
			model.addAttribute("emprestimo", new Emprestimo());
			return "cadastrarEmprestimo";
		}
		
        private void alunos(Model model) {
			List<Aluno> alunos = alunoService.findAll();
			model.addAttribute("alunos", alunos);
			
		}

		@PostMapping("save")
		public String saveEmprestimo(@ModelAttribute Emprestimo emprestimo, Model model) {	
			if (emprestimoService.existsByLivro(emprestimo.getLivro())) {
				model.addAttribute("mensagemErro", "Emprestimo do livro " + emprestimo.getLivro() + " já cadastrado.");
				return "cadastrarEmprestimo";
			} else {			
				emprestimoService.save(emprestimo);
			}
			model.addAttribute("mensagemSucesso", "Emprestimo do livro " + emprestimo.getLivro() + " cadastrado com sucesso!");
			return "cadastrarEmprestimo";
		}

		@GetMapping("/edit/{id}")
		public String editEmprestimo(@PathVariable Long id, Model model) {
			alunos(model);
			Emprestimo emprestimo = emprestimoService.findById(id);
			model.addAttribute("emprestimo", emprestimo);
			return "editarEmprestimo";
		}
		
		@GetMapping("list")
		public String listEmprestimos(Model model) {
			List<Emprestimo> emprestimos = emprestimoService.findAll();
			model.addAttribute("emprestimos", emprestimos);
			return "listarEmprestimo";
		}

		@PostMapping("/edit")
		public String editEmprestimoPost(@ModelAttribute Emprestimo emprestimo, Model model) {
			//// Verifica se a matricula alterado já existe em outro aluno, exeto ele mesmo
			if((!emprestimoService.findById(emprestimo.getId()).getLivro().equals(emprestimo.getLivro())) && emprestimoService.existsByLivro(emprestimo.getLivro())) {
				model.addAttribute("mensagemErro", "Empestimo com livro " + emprestimo.getLivro() + " já existe.");
				return listEmprestimos(model);
			}else {
			emprestimoService.save(emprestimo);
			}
			model.addAttribute("mensagemSucesso", "Emprestimo com livro " + emprestimo.getLivro() + " atualizado com sucesso!");
			return listEmprestimos(model);

		}
		
		@GetMapping("/delete/{id}")
		public String deleteEmprestimo(@PathVariable Long id, Model model) {
			emprestimoService.deleteById(id);
			model.addAttribute("mensagemSucesso",  "deletado com sucesso!");
			return listEmprestimos(model);
	}
}
	// http://localhost:8080/aluno/save
	// http://localhost:8080/aluno/form
