package br.com.barganhas.business.services.impl;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.FileTempTO;
import br.com.barganhas.business.services.AdvertisementPicture;
import br.com.barganhas.business.services.File;
import br.com.barganhas.business.services.persistencies.AdvertisementPicturePO;
import br.com.barganhas.commons.Util;

@Service("advertisementPictureBO")
public class AdvertisementPictureBO implements AdvertisementPicture {

	public static final String						BEAN_ALIAS = "advertisementPictureBO";
	
	@Autowired
	private File									fileService;
	
	@Autowired
	private AdvertisementPicturePO					persistencyLayer;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AdvertisementPictureTO newAdvertisementPicture(FileTO imageBase) throws IOException {
		// instanciate the current date to file temp
		Date currentDate = new Date();
		
		FileTempTO thumbnail = new FileTempTO();
		thumbnail.setContentType(imageBase.getContentType());
		thumbnail.setFileName(imageBase.getFileName());
		thumbnail.setData(Util.getImageByteArray(imageBase.getData(), MAX_HEIGHT_THUMBNAIL, MAX_WIDTH_THUMBNAIL));
		thumbnail.setSinceDate(currentDate);
		
		FileTempTO picture = new FileTempTO();
		picture.setContentType(imageBase.getContentType());
		picture.setFileName(imageBase.getFileName());
		picture.setData(Util.getImageByteArray(imageBase.getData(), MAX_HEIGHT_PICTURE, MAX_WIDTH_PICTURE));
		picture.setSinceDate(currentDate);

		thumbnail = (FileTempTO) fileService.insert(thumbnail);
		picture = (FileTempTO) fileService.insert(picture);

		AdvertisementPictureTO advertisementPictureToReturn = new AdvertisementPictureTO();
		
		FileTempTO thumbnailToReturn = new FileTempTO(thumbnail.getId());
		thumbnailToReturn.setFileName(thumbnail.getFileName());
		advertisementPictureToReturn.setThumbnail(thumbnailToReturn);
		
		FileTempTO pictureToReturn = new FileTempTO(picture.getId());
		advertisementPictureToReturn.setPicture(pictureToReturn);
		
		return advertisementPictureToReturn;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AdvertisementPictureTO insert(AdvertisementPictureTO advertisementPictureBase) {
		FileTO recoveredFile = null;
		AdvertisementPictureTO advertisementPicture = new AdvertisementPictureTO();
		
		recoveredFile = this.fileService.consult(advertisementPictureBase.getThumbnail());
		FileTO thumbnail = new FileTO();
		thumbnail.setContentType(recoveredFile.getContentType());
		thumbnail.setFileName(recoveredFile.getFileName());
		thumbnail.setData(recoveredFile.getData());
		thumbnail = this.fileService.insert(thumbnail);
		
		recoveredFile = this.fileService.consult(advertisementPictureBase.getPicture());
		FileTO picture = new FileTO();
		picture.setContentType(recoveredFile.getContentType());
		picture.setFileName(recoveredFile.getFileName());
		picture.setData(recoveredFile.getData());
		picture = this.fileService.insert(picture);
		
		advertisementPicture = this.persistencyLayer.insert(advertisementPicture);
		
		return advertisementPicture;
	}

	@Override
	@Transactional(readOnly = true)
	public AdvertisementPictureTO consult(AdvertisementPictureTO advertisementPicture) {
		return this.persistencyLayer.consult(advertisementPicture);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(AdvertisementPictureTO advertisementPicture) {
		this.persistencyLayer.delete(advertisementPicture);
	}
	
}
