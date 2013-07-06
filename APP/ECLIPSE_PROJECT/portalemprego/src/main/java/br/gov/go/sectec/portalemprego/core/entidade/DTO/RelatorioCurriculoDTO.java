package br.gov.go.sectec.portalemprego.core.entidade.DTO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * <b>Title:</b> RelatorioCurriculoDTO.java
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
 * @author Joffre
 * 
 * @version 1.0.0
 */
public class RelatorioCurriculoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idAluno;

	private String dsNomeAluno;

	private String dsNacionalidade;

	private String dsEstadoCivil;

	private String dsEndereco;

	private String dsBairro;

	private String dsCidade;

	private String dsUF;

	private String dsCEP;

	private String dsEscolaridade;

	private List<FormacaoAcademicaDTO> lsFormacaoAcademia;

	private List<ExperienciaProfissionalDTO> lsExperienciaProfissionalDTO;

	public Long getIdAluno() {

		return idAluno;
	}

	public void setIdAluno(Long idAluno) {

		this.idAluno = idAluno;
	}

	public String getDsNomeAluno() {

		return dsNomeAluno;
	}

	public void setDsNomeAluno(String dsNomeAluno) {

		this.dsNomeAluno = dsNomeAluno;
	}

	public String getDsNacionalidade() {

		return dsNacionalidade;
	}

	public void setDsNacionalidade(String dsNacionalidade) {

		this.dsNacionalidade = dsNacionalidade;
	}

	public String getDsEstadoCivil() {

		return dsEstadoCivil;
	}

	public void setDsEstadoCivil(String dsEstadoCivil) {

		this.dsEstadoCivil = dsEstadoCivil;
	}

	public String getDsEndereco() {

		return dsEndereco;
	}

	public void setDsEndereco(String dsEndereco) {

		this.dsEndereco = dsEndereco;
	}

	public String getDsBairro() {

		return dsBairro;
	}

	public void setDsBairro(String dsBairro) {

		this.dsBairro = dsBairro;
	}

	public String getDsCidade() {

		return dsCidade;
	}

	public void setDsCidade(String dsCidade) {

		this.dsCidade = dsCidade;
	}

	public String getDsUF() {

		return dsUF;
	}

	public void setDsUF(String dsUF) {

		this.dsUF = dsUF;
	}

	public String getDsCEP() {

		return dsCEP;
	}

	public void setDsCEP(String dsCEP) {

		this.dsCEP = dsCEP;
	}

	public String getDsEscolaridade() {

		return dsEscolaridade;
	}

	public void setDsEscolaridade(String dsEscolaridade) {

		this.dsEscolaridade = dsEscolaridade;
	}

	public List<FormacaoAcademicaDTO> getLsFormacaoAcademia() {

		return lsFormacaoAcademia;
	}

	public void setLsFormacaoAcademia(List<FormacaoAcademicaDTO> lsFormacaoAcademia) {

		this.lsFormacaoAcademia = lsFormacaoAcademia;
	}

	public List<ExperienciaProfissionalDTO> getLsExperienciaProfissionalDTO() {

		return lsExperienciaProfissionalDTO;
	}

	public void setLsExperienciaProfissionalDTO(List<ExperienciaProfissionalDTO> lsExperienciaProfissionalDTO) {

		this.lsExperienciaProfissionalDTO = lsExperienciaProfissionalDTO;
	}

}
