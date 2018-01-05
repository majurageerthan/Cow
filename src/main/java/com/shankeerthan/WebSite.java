package com.shankeerthan;


import javax.print.DocFlavor;
import java.awt.*;
import java.net.URI;
import java.net.URL;

public class WebSite {


    public static void open() {
        try {
            URI u = new URI("https://happycow.000webhostapp.com/");
            java.awt.Desktop.getDesktop().browse(u);

        } catch (Exception e) {

        }


    }
}
