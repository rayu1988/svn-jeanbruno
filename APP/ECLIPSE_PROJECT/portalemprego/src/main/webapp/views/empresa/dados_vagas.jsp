<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="grid" uri="/struts-jquery-grid-tags"%>

	<script>
	$(document).ready(function() {
        $("#idQtdVagas").keypress(verificaNumero);
    });
	</script>
	
		<script type="text/javascript">

  	function deleteLinkFormatterVagas(cellval, options, rowselect, selarrrow, icon,
  			link_class, link_action, event, rowid) {

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
	  <label>Cidade:</label> 
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
					headerKey="0" 
					headerValue="Selecione..."
				/>
	</li>
	
	<li>
	  <label>Área:</label>
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
					headerValue="Selecione..."
				/>
	</li>
	
	<li>
	  <label>Quantidade de Vagas:</label>
	  <s:textfield  name="vaga.qtdVagas" id="idQtdVagas" maxLength="9" size="30"  theme="simple" />
	</li>
	
	<li>
	  <label>Descrição:</label>
	   <s:textfield  name="vaga.dsVagaOfertada" id="idDsVagas" maxLength="100" size="30"  theme="simple" />
	</li>
	<li>
	  <label>Data da Expiração:</label>
	  <sj:datepicker id="data"  name="vaga.dataExpiracaoStr" size="30" readonly="true" changeMonth="true" changeYear="true" />
	</li>
	
	<li>
	  <s:submit   action="adicionarVagasOfertasEmpresa" value=""  cssStyle="background: url(/portalemprego/img/incluir.jpg); width: 78px; height: 27px;" />
	</li>
	
	<li>
	
	  <s:url var="obterVagasOfertadasURL" action="listarVagasOfertadas" namespace="empresa" /> 
	  <grid:grid gridModel="vagaList"
	             id="datagridVagas"
	             formIds="formEmpresaId"
    			 href="%{obterVagasOfertadasURL}"
    			loadingText="Carregando..."
    			pager="true"
    	        rowNum="10"
    	        width="670"
	  >
	  
	    <grid:gridColumn name="dsCidade" index="dsCidade" title="Cidade"/>
    	<grid:gridColumn name="dsArea" index="dsArea" title="Área" />
    	<grid:gridColumn name="qtdVagas" index="qtdVagas" title="QTD. Vagas"  />
    	<grid:gridColumn name="dataExpiracaoStr" index="dataExpiracaoStr"  title="Data da Expiração"   />
    	<grid:gridColumn name="index" index="index" title="Excluir" sortable="true" align="center" width="100" formatter="deleteLinkFormatterVagas" />
	  
	  </grid:grid>
	</li>
	
	
	  <s:hidden name="idVagasEx" />
      <sj:dialog id="excluirVagaEmpresa"
        buttons="{
                'Sim':function() { excluirVaga(); },
                'Não':function() { fecharModal(); }
                }"
        autoOpen="false"
        modal="true"
        title="Confirmação"
    >
     
 	 Deseja realmente excluir a vaga ?
     
    </sj:dialog>

</ul>