package br.edu.ifpb.academico.entity;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "alunos")
public class Aluno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	@NotBlank(message = "O email do aluno é obrigatório")
	@Column(unique = true, nullable = false)
	private String email;
	
	@NotBlank(message = "A série do aluno é obrigatória")
	@Column(nullable = false)
	private String serie;
	
	@NotBlank(message = "A turma do aluno é obrigatória")
	@Column(nullable = false)
	private String turma;
	
	@NotNull(message = "A data de nascimento do aluno é obrigatória")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "nascimento", nullable = false)
	private Date dataNascimento;
	
	@Column(unique = true, nullable = false)
	@NotBlank(message = "O telefone do aluno é obrigatório")
	private String telefone;

	@OneToOne(cascade = CascadeType.ALL)
	private Carteirinha carteirinha;

	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "aluno")
	private List<Emprestimo> emprestimos;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public Carteirinha getCarteirinha() {
		return carteirinha;
	}

	public void setCarteirinha(Carteirinha carteirinha) {
		this.carteirinha = carteirinha;
	}

	public List<Emprestimo> getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(List<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}

}
