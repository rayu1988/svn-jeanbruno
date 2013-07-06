package br.gov.go.sectec.portalemprego.negocio.dao;

import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.ControleEncaminhamentoDao;
import br.gov.go.sectec.portalemprego.core.entidade.ControleEncaminhamento;
import br.gov.go.sectec.portalemprego.negocio.dao.PersistenciaGenericaJpa;

@Repository("controleEncaminhamentoDao")
public class ControleEncaminhamentoDaoImpl extends PersistenciaGenericaJpa<ControleEncaminhamento, Integer> implements ControleEncaminhamentoDao {


}
