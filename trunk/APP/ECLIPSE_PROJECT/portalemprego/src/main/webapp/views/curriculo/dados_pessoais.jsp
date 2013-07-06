<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
	
<script>
	$(document).ready(function() {
		$('#cpfAluno').mask('999.999.999-99');
	});
</script>
 <ul id="dados-pessoais">
	
	<li>
	  <label> <spam>*</spam>Nome:</label> 
	   <s:textfield name="aluno.dsNome" maxLength="60" id="nomeAluno" theme="simple" size="30" />
    </li>
	
	<li>
	  <label><spam>*</spam>CPF:</label> 
	    <s:textfield name="aluno.nuCpf"  maxLength="18" id="cpfAluno" theme="simple" size="30" />
	</li>
	
	<li >
	  <label>Número RG:</label> 
	  <s:textfield name="aluno.nuRg" maxLength="18" id="rgAluno" theme="simple" size="30" />
    </li>
    
	<li >
	  <label>Orgão Emissor:</label> 
	  <s:textfield name="aluno.dsRgEmissor" maxLength="10" id="rgAluno" theme="simple" size="30" />
	</li>
	
	<li >
	  <label>Data da emissão:</label> 
	   <sj:datepicker id="data"  name="aluno.dtEmisaoStr" displayFormat="dd/mm/yy" size="30" readonly="true" changeMonth="true" changeYear="true"  />
	</li>
	
	<li >
	  <label> <spam>*</spam> Dt. Nascimento: </label> 
	  <sj:datepicker id="dataNascimento"  name="aluno.dtNascimentoStr" displayFormat="dd/mm/yy" size="30" readonly="true" changeMonth="true" changeYear="true"  />
	</li>
	
	<li >
	  <label>Estado Civil:</label> 
	  <s:select  cssStyle="width:210px;" name="idEstadoCivil" list="estadoCivilList" listKey="idDTO" listValue="nomeDTO" headerKey="0"  headerValue="Selecione..." theme="simple" />
    </li>
	
	<li>
	  <label> <spam>*</spam> Sexo:</label> 
	  <s:select cssStyle="width:210px;" name="tipoSexo" list="sexoList" listKey="tipo" listValue="descricao" headerKey="0" headerValue="Selecione..." theme="simple" />
	</li>
	
	<li>
	  <label> <spam>*</spam> Escolaridade:</label> 
	  <s:select cssStyle="width:210px;" name="idEscolaridade" list="escolaridadeList" listKey="idDTO" listValue="nomeDTO"  headerKey="0" headerValue="Selecione..." theme="simple" />
	</li>

</ul>