public class LazyObj implements Comparable<Object>{

    //dieses Objekt existiert nur, weil ich den Algorythmus zuerst in C geschrieben hatte
    //und ich "Struktur-Objekte" verwendet hatten, welche sich nicht anders in Java übersetzen lassen

    //ein Objekt dieser Art hat einen Farbwert und eine Anzahl, wie oft er in dem Bild vorkommt
    public short r;
    public short g;
    public short b;
    public int num = 1;
    public LazyObj(short a, short b, short c){
        this.r = a;
        this.g = b;
        this.b = c;
    }

    public void inc(){num++;}

    @Override
    public int compareTo(Object o){
        LazyObj a = (LazyObj) o;
        return this.num - a.num;
    }
}
