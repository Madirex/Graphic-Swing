package com.madirex;

import com.madirex.view.PaintFace;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame ventana = new JFrame();
        ventana.setSize(500, 500);
        ventana.add(new PaintFace());

        ventana.setVisible(true);
    }
}
