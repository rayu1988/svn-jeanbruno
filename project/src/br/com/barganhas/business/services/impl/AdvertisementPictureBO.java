package br.com.barganhas.business.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.FileTempTO;
import br.com.barganhas.business.services.AdvertisementPicture;
import br.com.barganhas.business.services.File;
import br.com.barganhas.business.services.persistencies.AdvertisementPicturePO;
import br.com.barganhas.commons.Util;

import com.google.appengine.api.datastore.EntityNotFoundException;
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

			AdvertisementPictureTO advertisementPictureToReturn = new AdvertisementPictureTO();
			
			FileTempTO thumbnailToReturn = new FileTempTO(thumbnail.getKey());
			thumbnailToReturn.setFileName(thumbnail.getFileName());
			advertisementPictureToReturn.setThumbnail(thumbnailToReturn);
			
			FileTempTO pictureToReturn = new FileTempTO(picture.getKey());
			advertisementPictureToReturn.setPicture(pictureToReturn);
			
			return advertisementPictureToReturn;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public AdvertisementPictureTO insert(AdvertisementPictureTO advertisementPictureBase) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
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
			
			advertisementPicture.setKeyThumbnail(thumbnail.getKey());
			advertisementPicture.setKeyPicture(picture.getKey());
			
			advertisementPicture = this.persistencyLayer.insert(advertisementPicture);
			
			transaction.commit();
			
			return advertisementPicture;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public AdvertisementPictureTO consult(AdvertisementPictureTO advertisementPicture) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			advertisementPicture = this.persistencyLayer.consult(advertisementPicture);
			
			transaction.commit();
			
			return advertisementPicture;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	public void delete(AdvertisementPictureTO advertisementPicture) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			advertisementPicture = this.consult(advertisementPicture);
			
			this.fileService.delete(new FileTO(advertisementPicture.getKeyThumbnail()));
			this.fileService.delete(new FileTO(advertisementPicture.getKeyPicture()));
			
			this.persistencyLayer.delete(advertisementPicture);
			
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public List<AdvertisementPictureTO> list(Integer startFrom) {
		return this.persistencyLayer.list(startFrom);
	}
	
}
