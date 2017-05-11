package com.nekio.ws.image.endpoint.contract;

import java.awt.Image;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface IImageServer{
    @WebMethod 
    public abstract Image downloadImage(String name);

    @WebMethod
    public abstract String uploadImage(Image resource);
    
    @WebMethod
    public abstract int uploadPreviews(Object object);
}
