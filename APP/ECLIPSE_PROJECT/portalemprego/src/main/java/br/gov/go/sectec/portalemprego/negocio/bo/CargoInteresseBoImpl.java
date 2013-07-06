package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.CargoInteresseBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.CargoInteresseDao;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;
import br.gov.go.sectec.portalemprego.core.entidade.CargoInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CargoInteresseDTO;

@Service("cargoInteresseBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
public class CargoInteresseBoImpl extends PremiumBOImpl<CargoInteresse, Long> implements CargoInteresseBo {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CargoInteresseDao cargoInteresseDao;

	@Override
	public List<CargoInteresseDTO> listarCargoInteresse() {

		return cargoInteresseDao.listarCargoInteresse();
	}

	@Override
	CargoInteresseDao getDAO() {

		return cargoInteresseDao;
	}

	@Override
	public CargoInteresse obterCargoInteressePorIdAluno(Long idAluno) {

		return cargoInteresseDao.obterCargoInteressePorIdAluno(idAluno);
	}

	@Override
	public List<CargoInteresseDTO> listarCurriculoCargo(Long idCidadeSelecao, Integer idTipoSexo, Integer idTipoFaixaEtaria, Long idArea) {

		return cargoInteresseDao.listarCurriculoCargo(idCidadeSelecao, idTipoSexo, idTipoFaixaEtaria,idArea);
	}
	

}
