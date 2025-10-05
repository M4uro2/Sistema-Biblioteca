package br.edu.ifpb.academico.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "carterinhas")
public class Carteirinha {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "O número da carteirinha não pode ser nulo")
	@Column(nullable = false, unique = true)
	private String numero;
	
	@NotNull(message = "A data de emissão não pode ser nula")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataEmissao", nullable = false)
	private Date dataEmissao;
	
	@NotNull(message = "A data de validade não pode ser nula")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "validade", nullable = false)
	private Date validade;
	
	@NotBlank(message = "O status da carteirinha não pode ser nulo")
	@Column(nullable = false)
	private String status;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "aluno_id")
	private Aluno aluno;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getValidade() {
		return validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	
	

}
