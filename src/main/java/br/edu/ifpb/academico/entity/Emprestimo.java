package br.edu.ifpb.academico.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message = "A data de empréstimo não pode ser nula")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataEmprestimo", nullable = false)
	private Date dataEmprestimo;
	
	@NotNull(message = "A data de devolução prevista não pode ser nula")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataDevolucaoPrevista", nullable = false)
	private Date dataDevolucaoPrevista;
	
	@NotNull(message = "A data de devolução real não pode ser nula")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataDevolucaoReal", nullable = false)
	private Date dataDevolucaoReal;

	@NotNull(message = "O status do empréstimo não pode ser nulo")
	@Column(nullable = false)
	private String status;
	
	@NotNull(message = "O livro emprestado não pode ser nulo")
	@Column(nullable = false)
	private String livro;

	@ManyToOne(
		fetch = FetchType.EAGER,
		optional = false)
		private Aluno aluno;
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucaoPrevista() {
		return dataDevolucaoPrevista;
	}

	public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) {
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
	}

	public Date getDataDevolucaoReal() {
		return dataDevolucaoReal;
	}

	public void setDataDevolucaoReal(Date dataDevolucaoReal) {
		this.dataDevolucaoReal = dataDevolucaoReal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLivro() {
		return livro;
	}

	public void setLivro(String livro) {
		this.livro = livro;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	
	 	
	

}
