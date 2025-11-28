package br.edu.ifpb.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.academico.entity.Role;
import br.edu.ifpb.academico.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/form")
    public String home(Model model) {
        model.addAttribute("roleObj", new Role());
        return "cadastrarRole";
    }

    @PostMapping("save")
    public String saveRole(@ModelAttribute("roleObj") Role role, Model model) {	
        // normaliza o nome do papel para incluir o sufixo "ROLE"
        String normalized = withRoleSuffix(role.getRole());
        role.setRole(normalized);

        // Verifica se o papel já existe
        if (roleService.existsByRole(normalized)) {
            model.addAttribute("mensagemErro", "Papel " + normalized + " já cadastrado.");
            return "cadastrarRole";
        } else {
            roleService.save(role);
        }
        model.addAttribute("mensagemSucesso", "Papel " + normalized + " cadastrado com sucesso!");
        return "cadastrarRole";
    }

    @GetMapping("/edit/{id}")
    public String editRole(@PathVariable Long id, Model model) {
        Role role = roleService.findById(id);
        model.addAttribute("roleObj", role);
        return "editarRole";
    }

    @PostMapping("/edit")
    public String editRolePost(@ModelAttribute("roleObj") Role role, Model model) {
        // normaliza o novo nome
        String newName = withRoleSuffix(role.getRole());
        role.setRole(newName);

        // Verifica se o nome alterado já existe em outro papel, exceto ele mesmo
        String original = roleService.findById(role.getId()).getRole();
        if ((!original.equals(newName)) && roleService.existsByRole(newName)) {
            model.addAttribute("mensagemErro", "Papel " + newName + " já existe.");
            return listRoles(model);
        } else {
            roleService.save(role);
        }

        model.addAttribute("mensagemSucesso", "Papel " + newName + " atualizado com sucesso!");
        return listRoles(model);
    }

    @GetMapping("list")
    public String listRoles(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "listarRole";
    }

    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id, Model model) {
        roleService.deleteById(id);
        model.addAttribute("mensagemSucesso",  "Papel deletado com sucesso!");
        return listRoles(model);
    }

    // helper para garantir o sufixo ROLE (em maiúsculas)
    private String withRoleSuffix(String name) {
        if (name == null) return null;
        name = name.trim().toUpperCase();
        if (!name.startsWith("ROLE_")) {
            name = "ROLE_" + name;
        }
        return name;
    }

}
