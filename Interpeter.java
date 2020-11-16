import javax.swing.*;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Interpeter extends Canvas{
    public static String cPic;

    //Ein Array, für die relevantesten Farben des Bildes
    public static short[][] colos = new short[16][3];
    public static int width;
    public static int height;


    @Override
    //Bei jedem repaint wird das ganze Bild gezeichnet
    public void paint(Graphics g){
        super.paint(g);
        try {
            Scanner abc = new Scanner(System.in);
            //int height = 600, width = 800/2;
            short[] a = new short[2];

            //Ich muss ein BufferedReader verwenden, da man über den normalen Scanner keine einzelnen Zeichen lesen kann
            BufferedReader red = new BufferedReader(new InputStreamReader(new FileInputStream(cPic), Charset.forName("UTF-8")));
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width/2; j++) {

                    //Ein Byte speichert zwei Pixel, weshalb ich immer zwei zeichne und nur bis zur halben Breite zähle
                    a = divide((short) ((char) red.read()));
                    g.setColor(toColor(a[0]));
                    g.drawLine(2 * j, i, 2 * j, i);
                    g.setColor(toColor(a[1]));
                    g.drawLine((2 * j) + 1, i, (2 * j) + 1, i);
                }
            }

            //schließt den BufferedReader
            red.close();

            //ich fange die beiden möglichen Fehler getrennt, damit ein debuggen einfacher fällt
        } catch(FileNotFoundException f){
            System.out.println("Error 4");
        } catch(IOException i){
            System.out.println("Error 5");
        }
    }

    //nimmt die Zahl a als Index und gibt die Farbe als Color-Objekt zurück
    public static Color toColor(int a){
        return new Color(colos[a][0], colos[a][1], colos[a][2]);
    }

    //teilt ein Byte in zwei Nibble und gibt diese als Array zurück
    public static short[] divide(short a){
        short[] s = new short[2];
        s[0] = (short)( a & 0x0F );
        s[1] = (short)( a >> 4);
        return s;
    }

    //holt die wichtgsten Farben aus einer Datei und schreibt sie in den Array
    public static void getColors(Scanner s) throws FileNotFoundException {
        s = new Scanner(new File("picone_cols.txt"));
        for(int i = 0; i < colos.length; i++){
            for(int j = 0; j < colos[i].length; j++){
                colos[i][j] = (short)Integer.parseInt(s.next().substring(2), 16);
            }
        }

    }
    public static void getColors(Scanner s, String p) throws FileNotFoundException {
        s = new Scanner(new File(p));
        for(int i = 0; i < colos.length; i++){
            for(int j = 0; j < colos[i].length; j++){
                colos[i][j] = (short)Integer.parseInt(s.next().substring(2), 16);
            }
        }
        width = s.nextInt();
        height = s.nextInt();
        s.close();
    }
    public static void intepret(File compPic, File cols){
        Scanner s = new Scanner(System.in);
        cPic = compPic.getName();

        JFrame wind = new JFrame("Fenstergröße ändern bei fehlerhafter Anzeige");
        wind.setSize(1000, 1000);
        wind.setResizable(true);
        wind.add(new Interpeter());
        wind.setVisible(true);
        wind.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try{
            getColors(s,cols.getName());

        } catch(FileNotFoundException f){
            System.out.println("Error 7");
        }
    }

    //DEBUG
    public static void showPicture(String convertedPic){
        //Standart erstellung eines Fensters
        cPic = convertedPic;
        JFrame wind = new JFrame("interpreted File");
        wind.setSize(900, 900);
        wind.setResizable(true);
        wind.add(new Interpeter());
        wind.setVisible(true);

        //beendet den Prozess mit dem schließen des Fensters
        wind.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    /**
     //bekommt Dateipfad in args mitgegeben
     public static void main(String[] args){
     String path = args[0];
     String cols = args[1];
     Scanner s = new Scanner(System.in);

     JFrame wind = new JFrame("Bild");
     wind.setSize(900, 900);
     wind.setResizable(true);
     wind.add(new Interpeter());
     wind.setVisible(true);
     wind.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
     try{
        getColors(s,cols);

     } catch(FileNotFoundException f){
        System.out.println("Error 7");
      }
     }

     **/
}