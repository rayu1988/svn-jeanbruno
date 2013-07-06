package br.gov.go.sectec.portalemprego.core.entidade.DTO;

import java.io.Serializable;

/**
 * <p>
 * <b>Title:</b> CurriculoAreaDTO.java
 * </p>
 * 
 * <p>
 * <b>Description:</b>
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
public class CurriculoAlunoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idCurriculo;

	private Long idAluno;

	private String aluno;

	public Long getIdAluno() {

		return this.idAluno;
	}

	public void setIdAluno(final Long idAluno) {

		this.idAluno = idAluno;
	}

	public String getAluno() {

		return this.aluno;
	}

	public void setAluno(final String aluno) {

		this.aluno = aluno;
	}

	public Long getIdCurriculo() {

		return this.idCurriculo;
	}

	public void setIdCurriculo(final Long idCurriculo) {

		this.idCurriculo = idCurriculo;
	}

}
