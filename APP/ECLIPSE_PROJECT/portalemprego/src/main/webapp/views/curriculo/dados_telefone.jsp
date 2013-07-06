<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="grid" uri="/struts-jquery-grid-tags"%>

<script>
	$(document).ready(function() {

		$.subscribe("removerDadosTelefone", function(event,data) {
			$("#idTipoTelefoneEmpresa").val("0");
			$("#idDDI").val("");
			$("#idDDD").val("");
			$("#idTelefoneCadastro").val("");
		});

		
		$("#idDDI").keypress(verificaNumero);
		$("#idDDD").keypress(verificaNumero);
		$("#idTelefoneCadastro").keypress(verificaNumero);
	});


</script>


<script type="text/javascript">
	function deleteLinkFormatter(cellval, options, rowselect, selarrrow, icon,
			link_class, link_action, event, rowid) {

		var teste = cellval;
		return "<a onClick='javascript: return delete_eleve("+ teste+")'><div style='cursor:pointer' ><img src='/portalemprego/img/delete.png' alt='excluir' /></div></a>";

	}

	function delete_eleve(teste) {
		$('input[name=idTelefoneEx]').val(teste);
		$('#excluirTelefone').dialog('open');
	}
	
	function excluirTelefone() {
		$.ajax({
		    type : "post",
		    url : "excluirTelefoneCurriculo",
		    data:$("#formCurriculoDadosId").serialize(),
		    dataType: "json",       
		    success : function(data) {
				        $("#datagridTelefone").trigger("reloadGrid");
		    }
		});

		fecharModal();
	}

	function fecharModal() {

		 $('#excluirTelefone').dialog('close');
		
	}
</script>


<div class="end">
	<spam style="color: #ff0101;">*</spam>
	Telefone:
</div>
<ul class="end">

    <s:hidden name="idTelefoneEx" />

	<li><label> <spam>*</spam> Tipo:
	</label> <s:url var="remoteurlTipotelefone" action="obterTipoTelefoneCurriculo" namespace="curriculo" /> 
	
	        <sj:select cssStyle="width:220px;"
			href="%{remoteurlTipotelefone}" id="idTipoTelefoneEmpresa"
			name="idTipoTelefone" list="tipoTelefoneList" listKey="idDTO"
			listValue="nomeDTO" emptyOption="false" headerKey="0"
			headerValue="Selecione..." /></li>

	<li><label> <spam>*</spam> DDI:
	
	</label><sj:textfield dataType="json"  name="telefone.nuDdi"   formIds="formCurriculoDadosId" id="idDDI" maxLength="2" size="30"
			theme="simple" reloadTopics="kkk"  /></li>


	<li><label> <spam>*</spam> DDD:
	</label> <s:textfield name="telefone.nuDdd"  id="idDDD" maxLength="3" size="30"
			theme="simple" /></li>

	<li><label> <spam>*</spam> Numero:
	</label> <s:textfield name="telefone.nuTelefone" id="idTelefoneCadastro"
			maxLength="8" size="30" theme="simple" /></li>

	<li><s:url id="adddTelefone"   action="adicionarTelefoneCurriculo" namespace="curriculo"   /> 
			
			<s:submit action="adicionarTelefoneCurriculo"
			value=""
			cssStyle="background: url(/portalemprego/img/incluir.jpg); width: 78px; height: 27px;" />
			<s:url var="obterTelefonesURL" action="obterTelefonesCurriculo" namespace="curriculo" />
	</li>

    <li>
    
	        <grid:grid gridModel="telefoneList"
			id="datagridTelefone" reloadTopics="grdiTelefone"  formIds="formCurriculoDadosId"
			href="%{obterTelefonesURL}" loadingText="Carregando..." dataType="json"
			width="675"  >

			<grid:gridColumn name="nuDdi" index="nuDdi" width="60"  title="DDI" />
			<grid:gridColumn name="nuDdd" index="nuDdd" title="DDD" width="60"  />
			<grid:gridColumn name="numeroDTO" index="numeroDTO" title="Telefone" />
			<grid:gridColumn name="dsTipo" index="dsTipo" title="Tipo" />
			<grid:gridColumn name="index" index="index" title="Exluir"
				sortable="true" align="center" width="80" 
				formatter="deleteLinkFormatter" />

		</grid:grid>
	</li>
</ul>
 <sj:dialog
        id="excluirTelefone"
        buttons="{
                'Sim':function() { excluirTelefone(); },
                'Não':function() { fecharModal(); }
                }"
        autoOpen="false"
        modal="true"
        title="Confirmação"
    >
     
     Deseja realmente excluir o telefone?
     
    </sj:dialog>
