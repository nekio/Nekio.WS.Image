package com.nekio.ws.image.endpoint;

import com.nekio.ws.image.endpoint.contract.IImageServer;
import com.nekio.ws.image.endpoint.publisher.Publishment;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.MTOM;
import nekio.library.common.helpers.ImageHelper;
import nekio.library.common.log.Log;

@MTOM
@WebService(endpointInterface = "com.nekio.ws.image.endpoint.contract.IImageServer")
public class ImageServer implements IImageServer{
    @Override
    public Image downloadImage(String name) {
        Image image = null;
        
        try {
            File file = new File(Publishment.DESTINATION_FOLDER + name);
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return image;
    }

    @Override
    public String uploadImage(Image resource) throws WebServiceException{
        String result = "0";
        
        try {
            BufferedImage bi = new BufferedImage(resource.getWidth(null), resource.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bi.createGraphics();
            g2.drawImage(resource, null, null);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, Publishment.IMAGE_EXTENSION, baos);
            InputStream input = new ByteArrayInputStream(baos.toByteArray());
            
            if(createImage(input))
                result = "1";
        } catch (Exception e) {
        }
        
        return result;
    }
    
    @Override
    public int uploadPreviews(Object object) throws WebServiceException{
        int errors = 0;
        
        try {
            List<Image> resources = (List<Image>)object;
            for(Image resource : resources){
                int width = resource.getWidth(null);
                int height = resource.getHeight(null);

                if(width > height){
                    width = 210;
                    height= 150;
                }else if(width < height){
                    width = 150;
                    height= 210;
                }else{
                    width = 200;
                    height= 200;
                }

                resource = resource.getScaledInstance(width, height, Image.SCALE_DEFAULT);

                BufferedImage bi = new BufferedImage(resource.getWidth(null), resource.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = bi.createGraphics();
                g2.drawImage(resource, null, null);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bi, Publishment.IMAGE_EXTENSION, baos);
                InputStream input = new ByteArrayInputStream(baos.toByteArray());

                if(!createImage(input))
                    errors++;
            }
        } catch (Exception e) {
        }
        
        return errors;
    }
    
    private boolean createImage(InputStream input){
        boolean result = false;
        
        if(input != null){
            String timestamp = Log.getTimestamp();
            timestamp = timestamp.replace("/","_");
            timestamp = timestamp.replace(":","_");
            timestamp = timestamp.replace(".","_");
            
            String[] datetime = timestamp.split(" ");
            
            try {
                ImageHelper.PATH_BASE = Publishment.DESTINATION_FOLDER;
                ImageHelper.saveImage(input, datetime[1], Publishment.IMAGE_EXTENSION, datetime[0]);
                
                result = true;
            } catch (Exception e) {
                Log.m("ImageServer - Error creating " + datetime[1]);
            }
        }
        
        return result;
    }
}
