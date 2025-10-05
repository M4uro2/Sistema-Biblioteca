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

	public List<Emprestimo> findAll() {
		return emprestimoRepository.findAll();
	}

	public Emprestimo findById(Long id) {
		return emprestimoRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("emprestimo não encontrado"));
	}

	
	public void deleteById(Long id) {
		if (!emprestimoRepository.existsById(id)) {
			throw new RuntimeException("emprestimo não encontrado");
		}
		emprestimoRepository.deleteById(id);
	}

	public boolean existsByLivro(String livro) {
		return emprestimoRepository.existsByLivro(livro);
	}

}
