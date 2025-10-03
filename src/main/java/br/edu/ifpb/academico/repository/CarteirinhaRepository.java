package br.edu.ifpb.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.academico.entity.Carteirinha;


@Repository
public interface CarteirinhaRepository extends JpaRepository<Carteirinha, Long> {
	
	boolean existsByNumero(String numero);

}
