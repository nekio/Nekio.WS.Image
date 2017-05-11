package com.nekio.ws.image.endpoint.publisher;

import com.nekio.ws.image.endpoint.ImageServer;
import javax.xml.ws.Endpoint;

public class ImagePublisher {
    public static void main(String[] args) {
        Endpoint.publish(Publishment.Path.Image.getWsdl(), new ImageServer());
    }
}
