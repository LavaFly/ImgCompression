import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Manager {
    public CTRLGui Gui;
    public static String sourcePath;
    public static String colsPath;
    public static String convPath;
    public static int width, height;
    public Manager(CTRLGui ctrl){
        Gui = ctrl;
    }
    public void compression(File sourcePic){
        double border = 0.9;
        String fileName = sourcePic.getName().replaceFirst(".ppm","");
        colsPath = String.format("colors_of_%s.txt", fileName);
        convPath = String.format("compressed_%s.txt", fileName);
        try {
            getSizeOfPicture(sourcePic);

            //Die train Methode ermittelt den Wert für die Kompression
            int param = Trainer.train(sourcePic, border);

            //Ermittelt die relevanten Farbwerte des Bildes
            Evaluater.printcols(sourcePic, colsPath, param);

            //komprimiert dar Bild aus sourcePath mit Farben von colsPath und schreibt in convPath
            Converter.conv(sourcePic, new File(colsPath), convPath);

            //es würde reichen alle Fehler einfach nur als Exception zu fangen, die Aufteilung vereinfacht aber das debuggen
        } catch (FileNotFoundException f) {
            System.out.println("Error 1");
        } catch (UnsupportedEncodingException u) {
            System.out.println("Error 2");
        } catch (SecurityException s) {
            System.out.println("Error 3");
        } catch (Exception e) {
            System.out.println("Unusual/Unexpected Error:\n" + e);
        }
    }

    public static void getSizeOfPicture(File f) throws FileNotFoundException{
        Scanner in = new Scanner(f);
        String trashcan = in.next();
        width = in.nextInt();
        height = in.nextInt();

        in.close();
    }



    //DELETE LATER
    //ONLY FOR BENCHMARK
    public static void manage(String s, double brd){
        double border = brd;

        sourcePath = String.format("MassConversion/testpics/%s.ppm", s);
        colsPath = String.format("MassConversion/colorcodes/%s_color.txt", s);
        convPath = String.format("MassConversion/compressed/%s_compressed.txt", s);

        try{
            getSizeOfPicture(new File(sourcePath));
            int param = Trainer.train(new File(sourcePath), border);
            Evaluater.printcols(new File(sourcePath), colsPath, param);
            Converter.conv(new File(sourcePath), new File(colsPath), convPath);
        } catch(Exception e){
            System.out.println(e);
        }
    }

    //Debug
    public static void checkArgsList(String[] args) {
        if (args[0] != "") {
            sourcePath = String.format("%s.txt", args[0]);
            colsPath = String.format("%s_colors.txt", args[0]);
            convPath = String.format("%s_compressed.txt", args[0]);
        } else {
            sourcePath = "yell.ppm";
            colsPath = "yell_colors.txt";
            convPath = "yell_compressed.txt";
        }
    }
    /**
    public static void main(String[] args) {

         try {
         FilePrep.revert(new File("/home/linux/CleanPictureInterpeter/protoype.txt"));
         } catch(Exception e){
         System.out.println(e);
         }
        if(false){
            //Die Grenze für die Anzahl als Prozent an Pixel, welche mindestens von den Farben vertreten werden sollten
            double border = 0.9;

            //Pfade für die jeweiligen vorhandenen oder noch zu erstellenen Dateien
            //checkArgsList(args);
            /**
             sourcePath = "yell.ppm";
             colsPath = "yell_colors.txt";
             convPath = "yell_compressed.txt";
            sourcePath = "picTest.ppm";
            colsPath = "picone_cols.txt";
            convPath = "onepic.txt";

            try {
                getSizeOfPicture(new File(sourcePath));

                //Die train Methode ermittelt den Wert für die Kompression
                int param = Trainer.train(new File(sourcePath), border);

                //Ermittelt die relevanten Farbwerte des Bildes
                Evaluater.printcols(new File(sourcePath), colsPath, param);

                //komprimiert dar Bild aus sourcePath mit Farben von colsPath und schreibt in convPath
                Converter.conv(new File(sourcePath), new File(colsPath), convPath);

                //Inpretiert die Datei bzw zeigt das Bild an
                Interpeter.showPicture(convPath);

                //es würde reichen alle Fehler einfach nur als Exception zu fangen, die Aufteilung vereinfacht aber das debuggen
            } catch (FileNotFoundException f) {
                System.out.println("Error 1");
            } catch (UnsupportedEncodingException u) {
                System.out.println("Error 2");
            } catch (SecurityException s) {
                System.out.println("Error 3");
            } catch (Exception e) {
                System.out.println("Unusual/Unexpected Error:\n" + e);
            }
        }
    }
    **/
}
