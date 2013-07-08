<%@ taglib uri="/struts-tags" prefix="s" %>

<script>
	$(document).ready(function() {
		$('#idUsuarioLogin').focus();
	});
</script>

<div class="quadro">
	<h2 class="titulo-campo">Login Instituição / Empresa</h2>
	<h2 class="titulo-campo">Login</h2>
	<div class="quadro-branco">
		<s:form id="us" namespace="/empresa" action="login" >
			<div>Usuário:</div>
			<label> <s:textfield name="empresa.dsLogin" id="idUsuarioLogin" maxLength="50" theme="simple"/> </label>
			 
			<div>Senha:</div>
			<label> <s:password name="empresa.dsSenha" id="idSenhaLogin" maxLength="20" theme="simple"/> </label>
			
			<div class="senha">
				<a href="#">Esqueci minha senha</a>
			</div>
			
			<s:submit  value="OK"  align="left" />
		</s:form>
	</div>
</div>