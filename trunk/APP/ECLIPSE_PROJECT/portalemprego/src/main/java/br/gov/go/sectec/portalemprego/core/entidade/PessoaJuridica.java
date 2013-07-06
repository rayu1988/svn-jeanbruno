package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the adm_pessoa_juridica database table.
 * 
 */
public class PessoaJuridica implements Serializable {

	private static final long serialVersionUID = 1L;
/*
	private static final long serialVersionUID = 1L;

	private Long idPessoaJuridica;

	private String dsEmail;

	private String dsRazaoSocial;

	private String dsResponsavel;

	private String nuCnpj;

	private String nuCpf;

	private List<ControleEmpregoEfetivo> controleEmpregoEfetivos;

	private List<ControleEncaminhamento> controleEncaminhamentos;

	private Endereco endereco;

	private RamoAtividade ramoAtividade;

	private List<VagaOfertada> vagaOfertadas;

	public PessoaJuridica() {

	}

	@Id
	@Column(name = "id_pessoa_juridica", unique = true, nullable = false)
	public Long getIdPessoaJuridica() {

		return this.idPessoaJuridica;
	}

	public void setIdPessoaJuridica(final Long idPessoaJuridica) {

		this.idPessoaJuridica = idPessoaJuridica;
	}

	@Column(name = "ds_email", length = 320)
	public String getDsEmail() {

		return this.dsEmail;
	}

	public void setDsEmail(final String dsEmail) {

		this.dsEmail = dsEmail;
	}

	@Column(name = "ds_razao_social", nullable = false, length = 60)
	public String getDsRazaoSocial() {

		return this.dsRazaoSocial;
	}

	public void setDsRazaoSocial(final String dsRazaoSocial) {

		this.dsRazaoSocial = dsRazaoSocial;
	}

	@Column(name = "ds_responsavel", length = 100)
	public String getDsResponsavel() {

		return this.dsResponsavel;
	}

	public void setDsResponsavel(final String dsResponsavel) {

		this.dsResponsavel = dsResponsavel;
	}

	@Column(name = "nu_cnpj", nullable = false, length = 18)
	public String getNuCnpj() {

		return this.nuCnpj;
	}

	public void setNuCnpj(final String nuCnpj) {

		this.nuCnpj = nuCnpj;
	}

	@Column(name = "nu_cpf", length = 11)
	public String getNuCpf() {

		return this.nuCpf;
	}

	public void setNuCpf(final String nuCpf) {

		this.nuCpf = nuCpf;
	}

	// bi-directional many-to-one association to ControleEmpregoEfetivo
	@OneToMany(mappedBy = "pessoaJuridica")
	public List<ControleEmpregoEfetivo> getControleEmpregoEfetivos() {

		return this.controleEmpregoEfetivos;
	}

	public void setControleEmpregoEfetivos(final List<ControleEmpregoEfetivo> controleEmpregoEfetivos) {

		this.controleEmpregoEfetivos = controleEmpregoEfetivos;
	}

	public ControleEmpregoEfetivo addControleEmpregoEfetivo(final ControleEmpregoEfetivo controleEmpregoEfetivo) {

		this.getControleEmpregoEfetivos().add(controleEmpregoEfetivo);
		controleEmpregoEfetivo.setPessoaJuridica(this);

		return controleEmpregoEfetivo;
	}

	public ControleEmpregoEfetivo removeControleEmpregoEfetivo(final ControleEmpregoEfetivo controleEmpregoEfetivo) {

		this.getControleEmpregoEfetivos().remove(controleEmpregoEfetivo);
		controleEmpregoEfetivo.setPessoaJuridica(null);

		return controleEmpregoEfetivo;
	}

	// bi-directional many-to-one association to ControleEncaminhamento
	@OneToMany(mappedBy = "pessoaJuridica")
	public List<ControleEncaminhamento> getControleEncaminhamentos() {

		return this.controleEncaminhamentos;
	}

	public void setControleEncaminhamentos(final List<ControleEncaminhamento> controleEncaminhamentos) {

		this.controleEncaminhamentos = controleEncaminhamentos;
	}

	public ControleEncaminhamento addControleEncaminhamento(final ControleEncaminhamento controleEncaminhamento) {

		this.getControleEncaminhamentos().add(controleEncaminhamento);
		controleEncaminhamento.setPessoaJuridica(this);

		return controleEncaminhamento;
	}

	public ControleEncaminhamento removeControleEncaminhamento(final ControleEncaminhamento controleEncaminhamento) {

		this.getControleEncaminhamentos().remove(controleEncaminhamento);
		controleEncaminhamento.setPessoaJuridica(null);

		return controleEncaminhamento;
	}

	// bi-directional many-to-one association to Endereco
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_endereco", nullable = false)
	public Endereco getEndereco() {

		return this.endereco;
	}

	public void setEndereco(final Endereco endereco) {

		this.endereco = endereco;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ramo_atividade")
	public RamoAtividade getRamoAtividade() {

		return this.ramoAtividade;
	}

	public void setRamoAtividade(final RamoAtividade ramoAtividade) {

		this.ramoAtividade = ramoAtividade;
	}
*/

}
