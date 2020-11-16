import java.io.File;
import java.io.FileNotFoundException;

public class Trainer {
    //ich möchte, dass die Farben der Pixel des komprimierten Bildes ein gewisses Verhältnis der Farben des
    //ursprünglichen Bildes abdecken, das Verhältnis wird in Prozent durch den Parameter border bestimmt
    public static int train(File f, double border) throws FileNotFoundException {
        //wirklich wirklich coole Methode

        //ich teste Werte für die Intervalle der doStuff-Methode
        //die Methode gibt die Summe an Pixeln zurück, welche sich durch 16 Farben mit einer gewissen Genauigkeit
        //in dem Bild ersetzen lassen

        //der erste Wert ist für den Parameter in der doStuff-Methode ist 1
        //der dif Wert gibt die spätere Differenz, zwischen r und lr ( = vorheriges r )
        short dif = 0x10, r = 1, lr;

        //val ist der Pozentwert an Pixeln, welchen die doStuff-Methode zurück gibt
        //bval = vorheriger val Wert, bbv = der Wert vor dem vorherigen...
        double val = 0, bval = 0, bbval = 0, bbbval;
        while(true){
            bbbval = bbval;
            bbval = bval;
            bval = val;

            val = (double)((Evaluater.doStuff(f, r)) / ((double)Manager.width * Manager.height));

            //lr ist der v
            lr = r;

            //wenn der bekommene Wert für val noch unter der Grenze ist, wird dif addiert
            if(val <= border) r += dif;
            else{
                //wenn der Wert über der Grenze ist, wird dif wieder von r abgezogen, da r zu groß war
                r -= dif;

                //dif wird halbiert, solange es nicht bereits den Wert 1 hat
                dif = (short)(dif != 1 ? dif >> 1 : dif);
            }

            //Da ich weiß, dass wenn z.B. r=15 zu wenige Pixel abdeckt, brauch ich nicht jeden Wert unter 15 untersuchen
            //da diese automatisch auch zu wenige abdecken werden. Also addiere ich zuerst 1, dann 1+16, dann 17+16...
            //bis ich merke, dass ich zu weit gegangen bin, geh ein Schritt zurück und addiere nur 16/2, im nächsten Durchlauf
            //teste ich wieder, ob ich zu weit bin und teile der Wert gegebenfalls wieder. Dies wiederholt sich, bis mein Wert
            //für r und dem vorherigen r sich nur um 1 unterscheiden, weshalb der eine Wert der kleinst mögliche Parameter
            //für die doStuff-Methode ist, welche einen Wert zurückgibt, der minimal zu groß ist.
            //Der Wert lr ist damit der größtmögliche Wert für r, damit die Methode einen Wert zurückgibt, der maximal zu klein ist.
            //Die Werte lr und r würden sich bis in die Ewigkeit abwechseln, weshalb ich zur Lösung einfach erkennen muss,
            //dass sich ich mich in einer wiederholenden Schleife befinde


            //schaut, ob ich mich in einer Schleife befinde
            if(bbval == val && bbbval == bval)break;
        }

        return lr;
    }
}