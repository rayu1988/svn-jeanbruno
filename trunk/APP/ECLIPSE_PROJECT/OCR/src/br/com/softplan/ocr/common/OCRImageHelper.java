/**
 * 
 */
package br.com.softplan.ocr.common;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;

/**
 * @author jean.villete
 *
 */
public class OCRImageHelper {

    /**
     * Convenience method that returns a scaled instance of the
     * provided {@code BufferedImage}.
     *
     * @param image the original image to be scaled
     * @param targetWidth the desired width of the scaled instance,
     *    in pixels
     * @param targetHeight the desired height of the scaled instance,
     *    in pixels
     * @return a scaled version of the original {@code BufferedImage}
     */
    public static BufferedImage getScaledInstance(BufferedImage image, int targetWidth, int targetHeight) {
        int type = (image.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage tmp = new BufferedImage(targetWidth, targetHeight, type);
        Graphics2D g2 = tmp.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        g2.dispose();
        return tmp;
    }

    /**
     * A replacement for the standard <code>BufferedImage.getSubimage</code> method.
     * 
     * @param image
     * @param x the X coordinate of the upper-left corner of the specified rectangular region
     * @param y the Y coordinate of the upper-left corner of the specified rectangular region
     * @param width the width of the specified rectangular region
     * @param height the height of the specified rectangular region
     * @return a BufferedImage that is the subimage of <code>image</code>.
     */
    public static BufferedImage getSubImage(BufferedImage image, int x, int y, int width, int height) {
        int type = (image.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage tmp = new BufferedImage(width, height, type);
        Graphics2D g2 = tmp.createGraphics();
        g2.drawImage(image.getSubimage(x, y, width, height), 0, 0, null);
        g2.dispose();
        return tmp;
    }

    /**
     * Gets an image from Clipboard.
     *
     * @return image
     */
    public static Image getClipboardImage() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            return (Image) clipboard.getData(DataFlavor.imageFlavor);
        } catch (Exception e) {
            return null;
        }
    }
}
