package br.edu.ifpb.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.academico.entity.Emprestimo;


@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
	
	boolean existsByLivro(String livro);

	boolean existsByAlunoId(Long alunoId);

}
