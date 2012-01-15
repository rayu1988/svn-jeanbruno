/**
 * 
 */
package br.com.softplan.ocr.entity;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import br.com.softplan.ocr.service.OCRImageService;

/**
 * Entity class.
 * A base (fundamental) document image known by the system.
 * This is a representative document image with their binaries through its image attribute (OCRImage), and points to a physical file through its file attribute. 
 * 
 * @author jean.villete
 *
 */
public class OCRDocumentImage implements Serializable {
	
	private static final long 		serialVersionUID = 3430604785211921849L;
	
	private File					fileImage;
	private OCRImage				image;
	
	public OCRDocumentImage(File fileImage) throws IOException, InterruptedException {
		if (fileImage == null || !fileImage.exists() || !fileImage.isFile()) throw new IllegalArgumentException();
		this.fileImage = fileImage;

		BufferedImage image = ImageIO.read(fileImage);
		OCRImageScale imageScale = new OCRImageScale(image.getWidth(null), image.getHeight(null));
		int[] arrayToBinaries = new int[imageScale.getWidth() * imageScale.getHeight()];
		
		//tries to grab the (x, y, w, h) rectangular section of pixels from the specified image into the given array. The pixels are stored into the array in the default RGB ColorModel. 
		PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, imageScale.getWidth(), imageScale.getHeight(), arrayToBinaries, 0, imageScale.getWidth());
		pixelGrabber.grabPixels();
		
		arrayToBinaries = OCRImageService.getBinariesAtGrayScale(arrayToBinaries, true);
		arrayToBinaries = OCRImageService.getFilteredBinaries(new OCRImage(arrayToBinaries, imageScale));
		this.image = new OCRImage(arrayToBinaries, imageScale);
	}

	// getters //
	public File getFileImage() {
		return fileImage;
	}
	public OCRImage getImage() {
		return image;
	}
}
