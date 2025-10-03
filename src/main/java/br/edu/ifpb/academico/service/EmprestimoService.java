package br.edu.ifpb.academico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.academico.entity.Emprestimo;
import br.edu.ifpb.academico.repository.EmprestimoRepository;

@Service
public class EmprestimoService {
	
	
	@Autowired
	protected EmprestimoRepository emprestimoRepository;
	
	public Emprestimo save(Emprestimo a) {
		return emprestimoRepository.save(a);
	}

	/**
	 * Retorna uma lista com todos os alunos cadastrados.
	 * 
	 * @return List<Aluno> - Lista de alunos.
	 */
	public List<Emprestimo> findAll() {
		return emprestimoRepository.findAll();
	}

	/**
	 * Busca um aluno pelo seu ID.
	 * 
	 * @param id - ID do aluno a ser buscado.
	 * @return Aluno - O aluno encontrado.
	 * @throws RuntimeException se o aluno não for encontrado.
	 */
	public Emprestimo findById(Long id) {
		return emprestimoRepository.findById(id).orElseThrow(() -> new RuntimeException("emprestimo não encontrado"));
	}

	/**
	 * Remove um aluno pelo seu ID.
	 * 
	 * @param id - ID do aluno a ser removido.
	 * @throws RuntimeException se o aluno não for encontrado.
	 */
	public void deleteById(Long id) {
		if (!emprestimoRepository.existsById(id)) {
			throw new RuntimeException("emprestimo não encontrado");
		}
		emprestimoRepository.deleteById(id);
	}

	/**
	 * Verifica se já existe um aluno cadastrado com a matrícula informada.
	 * 
	 * @param matricula - A matrícula do aluno a ser verificada.
	 * @return boolean - true se já existir um aluno com a matrícula, false caso
	 *         contrário.
	 */
	public boolean existsByLivro(String livro) {
		return emprestimoRepository.existsByLivro(livro);
	}

}
