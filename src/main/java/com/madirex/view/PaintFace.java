package com.madirex.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;

public class PaintFace extends JComponent {

    //Posiciones
    int facePositionX;
    int facePositionY;
    int eyePositionX;
    int eyePositionY;
    int eye2PositionX;
    int eye2PositionY;
    int mouthPositionX;

    //Movimiento
    int directionEyesX;
    int directionEyesY;
    int maxDirectionEyes;

    //Cejas
    static final int MAX_DESP_CEJAS = 10;
    int actualDespCejas;
    boolean cejasDirectionUp;

    //Otros
    boolean openMouth;
    int sombra;
    Color eyeColor;
    int cursorX;
    int cursorY;


    public PaintFace(){

        //VALORES INICIALES
        eyeColor = new Color(208, 26, 236, 128);
        openMouth = true;
        actualDespCejas = 0;
        cejasDirectionUp = true;
        sombra = 10;

        //CURSOR X E Y
        cursorX = 500;
        cursorY = 500;

        //OJOS FOLLOW
        maxDirectionEyes = 20;
        directionEyesX = 0;
        directionEyesY = 0;

        //Agregar Listeners
        addListeners();
        addTimer();
    }

    @Override
    public void paint (Graphics g) {
        //INICIALIZAR DIBUJO
        pintarFondo((Graphics2D) g);
        paintFace((Graphics2D) g);
    }

    private void addTimer() {
        Timer timer = new Timer(100, e -> {

            //MOVER CEJAS
            if (cejasDirectionUp) {
                if (actualDespCejas < MAX_DESP_CEJAS) {
                    actualDespCejas += 1;
                } else {
                    cejasDirectionUp = false;
                }
            }else{
                if (actualDespCejas > 0) {
                    actualDespCejas -= 1;
                } else {
                    cejasDirectionUp = true;
                }
            }

            repaint();
        });
        timer.setRepeats(true);
        timer.setDelay(100);
        timer.start();
    }

    /**
     * Agregar los listeners
     */
    private void addListeners() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //CAMBIAR COLOR A LOS OJOS
                Random rand = new Random();
                float r = rand.nextFloat();
                float g = rand.nextFloat();
                float b = rand.nextFloat();

                eyeColor = new Color(r, g, b);

                //ABRIR / CERRAR BOCA
                openMouth = !openMouth;

