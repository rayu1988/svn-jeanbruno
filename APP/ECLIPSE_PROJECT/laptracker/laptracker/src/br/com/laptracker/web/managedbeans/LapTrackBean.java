package br.com.laptracker.web.managedbeans;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import br.com.laptracker.business.entities.LapTrackTO;
import br.com.laptracker.business.exceptions.AppException;
import br.com.laptracker.business.service.LapTrack;
import br.com.laptracker.business.service.TrackMessage;
import br.com.laptracker.commons.RequestMessage;
import br.com.laptracker.commons.enums.SeverityMessage;
import br.com.laptracker.web.managedbeans.datamodel.CustomDataModel;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class LapTrackBean extends AppManagedBean {

	private UploadedFile				uploadedFile;
	private DataModel<Object>			dataModel;
	private LapTrackTO					lapTrack;
	
	public String prepareUpload() {
		try {
			return "lapTrackUpload";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String insert() {
		try {
			if (this.uploadedFile == null) {
				throw new AppException("fileIsMandatory");
			}
			
			InputStreamReader isr = new InputStreamReader(this.uploadedFile.getInputStream());
			BufferedReader br=new BufferedReader(isr);
			
			String line;
			StringBuffer xmlContent = new StringBuffer();
			while ((line = br.readLine()) != null) {
				xmlContent.append(line);
			}
			
			TrackMessage service = this.getServiceBusinessFactory().getTrackMessage();
			service.insert(this.getUserAccountLogged(), xmlContent.toString());
			
			this.setRequestMessage(new RequestMessage("fileUploadedSuccessfully", SeverityMessage.SUCCESS));
			
			return this.list();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String list() {
		try {
			LapTrack service = this.getServiceBusinessFactory().getLapTrack();
			List<LapTrackTO> list = service.listLapTrack(this.getUserAccountLogged());
			this.dataModel = new CustomDataModel(list);
			
			return "lapTrackList";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String remove() {
		try {
			LapTrack service = this.getServiceBusinessFactory().getLapTrack();
			service.removeLapTrack(this.lapTrack);
			
			this.setRequestMessage(new RequestMessage("lapTrackDeleteSuccessfully", SeverityMessage.SUCCESS));
			
			return this.list();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String consult() {
		try {
			LapTrack service = this.getServiceBusinessFactory().getLapTrack();
			this.lapTrack = service.consult(this.lapTrack);
			
			return "lapTrackConsult";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	// GETTERS AND SETTERS //
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}
	
	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public DataModel<Object> getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel<Object> dataModel) {
		this.dataModel = dataModel;
	}

	public LapTrackTO getLapTrack() {
		return lapTrack;
	}

	public void setLapTrack(LapTrackTO lapTrack) {
		this.lapTrack = lapTrack;
	}
}
