package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 
 * <p>
 * <b>Title:</b> FormacaoAcademica.java
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
@Entity(name = "adm_formacao_academica")
public class FormacaoAcademica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_formacao_academica", unique = true, nullable = false)
	private Long idFormacaoAcademica;

	@Column(name = "ds_curso", length = 250)
	private String dsCurso;

	@Column(name = "ds_instituicao", length = 250)
	private String dsInstituicao;

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_fim_curso")
	private Date dtFimCurso;

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_inicio_curso", nullable = false)
	private Date dtInicioCurso;

	@Column(name = "id_curso_sectec")
	private Long idCursoSectec;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_curriculo")
	private Curriculo curriculo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_grau", nullable = false)
	private TipoGrau tipoGrau;

	@Transient
	private String dtFimStr;

	@Transient
	private String dtInicioStr;

	@Transient
	private String periodo;

	public FormacaoAcademica() {

	}

	public Long getIdFormacaoAcademica() {

		return this.idFormacaoAcademica;
	}

	public void setIdFormacaoAcademica(final Long idFormacaoAcademica) {

		this.idFormacaoAcademica = idFormacaoAcademica;
	}

	public String getDsCurso() {

		return this.dsCurso;
	}

	public void setDsCurso(final String dsCurso) {

		this.dsCurso = dsCurso;
	}

	public String getDsInstituicao() {

		return this.dsInstituicao;
	}

	public void setDsInstituicao(final String dsInstituicao) {

		this.dsInstituicao = dsInstituicao;
	}

	public Date getDtFimCurso() {

		return this.dtFimCurso;
	}

	public void setDtFimCurso(final Date dtFimCurso) {

		this.dtFimCurso = dtFimCurso;
	}

	public Date getDtInicioCurso() {

		return this.dtInicioCurso;
	}

	public void setDtInicioCurso(final Date dtInicioCurso) {

		this.dtInicioCurso = dtInicioCurso;
	}

	public Long getIdCursoSectec() {

		return this.idCursoSectec;
	}

	public void setIdCursoSectec(final Long idCursoSectec) {

		this.idCursoSectec = idCursoSectec;
	}

	// bi-directional many-to-one association to Curriculo

	public Curriculo getCurriculo() {

		return this.curriculo;
	}

	public void setCurriculo(final Curriculo curriculo) {

		this.curriculo = curriculo;
	}

	// bi-directional many-to-one association to TipoGrau
	public TipoGrau getTipoGrau() {

		return this.tipoGrau;
	}

	public void setTipoGrau(final TipoGrau tipoGrau) {

		this.tipoGrau = tipoGrau;
	}

	public String getDtFimStr() {

		return this.dtFimStr;
	}

	public void setDtFimStr(final String dtFimStr) {

		this.dtFimStr = dtFimStr;
	}

	public String getDtInicioStr() {

		return this.dtInicioStr;
	}

	public void setDtInicioStr(final String dtInicioStr) {

		this.dtInicioStr = dtInicioStr;
	}

	public String getPeriodo() {

		return this.periodo;
	}

	public void setPeriodo(final String periodo) {

		this.periodo = periodo;
	}

}
