package pl.project.train.simulator;

import java.util.*;

public class StacjaKolejowa{
    protected List<Lokomotywa> oczekujacePociagi;
    private static int counter = 0;
    protected int id;
    protected final String nazwaStacji;
    protected static List<StacjaKolejowa> stacje;
    protected HashMap<StacjaKolejowa, Integer> polaczeniaDroga;
    protected List<StacjaKolejowa> zajeteTrasylista;


    public StacjaKolejowa(String nazwaStacji){
        this.nazwaStacji = nazwaStacji;
        id = counter;
        if (counter == 0) {stacje = new ArrayList<>();}
            polaczeniaDroga = new HashMap<>();
            oczekujacePociagi= new ArrayList<>();
        zajeteTrasylista=new ArrayList<>();
        stacje.add(this);
        counter++;
    }
    public void dodajPolaczenie(StacjaKolejowa stacja, int czasDojazdu) {
        if(!polaczeniaDroga.containsKey(stacja)&&stacja!=this){
        polaczeniaDroga.put(stacja, czasDojazdu);
        stacja.polaczeniaDroga.put(this, czasDojazdu);
        }
    }
    public static void pokazWszystkiePolaczenia() {
        for (StacjaKolejowa stacja : stacje) {
            System.out.print(stacja.nazwaStacji+" -> ");
            for (Map.Entry<StacjaKolejowa, Integer> stacja1 : stacja.polaczeniaDroga.entrySet()) {
            System.out.print( StacjaKolejowa.stacje.get(stacja1.getKey().id) + " w czasie " + stacja1.getValue()+", ");}
            System.out.println();

        }
        System.out.println();

    }
    public static void pokazWszystkieStacje() {
        for (StacjaKolejowa stacja : stacje) {
            System.out.print(stacja.nazwaStacji+" - "+ stacja.id+", ");
        }
        System.out.println();
    }
    protected static StacjaKolejowa getStacjaById(int u) {
        return stacje.get(u);
    }
    public synchronized void zajmijTrase(Lokomotywa lokomotywa) {
                    synchronized (zajeteTrasylista) {
                        StacjaKolejowa stacjaZrodlowa = StacjaKolejowa.stacje.get(lokomotywa.trasa.get(lokomotywa.getI()).id);
                        StacjaKolejowa stacjaNastepna = StacjaKolejowa.stacje.get(lokomotywa.nastepnaStacja.id);
                        zajeteTrasylista.add(stacjaNastepna);
                        StacjaKolejowa.stacje.get(stacjaNastepna.id).zajeteTrasylista.add(stacjaZrodlowa);
                    }

    }
    public void zwolnijTrase(Lokomotywa lokomotywa) {
        synchronized (zajeteTrasylista) {
            StacjaKolejowa stacjaZrodlowa = StacjaKolejowa.stacje.get(lokomotywa.trasa.get(lokomotywa.getI()).id);
            StacjaKolejowa stacjaNastepna = StacjaKolejowa.stacje.get(lokomotywa.nastepnaStacja.id);
            zajeteTrasylista.remove(stacjaNastepna);
            StacjaKolejowa.stacje.get(stacjaNastepna.id).zajeteTrasylista.remove(stacjaZrodlowa);
        }
            if (!oczekujacePociagi.isEmpty()) {
                Lokomotywa tmp = oczekujacePociagi.get(0);
                oczekujacePociagi.remove(0);
                synchronized (tmp) {
                    tmp.obudz();
                }
        }

    }

    public static int getCounter() {
        return counter;
    }

    public static List<StacjaKolejowa> getStacjeKolejoweLista() {
        return stacje;
    }

    public HashMap<StacjaKolejowa, Integer> getPolaczenie() {
        return polaczeniaDroga;
    }

    @Override
    public String toString() {
        return nazwaStacji;
    }


}

