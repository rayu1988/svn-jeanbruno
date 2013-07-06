package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 
 * <p>
 * <b>Title:</b> Empresa.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para Empresas/Instituicoes
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

@Entity(name = "adm_pessoa_juridica")
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_pessoa_juridica")
	private Long idEmpresa;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco")
	private Endereco endereco;

	@ManyToOne
	@JoinColumn(name = "id_ramo_atividade")
	private RamoAtividade ramoAtividade;

	@Column(name = "nu_cnpj")
	private String numCNPJ;

	@Column(name = "ds_razao_social")
	private String dsRazsoSocial;

	@Column(name = "ds_responsavel")
	private String dsResponsavel;

	@Column(name = "nu_cpf")
	private String numCPFResponsavel;

	@Column(name = "ds_email")
	private String emailResponsavel;

	@Column(name = "ds_login")
	private String dsLogin;

	@Column(name = "ds_senha")
	private String dsSenha;
	
	@Transient
	private String dsSenhaConfirmacao;

	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Telefone> telefones;
	
	@OneToMany(mappedBy = "empresa",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<VagaOfertada> vagaOfertadas;

	public Long getIdEmpresa() {
	
		return idEmpresa;
	}

	
	public void setIdEmpresa(Long idEmpresa) {
	
		this.idEmpresa = idEmpresa;
	}

	public Endereco getEndereco() {

		return endereco;
	}

	public void setEndereco(Endereco endereco) {

		this.endereco = endereco;
	}

	public RamoAtividade getRamoAtividade() {

		return ramoAtividade;
	}

	public void setRamoAtividade(RamoAtividade ramoAtividade) {

		this.ramoAtividade = ramoAtividade;
	}

	public String getDsRazsoSocial() {

		return dsRazsoSocial;
	}

	public void setDsRazsoSocial(String dsRazsoSocial) {

		this.dsRazsoSocial = dsRazsoSocial;
	}

	public String getDsResponsavel() {

		return dsResponsavel;
	}

	public void setDsResponsavel(String dsResponsavel) {

		this.dsResponsavel = dsResponsavel;
	}

	public String getNumCPFResponsavel() {

		return numCPFResponsavel;
	}

	public void setNumCPFResponsavel(String numCPFResponsavel) {

		this.numCPFResponsavel = numCPFResponsavel;
	}

	public String getEmailResponsavel() {

		return emailResponsavel;
	}

	public void setEmailResponsavel(String emailResponsavel) {

		this.emailResponsavel = emailResponsavel;
	}

	public String getNumCNPJ() {

		return numCNPJ;
	}

	public void setNumCNPJ(String numCNPJ) {

		this.numCNPJ = numCNPJ;
	}

	public List<Telefone> getTelefones() {

		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {

		this.telefones = telefones;
	}

	
	public List<VagaOfertada> getVagaOfertadas() {
	
		return vagaOfertadas;
	}

	
	public void setVagaOfertadas(List<VagaOfertada> vagaOfertadas) {
	
		this.vagaOfertadas = vagaOfertadas;
	}

	
	public String getDsLogin() {
	
		return dsLogin;
	}

	
	public void setDsLogin(String dsLogin) {
	
		this.dsLogin = dsLogin;
	}

	
	public String getDsSenha() {
	
		return dsSenha;
	}

	
	public void setDsSenha(String dsSenha) {
	
		this.dsSenha = dsSenha;
	}

	
	public String getDsSenhaConfirmacao() {
	
		return dsSenhaConfirmacao;
	}

	
	public void setDsSenhaConfirmacao(String dsSenhaConfirmacao) {
	
		this.dsSenhaConfirmacao = dsSenhaConfirmacao;
	}

}
