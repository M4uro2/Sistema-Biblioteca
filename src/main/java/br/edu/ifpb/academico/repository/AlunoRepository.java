package br.edu.ifpb.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.academico.entity.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	
	boolean existsByEmail(String email);

	 List<Aluno> findByCarteirinhaIsNull();
}
