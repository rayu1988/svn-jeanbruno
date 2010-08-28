<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="org.json.simple.JSONObject"%>
<%
	JSONObject obj=new JSONObject();
	try {
		// Neste exemplo, os objetos necessários para a assinatura serão armazenados em sessão, para que possamos quebrar em duas etapas. 
		byte [] conteudoAssinado = "test".getBytes();
		
		br.com.oaks.ICPBravo.certs.ICPBravoCertificate signer = (br.com.oaks.ICPBravo.certs.ICPBravoCertificate) session.getAttribute("signer");  
		if (signer == null) { // Fase 1 - Onde serão criados os atributos a assinar.
System.out.println("Fase 1");
			String certsP7B=request.getParameter("cert");
			certsP7B = br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(certsP7B);
			signer = br.com.oaks.ICPBravo.certs.ICPBravoCertificate.CreateFromP7BPEM(certsP7B, br.com.oaks.ICPBravo.manager.ICPBravoManager.getDefaultManager(), "signatario");
			br.com.oaks.ICPBravo.cms.CMSSignedData signCMS = new br.com.oaks.ICPBravo.cms.CMSSignedData();

			signCMS.setData(conteudoAssinado);
			br.com.oaks.ICPBravo.cms.policies.Policy policy = new br.com.oaks.ICPBravo.cms.policies.AdCpPolicy();
			br.com.oaks.ICPBravo.cms.CMSSignerInformation si = signCMS.addSigner(signer, policy);
			byte [] toSign = si.generateSignedAttributes().getDEREncoded();
			String toSignB64 = new sun.misc.BASE64Encoder().encode(toSign);
System.out.println("toSign: "+toSignB64);

			session.setAttribute("signer", signer);
			session.setAttribute("toSign", toSign);
			obj.put("toSign",toSignB64);
		} else { // Fase 2 - Concluir a assinatura
System.out.println("Fase 2");
			String signB64=request.getParameter("signature");
System.out.println("sign: "+signB64);
			signB64 = br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(signB64);
			byte [] sign = new sun.misc.BASE64Decoder().decodeBuffer(signB64);
			
			byte [] toSign = (byte []) session.getAttribute("toSign");

			br.com.oaks.ICPBravo.cms.CMSSignedData signCMS = new br.com.oaks.ICPBravo.cms.CMSSignedData();
			signCMS.setEncapsulate(true);
			signCMS.setData(conteudoAssinado);
			br.com.oaks.ICPBravo.cms.CMSSignerInformation si = signCMS.addSigner(signer);
			si.setSign(toSign, sign);
			
			byte[] cmsEncoded = signCMS.getASN1Encoded();
			String cmsEncodedB64 = new sun.misc.BASE64Encoder().encode(cmsEncoded); 
			
System.out.println("signCMS: "+cmsEncodedB64);
			obj.put("signCMS", cmsEncodedB64);
			
			// Limpa a sessão para uma nova assinatura
			session.removeAttribute("signer");
			session.removeAttribute("toSign");
		}
	} catch (Exception e) {
		e.printStackTrace();
		obj.put("err",e.getMessage());
	}
	out.print(obj);
	out.flush();
%>
