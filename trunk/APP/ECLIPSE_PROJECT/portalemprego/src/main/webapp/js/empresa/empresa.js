var indexElement_PhoneNumber = "";

function getParameters_PhoneNumber(phoneIndex) {
	if (phoneIndex != null) indexElement_PhoneNumber = phoneIndex;
	
	var valDDI = $('#idDDI').val();
	var valDDD = $('#idDDD').val();
	var valTelefoneCadastro = $('#idTelefoneCadastro').val();
	var valIdTipoTelefone = $('#idTipoTelefoneEmpresa').val();
	var param = { 'telefone.nuDdi':valDDI , 'telefone.nuDdd':valDDD, 'telefone.nuTelefone':valTelefoneCadastro, 'idTipoTelefone':valIdTipoTelefone, 'indexElementList':indexElement_PhoneNumber };
	return param;
}

function clearForm_PhoneNumber() {
	$('#idDDI').val("");
	$('#idDDD').val("");
	$('#idTelefoneCadastro').val("");
	$('#idTipoTelefoneEmpresa').val("0");
	
	indexElement_PhoneNumber = "";
	
	$('#idButtonSavePhoneNumber').hide();
	$('#idButtonCancelPhoneNumber').hide();
	$('#idButtonAddPhoneNumber').show();
}

function refreshPartialContent_PhoneNumber() {
	$.ajax({
		url: 'empresa/loadTablePhoneNumber.action',
		type: 'POST',
		data: getParameters_PhoneNumber(),
		dataType: 'html',
		success: function (htmlContent) {
			clearForm_PhoneNumber();
			
			$('#idDadosTelefoneTable').empty();
			$('#idDadosTelefoneTable').html(htmlContent);
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert('An error occurred! ' + thrownError);
		}
	});
}

function buildHtmlContentButtons_PhoneNumber(cellval, options, rowselect, selarrrow, icon, link_class, link_action, event, rowid) {
	var htmlContent = "<div style='white-space: nowrap;'>";
	htmlContent += "<a href='#' alt='Alterar' class='customizedLinks' style='margin-right: 15px;' onclick='edit_PhoneNumber(" + cellval + "); return false;'>Alterar</a>";
	htmlContent += "<a href='#' alt='Excluir' class='customizedLinks' onclick='delete_PhoneNumber(" + cellval + "); return false;'>Excluir</a>";
	htmlContent += "</div>";
	return htmlContent;
}

function delete_PhoneNumber(phoneIndex) {
	$.ajax({
		url: 'empresa/excluirTelefoneEmpresa.action',
		type: 'POST',
		data: getParameters_PhoneNumber(phoneIndex),
		dataType: 'json',
		success: function (data) {
			if (data.processingReturn.success) {
				refreshPartialContent_PhoneNumber();
			} else {
				alert(data.processingReturn.errorMsg);
			}
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert('An error occurred! ' + thrownError);
		}
	});
}

function edit_PhoneNumber(phoneIndex) {
	$.ajax({
		url: 'empresa/editarTelefoneEmpresa.action',
		type: 'POST',
		data: getParameters_PhoneNumber(phoneIndex),
		dataType: 'json',
		success: function(data) {
			$('#idButtonSavePhoneNumber').show();
			$('#idButtonCancelPhoneNumber').show();
			$('#idButtonAddPhoneNumber').hide();
			indexElement_PhoneNumber = data.indexElementList;
			
			$('#idDDI').val(data.telefone.nuDdi);
	    	$('#idDDD').val(data.telefone.nuDdd);
	    	$('#idTelefoneCadastro').val(data.telefone.nuTelefone);
	    	$('#idTipoTelefoneEmpresa').val(data.idTipoTelefone);
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert('An error occurred! ' + thrownError);
		}
	});
}

$(document).ready(function(){
	$("#idDDI").keypress(verificaNumero);
	$("#idDDD").keypress(verificaNumero);
	$("#idTelefoneCadastro").keypress(verificaNumero);
	
	$('#idButtonAddPhoneNumber').click(function(){
		$.ajax({
			url: 'empresa/adicionarTelefoneEmpresa.action',
			type: 'POST',
			data: getParameters_PhoneNumber(),
			dataType: 'json',
			success: function (data) {
				if (data.processingReturn.success) {
					refreshPartialContent_PhoneNumber();
				} else {
					alert(data.processingReturn.errorMsg);
				}
			},
			error: function(xhr, ajaxOptions, thrownError){
				alert('An error occurred! ' + thrownError);
			}
		});
    });
	$('#idButtonCancelPhoneNumber').click( clearForm_PhoneNumber );
	$('#idButtonSavePhoneNumber').click(function(){
    	$.ajax({
			url: 'empresa/salvarTelefoneEmpresa.action',
			type: 'POST',
			data: getParameters_PhoneNumber(),
			dataType: 'json',
			success: function (data) {
				if (data.processingReturn.success) {
					refreshPartialContent_PhoneNumber();
				} else {
					alert(data.processingReturn.errorMsg);
				}
			},
			error: function(xhr, ajaxOptions, thrownError){
				alert('An error occurred! ' + thrownError);
			}
		});
	});
});

function deleteTelefoneModal(idDeletar) {
	$('input[name=idTelefoneEx]').val(idDeletar);
	$('#excluirTelefoneCurriculo').dialog('open');
}

function excluirExperiencia() {
	$.ajax({
	    type : "post",
	    url : "excluirTelefoneEmpresa",
	    data:$("#formEmpresaId").serialize(),
	    dataType: "json",       
	    success : function(data) {
			        $("#datagridTelefoneEmpresa").trigger("reloadGrid");
	    }
	});
	fecharModalTelefone();
}

function fecharModalTelefone() {
	 $('#excluirTelefoneCurriculo').dialog('close');
}