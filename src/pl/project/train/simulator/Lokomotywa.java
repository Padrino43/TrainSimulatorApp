package pl.project.train.simulator;


import java.util.*;


public class Lokomotywa implements SiecElektryczna, Runnable {

    public static List<Lokomotywa> wszystkieLokomotywy;
    public static List<Lokomotywa> wszystkieLokomotywySortowanie;
    protected final String nazwa;


    protected StacjaKolejowa stacjaZrodlowa;
    protected StacjaKolejowa stacjaMacierzysta;
    protected StacjaKolejowa stacjaDocelowa;
    protected StacjaKolejowa nastepnaStacja;
    protected List<StacjaKolejowa> trasa;
    protected double aktualnaPrzebytaDroga;
    protected double drogaMiedzyStacjami;
    protected double drogaPrzebytaMiedzyStacjami;
    protected double calaDlugoscDrogi;
    private int i;


    private static int liczbaLokomotyw=0;
    protected int idLokomotywy;

    private final String numerBoczny;

    protected double predkoscLokomotywy;

    protected int licznikPrzekroczen;
    protected ArrayList<Wagon> wagony;

    protected int maksymalnyUciag;
    private int aktualnyUciag;
    protected int maksymalnaIloscWagonow;
    private int aktualnaIloscWagonow;
    protected int maksymalnaIloscWagonowElektrycznych;
    private int aktualnaIloscWagonowElektrycznych;


    public Lokomotywa(String nazwa, StacjaKolejowa stacjaZrodlowa, StacjaKolejowa stacjaDocelowa,int maksymalnyUciag,int maksymalnaIloscWagonow,int maksymalnaIloscWagonowElektrycznych) {
        this.numerBoczny="LC-"+liczbaLokomotyw;
        this.nazwa = nazwa;
        this.stacjaMacierzysta = stacjaZrodlowa;
        this.stacjaZrodlowa = stacjaZrodlowa;
        this.stacjaDocelowa = stacjaDocelowa;
        this.maksymalnyUciag = maksymalnyUciag;
        this.maksymalnaIloscWagonow = maksymalnaIloscWagonow;
        this.maksymalnaIloscWagonowElektrycznych = maksymalnaIloscWagonowElektrycznych;
        if(liczbaLokomotyw==0){
            wszystkieLokomotywy=new ArrayList<>();
            wszystkieLokomotywySortowanie=new ArrayList<>();
        }
        i=0;
        predkoscLokomotywy= 120.00;
        drogaPrzebytaMiedzyStacjami=0.0;
        aktualnyUciag = 0;
        aktualnaIloscWagonow = 0;
        aktualnaIloscWagonowElektrycznych = 0;
        licznikPrzekroczen=0;
        idLokomotywy=liczbaLokomotyw;
        wagony = new ArrayList<>();
        trasa = new ArrayList<>();
        liczbaLokomotyw++;
        wszystkieLokomotywy.add(this);
        wszystkieLokomotywySortowanie.add(this);
        utworzTrase();
    }

