package com.stephen.entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Objects;

/**
 * @author Stephen Chen
 * The Image class represents an image object that contains its source and its BufferedImage representation.
 */
public class Image {
    private String source; // the image source URL or file path
    private BufferedImage image; // the BufferedImage object used to represent the image

    /**
     * Constructs an Image object with the given image source.
     * @param source the image source URL or file path
     */
    public Image(String source){
        setSource(source);
    }

    /**
     * getters and setters
     */
    public BufferedImage getImage() {
        return image;
    }

    public String getSource() {
        return source;
    }

    /**
     * Sets the image source and initializes the BufferedImage representation.
     * If the source is a URL, reads the image from the URL. Otherwise, reads the image from the file path.
     * If the source is invalid or an exception is thrown, sets the source to an empty string.
     * @param source the image source URL or file path
     */
    private void setSource(String source) {
        this.source = source;
        try{
            this.image = ImageIO.read(new URL(source));
        }
        catch (Exception e1){
            try{
                this.image = ImageIO.read(new File(source));
            }
            catch (Exception e2){
                this.source = "";
            }
        }
    }

    /**
     * Returns a Base64-encoded string of the image in case future development requires image transmission.
     * @return a Base64-encoded string of the image
     * @throws IOException if an I/O error occurs while encoding the image
     */
    public String getEncodedImage() throws IOException {
        if (!Objects.equals(source, "")){
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(image, "png", output);
            String imageAsBase64 = Base64.getEncoder().encodeToString(output.toByteArray());
            return imageAsBase64;
        }
        return "Unsupported source";
    }
}
