package br.edu.ifpb.academico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.academico.entity.Role;
import br.edu.ifpb.academico.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role save( Role a) {
		return roleRepository.save(a);
	}

	public List <Role> findAll() {
		return roleRepository.findAll();
	}

	public Role findById(Long id) {
		return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role não encontrado"));
	}

	public void deleteById(Long id) {
		if (!roleRepository.existsById(id)) {
			throw new RuntimeException("Role não encontrado");
		}
		roleRepository.deleteById(id);
	}

	public boolean existsByRole(String role) {
		return roleRepository.existsByRole(role);
	}

}
