package br.edu.ifpb.academico.controller;

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
import br.edu.ifpb.academico.service.EmprestimoService;

@Controller
@RequestMapping("/emprestimo")
public class EmprestimoController {
	
	
		@Autowired
		protected EmprestimoService emprestimoService;
		

		@GetMapping("/form")
		public String home(Model model) {
			model.addAttribute("emprestimo", new Emprestimo());
			return "cadastrarEmprestimo";
		}
		

		/**
		 * Salva um novo aluno no sistema.
		 * 
		 * @param aluno O objeto Aluno a ser salvo.
		 * @param model O modelo para adicionar atributos à view.
		 * @return A view de cadastro de aluno com mensagem de sucesso ou erro.
		 */
		@PostMapping("save")
		public String saveEmprestimo(@ModelAttribute Emprestimo emprestimo, Model model) {	
			// Verifica se o aluno já existe pelo número de matrícula
			if (emprestimoService.existsByLivro(emprestimo.getLivro())) {
				model.addAttribute("mensagemErro", "Emprestimo do livro " + emprestimo.getLivro() + " já cadastrado.");
				return "cadastrarEmprestimo";
			} else {			
				emprestimoService.save(emprestimo);
			}
			model.addAttribute("mensagemSucesso", "Emprestimo do livro " + emprestimo.getLivro() + " cadastrado com sucesso!");
			return "cadastrarEmprestimo";
		}


		/** Exibe o formulário de edição de um aluno existente.
		 * 
		 * @param id    O ID do aluno a ser editado.
		 * @param model O modelo para adicionar atributos à view.
		 * @return A view de edição de aluno.
		 */
		@GetMapping("/edit/{id}")
		public String editEmprestimo(@PathVariable Long id, Model model) {
			Emprestimo emprestimo = emprestimoService.findById(id);
			model.addAttribute("emprestimo", emprestimo);
			return "editarEmprestimo";
		}
		
		/**
		 * Lista todos os alunos cadastrados no sistema.
		 * 
		 * @param model O modelo para adicionar atributos à view.
		 * @return a pagina de listagem de alunos.
		 */
		@GetMapping("list")
		public String listEmprestimos(Model model) {
			model.addAttribute("emprestimos", emprestimoService.findAll());
			return "listarEmprestimo";
		}

		/**
		 * Atualiza os dados de um aluno existente.
		 * @param aluno O objeto Aluno com os dados atualizados.
		 * @param model O modelo para adicionar atributos à view.
		 * @return A view de listagem de alunos.
		 */
		@PostMapping("/edit")
		public String editEmprestimoPost(@ModelAttribute Emprestimo emprestimo, Model model) {
			//// Verifica se a matricula alterado já existe em outro aluno, exeto ele mesmo
			if((!emprestimoService.findById(emprestimo.getId()).getLivro().equals(emprestimo.getLivro())) && emprestimoService.existsByLivro(emprestimo.getLivro())) {
				model.addAttribute("mensagemErro", "Empestimo com livro " + emprestimo.getLivro() + " já existe.");
				return listEmprestimos(model);
			}else {
			emprestimoService.save(emprestimo);
			}
			
			model.addAttribute("mensagemSucesso", "Empestimo com livro " + emprestimo.getLivro() + " atualizado com sucesso!");
			return listEmprestimos(model);

		}
		
		
		/**
		 * Exclui um aluno pelo seu ID.
		 * 
		 * @param id    O ID do aluno a ser excluído.
		 * @param model O modelo para adicionar atributos à view.
		 * @return A view de listagem de alunos com mensagem de sucesso ou erro.
		 */
		@GetMapping("/delete/{id}")
		public String deleteEmprestimo(@PathVariable Long id, Model model) {
			emprestimoService.deleteById(id);
			model.addAttribute("mensagemSucesso",  "deletado com sucesso!");
			return listEmprestimos(model);
	}
	}
	// http://localhost:8080/aluno/save
	// http://localhost:8080/aluno/form


