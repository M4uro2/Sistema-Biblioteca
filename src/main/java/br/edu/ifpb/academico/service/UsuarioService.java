package br.edu.ifpb.academico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifpb.academico.entity.Usuario;
import br.edu.ifpb.academico.repository.UsuarioRepository;



@Service
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario save(Usuario u) {
		return usuarioRepository.save(u);
	}

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Usuario findById(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
	}

	public void deleteById(Long id) {
		if (!usuarioRepository.existsById(id)) {
			throw new RuntimeException("Usuario não encontrado");
		}
		usuarioRepository.deleteById(id);
	}

	public boolean existsByMatricula(String matricula) {
		return usuarioRepository.existsByMatricula(matricula);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return usuarioRepository.findByLogin(username)
			.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
	}

	

}
