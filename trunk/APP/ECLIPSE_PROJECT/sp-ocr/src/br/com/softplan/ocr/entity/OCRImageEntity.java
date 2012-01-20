/**
 * 
 */
package br.com.softplan.ocr.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOImage;

import br.com.softplan.ocr.common.OCRImageHelper;
import br.com.softplan.ocr.common.OCRImageIOHelper;

/**
 * @author jean.villete
 *
 */
public class OCRImageEntity {
	
	private final static Rectangle EMPTY_RECTANGLE = new Rectangle();
	
    /** input images */
    private List<IIOImage> oimages;
    /** input image File */
    private File imageFile;
    /** index of pages, such as in multi-page TIFF image */
    private int index;
    /** bounding rectangle */
    private Rectangle rect;
    /** Horizontal Resolution */
    private int dpiX;
    /** Vertical Resolution */
    private int dpiY;

    /**
     * Constructor.
     * @param oimages a list of <code>IIOImage</code> objects
     * @param index
     * @param the bounding rectangle defines the region of the image to be recognized. A rectangle of zero dimension or <code>null</code> indicates the whole image.
     */
    public OCRImageEntity(List<IIOImage> oimages, int index, Rectangle rect) {
        this.oimages = oimages;
        this.index = index;
        this.rect = rect;
    }

    /**
     * Constructor.
     * @param imageFile an image file
     * @param index
     * @param the bounding rectangle defines the region of the image to be recognized. A rectangle of zero dimension or <code>null</code> indicates the whole image.
     */
    public OCRImageEntity(File imageFile, int index, Rectangle rect) {
        this.imageFile = imageFile;
        this.index = index;
        this.rect = rect;
    }

    /**
     * @return the list of oimages
     */
    public List<IIOImage> getOimages() {
        return oimages;
    }

    /**
     * @return the imageFile
     */
    public File getImageFile() {
        return imageFile;
    }

    /**
     * @return the ClonedImageFiles
     */
    public List<File> getClonedImageFiles() throws IOException {
        if (oimages != null) {
            if (dpiX == 0 || dpiY == 0) {
                if (rect == null || rect.equals(EMPTY_RECTANGLE)) {
                    return OCRImageIOHelper.createTiffFiles(oimages, index);
                } else {
                    // rectangular region
//                    BufferedImage bi = ((BufferedImage) oimages.get(index).getRenderedImage()).getSubimage(rect.x, rect.y, rect.width, rect.height);
                    // On Linux, the standard getSubimage method has generated images that Tesseract does not like.
                    BufferedImage bi = OCRImageHelper.getSubImage((BufferedImage) oimages.get(index).getRenderedImage(), rect.x, rect.y, rect.width, rect.height);
                    List<IIOImage> tempList = new ArrayList<IIOImage>();
                    tempList.add(new IIOImage(bi, null, null));
                    return OCRImageIOHelper.createTiffFiles(tempList, 0);
                }
            } else {
                // scaling
                if (rect == null || rect.equals(EMPTY_RECTANGLE)) {
                    List<IIOImage> tempList = new ArrayList<IIOImage>();
                    for (IIOImage oimage : (index == -1 ? oimages : oimages.subList(index, index + 1))) {
                        BufferedImage bi = (BufferedImage) oimage.getRenderedImage();
                        Map<String, String> metadata = OCRImageIOHelper.readImageData(oimage);
                        float scale = dpiX / Float.parseFloat(metadata.get("dpiX"));
                        bi = OCRImageHelper.getScaledInstance(bi, (int) (bi.getWidth() * scale), (int) (bi.getHeight() * scale));
                        tempList.add(new IIOImage(bi, null, null));
                    }
                    return OCRImageIOHelper.createTiffFiles(tempList, (index == -1 ? index : 0), dpiX, dpiY);
                } else {
                    // rectangular region
                    //Cut out the subimage first and rescale that
                    BufferedImage bi = ((BufferedImage) oimages.get(index).getRenderedImage()).getSubimage(rect.x, rect.y, rect.width, rect.height);
                    Map<String, String> metadata = OCRImageIOHelper.readImageData(oimages.get(index));
                    float scale = dpiX / Float.parseFloat(metadata.get("dpiX"));
                    bi = OCRImageHelper.getScaledInstance(bi, (int) (bi.getWidth() * scale), (int) (bi.getHeight() * scale));
                    List<IIOImage> tempList = new ArrayList<IIOImage>();
                    tempList.add(new IIOImage(bi, null, null));
                    return OCRImageIOHelper.createTiffFiles(tempList, 0, dpiX, dpiY);
                }
            }
        } else {
            return OCRImageIOHelper.createTiffFiles(imageFile, index);
        }
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return the bounding rectangle
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * Sets screenshot mode.
     * @param mode true for resampling the input image; false for no manipulation of the image
     */
    public void setScreenshotMode(boolean mode) {
        dpiX = mode ? 300 : 0;
        dpiY = mode ? 300 : 0;
    }

    /**
     * Sets resolution (DPI).
     * @param dpiX horizontal resolution
     * @param dpiY vertical resolution
     */
    public void setResolution(int dpiX, int dpiY) {
        this.dpiX = dpiX;
        this.dpiY = dpiY;
    }
}
