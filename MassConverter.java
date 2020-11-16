import java.io.BufferedReader;
import java.io.InputStreamReader;
public class MassConverter {
    //BENCHMARK
    //komprimiert einen Ordner und misst die Zeit
   public static void not_in_use(String[] args){
           long timeStart = System.currentTimeMillis();

           ProcessBuilder Pb = new ProcessBuilder();
           String cmd = "ls MassConversion/testpics | sed -e 's/\\.ppm$//'"; //bash-Befehl um alle Namen der Bilder in dem Ordner zu bekommen
           Pb.command("bash", "-c", cmd);
           try {
               Process p = Pb.start();
               BufferedReader redr = new BufferedReader(new InputStreamReader(p.getInputStream()));
               String l;
               String[] params = new String[40];
               byte b = 0;
               while ((l = redr.readLine()) != null) {
                   params[b] = l;
                   b++;
               }
               int c = 0;
               while (params[c] != null) {
                   System.out.println("Hey im still alive!");
                   Manager.manage(params[c], (double) 0.85);
                   DeEspresso.depressing(params[c]);
                   c++;
               }
           } catch (Exception e) {
               System.out.println(e);
           }
           long alltime = (System.currentTimeMillis() - timeStart);
           //gibt zuerst Zeit in Sekunden an, der Rest ist die Umrechnung der Sekunden in Stunden/Minuten/Sekunden
           System.out.println(String.format("This took %ds -> %02dh%02dm%02ds", alltime / 1000, ( ( alltime / 1000 ) / 3600), ( ( ( alltime / 1000 ) % 3600 ) / 60 ), ( ( alltime / 1000 ) / 60 ) ) );
           //System.out.println(String.format("Evaluater.doStuff() was called %d times", Evaluater.count));

   }
}
