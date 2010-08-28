<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="org.json.simple.JSONObject"%>
<%
	JSONObject obj=new JSONObject();
	try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
		br.com.oaks.ICPBravo.manager.ICPBravoManager manager = new br.com.oaks.ICPBravo.manager.PKCS12Manager(resourceBundle.getString("home")+"/certs/RaizTreinamento.p12", "1234".toCharArray());
		manager.reload();
		br.com.oaks.ICPBravo.certs.ICPBravoCertificate certRoot = manager.getCertificates().get(0);
		String csrB64=java.net.URLDecoder.decode(request.getParameter("csr"),"UTF-8");
		long numSerie=Long.parseLong(java.net.URLDecoder.decode(request.getParameter("numSerie"),"UTF-8"));
		String alias=java.net.URLDecoder.decode(request.getParameter("alias"),"UTF-8");
		String email=java.net.URLDecoder.decode(request.getParameter("email"),"UTF-8");
		String nascimentoStr=java.net.URLDecoder.decode(request.getParameter("nascimento"),"UTF-8");
		int year = Integer.parseInt(nascimentoStr.substring(0,4));
		int month = Integer.parseInt(nascimentoStr.substring(4,6))-1;
		int day = Integer.parseInt(nascimentoStr.substring(6,8));
		java.util.GregorianCalendar nascimento = new java.util.GregorianCalendar(year, month, day);
		String cpf=java.net.URLDecoder.decode(request.getParameter("cpf"),"UTF-8");
		String nis=java.net.URLDecoder.decode(request.getParameter("nis"),"UTF-8");
		String rg=java.net.URLDecoder.decode(request.getParameter("rg"),"UTF-8");
		String oe=java.net.URLDecoder.decode(request.getParameter("oe"),"UTF-8");
		String tituloEleitor=java.net.URLDecoder.decode(request.getParameter("tituloEleitor"),"UTF-8");
		String zona=java.net.URLDecoder.decode(request.getParameter("zona"),"UTF-8");
		String sessao=java.net.URLDecoder.decode(request.getParameter("sessao"),"UTF-8");
		String municipioUf=java.net.URLDecoder.decode(request.getParameter("municipioUf"),"UTF-8");
		String inss=java.net.URLDecoder.decode(request.getParameter("inss"),"UTF-8");

System.out.println("CSR:"+csrB64 + ", NumSerie:"+numSerie + ", Alias:"+alias + ", Email:"+email + ", NascimentoStr:"+nascimentoStr + ", CPF:"+cpf + ", NIS:"+nis + ", RG:"+rg + ", OE:"+oe + ", TituloEleitor:"+tituloEleitor + ", Zona:"+zona + ", Sessao:"+sessao + ", municipioUf:"+municipioUf + ", INSS:"+inss);
		
		br.com.oaks.ICPBravo.certs.ICPBravoCertificationRequest csr = new br.com.oaks.ICPBravo.certs.ICPBravoCertificationRequest(new br.com.oaks.ICPBravo.cms.content.Base64Content(csrB64));

		java.util.Date from = br.com.oaks.ICPBravo.util.ICPBravoUtil.getDateNow();
		java.util.Date to = br.com.oaks.ICPBravo.util.ICPBravoUtil.getTo(br.com.oaks.ICPBravo.util.ICPBravoUtil.MONTHS, 1);

		br.com.oaks.ICPBravo.certs.ICPBravoCertificateGenerator gen = new br.com.oaks.ICPBravo.certs.ICPBravoCertificateGenerator(
				manager.getProvider(), alias, java.math.BigInteger.valueOf(numSerie), csr, certRoot,  
				br.com.oaks.ICPBravo.certs.ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(),
				br.com.oaks.ICPBravo.certs.ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
				null,
				br.com.oaks.ICPBravo.asn1.oiw.OIWObjectIdentifiers.OID_ICPBrasilA1,
				null, 
				from, to,  
				email, nascimento.getTime(), cpf, nis, rg, oe,
				tituloEleitor, zona, sessao, municipioUf, inss);
		br.com.oaks.ICPBravo.certs.ICPBravoCertificate cert = gen.generate(manager, false);
		br.com.oaks.ICPBravo.util.ICPBravoUtil.writeFile(resourceBundle.getString("home")+"/certs/"+alias+".cer", cert.getEncoded());
		obj.put("certificate", cert.getP7BPEM());
	} catch (Exception e) {
		e.printStackTrace();
		obj.put("err",e.getMessage());
	}
	out.print(obj);
	out.flush();
%>
