package br.gov.go.sectec.portalemprego.comum.enumerator;
/**
 * <p>
 * <b>Title:</b> sexoEnum.java
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
public enum SexoEnum {

	FEMININO("Feminino",1),
	MASCULINO("Masculino",2);
	
	
	private SexoEnum(String descricao, Integer tipo) {

		this.descricao = descricao;

		this.tipo = tipo;
	}
	
	private String descricao;
	
	private Integer tipo;

	
	public String getDescricao() {
	
		return descricao;
	}

	
	public void setDescricao(String descricao) {
	
		this.descricao = descricao;
	}

	
	public Integer getTipo() {
	
		return tipo;
	}

	
	public void setTipo(Integer tipo) {
	
		this.tipo = tipo;
	}


	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param tpSexo
	 * @return
	 */
	public static SexoEnum get(Integer tpSexo) {

		if(tpSexo.equals(1)){
			
			return SexoEnum.FEMININO;
		}

		return SexoEnum.MASCULINO;
	}
}
