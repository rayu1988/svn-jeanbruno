package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the adm_controle_emprego_efetivo database table.
 * 
 */
@Entity
@Table(name = "adm_controle_emprego_efetivo")
public class ControleEmpregoEfetivo implements Serializable {

	private static final long serialVersionUID = 1L;

	private ControleEmpregoEfetivoPK id;

	private String dsSugestao;

	private Date dtAtualizacao;

	private Boolean isEgressoRequisitoTecnico;

	private Boolean isHouveEfetivacaoEmprego;

	//private PessoaJuridica pessoaJuridica;

	public ControleEmpregoEfetivo() {

	}

	@EmbeddedId
	public ControleEmpregoEfetivoPK getId() {

		return this.id;
	}

	public void setId(final ControleEmpregoEfetivoPK id) {

		this.id = id;
	}

	@Column(name = "ds_sugestao", length = 4000)
	public String getDsSugestao() {

		return this.dsSugestao;
	}

	public void setDsSugestao(final String dsSugestao) {

		this.dsSugestao = dsSugestao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_atualizacao")
	public Date getDtAtualizacao() {

		return this.dtAtualizacao;
	}

	public void setDtAtualizacao(final Date dtAtualizacao) {

		this.dtAtualizacao = dtAtualizacao;
	}

	@Column(name = "is_egresso_requisito_tecnico")
	public Boolean getIsEgressoRequisitoTecnico() {

		return this.isEgressoRequisitoTecnico;
	}

	public void setIsEgressoRequisitoTecnico(final Boolean isEgressoRequisitoTecnico) {

		this.isEgressoRequisitoTecnico = isEgressoRequisitoTecnico;
	}

	@Column(name = "is_houve_efetivacao_emprego")
	public Boolean getIsHouveEfetivacaoEmprego() {

		return this.isHouveEfetivacaoEmprego;
	}

	public void setIsHouveEfetivacaoEmprego(final Boolean isHouveEfetivacaoEmprego) {

		this.isHouveEfetivacaoEmprego = isHouveEfetivacaoEmprego;
	}

}
