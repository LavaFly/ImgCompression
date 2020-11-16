import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Converter {
    //Ein Array, für die relevantesten Farben des Bildes
    public static short[][] colos = new short[16][3];

    //holt die wichtgsten Farben aus einer Datei und schreibt sie in den Array
    public static void getcols(Scanner s, File cols) throws FileNotFoundException{
        s = new Scanner(cols);
        for(int i = 0; i < colos.length; i++){
            for(int j = 0; j < colos[i].length; j++){
                colos[i][j] = (short)Integer.parseInt(s.next().substring(2), 16);
            }
        }
        s.close();
    }

    public static short compare(short r, short g, short b){
        //muss einfach ein hoher Wert sein
        int tmpDif, lowestDif = 100000;
        short col = 0;
        //vergleicht den bekommenen Pixel auf Ähnlichkeiten mit den Pixeln des Arrays
        //der nächst liegende Pixel des Arrays wird bestimmt und durch diesen wird der Ursprüngliche ersetzt
        for(short i = 0; i < colos.length; i++){
            tmpDif =  (r - colos[i][0] >= 0 ? r - colos[i][0] : - (r - colos[i][0]))
                    + (g - colos[i][1] >= 0 ? g - colos[i][1] : - (g - colos[i][1]))
                    + (b - colos[i][2] >= 0 ? b - colos[i][2] : - (b - colos[i][2]));
            if(lowestDif >= tmpDif || lowestDif == 0){
                lowestDif = tmpDif;
                col = i;
            }
            if(tmpDif == 0){
                return i;
            }
        }
        return col;
    }

    public static short add(short a, short b){
        return (short)( a < b ? ( ( b << 4 ) | a ) : ( ( a << 4 ) | b) );//two 4bit numbers to one byte
    }

    //Komprimiert des Bild
    public static void conv(File in, File cols, String convertedPath) throws FileNotFoundException, SecurityException, UnsupportedEncodingException {
        //Scanner kann nicht "leer" initialisiert werden, deshalb System.in
        Scanner sc = new Scanner(System.in);
        getcols(sc, cols);

        //Hier wird der Scanner auf das eigentliche Bild gestellt
        sc = new Scanner(in);
        PrintWriter pw = new PrintWriter(new File(convertedPath));

        //löscht den Header einer PPM(P3) Datei (Länge,Breite des Bildes und max Farb Werte)
        String trashcan = sc.next() + sc.next() + sc.next() + sc.next();

        //Das ist schon die ganze Kompression des Bildes
        while(sc.hasNext()) {
            pw.print((char)(add(compare(sc.nextShort(), sc.nextShort(), sc.nextShort()),
                                compare(sc.nextShort(), sc.nextShort(), sc.nextShort()))));
        }

        //Schließen des Scanners und PrintWriters
        sc.close();
        pw.close();
    }

    //Debug
    public static int sum(short... toAdd){
        short returnSum = 0;
        for(short add : toAdd){
            returnSum += add;
        }
        return returnSum;
    }
}