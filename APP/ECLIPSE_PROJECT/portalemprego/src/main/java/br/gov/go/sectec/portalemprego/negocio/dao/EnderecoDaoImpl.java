package br.gov.go.sectec.portalemprego.negocio.dao;

import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.EnderecoDao;
import br.gov.go.sectec.portalemprego.core.entidade.Endereco;
import br.gov.go.sectec.portalemprego.negocio.dao.PersistenciaGenericaJpa;

@Repository("enderecoDao")
public class EnderecoDaoImpl extends PersistenciaGenericaJpa<Endereco, Integer> implements EnderecoDao {


}
