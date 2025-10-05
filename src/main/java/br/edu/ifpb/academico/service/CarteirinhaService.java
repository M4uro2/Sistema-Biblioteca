package br.edu.ifpb.academico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.academico.entity.Carteirinha;
import br.edu.ifpb.academico.repository.CarteirinhaRepository;


@Service
public class CarteirinhaService {


	
	@Autowired
	protected CarteirinhaRepository carteirinhaRepository;
	
	public Carteirinha save(Carteirinha a) {
		return carteirinhaRepository.save(a);	
	}

	public List<Carteirinha> findAll() {
		return carteirinhaRepository.findAll();
	}

	public Carteirinha findById(Long id) {
		return carteirinhaRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("carteirinha não encontrado"));
	}

	public void deleteById(Long id) {
		if (!carteirinhaRepository.existsById(id)) {
			throw new RuntimeException("carteirinha não encontrado");
		}
		carteirinhaRepository.deleteById(id);
	}

	public boolean existsByNumero(String numero) {
		return carteirinhaRepository.existsByNumero(numero);
	}

}
