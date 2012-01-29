package br.com.softplan.web.ocr.struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class UploadForm extends ActionForm {
	
	private static final long 			serialVersionUID = 9178730649291494964L;
	
	private FormFile					fileUpload;
	private boolean						debugText;
	
	// getters and setters //
	public FormFile getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}

	public boolean isDebugText() {
		return debugText;
	}

	public void setDebugText(boolean debugText) {
		this.debugText = debugText;
	}
	
}
