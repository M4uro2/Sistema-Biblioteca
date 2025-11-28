package br.edu.ifpb.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.academico.entity.Role;
import br.edu.ifpb.academico.entity.Usuario;
import br.edu.ifpb.academico.service.RoleService;
import br.edu.ifpb.academico.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;

    @GetMapping("/form")
	public String home(Model model) {
        roles(model);
		model.addAttribute("usuario", new Usuario());
		return "cadastrarUsuario";
	}

    private void roles(Model model) {
		List<Role> roles = roleService.findAll();
		model.addAttribute("roles", roles);
	}

    @PostMapping("save")
	public String saveUsuario(@ModelAttribute Usuario usuario, Model model) {	
		// Verifica se o usuário já existe pela matrícula
		if (usuarioService.existsByMatricula(usuario.getMatricula())) {
			model.addAttribute("mensagemErro", "Usuário com matrícula " + usuario.getMatricula() + " já cadastrado.");
			return home(model);
		}
		if(usuarioService.existsByEmail(usuario.getEmail())){
			model.addAttribute("mensagemErro", "Usuário com Email " + usuario.getEmail() + " já cadastrado.");
			return home(model);
		}
		 else {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			usuarioService.save(usuario);
		}
		model.addAttribute("mensagemSucesso", "Usuário com matrícula " + usuario.getMatricula() + " cadastrado com sucesso!");
		return home(model);
	}

    @GetMapping("/edit/{id}")
	public String editUsuario(@PathVariable Long id, Model model) {
        roles(model);
		Usuario usuario = usuarioService.findById(id);
		model.addAttribute("usuario", usuario);
		return "editarUsuario";
	}

    @PostMapping("/edit")
	public String editUsuarioPost(@ModelAttribute Usuario usuario, Model model) {
		//// Verifica se a matricula alterado já existe em outro usuario, exeto ele mesmo
		if((!usuarioService.findById(usuario.getId()).getMatricula().equals(usuario.getMatricula())) && usuarioService.existsByMatricula(usuario.getMatricula())) {
			model.addAttribute("mensagemErro", "Usuário com matrícula " + usuario.getMatricula() + " já existe.");
			return listUsuarios(model);
		}
		if((!usuarioService.findById(usuario.getId()).getEmail().equals(usuario.getEmail())) && usuarioService.existsByEmail(usuario.getEmail())) {
			model.addAttribute("mensagemErro", "Usuário com Email " + usuario.getEmail() + " já existe.");
			return listUsuarios(model);
		}
		else {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioService.save(usuario);
		}
		model.addAttribute("mensagemSucesso", "Usuário com Email " + usuario.getEmail() + " atualizado com sucesso!");
		return listUsuarios(model);
	}

    @GetMapping("list")
	public String listUsuarios(Model model) {
		model.addAttribute("usuarios", usuarioService.findAll());
		return "listarUsuario";
	}

    @GetMapping("/delete/{id}")
	public String deleteUsuario(@PathVariable Long id, Model model) {
		usuarioService.deleteById(id);
		model.addAttribute("mensagemSucesso",  "Usuário deletado com sucesso!");
		return listUsuarios(model);
	}







}
