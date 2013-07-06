<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

 	<ul class="atividade">
	   <li>
	     <label><spam style="color: #ff0101;">*</spam>Ramo / Atividade:</label>
	          <s:url var="remoteurlramoatividade" action="listarRamoAtividade" namespace="empresa" />
	     	   <sj:select 
				    cssStyle="width:210px;"
					href="%{remoteurlramoatividade}" 
					id="idRamoAtividade"
					name="idRamoAtividade" 
					list="ramoList" 
					listKey="idDTO" 
					listValue="nomeDTO" 
					emptyOption="false" 
					headerKey="0" 
					headerValue="Selecione..."
				/>
	   </li>
	   
	   <li>
	     <label><spam style="color: #ff0101;">*</spam>Usúario:</label>
	       <s:textfield  name="empresa.dsLogin" id="idLogin" size="30" maxLength="50"  theme="simple"/>
	   </li>
	   
	   <li>
	     <label><spam style="color: #ff0101;">*</spam>Senha:</label>
	     <s:password  name="empresa.dsSenha"  accesskey="true" id="idSenha" size="30" maxLength="20"  theme="simple"  />
	   </li>
	   
	   <li>
	     <label><spam style="color: #ff0101;">*</spam>Confirma Senha:</label>
	     <s:password  name="empresa.dsSenhaConfirmacao"  id="idSenhaConfirmacao" size="30" maxLength="20"  theme="simple"  />
	   </li>
	   
   </ul> 