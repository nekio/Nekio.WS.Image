package com.nekio.ws.image.endpoint.publisher;

public class Publishment {
    public static final String PATH = "http://localhost:8080/Nekio.WS.Image-war/ws/";
    public static final String SERVICE_URI = "http://endpoint.image.ws.nekio.com/";
    
    public static final String DESTINATION_FOLDER = "C:\\test\\";
    public static final String IMAGE_EXTENSION = "jpg";
    public static final int MAX_LOT_IMAGE = 10;
    
    public enum Path{
        Image("image"); // http://localhost:8080/Nekio.WS.Common-war/ws/ping
        
        private String name;
        
        private Path(String name){
            this.name = name;
        }
        
        public String getPath(){
            return PATH + name;
        }
        
        public String getWsdl(){
            return PATH + name + "?wsdl";
        }
    }
    
}
