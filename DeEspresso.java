import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;
public class DeEspresso {
    public static short[][] colos = new short[16][3];
    public static void preview(File compPic, File colors){
        try{
            //Scanner wird nur auf System.in gesetzt, weil er irgendein Parameter zum initialisieren braucht
            Scanner in = new Scanner(System.in);
            PrintWriter pw = new PrintWriter(new File("not_" + compPic.getName().replace(".txt", "") + ".ppm"));
            getcols(in,colors);
            short[] a = new short[2];
            int height = Manager.height, width = Manager.width/2;

            BufferedReader red = new BufferedReader(new InputStreamReader(new FileInputStream(compPic), Charset.forName("UTF-8")));
            pw.print(String.format("P3\n" +
                            "%d %d\n" +
                            "255\n",
                    width*2, height));
            for(int i = 0;i<height;i++){
                for(int j = 0;j<width;j++){
                    a = divide((short) ((char) red.read()));
                    pw.print(String.format("%d %d %d ", colos[a[0]][0], colos[a[0]][1], colos[a[0]][2]));
                    pw.print(String.format("%d %d %d ", colos[a[1]][0], colos[a[1]][1], colos[a[1]][2]));
                    if(j%16==0) pw.print("\n");
                }
            }
            pw.close();
            red.close();

        } catch(Exception e){
            System.out.println(e);//ist nicht besonders verantwortlich, aber ich erwarte einfach keine Fehler
        }
    }

    public static void getcols(Scanner s, File cols) throws FileNotFoundException{
        s = new Scanner(cols);
        for(int i = 0; i < colos.length; i++){
            for(int j = 0; j < colos[i].length; j++){
                colos[i][j] = (short)Integer.parseInt(s.next().substring(2), 16);
            }
        }
        s.close();
    }
    //die Methode teilt ein Byte in zwei Hälften(Nibble)
    public static short[] divide(short a){
        short[] s = new short[2];
        s[0] = (short)( a & 0x0F );
        s[1] = (short)( a >> 4);
        return s;
    }

    //DEBUG

    /**
     public static void main(String[] agrs){
     try{
     Scanner in = new Scanner(System.in);
     PrintWriter pw = new PrintWriter(new File("piconecomp.ppm"));
     getcols(in,new File("picone_cols.txt"));
     short[] a = new short[2];
     int height = 1024, width = 346;
     //int height = Manager.height, width = Manager.width/2;

     BufferedReader red = new BufferedReader(new InputStreamReader(new FileInputStream("onepic.txt"), Charset.forName("UTF-8")));
     pw.print(String.format("P3\n" +
     "%d %d\n" +
     "255\n",
     width, height));
     for(int i = 0;i<height;i++){
     for(int j = 0;j<width;j++){
     a = divide((short) ((char) red.read()));
     pw.print(String.format("%d %d %d ", colos[a[0]][0], colos[a[0]][1], colos[a[0]][2]));
     pw.print(String.format("%d %d %d ", colos[a[1]][0], colos[a[1]][1], colos[a[1]][2]));
     if(j%16==0) pw.print("\n");
     }
     }
     pw.close();
     red.close();

     } catch(Exception e){
     System.out.println(e);
     }
     }

     **/

    //DELETE LATER
    //ONLY FOR BENCHMARK
    public static void depressing(String s){
        try{
            Scanner in = new Scanner(System.in);
            PrintWriter pw = new PrintWriter(new File(String.format("MassConversion/depressed/%s_depressed.ppm", s)));
            getcols(in,new File(String.format("MassConversion/colorcodes/%s_color.txt", s)));
            short[] a = new short[2];
            int height = Manager.height, width = Manager.width/2;

            BufferedReader red = new BufferedReader(new InputStreamReader(new FileInputStream(String.format("MassConversion/compressed/%s_compressed.txt", s)), Charset.forName("UTF-8")));
            pw.print(String.format("P3\n" +
                            "%d %d\n" +
                            "255\n",
                    width*2, height));
            for(int i = 0;i<height;i++){
                for(int j = 0;j<width;j++){
                    a = divide((short) ((char) red.read()));
                    pw.print(String.format("%d %d %d ", colos[a[0]][0], colos[a[0]][1], colos[a[0]][2]));
                    pw.print(String.format("%d %d %d ", colos[a[1]][0], colos[a[1]][1], colos[a[1]][2]));
                    if(j%16==0) pw.print("\n");
                }
            }
            pw.close();
            red.close();

        } catch(Exception e){
            System.out.println(e);
        }

    }
}
