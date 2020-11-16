import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Evaluater {
    //prüft einen Farbwert auf Ähnlichkeiten aus der Liste, fügt ihn hinzu bei fehlen von diesen
    public static void checkList(short dif, short one, short two, short three, ArrayList<LazyObj> a){
        boolean b = true;
        if(a.isEmpty()){
            a.add(new LazyObj(one, two, three));
        } else {
            for(int i = 0;i < a.size();i++){
                if(inRange(a.get(i).r, one, dif) && inRange(a.get(i).g, two, dif) && inRange(a.get(i).b, three, dif)){
                    a.get(i).inc();
                    b = false;
                    break;
                }
            }
            if(b) a.add(new LazyObj(one, two, three));
        }
    }

    //prüft, ob eine Zahl im Invervall [a-maxDif, a+maxDif] ist
    public static boolean inRange(short a, short b, short maxDif){
        if((a <= b ? b - a : a - b) <= maxDif) return true;
        return false;
    }

    //sucht nach den relevantesten Farben in einem Bild
    public static int doStuff(File f, int para) throws FileNotFoundException{
        Scanner s = new Scanner(f);
        ArrayList<LazyObj> all = new ArrayList<>();
        String trashcan = s.next() + s.next() + s.next() + s.next();
        //int height = 600, width = 800;
        int height = Manager.height, width = Manager.width;
        for(int i = 0;i < height; i++){
            for(int j = 0;j < width; j++){

                //prüft ob die Farbwerte auf ihre Differenz mit den Werten der Liste
                checkList((short)para, s.nextShort(), s.nextShort(), s.nextShort(), all);
            }
        }

        //Ich hatte zuerst einen QuickSort verwendet, es gibt allerdings schon Sortieralgorythmen für Arraylisten
        Collections.sort(all);

        //die Funktion braucht einen Lambdaausdruck, ich will aber nichts damit machen, also bleibt er leer
        all.forEach(LazyObj ->{;});

        //gibt die Summe der 16 relevantesten Pixel zurück, welche sie innerhalb einer gewissen Differenz abdecken
        int sum = 0;
        for(int i = all.size()-1;i>=(all.size() >= 16 ? all.size()-16 : all.size()); i--){
            sum += all.get(i).num;
        }

        //schließt den Scanner und gibt die Summe zurück
        s.close();
        return sum;
    }

    //erstellt die Datei, mit den 16 relevantesten Farben des Bildes
    public static void printcols(File f,String colorPath, int param) throws FileNotFoundException, SecurityException, UnsupportedEncodingException {

        //von hier
        Scanner s = new Scanner(f);
        PrintWriter pw = new PrintWriter(new File(colorPath));
        ArrayList<LazyObj> all = new ArrayList<>();
        String trashcan = s.next() + s.next() + s.next() + s.next();
        int height = Manager.height, width = Manager.width;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                checkList((short)param, s.nextShort(), s.nextShort(), s.nextShort(), all);
            }
        }
        Collections.sort(all);
        all.forEach(LazyObj ->{;});
        //bis hier, hab ich schon alles erklärt

        //schreibt die Werte in die Datei, da ich printf aus C inzwischen sehr gewohnt bin, verwende ich in Java das fast identische String.format
        for(int i = all.size()-1;i>=(all.size() >= 16 ? all.size()-16 : all.size()); i--){
            //pw.println(String.format("%d\t%d\t%d", all.get(i).r, all.get(i).g, all.get(i).b));
            pw.println(String.format("%#x\t%#x\t%#x", all.get(i).r, all.get(i).g, all.get(i).b));
        }
        //schreibt noch die Breite und Höhe dazu
        pw.println(String.format("%d %d", width, height));

        //schließt PrintWriter und Scanner
        pw.close();
        s.close();
    }
}
