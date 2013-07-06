package br.gov.go.sectec.portalemprego.web.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.go.sectec.portalemprego.comum.enumerator.FaixaEtariaEnum;
import br.gov.go.sectec.portalemprego.comum.enumerator.SexoEnum;
import br.gov.go.sectec.portalemprego.comum.interfacebo.CurriculoBo;
import br.gov.go.sectec.portalemprego.comum.utilitario.ValidatorUtil;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.AreaInteresseDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CidadeDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CurriculoAlunoDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CurriculoAreaDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CurriculoCargoDTO;

/**
 * <p>
 * <b>Title:</b> VisualizarCurriculoAction.java
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
@ParentPackage(value = "portalemprego")
@Namespace("/visualizar")
@Component("visualizarAction")
public class VisualizarCurriculoAction extends PremiumAction {

	private static final long serialVersionUID = 1L;

	// booleanos
	private boolean showCurriculoArea;

	private boolean showCurriculoCargo;

	private boolean showCurriculo;

	@Autowired
	private CurriculoBo curriculoBo;

	// totais
	private Long totalArea;

	private Long totalCargo;

	// ids seleção combos.
	private Long idCidadeSelecao;

	private Long idAreaInteresSelecao;

	private Integer idTipoSexo;

	private Integer idTipoFaixaEtaria;

	private Integer idAluno;

	private String nomeAreaInteresseCurriculo;

	private String nomeCargoInteresseCurriculo;

	// listas
	private List<CidadeDTO> cidadeList;

	private List<AreaInteresseDTO> areaInteresseList;

	private List<SexoEnum> sexoList = new ArrayList<SexoEnum>(Arrays.asList(SexoEnum.values()));

	private List<FaixaEtariaEnum> faixaEtarialist = new ArrayList<FaixaEtariaEnum>(Arrays.asList(FaixaEtariaEnum.values()));

	private List<CurriculoAreaDTO> curriculoAreaList;

	private List<CurriculoCargoDTO> curriculoCargoList;

	private List<CurriculoAlunoDTO> curriculoAlunoList;
	

	@Action(value = "/gerarRelatorioAluno", results = { @Result(location="/jasperreports/relatorio.jasper", type = "jasper", name = EmpresaAction.SUCCESS,  params = { "localtion", "relatorio.jasper" 
			,"dataSource","curriculoCargoList","format","PDF"}) })
	public String gerarRelatorioAluno() {
		this.curriculoCargoList = new ArrayList<CurriculoCargoDTO>();
		CurriculoCargoDTO dto = new CurriculoCargoDTO();
		curriculoCargoList.add(dto);
		
		return PremiumAction.SUCCESS;
	}

	
	@Action(value = "/abreVisualizarCurriculo", results = { @Result(location = "visualizar.curriculo.page", type = "tiles", name = EmpresaAction.SUCCESS) })
	public String abreVisualizarCurriculo() {

		return PremiumAction.SUCCESS;
	}

	@Action(value = "/abreVisualizarCurriculoTable", results = { @Result(location = "visualizar.curriculo.page", type = "tiles", name = EmpresaAction.SUCCESS) })
	public String abreVisualizarCurriculoTable() {

		this.showCurriculo = true;

		this.showCurriculoArea = true;

		this.showCurriculoCargo = true;

		this.curriculoCargoList = (List<CurriculoCargoDTO>) this.obterDaSessao("curriculoCargoList");

		Long idCargo = null;

		for (final CurriculoCargoDTO dto : this.curriculoCargoList) {

			if (dto.getCargo().equalsIgnoreCase(this.getNomeCargoInteresseCurriculo().trim())) {

				idCargo = dto.getIdCargo();

				break;

			}

		}

		this.obterValoresSessao();

		this.curriculoAlunoList = this.curriculoBo.listarCurriculoAluno(this.idCidadeSelecao, this.idTipoSexo, this.idTipoFaixaEtaria, idCargo);

		this.totalArea = (Long) obterDaSessao("totalArea");
		
		this.incluirSessao("curriculoAlunoList", this.curriculoAlunoList);

		return PremiumAction.SUCCESS;
	}

	@Action(value = "/abreCurriculoPorCargo", results = { @Result(location = "visualizar.curriculo.page", type = "tiles", name = EmpresaAction.SUCCESS) })
	public String abreCurriculoPorCargo() {

		this.showCurriculoArea = true;

		this.showCurriculoCargo = true;

		this.showCurriculo = false;

		this.curriculoAreaList = (List<CurriculoAreaDTO>) this.obterDaSessao("curriculoAreaList");

		Long idArea = null;

		for (final CurriculoAreaDTO dto : this.curriculoAreaList) {

			if (dto.getArea().equalsIgnoreCase(this.getNomeAreaInteresseCurriculo().trim())) {

				idArea = dto.getIdArea();

				break;

			}

		}

		this.obterValoresSessao();

		final Map<String, Object> dados = this.curriculoBo.listarCurriculoCargo(this.idCidadeSelecao, this.idTipoSexo, this.idTipoFaixaEtaria, idArea);

		this.curriculoCargoList = ( (List<CurriculoCargoDTO>) dados.get("dados") );

		this.totalCargo = (Long) dados.get("total");
		
		this.totalArea = (Long) obterDaSessao("totalArea");

		this.atribuirValoresIds();

		this.incluirSessao("curriculoCargoList", this.curriculoCargoList);

		return PremiumAction.SUCCESS;
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void obterValoresSessao() {

		this.idCidadeSelecao = (Long) this.obterDaSessao("idCidadeSelecao");

		this.idTipoFaixaEtaria = (Integer) this.obterDaSessao("idTipoFaixaEtaria");

		this.idTipoSexo = (Integer) this.obterDaSessao("idTipoSexo");
	}

	@Action(value = "/listarVisualizarCidadeGoias", results = { @Result(name = "success", type = "json", params = { "includeProperties", "cidadeList.*" }) })
	public String listarVisualizarCidadeGoias() {

		this.setCidadeList(this.curriculoBo.obterCidadePorSiglaUf("GO"));

		return PremiumAction.SUCCESS;
	}

	@Action(value = "/listarAreaInteresseVisualizar", results = { @Result(name = "success", type = "json", params = { "includeProperties", "areaInteresseList.*" }) })
	public String listarAreaInteresseVisualizar() {

		this.setAreaInteresseList(this.curriculoBo.ListarAreaInteresse());

		return PremiumAction.SUCCESS;
	}

	@Action(value = "/pesquisarCurriculoPorArea", results = { @Result(location = "visualizar.curriculo.page", type = "tiles", name = EmpresaAction.SUCCESS) })
	public String pesquisarCurriculoPorArea() {

		this.showCurriculoCargo = false;

		this.showCurriculo = false;

		if (ValidatorUtil.isNull(this.idCidadeSelecao) || this.idCidadeSelecao <= 0) {

			this.adicionarMensagemValidacao("O campo cidade é obrigatório");

			this.showCurriculoArea = false;

			return PremiumAction.SUCCESS;

		}

		final Map<String, Object> dados = this.curriculoBo.listarCurriculoArea(this.idCidadeSelecao, this.idTipoSexo, this.idTipoFaixaEtaria, this.idAreaInteresSelecao);

		this.curriculoAreaList = ( (List<CurriculoAreaDTO>) dados.get("dados") );

		this.totalArea = (Long) dados.get("total");

		this.incluirSessao("curriculoAreaList", this.curriculoAreaList);

		this.incluirSessao("totalArea", this.totalArea);

		this.showCurriculoArea = true;

		this.atribuirValoresIds();

		return PremiumAction.SUCCESS;
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void atribuirValoresIds() {

		this.incluirSessao("idCidadeSelecao", this.idCidadeSelecao);

		this.incluirSessao("idTipoFaixaEtaria", this.idTipoFaixaEtaria);

		this.incluirSessao("idTipoSexo", this.idTipoSexo);

	}

	public boolean isShowCurriculoArea() {

		return this.showCurriculoArea;
	}

	public void setShowCurriculoArea(final boolean showCurriculoArea) {

		this.showCurriculoArea = showCurriculoArea;
	}

	public List<CidadeDTO> getCidadeList() {

		return this.cidadeList;
	}

	public void setCidadeList(final List<CidadeDTO> cidadeList) {

		this.cidadeList = cidadeList;
	}

	public Long getIdCidadeSelecao() {

		return this.idCidadeSelecao;
	}

	public void setIdCidadeSelecao(final Long idCidadeSelecao) {

		this.idCidadeSelecao = idCidadeSelecao;
	}

	public List<SexoEnum> getSexoList() {

		return this.sexoList;
	}

	public void setSexoList(final List<SexoEnum> sexoList) {

		this.sexoList = sexoList;
	}

	public Integer getIdTipoSexo() {

		return this.idTipoSexo;
	}

	public void setIdTipoSexo(final Integer idTipoSexo) {

		this.idTipoSexo = idTipoSexo;
	}

	public CurriculoBo getCurriculoBo() {

		return this.curriculoBo;
	}

	public void setCurriculoBo(final CurriculoBo curriculoBo) {

		this.curriculoBo = curriculoBo;
	}

	public Integer getIdTipoFaixaEtaria() {

		return this.idTipoFaixaEtaria;
	}

	public void setIdTipoFaixaEtaria(final Integer idTipoFaixaEtaria) {

		this.idTipoFaixaEtaria = idTipoFaixaEtaria;
	}

	public List<FaixaEtariaEnum> getFaixaEtarialist() {

		return this.faixaEtarialist;
	}

	public void setFaixaEtarialist(final List<FaixaEtariaEnum> faixaEtarialist) {

		this.faixaEtarialist = faixaEtarialist;
	}

	public List<AreaInteresseDTO> getAreaInteresseList() {

		return this.areaInteresseList;
	}

	public void setAreaInteresseList(final List<AreaInteresseDTO> areaInteresseList) {

		this.areaInteresseList = areaInteresseList;
	}

	public Long getIdAreaInteresSelecao() {

		return this.idAreaInteresSelecao;
	}

	public void setIdAreaInteresSelecao(final Long idAreaInteresSelecao) {

		this.idAreaInteresSelecao = idAreaInteresSelecao;
	}

	public List<CurriculoAreaDTO> getCurriculoAreaList() {

		return this.curriculoAreaList;
	}

	public void setCurriculoAreaList(final List<CurriculoAreaDTO> curriculoAreaList) {

		this.curriculoAreaList = curriculoAreaList;
	}

	public Long getTotalArea() {

		return this.totalArea;
	}

	public void setTotalArea(final Long totalArea) {

		this.totalArea = totalArea;
	}

	public boolean isShowCurriculoCargo() {

		return this.showCurriculoCargo;
	}

	public void setShowCurriculoCargo(final boolean showCurriculoCargo) {

		this.showCurriculoCargo = showCurriculoCargo;
	}

	public String getNomeAreaInteresseCurriculo() {

		return this.nomeAreaInteresseCurriculo;
	}

	public void setNomeAreaInteresseCurriculo(final String nomeAreaInteresseCurriculo) {

		this.nomeAreaInteresseCurriculo = nomeAreaInteresseCurriculo;
	}

	public Long getTotalCargo() {

		return this.totalCargo;
	}

	public void setTotalCargo(final Long totalCargo) {

		this.totalCargo = totalCargo;
	}

	public List<CurriculoCargoDTO> getCurriculoCargoList() {

		return this.curriculoCargoList;
	}

	public void setCurriculoCargoList(final List<CurriculoCargoDTO> curriculoCargoList) {

		this.curriculoCargoList = curriculoCargoList;
	}

	public String getNomeCargoInteresseCurriculo() {

		return this.nomeCargoInteresseCurriculo;
	}

	public void setNomeCargoInteresseCurriculo(final String nomeCargoInteresseCurriculo) {

		this.nomeCargoInteresseCurriculo = nomeCargoInteresseCurriculo;
	}

	public boolean isShowCurriculo() {

		return this.showCurriculo;
	}

	public void setShowCurriculo(final boolean showCurriculo) {

		this.showCurriculo = showCurriculo;
	}

	public List<CurriculoAlunoDTO> getCurriculoAlunoList() {

		return this.curriculoAlunoList;
	}

	public void setCurriculoAlunoList(final List<CurriculoAlunoDTO> curriculoAlunoList) {

		this.curriculoAlunoList = curriculoAlunoList;
	}

	public Integer getIdAluno() {

		return this.idAluno;
	}

	public void setIdAluno(final Integer idAluno) {

		this.idAluno = idAluno;
	}

}
