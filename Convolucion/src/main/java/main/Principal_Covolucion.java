/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author Azucena
 */
public class Principal_Covolucion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String ruta1 = "/Users/Martha Jazmin/Documents/NetBeansProjects/Convolucion/src/main/java/img/montaña.jpg";
        String ruta2 = "/Users/Martha Jazmin/Documents/NetBeansProjects/Convolucion/src/main/java/img/panda.jpg";
        String ruta3 = "/Users/Martha Jazmin/Documents/NetBeansProjects/Convolucion/src/main/java/img/leon.jpg";

        System.out.println("¿Qué tipo de filtro desea aplicar a la imagen?");
        System.out.println("-> De la media");
        System.out.println("-> Sharpen");
        System.out.println("-> Prewitt Horizontal");
        System.out.println(" ");
        System.out.println("Introduzca el nombre del filtro: ");
        Scanner scanner = new Scanner(System.in);
        String respuesta = scanner.nextLine();
        BufferedImage img1 = null;

        try {
            if (respuesta.equals("De la media")) {
            img1 = ImageIO.read(new File(ruta1));
            ImageIO.write(laMedia(img1), "png", 
                    new File("/Users/Martha Jazmin/Documents/NetBeansProjects/Convolucion/src/main/java/img/Media.png"));
            
            }else if (respuesta.equals("Sharpen")) {
            img1 = ImageIO.read(new File(ruta2));
            ImageIO.write(sharpen(img1), "png", 
                    new File("/Users/Martha Jazmin/Documents/NetBeansProjects/Convolucion/src/main/java/img/Sharpen.png"));
            
            }else{
            img1 = ImageIO.read(new File(ruta3));
            ImageIO.write(prewittH(img1), "png", 
                    new File("/Users/Martha Jazmin/Documents/NetBeansProjects/Convolucion/src/main/java/img/PrewittH.png"));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static BufferedImage laMedia(BufferedImage img1) {
        float[][] matriz;
        matriz = new float[3][3];
        int colorR;
        int colorG;
        int colorB;
        Color Black = new Color(0, 0, 0); // Color negro 
        int rgbB = Black.getRGB();
        float salidaR = 0;
        float salidaG = 0;
        float salidaB = 0;
        int rgb = 0;

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                matriz[i][j] = 0.11111f; // = 1/9
            }
        }

        //alto
        for (int y = 0; y < img1.getHeight(); y++) {
            //ancho
            for (int x = 0; x < img1.getWidth(); x++) {//para pintar el borde superior e izquierdo
                if ((x == 0 || y == 0) || ((img1.getWidth() - 1) == x || (img1.getHeight() - 1) == y)) {
                    img1.setRGB(x, y, rgbB);

                } else {
                    /* rgb = img1.getRGB(x, y);
                    Color color = new Color(rgb);
                    colorR = color.getRed();
                    colorG = color.getBlue();
                    colorB = color.getBlue();*/

                    for (int i = 0; i <= 2; i++) {//se corta hasta el ancho y alto del filtro de la convolucion
                        for (int j = 0; j <= 2; j++) {
                            rgb = img1.getRGB((x - 1) + j, (y - 1) + i);
                            Color color = new Color(rgb);
                            colorR = color.getRed();
                            colorG = color.getGreen();
                            colorB = color.getBlue();
                            salidaR = salidaR + (matriz[i][j] * colorR);
                            salidaG = salidaG + (matriz[i][j] * colorG);
                            salidaB = salidaB + (matriz[i][j] * colorB);

                        }
                    }
                    Color nuevoColor = new Color((int) salidaR, (int) salidaG, (int) salidaB);
                    img1.setRGB(x, y, nuevoColor.getRGB());// se pasan a int las salidas que son de tipo float

                    salidaR = 0;
                    salidaG = 0;
                    salidaB = 0;
                }

            }
        }
        return img1;
    }

    public static BufferedImage sharpen(BufferedImage img1) {

        float[][] matriz;
        matriz = new float[3][3];
        int colorR;
        int colorG;
        int colorB;
        Color Black = new Color(0, 0, 0); // Color negro 
        int rgbB = Black.getRGB();
        float salidaR = 0;
        float salidaG = 0;
        float salidaB = 0;
        int rgb = 0;

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                matriz[i][j] = -1;
            }
        }
        matriz[1][1] = 9; //al centro de la matriz de 3x3 se le asigna un 9

        //alto
        for (int y = 0; y < img1.getHeight(); y++) {
            //ancho
            for (int x = 0; x < img1.getWidth(); x++) {//para pintar el borde superior e izquierdo
                if ((x == 0 || y == 0) || ((img1.getWidth() - 1) == x || (img1.getHeight() - 1) == y)) {
                    img1.setRGB(x, y, rgbB);

                } else {
                    for (int i = 0; i <= 2; i++) {//se corta hasta el ancho y alto del filtro de la convolucion
                        for (int j = 0; j <= 2; j++) {

                            rgb = img1.getRGB((x - 1) + j, (y - 1) + i);
                            Color color = new Color(rgb);
                            colorR = color.getRed();
                            colorG = color.getGreen();
                            colorB = color.getBlue();
                            salidaR = salidaR + (matriz[i][j] * colorR);
                            salidaG = salidaG + (matriz[i][j] * colorG);
                            salidaB = salidaB + (matriz[i][j] * colorB);
                        }

                    } // se pasan a int las salidas que son de tipo float
                    Color nuevoColor = new Color(comparar((int) salidaR), comparar((int) salidaG), comparar((int) salidaB));
                    img1.setRGB(x, y, nuevoColor.getRGB());

                    salidaR = 0;
                    salidaG = 0;
                    salidaB = 0;
                }

            }
        }
        return img1;

    }

    public static BufferedImage prewittH(BufferedImage img1) {
        float[][] matriz;
        matriz = new float[3][3];
        int colorR;
        int colorG;
        int colorB;
        Color Black = new Color(0, 0, 0); // Color negro 
        int rgbB = Black.getRGB();
        float salidaR = 0;
        float salidaG = 0;
        float salidaB = 0;
        int rgb = 0;
        //se le asigna valores a la máscara
        matriz[0][0] = -1;
        matriz[0][1] = -1;
        matriz[0][2] = -1;
        matriz[1][0] = 0;
        matriz[1][1] = 0;
        matriz[1][2] = 0;
        matriz[2][0] = 1;
        matriz[2][1] = 1;
        matriz[2][2] = 1;

        //alto
        for (int y = 0; y < img1.getHeight(); y++) {
            //ancho
            for (int x = 0; x < img1.getWidth(); x++) {//para pintar el borde superior e izquierdo
                if ((x == 0 || y == 0) || ((img1.getWidth() - 1) == x || (img1.getHeight() - 1) == y)) {
                    img1.setRGB(x, y, rgbB);

                } else {

                    for (int i = 0; i <= 2; i++) {//se corta hasta el ancho y alto del filtro de la convolucion
                        for (int j = 0; j <= 2; j++) {
                            rgb = img1.getRGB((x - 1) + j, (y - 1) + i);
                            Color color = new Color(rgb);
                            colorR = color.getRed();
                            colorG = color.getGreen();
                            colorB = color.getBlue();
                            salidaR = salidaR + (matriz[i][j] * colorR);
                            salidaG = salidaG + (matriz[i][j] * colorG);
                            salidaB = salidaB + (matriz[i][j] * colorB);
                        }
                    }/// se pasan a int las salidas que son de tipo float
                    Color nuevoColor = new Color(comparar((int) salidaR), comparar((int) salidaG), comparar((int) salidaB));
                    img1.setRGB(x, y, nuevoColor.getRGB());

                    salidaR = 0;
                    salidaG = 0;
                    salidaB = 0;
                }

            }
        }
        return img1;
    }

    public static int comparar(int a) { //para que los colores RGB no den error por salirse del rango 0-255
        if (a >= 0 && a <= 255) {
            return a;
        } else if (a > 255) {
            return 255;
        } else {
            return 0;
        }

    }

}
