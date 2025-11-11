package br.edu.ifpb.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.academico.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRole(String role);
}