    private void speedChange () throws RailroadHazard, InterruptedException {
            if (predkoscLokomotywy > 200.00) licznikPrzekroczen++;
            else licznikPrzekroczen = 0;
            if (licznikPrzekroczen >= 5) {
                predkoscLokomotywy-=50.00;
                throw new RailroadHazard("\n(" + (predkoscLokomotywy+50) + ") Lokomotywa - zaraz sie wykolei!\n"
                        + numerBoczny + " skład:\n" + ((wagony.isEmpty()) ? " brak składu" : pokazWagony()));
            }
            if (predkoscLokomotywy < 30) predkoscLokomotywy += 20;
            float upOrDown = (float) Math.random() * 1;
            if (upOrDown < 0.5)
                this.predkoscLokomotywy +=(int) (predkoscLokomotywy * 0.03);
            else
                this.predkoscLokomotywy -=(int) (predkoscLokomotywy * 0.03);
    }
    public void dodajWagon(Wagon wagon, double wagaZaladunku) throws MaksymalnaIloscWagonowElekException,MaksUciagLocPrzekroczonyException,MaksIloscWagonowException {
                if (aktualnaIloscWagonow + 1 > maksymalnaIloscWagonow)
                    throw new MaksIloscWagonowException("Nie mozna dodac wagonu " + wagon.getClass().getSimpleName() + " - przekroczona maks ilosc: " + maksymalnaIloscWagonow);
                else if (aktualnyUciag + 1 > maksymalnyUciag)
                    throw new MaksUciagLocPrzekroczonyException("Nie mozna dodac wagonu " + wagon.getClass().getSimpleName() + " - przekroczony maks uciag: " + maksymalnyUciag);
                else if (aktualnaIloscWagonowElektrycznych + 1 > maksymalnaIloscWagonowElektrycznych)
                    throw new MaksymalnaIloscWagonowElekException("Nie mozna dodac wagonu " + wagon.getClass().getSimpleName() + " - przekroczona maks ilosc elek wagonow: " + aktualnaIloscWagonowElektrycznych);
                else {
                    wagon.zaladuj(wagaZaladunku);
                    wagony.add(wagon);
                    aktualnyUciag += wagon.getWagaBrutto();
                    aktualnaIloscWagonow += 1;
                    if (wagon instanceof SiecElektryczna)
                        aktualnaIloscWagonowElektrycznych += 1;
                    wagony.sort(Comparator.comparingDouble(Wagon::getWagaBrutto));
                }
    }
    public void usunWagon(Wagon wagon) {
        if(aktualnaIloscWagonow!=0)
        {
            if(wagony.contains(wagon))
            {
                aktualnyUciag-=wagon.getWagaBrutto();
                wagony.remove(wagon);
                aktualnaIloscWagonow--;
                if(wagon instanceof SiecElektryczna)
                    aktualnaIloscWagonowElektrycznych--;
                wagony.sort(Comparator.comparingDouble(Wagon::getWagaBrutto));
            }
        }
    }
    public String pokazWagony() {
        String tmp= "";
        for(Wagon wagon:wagony){
            tmp+=wagon+"\n";
        }
        return tmp;
    }
    public void raportPociagu() {
        String raport= "nazwa='" + nazwa + '\'' +
                ", stacja Zrodlowa/Aktualna/Docelowa='" + stacjaZrodlowa + "/" + stacjaMacierzysta + "/" + stacjaDocelowa + '\'' +
                ", uciag='" + aktualnyUciag + "/" + maksymalnyUciag + '\'' +
                ", wagony='" + aktualnaIloscWagonow + "/" + maksymalnaIloscWagonow + '\'' +
                ", wagony elektryczne='" + aktualnaIloscWagonowElektrycznych + "/" + maksymalnaIloscWagonowElektrycznych + '\'' +
                '}' + "\nWagony:";

        for (Object wagon : wagony)
            raport+=(wagon+"\n");
        raport+=("Procent drogi miedzy stacja startowa a docelowa: " +((aktualnaPrzebytaDroga/calaDlugoscDrogi)*100+" %\n"));
        raport+="\nProcent drogi miedzy najblizszymi stacjami:";
        if((trasa.indexOf(stacjaMacierzysta)+1)<trasa.size()) {
            raport += (100 - (drogaMiedzyStacjami / StacjaKolejowa.stacje.get(stacjaMacierzysta.id).polaczeniaDroga.get(trasa.get(trasa.indexOf(stacjaMacierzysta) + 1)) * 100) + "%");
        }
        else
            raport += (100 - (drogaMiedzyStacjami / StacjaKolejowa.stacje.get(stacjaMacierzysta.id).polaczeniaDroga.get(trasa.get(trasa.indexOf(stacjaDocelowa))) * 100) + "%");
        raport+="\n\n";
        System.out.println(raport);
    }
    public void pokazTrase() {
        System.out.print("Trasa "+numerBoczny+": ");
        for (StacjaKolejowa stacja: trasa)
        {
            System.out.print(StacjaKolejowa.stacje.get(stacja.id)+", ");
        }
        System.out.println();
    }
    private void utworzTrase() {
        if (trasa.isEmpty()) {
            trasa = zbudujTrase(stacjaZrodlowa, stacjaDocelowa);
            calaDlugoscDrogi=0;
            aktualnaPrzebytaDroga=0;
            i=0;
            for (int i = 0; i < trasa.size()-1; i++) {
                calaDlugoscDrogi+=StacjaKolejowa.stacje.get(trasa.get(i).id).polaczeniaDroga.get(trasa.get(i+1));
            }
        }
    }
    private static List<StacjaKolejowa> zbudujTrase(StacjaKolejowa zrodlowa, StacjaKolejowa docelowa) {
        List<StacjaKolejowa> trasaFinalna= new ArrayList<>();

        int[] dystans = new int[StacjaKolejowa.getCounter()];
        Arrays.fill(dystans, Integer.MAX_VALUE);
        int[] sasiedzi = new int[StacjaKolejowa.getCounter()];
        Arrays.fill(sasiedzi, -1);

        dystans[zrodlowa.id] = 0;

        PriorityQueue<Integer> kolejkaDystansu = new PriorityQueue<>(StacjaKolejowa.getCounter(), Comparator.comparingInt(o -> dystans[o]));
        kolejkaDystansu.offer(zrodlowa.id);

        while (!kolejkaDystansu.isEmpty()) {
            int u = kolejkaDystansu.poll();
            for (Map.Entry<StacjaKolejowa, Integer> stacja : StacjaKolejowa.getStacjaById(u).polaczeniaDroga.entrySet()) {
                int v = stacja.getKey().id;
                if (dystans[v] > dystans[u] + stacja.getValue()) {
                    dystans[v] = dystans[u] + stacja.getValue();
                    sasiedzi[v] = u;
                    kolejkaDystansu.offer(v);
                }
            }
        }

        if (sasiedzi[docelowa.id] == -1) {
            return trasaFinalna;
        }

        int temp = docelowa.id;
        while (temp != -1) {
            trasaFinalna.add(StacjaKolejowa.stacje.get(temp));
            temp = sasiedzi[temp];
        }
        Collections.reverse(trasaFinalna);
        return trasaFinalna;

    }
    public synchronized void obudz()
    {
        notify();
    }
    public int getI() {
        return i;
    }

