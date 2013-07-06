package br.gov.go.sectec.portalemprego.negocio.dao;

import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.ControleEmpregoEfetivoDao;
import br.gov.go.sectec.portalemprego.core.entidade.ControleEmpregoEfetivo;
import br.gov.go.sectec.portalemprego.negocio.dao.PersistenciaGenericaJpa;

@Repository("controleEmpregoEfetivoDao")
public class ControleEmpregoEfetivoDaoImpl extends PersistenciaGenericaJpa<ControleEmpregoEfetivo, Integer> implements ControleEmpregoEfetivoDao {
 

}
