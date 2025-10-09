package br.edu.ifpb.academico.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.academico.entity.Aluno;
import br.edu.ifpb.academico.repository.AlunoRepository;

@Service
public class AlunoService {

	@Autowired
	protected AlunoRepository alunoRepository;
	
	public Aluno save (Aluno a) {
		return alunoRepository.save(a);
	}
	
	
	 /** Retorna uma lista com todos os alunos cadastrados.
	 *   @return List<Aluno> - Lista de alunos.
	 */
	public List<Aluno> findAll() {
		return alunoRepository.findAll();
	}
	
	 /** Busca um aluno pelo seu ID.
	 *   @param id - ID do aluno a ser buscado.
	 *   @return Aluno - O aluno encontrado.
	 *   @throws RuntimeException se o aluno não for encontrado.
	 */
	public Aluno findById(Long id) {
		return alunoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("aluno não encontrado"));
	}
	
	 /** Remove um aluno pelo seu ID.
	 *   @param id - ID do aluno a ser removido.
	 *   @throws RuntimeException se o aluno não for encontrado.
	 */
	public void deleteById(Long id) {
		if (!alunoRepository.existsById(id)) {
			throw new RuntimeException("aluno não encontrado");
		}
		alunoRepository.deleteById(id);
	}

	 /** Verifica se já existe um aluno cadastrado com a matrícula informada.
	 *   @param matricula - A matrícula do aluno a ser verificada.
	 *   @return boolean - true se já existir um aluno com a matrícula, false caso contrário.
	 */
	public boolean existsByEmail(String aluno) {
		return alunoRepository.existsByEmail(aluno);
 	}

	public List<Aluno> findAlunosSemCarteirinha() {
			return alunoRepository.findByCarteirinhaIsNull();
		}

	// retorna um aluno com seus empréstimos associados
	public Aluno findByIdWithEmprestimos(Long id) {
		return alunoRepository.findByIdWithEmprestimos(id);
	}

	
}
