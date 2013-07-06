package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 
 * <p>
 * <b>Title:</b> Aluno.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para Alunos / Curriculos
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
@Entity(name = "adm_aluno")
public class Aluno implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_aluno")
	private Long idAluno;

	@Column(name = "ds_email_pessoal")
	private String dsEmailPessoal;

	@Column(name = "ds_login")
	private String dsLogin;

	@Column(name = "ds_matricula")
	private String dsMatricula;

	@Column(name = "ds_nome")
	private String dsNome;

	@Column(name = "ds_rg_emissor")
	private String dsRgEmissor;

	@Column(name = "ds_senha")
	private String dsSenha;

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_cadastro")
	private Date dtCadastro;

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_nascimento")
	private Date dtNascimento;

	@Column(name = "dt_rg_emissao")
	private Date dtRgEmissao;

	@Column(name = "id_controle_encaminhamento")
	private Integer idControleEncaminhamento;

	@Column(name = "id_naturalidade")
	private Long idNaturalidade;

	@Column(name = "is_aceite_termo_compromisso")
	private Boolean isAceiteTermoCompromisso;

	@Column(name = "is_ativo")
	private Boolean isAtivo;

	@Column(name = "is_deficiente")
	private Boolean isDeficiente;

	@Column(name = "is_disponibilidade_viajar")
	private Boolean isDisponibilidadeViajar;

	@Column(name = "is_divulgar_dado")
	private Boolean isDivulgarDado;

	@Column(name = "nu_cpf")
	private String nuCpf;

	@Column(name = "nu_filho")
	private Integer nuFilho;

	@Column(name = "nu_rg")
	private String nuRg;

	@Column(name = "tp_sexo")
	private Integer tpSexo;

	@Column(name = "vl_pretensao_salarial")
	private BigDecimal vlPretensaoSalarial;

	@ManyToOne
	@JoinColumn(name = "id_escolaridade")
	private Escolaridade escolaridade;

	@ManyToOne
	@JoinColumn(name = "id_cargo_interesse")
	private CargoInteresse cargoInteresse;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco")
	private Endereco endereco;

	@ManyToOne
	@JoinColumn(name = "id_estado_civil")
	private EstadoCivil estadoCivil;

	@ManyToOne
	@JoinColumn(name = "id_tipo_deficiencia")
	private TipoDeficiencia tipoDeficiencia;

	@OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
	private List<Curriculo> curriculos;

	@OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Telefone> telefones;
	
	@Transient
	private String dtEmisaoStr;

	@Transient
	private String dtNascimentoStr;

	@Transient
	private String vlPretSalarioStr;
	
	
	public String getDsEmailPessoal() {

		return dsEmailPessoal;
	}

	
	public Long getIdAluno() {
	
		return idAluno;
	}

	
	public void setIdAluno(Long idAluno) {
	
		this.idAluno = idAluno;
	}

	
	public List<Curriculo> getCurriculos() {
	
		return curriculos;
	}

	
	public void setCurriculos(List<Curriculo> curriculos) {
	
		this.curriculos = curriculos;
	}

	public void setDsEmailPessoal(String dsEmailPessoal) {

		this.dsEmailPessoal = dsEmailPessoal;
	}

	public String getDsLogin() {

		return dsLogin;
	}

	public void setDsLogin(String dsLogin) {

		this.dsLogin = dsLogin;
	}

	public String getDsMatricula() {

		return dsMatricula;
	}

	public void setDsMatricula(String dsMatricula) {

		this.dsMatricula = dsMatricula;
	}

	public String getDsNome() {

		return dsNome;
	}

	public void setDsNome(String dsNome) {

		this.dsNome = dsNome;
	}

	public String getDsRgEmissor() {

		return dsRgEmissor;
	}

	public void setDsRgEmissor(String dsRgEmissor) {

		this.dsRgEmissor = dsRgEmissor;
	}

	public String getDsSenha() {

		return dsSenha;
	}

	public void setDsSenha(String dsSenha) {

		this.dsSenha = dsSenha;
	}

	public Date getDtCadastro() {

		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {

		this.dtCadastro = dtCadastro;
	}

	public Date getDtNascimento() {

		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {

		this.dtNascimento = dtNascimento;
	}

	public Date getDtRgEmissao() {

		return dtRgEmissao;
	}

	public void setDtRgEmissao(Date dtRgEmissao) {

		this.dtRgEmissao = dtRgEmissao;
	}

	public Integer getIdControleEncaminhamento() {

		return idControleEncaminhamento;
	}

	public void setIdControleEncaminhamento(Integer idControleEncaminhamento) {

		this.idControleEncaminhamento = idControleEncaminhamento;
	}

	public Long getIdNaturalidade() {

		return idNaturalidade;
	}

	public void setIdNaturalidade(Long idNaturalidade) {

		this.idNaturalidade = idNaturalidade;
	}

	public Boolean getIsAceiteTermoCompromisso() {

		return isAceiteTermoCompromisso;
	}

	public void setIsAceiteTermoCompromisso(Boolean isAceiteTermoCompromisso) {

		this.isAceiteTermoCompromisso = isAceiteTermoCompromisso;
	}

	public Boolean getIsAtivo() {

		return isAtivo;
	}

	public void setIsAtivo(Boolean isAtivo) {

		this.isAtivo = isAtivo;
	}

	public Boolean getIsDeficiente() {

		return isDeficiente;
	}

	public void setIsDeficiente(Boolean isDeficiente) {

		this.isDeficiente = isDeficiente;
	}

	public Boolean getIsDisponibilidadeViajar() {

		return isDisponibilidadeViajar;
	}

	public void setIsDisponibilidadeViajar(Boolean isDisponibilidadeViajar) {

		this.isDisponibilidadeViajar = isDisponibilidadeViajar;
	}

	public Boolean getIsDivulgarDado() {

		return isDivulgarDado;
	}

	public void setIsDivulgarDado(Boolean isDivulgarDado) {

		this.isDivulgarDado = isDivulgarDado;
	}

	public String getNuCpf() {

		return nuCpf;
	}

	public void setNuCpf(String nuCpf) {

		this.nuCpf = nuCpf;
	}

	public Integer getNuFilho() {

		return nuFilho;
	}

	public void setNuFilho(Integer nuFilho) {

		this.nuFilho = nuFilho;
	}

	public String getNuRg() {

		return nuRg;
	}

	public void setNuRg(String nuRg) {

		this.nuRg = nuRg;
	}

	public Integer getTpSexo() {

		return tpSexo;
	}

	public void setTpSexo(Integer tpSexo) {

		this.tpSexo = tpSexo;
	}

	
	public BigDecimal getVlPretensaoSalarial() {
	
		return vlPretensaoSalarial;
	}


	
	public void setVlPretensaoSalarial(BigDecimal vlPretensaoSalarial) {
	
		this.vlPretensaoSalarial = vlPretensaoSalarial;
	}


	public Escolaridade getEscolaridade() {

		return escolaridade;
	}

	public void setEscolaridade(Escolaridade escolaridade) {

		this.escolaridade = escolaridade;
	}

	public CargoInteresse getCargoInteresse() {

		return cargoInteresse;
	}

	public void setCargoInteresse(CargoInteresse cargoInteresse) {

		this.cargoInteresse = cargoInteresse;
	}

	public Endereco getEndereco() {

		return endereco;
	}

	public void setEndereco(Endereco endereco) {

		this.endereco = endereco;
	}

	public EstadoCivil getEstadoCivil() {

		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {

		this.estadoCivil = estadoCivil;
	}

	public TipoDeficiencia getTipoDeficiencia() {

		return tipoDeficiencia;
	}

	public void setTipoDeficiencia(TipoDeficiencia tipoDeficiencia) {

		this.tipoDeficiencia = tipoDeficiencia;
	}

	public List<Telefone> getTelefones() {

		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {

		this.telefones = telefones;
	}


	
	public String getDtEmisaoStr() {
	
		return dtEmisaoStr;
	}


	
	public void setDtEmisaoStr(String dtEmisaoStr) {
	
		this.dtEmisaoStr = dtEmisaoStr;
	}


	
	public String getDtNascimentoStr() {
	
		return dtNascimentoStr;
	}


	
	public void setDtNascimentoStr(String dtNascimentoStr) {
	
		this.dtNascimentoStr = dtNascimentoStr;
	}


	
	public String getVlPretSalarioStr() {
	
		return vlPretSalarioStr;
	}


	
	public void setVlPretSalarioStr(String vlPretSalarioStr) {
	
		this.vlPretSalarioStr = vlPretSalarioStr;
	}


}
