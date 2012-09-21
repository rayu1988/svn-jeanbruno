package br.com.barganhas.business.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.FileTempTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.AdvertisementPicture;
import br.com.barganhas.business.services.File;
import br.com.barganhas.business.services.persistencies.AdvertisementPicturePO;
import br.com.barganhas.commons.Util;

import com.google.appengine.api.datastore.Transaction;

@Service("advertisementPictureBO")
public class AdvertisementPictureBO implements AdvertisementPicture {

	public static final String						BEAN_ALIAS = "advertisementPictureBO";

	@Autowired
	private File									fileService;
	
	@Autowired
	private AdvertisementPicturePO					persistencyLayer;
	
	@Override
	public AdvertisementPictureTO newAdvertisementPicture(FileTO imageBase) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			AdvertisementPictureTO advertisementPicture = new AdvertisementPictureTO();

			// instanciate the current date to file temp
			Date currentDate = new Date();
			
			FileTempTO thumbnail = new FileTempTO();
			thumbnail.setContentType(imageBase.getContentType());
			thumbnail.setFileName(imageBase.getFileName());
			thumbnail.setData(Util.transformBlobImage(imageBase.getData(), MAX_HEIGHT_THUMBNAIL, MAX_WIDTH_THUMBNAIL));
			thumbnail.setPersistedDate(currentDate);
			
			FileTempTO picture = new FileTempTO();
			picture.setContentType(imageBase.getContentType());
			picture.setFileName(imageBase.getFileName());
			picture.setData(Util.transformBlobImage(imageBase.getData(), MAX_HEIGHT_PICTURE, MAX_WIDTH_PICTURE));
			picture.setPersistedDate(currentDate);

			thumbnail = (FileTempTO) fileService.insert(thumbnail, null);
			picture = (FileTempTO) fileService.insert(picture, null);
			
			advertisementPicture.setThumbnail(thumbnail);
			advertisementPicture.setPicture(picture);
			
			return advertisementPicture;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public AdvertisementPictureTO insert(AdvertisementPictureTO advertisementPictureBase) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			AdvertisementPictureTO advertisementPicture = new AdvertisementPictureTO();
			
			FileTO thumbnail = new FileTO();
			thumbnail.setContentType(advertisementPictureBase.getThumbnail().getContentType());
			thumbnail.setFileName(advertisementPictureBase.getThumbnail().getFileName());
			thumbnail.setData(advertisementPictureBase.getThumbnail().getData());
			thumbnail = this.fileService.insert(thumbnail);
			
			FileTO picture = new FileTO();
			picture.setContentType(advertisementPictureBase.getPicture().getContentType());
			picture.setFileName(advertisementPictureBase.getPicture().getFileName());
			picture.setData(advertisementPictureBase.getPicture().getData());
			picture = this.fileService.insert(picture);
			
			advertisementPicture.setKeyThumbnail(thumbnail.getKey());
			advertisementPicture.setKeyPicture(picture.getKey());
			
			advertisementPicture = this.persistencyLayer.insert(advertisementPicture);
			
			transaction.commit();
			
			return advertisementPicture;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
}
