<html>
<header>
	<title>ICPBravo - Revogação de certificado - Solicitação</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1>Revogação de certificado - Solicitação</h1>
<h3>Client-Side</h3>
<p>O exemplo corrente visa demonstrar a inclusão de um certificado na lista de revogados.</p>
<p>Este passo é apenas o formulário para solicitação.</p>
<i>
<p>obs.: Uma característica de nosso exemplo que pode comprometer o teste é a seguinte:<br />Quando geramos a nossa primeira lista de revogados (CRL), informamos que o prazo de validade dela era de 1 mês. Quando fizemos a verificação do certificado, esta lista foi carregada para o cache da biblioteca, onde só será recarregada ao fim do prazo de validade do CRL, portanto, ao gerarmos esta nova lista para este exemplo, devemos apagar a lista do cache da biblioteca (no diretório C:\Users\<Seu usuário>\.ICPBravo\CRL\*).</p>
<p>Em uma AC em produção este problema não acontece, pois não é previsto a geração de CRLs antes do prazo de validade da mesma.</p>
</i>
<hr />
<form name='crl' action='RevogateCertificateServer.jsp'>
Número de Série: <input type='text' name='numSerie' value='0'><br /> 
Razão: <select name='reason'>
	<option value='0'>Unspecified</option>
	<option value='1'>Key Compromise</option>
	<option value='2'>CA Compromise</option>
	<option value='3'>Affiliation Changed</option>
	<option value='4'>Superseded</option>
	<option value='5'>Cessation of Operation</option>
	<option value='6'>Certificate Hold</option>
	<option value='8'>Remove from CRL</option>
	<option value='9'>Privilege with drawn</option>
	<option value='10'>AACompromise</option>
</select><br />
<input type='Submit' value='Revogar'>
</form>

</body></html>