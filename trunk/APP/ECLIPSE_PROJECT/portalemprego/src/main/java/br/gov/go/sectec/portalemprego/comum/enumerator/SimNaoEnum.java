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
public enum SimNaoEnum {

	SIM(true,"Sim"),
	NAO(false,"Não");
	
	
	private SimNaoEnum(boolean value, String descricao) {

		this.value = value;
		
		this.descricao = descricao;
	}
	
	private boolean value;
	
	private String descricao;
	

	
	public boolean isValue() {
	
		return value;
	}



	
	public void setValue(boolean value) {
	
		this.value = value;
	}



	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param tpSexo
	 * @return
	 */
	public static SimNaoEnum get(boolean value) {

		if(value){
			
			return SimNaoEnum.SIM;
			
		}
		return SimNaoEnum.NAO;
	}




	
	public String getDescricao() {
	
		return descricao;
	}




	
	public void setDescricao(String descricao) {
	
		this.descricao = descricao;
	}
	
}
