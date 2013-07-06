<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script>
	$(document).ready(function() {
		$('#cnpj').mask('99.999.999/9999-99');
		$('#cpf').mask('999.999.999-99');
	});
</script>

	<ul id="dados-pessoais">
		<li>
		  <label> <spam>*</spam> CNPJ:</label> 
		  <s:textfield name="empresa.numCNPJ"    maxLength="18" id="cnpj" theme="simple" size="30" />
		</li>
		
		<li>
		 <label><spam>*</spam>Razão Social:</label> 
		 <s:textfield  name="empresa.dsRazsoSocial" id="Razão-Social" size="30" maxLength="60"  theme="simple"/>
		</li>
		
		<li>
		 <label>Responsável:</label> 
		 <s:textfield  name="empresa.dsResponsavel" id="Responsável" size="30" maxLength="100"  theme="simple"/>
		</li>
		
		<li>
		 <label>CPF:</label> 
		 <s:textfield  name="empresa.numCPFResponsavel"  maxLength="14"  id="cpf" size="30"  theme="simple" />
		</li>
		
		<li>
		 <label><spam>*</spam>E-mail:</label> 
		 <s:textfield  name="empresa.emailResponsavel" id="e-mail" maxLength="255" size="30"  theme="simple"/>
		</li>
	</ul>
