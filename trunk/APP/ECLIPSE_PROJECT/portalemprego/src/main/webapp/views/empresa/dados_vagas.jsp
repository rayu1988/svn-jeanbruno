<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="grid" uri="/struts-jquery-grid-tags"%>

<script type="text/javascript">
  	function deleteLinkFormatterVagas(cellval, options, rowselect, selarrrow, icon, link_class, link_action, event, rowid) {
  		var idDeletar = cellval;
  		return "<a onClick='javascript: return deleteVagaModal("+ idDeletar+")'><div style='cursor:pointer' ><img src='/portalemprego/img/delete.png' alt='excluir' /></div></a>";
  	}

  	function deleteVagaModal(idDeletar) {
  		$('input[name=idVagasEx]').val(idDeletar);
  		$('#excluirVagaEmpresa').dialog('open');
  	}
  	
  	function excluirVaga() {
  		$.ajax({
  		    type : "post",
  		    url : "excluirVagasEmpresa",
  		    data:$("#formEmpresaId").serialize(),
  		    dataType: "json",       
  		    success : function(data) {
  				        $("#datagridVagas").trigger("reloadGrid");
  		    }
  		});
  		fecharModal();
  	}

  	function fecharModal() {
  		 $('#excluirVagaEmpresa').dialog('close');
  	}
</script>

<div class="end">Disponibilizar Vagas</div>

<ul class="end">
	<li>
	  <label> <spam>*</spam> Cidade:</label> 
	    <s:url var="remoteurlCidadeGoias" action="listarCidadesGoias" namespace="empresa" /> 
		<sj:select 
			cssStyle="width:210px;"
			href="%{remoteurlCidadeGoias}" 
			id="idCidadeVagaSelecao"
			name="vaga.idCidade"
			list="cidadeVagaList" 
			listKey="idDTO" 
			listValue="nomeDTO" 
			emptyOption="false" 
			headerKey="0" headerValue="Selecione..." />
	</li>
	<li>
	  <label> <spam>*</spam> Área:</label>
		<s:url var="remoteurlAreaInteresse" action="listarAreaInteresse" namespace="empresa" /> 
		<sj:select 
			cssStyle="width:210px;"
			href="%{remoteurlAreaInteresse}" 
			id="idAreaInteresse"
			name="vaga.idArea" 
			list="areaList" 
			listKey="idDTO" 
			listValue="nomeDTO" 
			emptyOption="false" 
			headerKey="0" 
			headerValue="Selecione..." />
	</li>
	<li>
	  <label> <spam>*</spam> Quantidade de Vagas:</label>
	  <s:textfield  name="vaga.qtdVagas" id="idQtdVagas" maxLength="9" size="30"  theme="simple" />
	</li>
	<li>
		<label> <spam>*</spam> Data de Expiração:</label>
		<sj:datepicker id="idDataExpiracao" showButtonPanel="false" name="vaga.dataExpiracaoStr" size="30" readonly="false" changeMonth="true" changeYear="true" displayFormat="dd/mm/yy" />
	</li>
	<li>
		<label> <spam>*</spam> Descrição:</label>
		<s:textfield  name="vaga.dsVagaOfertada" id="idDsVagas" maxLength="100" size="60"  theme="simple" />
	</li>
	<li>
		<div style="margin-left: 100px;">
			<!-- incluir -->
			<input type="button" value="Adicionar Vaga" name="buttonAddJobVacancy" id="idButtonAddJobVacancy" class="customizedButton"/>
			
			<!-- salvar e cancelar -->
			<input type="button" value="Salvar" name="buttonSaveJobVacancy" id="idButtonSaveJobVacancy" class="customizedButton" style="display:none;"/>
			<input type="button" value="Cancelar" name="buttonCancelJobVacancy" id="idButtonCancelJobVacancy" class="customizedButton" style="display:none;"/>
		</div>
	</li>
	<li id="idDadosVagasTable">
		<jsp:include page="dados_vagas_table.jsp" />
	</li>
</ul>