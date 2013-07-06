package br.gov.go.sectec.portalemprego.negocio.dao;

import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.PessoaJuridicaDao;
import br.gov.go.sectec.portalemprego.core.entidade.PessoaJuridica;
import br.gov.go.sectec.portalemprego.negocio.dao.PersistenciaGenericaJpa;

@Repository("pessoaJuridicaDao")
public class PessoaJuridicaDaoImpl extends PersistenciaGenericaJpa<PessoaJuridica, Integer> implements PessoaJuridicaDao {


}
