package com.nekio.ws.test;

import com.nekio.ws.image.endpoint.contract.IImageServer;
import com.nekio.ws.image.endpoint.publisher.Publishment;
import java.awt.Image;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import nekio.library.common.helpers.FileHelper;
import nekio.library.common.log.Log;

public class ImageClient {
    public static void main(String[] args) throws Exception{
        URL url = new URL(Publishment.Path.Image.getWsdl());
        String serviceURI = Publishment.SERVICE_URI;
        String serviceName = "ImageServerService";

        QName qname = new QName(serviceURI, serviceName);

        Service service = Service.create(url, qname);
        IImageServer contract = service.getPort(IImageServer.class);

        //download(contract, "duke.gif");
        //upload(contract, file);
        
        File folder = new File("C:\\Users\\SITI\\Pictures\\Tamsa\\");
        uploadPreview(contract, folder);
    }
    
    private static void download(IImageServer contract, String filename){
        Image image = contract.downloadImage(filename);

        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        frame.add(new JLabel(new ImageIcon(image)));
        frame.setVisible(true);
    }
    
    private static void upload(IImageServer contract, File file) throws Exception{
        Image resource = ImageIO.read(file);

        BindingProvider bp = (BindingProvider)contract;
        SOAPBinding binding = (SOAPBinding) bp.getBinding();
        binding.setMTOMEnabled(true);

        String statusImage = contract.uploadImage(resource);
        System.out.println("imageServer.uploadImage() : " + statusImage);
    }
    
    private static void uploadPreview(IImageServer contract, File folder) throws Exception{
        BindingProvider bp = (BindingProvider)contract;
        SOAPBinding binding = (SOAPBinding) bp.getBinding();
        binding.setMTOMEnabled(true);
        
        List<Image> resources = new ArrayList<Image>();
        int counter = 0;
        int errors = 0;
        
        for(File file : FileHelper.getAllFiles(folder, Publishment.IMAGE_EXTENSION)){
            resources.add(ImageIO.read(file));
            
            if(counter > 0 && counter % Publishment.MAX_LOT_IMAGE == 0){
                errors = contract.uploadPreviews(resources);
                Log.m("imageServer.uploadPreview() : " + errors);
                
                resources.clear();
            }
            
            counter++;
        }
    }
}
