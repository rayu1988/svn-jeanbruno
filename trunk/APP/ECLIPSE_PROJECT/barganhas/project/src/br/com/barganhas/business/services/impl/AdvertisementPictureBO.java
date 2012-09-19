package br.com.barganhas.business.services.impl;

import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.services.AdvertisementPicture;
import br.com.barganhas.commons.Util;

@Service("advertisementPictureBO")
public class AdvertisementPictureBO implements AdvertisementPicture {

	public static final String						BEAN_ALIAS = "advertisementPictureBO";

	@Override
	public AdvertisementPictureTO newAdvertisementPicture(FileTO imageBase) {
		AdvertisementPictureTO advertisementPicture = new AdvertisementPictureTO();

		FileTO thumbnail = new FileTO();
		thumbnail.setContentType(imageBase.getContentType());
		thumbnail.setFileName(imageBase.getFileName());
		thumbnail.setData(Util.transformBlobImage(imageBase.getData(), MAX_HEIGHT_THUMBNAIL, MAX_WIDTH_THUMBNAIL));
		
		FileTO picture = new FileTO();
		picture.setContentType(imageBase.getContentType());
		picture.setFileName(imageBase.getFileName());
		picture.setData(Util.transformBlobImage(imageBase.getData(), MAX_HEIGHT_PICTURE, MAX_WIDTH_PICTURE));

		advertisementPicture.setThumbnail(thumbnail);
		advertisementPicture.setPicture(picture);
		
		return advertisementPicture;
	}
	
}