    @Override
    public void run() {
       while(!Thread.interrupted()) {

            i=0;
            for (; i < trasa.size() - 1||trasa.isEmpty();) {
                try {
                    drogaMiedzyStacjami = StacjaKolejowa.stacje.get(stacjaMacierzysta.id).polaczeniaDroga.get(trasa.get(trasa.indexOf(stacjaMacierzysta)+1));
                    nastepnaStacja=trasa.get(trasa.indexOf(stacjaMacierzysta)+1);

                    synchronized (this){
                        if(StacjaKolejowa.stacje.get(trasa.get(i).id).zajeteTrasylista.contains(nastepnaStacja)) {
                            StacjaKolejowa.stacje.get(trasa.get(i + 1).id).oczekujacePociagi.add(this);
                            wait();
                        }
                    }

                    synchronized (trasa.get(i).zajeteTrasylista) {
                        StacjaKolejowa.stacje.get(trasa.get(i).id).zajmijTrase(this);
                    }

                    boolean dziala = true;
                    while (dziala) {
                        try {
                            speedChange();
                            if(drogaMiedzyStacjami-predkoscLokomotywy<0){
                                aktualnaPrzebytaDroga+=drogaMiedzyStacjami;
                                drogaMiedzyStacjami=0;
                            }
                            else {
                                drogaMiedzyStacjami -= predkoscLokomotywy;
                                aktualnaPrzebytaDroga += predkoscLokomotywy;}
                            Thread.sleep(1000);
                            if (drogaMiedzyStacjami <= 0) {
                                dziala = false;
                            }
                        } catch (RailroadHazard | InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    synchronized (trasa.get(i).zajeteTrasylista) {
                        StacjaKolejowa.stacje.get(trasa.get(i).id).zwolnijTrase(this);
                    }

                    stacjaMacierzysta = StacjaKolejowa.stacje.get(trasa.get(trasa.indexOf(stacjaMacierzysta)+1).id);
                    if (stacjaMacierzysta != stacjaDocelowa) {
                        Thread.sleep(2_000);
                    } else {
                        trasa.clear();
                        stacjaDocelowa=stacjaZrodlowa;
                        stacjaZrodlowa=stacjaMacierzysta;
                        utworzTrase();
                        Thread.sleep(30_000);
                    }
                } catch (InterruptedException  e) {
                    e.printStackTrace();
                }

                ++i;
            }




        }
    }
    @Override
    public void wyswietlInformacjeoDostawcy() {
        System.out.println(SiecElektryczna.dostawcaPradu);
    }

    @Override
    public void wyswietlInformacjeoNapieciu() {
        System.out.println(SiecElektryczna.napiecie);
    }


}
