package br.gov.go.sectec.portalemprego.negocio.dao;

import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.VagaOfertadaDao;
import br.gov.go.sectec.portalemprego.core.entidade.VagaOfertada;
import br.gov.go.sectec.portalemprego.negocio.dao.PersistenciaGenericaJpa;

@Repository("vagaOfertadaDao")
public class VagaOfertadaDaoImpl extends PersistenciaGenericaJpa<VagaOfertada, Integer> implements VagaOfertadaDao {


}
