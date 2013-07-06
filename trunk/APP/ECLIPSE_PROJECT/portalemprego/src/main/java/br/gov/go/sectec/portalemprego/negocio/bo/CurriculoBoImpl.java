package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.AreaInteresseBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.CargoInteresseBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.CidadeBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.CurriculoBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.CurriculoDao;
import br.gov.go.sectec.portalemprego.core.entidade.Curriculo;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.AreaInteresseDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CargoInteresseDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CidadeDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CurriculoAlunoDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CurriculoAreaDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CurriculoCargoDTO;

/**
 * 
 * <p>
 * <b>Title:</b> CurriculoBoImpl.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Classe de Negocio
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
@Service("curriculoBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CurriculoBoImpl extends PremiumBOImpl<Curriculo, Long> implements CurriculoBo {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CurriculoDao dao;

	@Autowired
	private CidadeBo cidadeBo;

	@Autowired
	private AreaInteresseBo areaInteresseBo;

	@Autowired
	private CargoInteresseBo cargoInteresseBo;

	@Override
	public List<CidadeDTO> obterCidadePorSiglaUf(final String siglaUf) {

		return this.cidadeBo.obterCidadePorSiglaUf(siglaUf);
	}

	@Override
	public List<AreaInteresseDTO> ListarAreaInteresse() {

		return this.areaInteresseBo.listarAreaInteresseOrdenada();
	}

	public Long obterQtdCurriculoPorArea(final Long idAreaInteresse) {

		return this.getDAO().obterQtdCurriculoPorArea(idAreaInteresse);

	}

	@Override
	public Curriculo obterPorIdAluno(final Long idAluno) {

		return this.dao.obterPorIdAluno(idAluno);
	}

	@Override
	CurriculoDao getDAO() {

		return this.dao;
	}

	@Override
	public Map<String, Object> listarCurriculoArea(final Long idCidadeSelecao, final Integer idTipoSexo, final Integer idTipoFaixaEtaria, final Long idAreaInteresSelecao) {

		final Map<String, Object> total = new HashMap<String, Object>();

		final List<AreaInteresseDTO> areas = this.areaInteresseBo.ListarAreaInteresse(idCidadeSelecao, idTipoSexo, idTipoFaixaEtaria, idAreaInteresSelecao);

		final List<CurriculoAreaDTO> cvAreas = new ArrayList<CurriculoAreaDTO>();

		Long totalCurriculo = new Long(0);

		for (final AreaInteresseDTO areaDTO : areas) {

			final CurriculoAreaDTO dto = new CurriculoAreaDTO();

			dto.setIdArea(areaDTO.getIdDTO());

			dto.setArea(areaDTO.getNomeDTO());

			final Long qtdCvAera = this.obterQtdCurriculoPorArea(areaDTO.getIdDTO());

			totalCurriculo = totalCurriculo + qtdCvAera;

			dto.setTotal(qtdCvAera);

			cvAreas.add(dto);
		}

		total.put("dados", cvAreas);

		total.put("total", totalCurriculo);

		return total;
	}

	@Override
	public Map<String, Object> listarCurriculoCargo(final Long idCidadeSelecao, final Integer idTipoSexo, final Integer idTipoFaixaEtaria, final Long idArea) {

		final Map<String, Object> total = new HashMap<String, Object>();

		final List<CargoInteresseDTO> listaCargo = this.cargoInteresseBo.listarCurriculoCargo(idCidadeSelecao, idTipoSexo, idTipoFaixaEtaria, idArea);

		final List<CurriculoCargoDTO> cvCargos = new ArrayList<CurriculoCargoDTO>();

		Long totalCurriculo = new Long(0);

		for (final CargoInteresseDTO cargoDTO : listaCargo) {

			final CurriculoCargoDTO dto = new CurriculoCargoDTO();

			dto.setIdCargo(cargoDTO.getIdDTO());

			dto.setCargo(cargoDTO.getNomeDTO());

			final Long qtdCvCargo = this.obterQtdCurriculoPorCargo(cargoDTO.getIdDTO());

			totalCurriculo = totalCurriculo + qtdCvCargo;

			dto.setTotal(qtdCvCargo);

			cvCargos.add(dto);
		}

		total.put("dados", cvCargos);

		total.put("total", totalCurriculo);

		return total;
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param idDTO
	 * 
	 * @return
	 */
	private Long obterQtdCurriculoPorCargo(final Long idCargo) {

		return this.getDAO().obterQtdCurriculoPorCargo(idCargo);
	}

	@Override
	public List<CurriculoAlunoDTO> listarCurriculoAluno(final Long idCidadeSelecao, final Integer idTipoSexo, final Integer idTipoFaixaEtaria, final Long idCargo) {

		final List<CurriculoAlunoDTO> listaAluno = this.getDAO().listarCurriculoAluno(idCidadeSelecao, idTipoSexo, idTipoFaixaEtaria, idCargo);

		return listaAluno;
	}

}
