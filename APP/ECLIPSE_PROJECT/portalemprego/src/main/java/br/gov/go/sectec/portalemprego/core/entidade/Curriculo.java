package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 
 * <p>
 * <b>Title:</b> Curriculo.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para Curriculo
 * </p>
 * 
 * <p>
 * <b>Company: </b> Premium Consultoria e Sistemas
 * </p>
 * 
 * @author Silvânio
 * 
 * @version 1.0.0
 */
@Entity(name = "adm_curriculo")
public class Curriculo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_curriculo", unique = true, nullable = false)
	private Long idCurriculo;

	@ManyToOne()
	@JoinColumn(name = "id_aluno")
	private Aluno aluno;

	@OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL)
	private List<ExperienciaProfissional> experienciaProfissionals;

	@OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL)
	private List<FormacaoAcademica> formacaoAcademicas;

	public Long getIdCurriculo() {

		return this.idCurriculo;
	}

	public void setIdCurriculo(final Long idCurriculo) {

		this.idCurriculo = idCurriculo;
	}

	public Aluno getAluno() {

		return this.aluno;
	}

	public void setAluno(final Aluno aluno) {

		this.aluno = aluno;
	}

	public List<ExperienciaProfissional> getExperienciaProfissionals() {

		return this.experienciaProfissionals;
	}

	public void setExperienciaProfissionals(final List<ExperienciaProfissional> experienciaProfissionals) {

		this.experienciaProfissionals = experienciaProfissionals;
	}

	public List<FormacaoAcademica> getFormacaoAcademicas() {

		return this.formacaoAcademicas;
	}

	public void setFormacaoAcademicas(final List<FormacaoAcademica> formacaoAcademicas) {

		this.formacaoAcademicas = formacaoAcademicas;
	}

}
