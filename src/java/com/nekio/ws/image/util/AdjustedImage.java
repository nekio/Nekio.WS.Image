package com.nekio.ws.image.util;

// <editor-fold defaultstate="collapsed" desc="Librerias">
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.border.Border;
// </editor-fold>

public class AdjustedImage implements Border{
    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private BufferedImage image = null;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    public AdjustedImage(BufferedImage image) {
        this.image = image;       
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos Sobreescritos">
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int ancho, int alto) {
        if(image != null)
            g.drawImage(image, 0, 0, ancho, alto, null);
    }
    
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }
    
    @Override
    public boolean isBorderOpaque() {
        return true;
    }
    // </editor-fold>
}
