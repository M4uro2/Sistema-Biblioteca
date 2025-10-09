package br.edu.ifpb.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.academico.entity.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	
	boolean existsByEmail(String email);

	 List<Aluno> findByCarteirinhaIsNull();

	 // retorna um aluno com seus empréstimos associados
    @Query("SELECT a FROM Aluno a LEFT JOIN FETCH a.emprestimos WHERE a.id = :id")
    Aluno findByIdWithEmprestimos(@Param("id") Long id);
}
