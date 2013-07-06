package br.gov.go.sectec.portalemprego.core.entidade.DTO;

import java.io.Serializable;
import java.util.Date;

import br.gov.go.sectec.portalemprego.comum.utilitario.DataUtil;

/**
 * <p>
 * <b>Title:</b> EscolaridadeDTO.java
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
public class FormacaoAcademicaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idDTO;

	private Long idCursoSectec;

	private Long idGrau;

	private String grau;

	private String instituicao;

	private String curso;

	private String dtFim;

	private String dtInicio;

	private Date dtFinal;
	
	private Date dtInicial;
	
	private String periodo;

	private Integer index;

	public Long getIdCursoSectec() {

		return this.idCursoSectec;
	}

	public void setIdCursoSectec(final Long idCursoSectec) {

		this.idCursoSectec = idCursoSectec;
	}

	public Long getIdGrau() {

		return this.idGrau;
	}

	public void setIdGrau(final Long idGrau) {

		this.idGrau = idGrau;
	}

	public String getGrau() {

		return this.grau;
	}

	public void setGrau(final String grau) {

		this.grau = grau;
	}

	public String getPeriodo() {

		return this.periodo;
	}

	public void setPeriodo(final String periodo) {

		this.periodo = periodo;
	}

	public String getCurso() {

		return this.curso;
	}

	public void setCurso(final String curso) {

		this.curso = curso;
	}

	public String getDtFim() {

		return this.dtFim;
	}

	public void setDtFim(final String dtFim) {

		this.dtFim = dtFim;
	}

	public String getDtInicio() {

		return this.dtInicio;
	}

	public void setDtInicio(final String dtInicio) {

		this.dtInicio = dtInicio;
	}

	public String getInstituicao() {

		return this.instituicao;
	}

	public void setInstituicao(final String instituicao) {

		this.instituicao = instituicao;
	}

	public Integer getIndex() {

		return this.index;
	}

	public void setIndex(final Integer index) {

		this.index = index;
	}

	public Long getIdDTO() {

		return this.idDTO;
	}

	public void setIdDTO(final Long idDTO) {

		this.idDTO = idDTO;
	}

	
	public Date getDtFinal() {
	
		return dtFinal;
	}

	
	public void setDtFinal(Date dtFinal) {
	
		this.dtFinal = dtFinal;
	}

	
	public Date getDtInicial() {
	
		return dtInicial;
	}

	
	public void setDtInicial(Date dtInicial) {
		
		this.dtInicial = dtInicial;
	}

	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 */
	public void formatarDatas() {
		
		if(dtInicial!=null){
			
			dtInicio = DataUtil.format(dtInicial);
		}
		if(dtFinal!=null){
			
			dtFim = DataUtil.format(dtFinal);
		}
		
		setPeriodo(getDtInicio() + " à " + getDtFim());

	}
	
	

}
