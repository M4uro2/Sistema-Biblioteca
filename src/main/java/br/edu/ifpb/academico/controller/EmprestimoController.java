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
			} 
			
			if (emprestimo.getDataDevolucaoReal().before(emprestimo.getDataEmprestimo())) {
				model.addAttribute("mensagemErro", "A data de emprestimo deve ser menor que a data de devolução real.");
				alunos(model); // Recarrega a lista de alunos para o formulário
				return "cadastrarEmprestimo";
			}
			
			if (emprestimo.getDataDevolucaoPrevista().before(emprestimo.getDataEmprestimo())) {
				model.addAttribute("mensagemErro", "A data de emprestimo deve ser menor que a data de devolução prevista.");
				alunos(model); // Recarrega a lista de alunos para o formulário
				return "cadastrarEmprestimo";
			}
			
			if (emprestimo.getDataDevolucaoPrevista().before(emprestimo.getDataEmprestimo()) && emprestimo.getDataDevolucaoReal().before(emprestimo.getDataEmprestimo())) {
				model.addAttribute("mensagemErro", "A data de emprestimo deve ser menor que as datas de devoluções.");
				alunos(model); // Recarrega a lista de alunos para o formulário
				return "cadastrarEmprestimo";
			}
			
			else {			
				emprestimoService.save(emprestimo);
			}
				
			
			
			
			model.addAttribute("mensagemSucesso", "Emprestimo do livro " + emprestimo.getLivro() + " cadastrado com sucesso!");
			return "cadastrarEmprestimo";
		}

		@GetMapping("/edit/{id}")
		public String editEmprestimo(@PathVariable("id") Long id, Model model) {
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
			}
			
			if (emprestimo.getDataDevolucaoReal().before(emprestimo.getDataEmprestimo())) {
				model.addAttribute("mensagemErro", "A data de emprestimo deve ser menor que a data de devolução real.");
				alunos(model); // Recarrega a lista de alunos para o formulário
				return "editarEmprestimo";
			}
			
			if (emprestimo.getDataDevolucaoPrevista().before(emprestimo.getDataEmprestimo())) {
				model.addAttribute("mensagemErro", "A data de emprestimo deve ser menor que a data de devolução prevista.");
				alunos(model); // Recarrega a lista de alunos para o formulário
				return "editarEmprestimo";
			}
			
			if (emprestimo.getDataDevolucaoPrevista().before(emprestimo.getDataEmprestimo()) && emprestimo.getDataDevolucaoReal().before(emprestimo.getDataEmprestimo())) {
				model.addAttribute("mensagemErro", "A data de emprestimo deve ser menor que as datas de devoluções.");
				alunos(model); // Recarrega a lista de alunos para o formulário
				return "editarEmprestimo";
			}
			
			else {
			emprestimoService.save(emprestimo);
			}
			model.addAttribute("mensagemSucesso", "Emprestimo com livro " + emprestimo.getLivro() + " atualizado com sucesso!");
			return listEmprestimos(model);

		}
		
		@GetMapping("/delete/{id}")
		public String deleteEmprestimo(@PathVariable("id") Long id, Model model) {
			emprestimoService.deleteById(id);
			model.addAttribute("mensagemSucesso",  "deletado com sucesso!");
			return listEmprestimos(model);
	}
}
	// http://localhost:8080/aluno/save
	// http://localhost:8080/aluno/form