                //Repintar
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //Vacío
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Vacío
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //Vacío
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //Vacío
            }
        });

        ///////CURSOR QUE SIGUE A LOS OJOS
            addMouseMotionListener( new MouseMotionAdapter() {
                @Override
                public void mouseMoved( MouseEvent e ) {
                    cursorX = e.getX();
                    cursorY = e.getY();
                }
            } );
    }

    /**
     * Pintar el fondo con un bucle
     * @param g Graphics2D
     */
    public void pintarFondo(Graphics2D g){

        for (int y =0 ; y <= 500 ; y++) {
            g.setColor(new Color(137, 133, 255));
            g.drawLine(0,y,500,y);
            g.setColor(new Color(10,10,10));

            /////
            //DIBUJAR ÁM
            /////
            int pos = 420;

                //Dibujar Á
                g.drawLine(pos, pos, pos + 10, pos + 30); // /
                g.drawLine(pos, pos, pos - 10, pos + 30); // \
                g.drawLine(pos + 5, pos + 15, pos - 5, pos + 15); // _
                g.drawLine(pos + 10, pos + 24 - 30, pos, pos + 24 - 30 + 5); // ´

                //Dibujar M
                g.drawLine(pos + 10 + (30), pos, pos + (30), pos + 15); // /
                g.drawLine(pos - 10 + (30), pos, pos + (30), pos + 15); // \
                g.drawLine(pos - 10 + (30), pos, pos - 10 + (30), pos + 30); // |
                g.drawLine(pos + 10 + (30), pos, pos + 10 + (30), pos + 30); // |
        }
    }

    /**
     * Pintar la cara
     * @param g Graphics2D
     */
    public void paintFace(Graphics2D g){
        //Antialiasing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //POSICIONES
        facePositionX = 100;
        facePositionY = 100 + actualDespCejas / 2;
        eyePositionX = 70 - 10;
        eyePositionY = 50;
        eye2PositionX = 190 - 10;
        eye2PositionY = 50;
        mouthPositionX = 50;

        //Agregar cara y complementos
        addShirt(g);
        addFace(g);
        addHair(g);
        addeyebrows(g);
        addEyes(g);
        addMouth(g);
    }

    private void addShirt(Graphics2D g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRoundRect(100 + 50 - sombra,100 + 250 - sombra,190,250,20,20);
        g.setColor(new Color(155, 4, 52));
        g.fillRoundRect(100 + 50,100 + 250,190,250,20,20);

        //MANGAS
        g.setColor(new Color(124, 4, 42));
        g.fillRoundRect(100 + 50,100 + 250 - actualDespCejas / 5,50,250,20,20); //IZQ
        g.fillRoundRect(100 + 200,100 + 250 - actualDespCejas / 5,50,250,20,20); //DER
    }

    private void addFace(Graphics2D g) {
        g.setColor(new Color(0, 0, 0));
        g.fillOval(facePositionX + 20 - sombra,facePositionY - sombra,250,255);
        g.setColor(new Color(220, 171, 131));
        g.fillOval(facePositionX + 20,facePositionY,250,255);
    }

    private void addHair(Graphics2D g) {
        g.setColor(new Color(19, 19, 19));
        g.fillRoundRect(facePositionX + 70,facePositionY - 20,160,50,100,100);
        for(int i = 0; i < 4; i++) {
            g.fillRoundRect(facePositionX + 200 + (15 * i), facePositionY + (15 * i), 50, 50, 100, 100);
            g.fillRoundRect(facePositionX + (15 * i), facePositionY + 50 - (15 * i), 50, 50, 100, 100);
        }
    }

    private void addeyebrows(Graphics2D g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(facePositionX + 50,facePositionY + 20 + actualDespCejas,90,25);

        g.setColor(new Color(0, 0, 0));
        g.fillRect(facePositionX + 50 + 120,facePositionY + 20 + actualDespCejas,90,25);
    }

    private void addMouth(Graphics2D g) {
        if (openMouth) {
            //Base
            g.setColor(new Color(17, 17, 17));
            g.fillArc(facePositionX + mouthPositionX, facePositionY + 123, 140,100,270,90);

            //Lengua
            g.setColor(new Color(255, 0, 0));
            g.fillOval(facePositionX + mouthPositionX + 75, facePositionY + 100 + 19 + 55, 45, 40);

            //Dientes
            g.setColor(new Color(250, 250, 250));
            g.fillRect(facePositionX + mouthPositionX + 70, facePositionY + 100 + 19 + 55, 20, 15);
            g.fillRect(facePositionX + mouthPositionX + 70 + 22, facePositionY + 100 + 19 + 55, 20, 15);

        }else{
            //Base
            g.setColor(new Color(17, 17, 17));
            g.fillArc(facePositionX + mouthPositionX, facePositionY + 123 + 50, 140,50,270,90);
        }
    }

    private void addEyes(Graphics2D g) {
        int actualEyePos = eyePositionX;

        //OJOS QUE SIGUEN AL CURSOR
        if (facePositionX + actualEyePos < cursorX){
            if (directionEyesX < maxDirectionEyes * 2) {
                directionEyesX += 3;
            }
        }else{
           if (directionEyesX > maxDirectionEyes * -1) {
               directionEyesX -= 3;
            }
        }

        if (facePositionY + actualEyePos < cursorY){
             if (directionEyesY < maxDirectionEyes * 2) {
                 directionEyesY += 3;
             }
         }else{
             if (directionEyesY > maxDirectionEyes * -1) {
                 directionEyesY -= 3;
             }
         }

        addEyesMovement(g, actualEyePos);
    }

    private void addEyesMovement(Graphics2D g, int actualEyePos) {
        boolean salir = false;
        do {
            g.setColor(new Color(5, 5, 5));
            g.fillOval(facePositionX + actualEyePos, facePositionY + eyePositionY, 80, 80);
            g.setColor(eyeColor);
            g.fillOval(facePositionX + actualEyePos + 10, facePositionY + eyePositionY + 5, 50, 65);
            g.setColor(new Color(255, 255, 255));
            g.fillOval(facePositionX + actualEyePos + 10 + directionEyesX  / 5, facePositionY + eyePositionY + 14
                    + directionEyesY / 5, 25, 25);
            g.setColor(new Color(255, 255, 255));
            g.fillOval(facePositionX + actualEyePos + 40 + directionEyesX  / 5, facePositionY + eyePositionY + 20
                    + directionEyesY  / 5, 30, 40);
            g.setColor(new Color(5, 5, 5));
            g.fillOval(facePositionX + actualEyePos + 40 + directionEyesX  / 2, facePositionY + eyePositionY + 20
                    + directionEyesY  / 2, 20, 25);
            g.setColor(new Color(250, 250, 250));
            g.fillOval(facePositionX + actualEyePos + 20 + directionEyesX / 3, facePositionY + eyePositionY + 40
                    + directionEyesY / 5, 13, 13);

            //Setup segundo ojo
            if (actualEyePos == eye2PositionX) salir = true;
            else actualEyePos = eye2PositionX;

        }while(!salir);
    }
}
