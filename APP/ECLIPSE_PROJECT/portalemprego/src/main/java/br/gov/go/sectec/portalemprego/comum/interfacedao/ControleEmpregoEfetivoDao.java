package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.ControleEmpregoEfetivo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;

public interface ControleEmpregoEfetivoDao extends PersistenciaGenerica<ControleEmpregoEfetivo, Integer> {
	public ControleEmpregoEfetivo consultar(Integer id);
	public List<ControleEmpregoEfetivo> listar();
	public void incluir(ControleEmpregoEfetivo tipoGrau);
	public ControleEmpregoEfetivo alterar(ControleEmpregoEfetivo tipoGrau);
	public void excluir(ControleEmpregoEfetivo tipoGrau);
}
