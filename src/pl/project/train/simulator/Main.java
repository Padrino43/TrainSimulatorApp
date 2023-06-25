package pl.project.train.simulator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    static ArrayList<Wagon> tempWagony = new ArrayList<>();
    static ArrayList<Wagon> przykladoweWagony = new ArrayList<>();
    static boolean odpalonaSymulacja=false;
    static int s=0;
    static int l=0;
    static String[] tab ={"ABC Inc.", "XYZ Corporation", "Smith and Sons", "Johnson & Johnson", "Acme Industries", "Big Bank",
            "Global Enterprises", "Tech Solutions", "Innovative Minds", "Peak Performance", "Dynamic Data", "Red Rock Software",
            "Globex Corporation", "Swift Logistics", "White Whale Inc."};
    public static void main(String[] args) {
        new Thread(()->{
            try{

                stworzStacje();

                Thread.sleep(4000);
                stworzPociagi();
                Thread.sleep(2000);
            } catch (MaksUciagLocPrzekroczonyException | MaksymalnaIloscWagonowElekException |
                     MaksIloscWagonowException | InterruptedException e) {
                return;
            }
        }).start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            return;
        }
        MENU();

    }
    public static void MENU() {
        System.out.println("WITAMY W SYMULATORZE KOLEI");
            Scanner in=new Scanner(System.in);
            boolean dziala=true;
            boolean dziala1=true;
            Lokomotywa locoTemp;
        while(dziala)
        {
            try {
                System.out.println("(1) LOKOMOTYWA\n(2) WAGON\n(3) STACJA_KOLEJOWA\n(4) URUCHOM_SYMULACJE\n(0) WYJSCIE");
                locoTemp = null;
                switch (in.nextInt()) {
                    case 1 -> {dziala1=true;
                        while(dziala1){
                            System.out.println("(1) UTWORZ_LOKOMOTYWE\n(2) WYBIERZ_LOKOMOTYWE\n(3) DODAJ WAGON\n(4) USUN WAGON\n" +
                                    "(5) ZALADUJ/ROZLADUJ WAGON\n(6) WYSWIETL WAGONY\n(7) USUN LOKOMOTYWE\n" +
                                    "(8) WYSWIETL RAPORT\n(9) WYSWIETL TRASE\n(0) WROC\n");
                            switch (in.nextInt()) {
                            case 1 -> {
                                stworzLokomotywe();
                                System.out.println("Stworzono lokomotywe");
                            }
                            case 2 -> {
                                    System.out.println("Wybierz indeks lokomotywy");
                                    int i = 0;
                                    for (Lokomotywa e :
                                            Lokomotywa.wszystkieLokomotywy) {
                                        System.out.print("(" + i++ + ")" +e.nazwa + ", ");
                                    }
                                    System.out.println();
                                    int n = in.nextInt();
                                    if (Lokomotywa.wszystkieLokomotywy.size() > n && 0 <= n) {
                                        locoTemp = Lokomotywa.wszystkieLokomotywy.get(n);
                                        System.out.println("Wybrano lokomotywe");}

                            }
                            case 3 -> {
                                if (!tempWagony.isEmpty() && locoTemp!=null) {
                                    System.out.println("Wybierz indeks wagonu");
                                    int i = 0;
                                    for (Wagon e :
                                            tempWagony) {
                                        System.out.print("(" + i++ + ")" + e + "\n");
                                    }
                                    int n = in.nextInt();
                                    System.out.println("Podaj wage zaladunku(double)");
                                    if (tempWagony.size() > n && 0 <= n) {
                                        locoTemp.dodajWagon(tempWagony.get(n), in.nextDouble());
                                        tempWagony.remove(n);
                                        System.out.println("Dodano wagon");

                                    } else System.out.println("Podano wagon poza przedzialem");
                                }else {
                                System.out.println("Nie wybrano lokomotywy lub brak wagonow do dodania");}
                            }
                            case 4 -> {
                                if (locoTemp!=null) {
                                    System.out.println("Wybierz indeks wagonu");
                                    int i = 0;
                                    for (Wagon e :
                                            locoTemp.wagony) {
                                        System.out.print("(" + i++ + ")" + e + "\n");
                                    }
                                    int n = in.nextInt();
                                    if (locoTemp.wagony.size() > n && 0 <= n)
                                        locoTemp.usunWagon(locoTemp.wagony.get(n));
                                    else System.out.println("Lokomotywa nie ma wagonow");
                                    } else System.out.println("Nie wybrano lokomotywy");

                            }
                            case 5 -> {
                                if (locoTemp!=null) {
                                    System.out.println("Wybierz indeks wagonu");
                                    int i = 0;
                                    for (Wagon e :
                                            locoTemp.wagony) {
                                        System.out.print("(" + i++ + ")" + e + "\n");
                                    }
                                    int n = in.nextInt();
                                    if (locoTemp.wagony.size() > n && 0 <= n) {
                                        if(locoTemp.wagony.get(n).wagaBrutto<locoTemp.wagony.get(n).maksWagaBrutto){
                                        System.out.println("Podaj wage zaladunku(double)");
                                        locoTemp.wagony.get(n).zaladuj(in.nextDouble());}
                                        else {
                                            System.out.println("Rozladowano wagon");
                                            locoTemp.wagony.get(n).rozladuj();
                                        }

                                    } else System.out.println("Podano wagon poza przedzialem");
                                }
                                else {
                                System.out.println("Nie wybrano lokomotywy");}
                            }
                            case 6 -> {
                                if (locoTemp!=null) {
                                    if(!locoTemp.wagony.isEmpty()){
                                        int i = 0;
                                        for (Wagon e :
                                                locoTemp.wagony) {
                                            System.out.print("(" + i++ + ")" + e + "\n");
                                        }
                                    }
                                    else System.out.println("Lokomotywa nie posiada wagonow");
                                }else System.out.println("Nie wybrano lokomotywy");
                            }
                            case 7 -> {
                                if (locoTemp != null) {
                                    System.out.println("Wybierz indeks lokomotywy");
                                    int i = 0;
                                    for (Lokomotywa e :
                                            Lokomotywa.wszystkieLokomotywy) {
                                        System.out.print("(" + i++ + ")" + e.nazwa + ", ");
                                    }
                                    int n = in.nextInt();
                                    if (Lokomotywa.wszystkieLokomotywy.size() > n && 0 <= n){
                                        Lokomotywa.wszystkieLokomotywy.remove(n);
                                        Lokomotywa.wszystkieLokomotywySortowanie.remove(n);
                                    System.out.println("Usunieto lokomotywe");}
                                } else System.out.println("Nie wybrano lokomotywy");
                            }
                            case 8 -> {
                                if (locoTemp != null) {
                                        Lokomotywa.wszystkieLokomotywy.get(locoTemp.idLokomotywy).raportPociagu();
                                } else System.out.println("Nie wybrano lokomotywy");

                            }
                            case 9 ->{
                                if (locoTemp != null) {
                                    Lokomotywa.wszystkieLokomotywy.get(locoTemp.idLokomotywy).pokazTrase();
                                } else System.out.println("Nie wybrano lokomotywy");
                            }
                            case 0 ->{dziala1=false;}
                        }
                            System.out.println();}
                    }
                    case 2 -> {
                        dziala1=true;
                            while(dziala1){
                                System.out.println("(1) UTWORZ_WAGON\n(2) USUN_WAGON\n(0) WYJSCIE");
                                switch(in.nextInt())
                                {
                                    case 1 ->{
                                        stworzWagon();
                                    }
                                    case 2->{
                                        System.out.println("Wybierz indeks wagon");
                                        int i = 0;
                                        for (Wagon e :
                                                tempWagony) {
                                            System.out.print("(" + i++ + ")" +e.getClass().getSimpleName() + ", ");
                                        }
                                        System.out.println();
                                        int n = in.nextInt();
                                        if (tempWagony.size() > n && 0 <= n) {
                                            tempWagony.remove(n);
                                            System.out.println("Usunieto wagon");}
                                    }
                                    case 0->{
                                        dziala1=false;
                                    }
                                }

                            }
                    }
                    case 3 -> {
                        dziala1=true;
                        while(dziala1){
                            System.out.println("(1) UTWORZ_STACJE\n(2) USUN_STACJE\n(3) DODAJ_POLACZENIE\n(4) POKAZ_STACJE\n(5) POKAZ_POLACZENIA\n(0) WYJSCIE");
                            switch(in.nextInt())
                            {
                                case 1 ->{
                                    stworzStacjeKolejowa();
                                }
                                case 2->{
                                    System.out.println("Wybierz indeks stacji do usuniecia");
                                    int i = 0;
                                    for (StacjaKolejowa e :
                                            StacjaKolejowa.stacje) {
                                        System.out.print("(" + i++ + ")" +e.nazwaStacji + ", ");
                                    }
                                    System.out.println();
                                    int n = in.nextInt();
                                    if (StacjaKolejowa.stacje.size() > n && 0 <= n) {
                                        StacjaKolejowa.stacje.remove(n);
                                        System.out.println("Usunieto stacje");}
                                }
                                case 3->{
                                    System.out.println("Wybierz kolejno indeksy stacji do polaczenia");
                                    int i = 0;
                                    for (StacjaKolejowa e :
                                            StacjaKolejowa.stacje) {
                                        System.out.print("(" + i++ + ")" +e.nazwaStacji + ", ");
                                    }
                                    System.out.println();
                                    int n = in.nextInt();
                                    int k =in.nextInt();
                                    if ((StacjaKolejowa.stacje.size() > n && 0 <= n)&&n!=k&&(StacjaKolejowa.stacje.size() > k && 0 <= k)) {
                                        StacjaKolejowa.stacje.get(n).dodajPolaczenie(StacjaKolejowa.stacje.get(k),(int) (Math.random() * 9000) + 1000);
                                    }
                                    else System.out.println("Bledny wybor stacji");
                                }
                                case 4 ->{
                                    StacjaKolejowa.pokazWszystkieStacje();
                                }
                                case 5 ->{
                                    StacjaKolejowa.pokazWszystkiePolaczenia();
                                }
                                case 0->{
                                    dziala1=false;
                                }
                            }

                        }
                    }
                    case 4 -> {
                        if(!odpalonaSymulacja){
                        symulacja();
                        System.out.println("Symulacja wystartowala");}
                        else System.out.println("Symulacja juz dziala!");
                    }
                    case 0 -> {
                        if (odpalonaSymulacja)
                            symulacja();
                        dziala = false;
                        System.out.println("WYJSCIE");
                        System.exit(0);
                    }
                    default -> {
                    }
                }
            }catch (MaksymalnaIloscWagonowElekException | MaksUciagLocPrzekroczonyException | MaksIloscWagonowException e)
            {
                System.out.println(e.getMessage());
            }
        }

    }
    public static void stworzStacje(){
        for (; s < 100; s++) {
            new StacjaKolejowa("Stacja"+s);
        }
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10; j++) {
                    StacjaKolejowa sk = StacjaKolejowa.stacje.get((int)(Math.random()*StacjaKolejowa.stacje.size()));
                    StacjaKolejowa.stacje.get(i).dodajPolaczenie(sk, (int) (Math.random() * 10_000) + 5_000);
                }
            }
        }
    public static void stworzPociagi() throws MaksUciagLocPrzekroczonyException, MaksymalnaIloscWagonowElekException, MaksIloscWagonowException {
        for (; l < 25; l++) {
            new Lokomotywa("A" + l, StacjaKolejowa.stacje.get((int) (Math.random() * 100)), StacjaKolejowa.stacje.get((int) (Math.random() * 100)),
                    (int) (Math.random() * 90_000_000) + 10_000_000, (int) (Math.random() * 10) + 10,
                    (int) (Math.random() * 5) + 5);
            int n = (int) ((Math.random() * 5) +5);
            for (int j = 0; j < n; j++) {
                Lokomotywa.wszystkieLokomotywy.get(l).dodajWagon(losujWagon(), Math.random()*30_000+20_000);
            }
        }
        System.out.println();
        zapisDoPliku();
    }
    public static void symulacja() {
        if(!odpalonaSymulacja){
            for (Lokomotywa loco:Lokomotywa.wszystkieLokomotywy) {
                new Thread(loco).start();
            }
            odpalonaSymulacja=true;}
    }
    public static void stworzLokomotywe() {
        new Thread(new Lokomotywa("A"+(l++),
                StacjaKolejowa.stacje.get((int)(Math.random()*StacjaKolejowa.stacje.size())),
                StacjaKolejowa.stacje.get((int)(Math.random()*StacjaKolejowa.stacje.size())),
                ((int)(Math.random()*40_000)+50_000),
                ((int) (Math.random() * 10) + 10),
                ((int) (Math.random() * 5) + 5)
        )).start();
    }
    public static Wagon losujWagon() {
        switch((int)(Math.random()*10)+1)
        {
            case 1->{
                return new wPasazerski((Math.random()*10_000)+5_000,
                        (Math.random()*30_000)+20_000,(int)(Math.random()*300),
                        wPasazerski.generateKlimatyzacja(),new Random().nextBoolean());
            }
            case 2->{
                return new wPocztowy((Math.random()*10_000)+5_000,
                        (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],
                        Wagon.generateZabezpieczenia(),(int)(Math.random()*90)+10,
                        (int)(Math.random()*300),new Random().nextBoolean());
            }
            case 3->{
                return new wBagazowoPocztowy((Math.random()*10_000)+5_000,
                        (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],
                        (int)(Math.random()*300),new Random().nextBoolean(),
                        Wagon.generateZabezpieczenia());
            }
            case 4->{
                return new wRestauracyjny((Math.random()*10_000)+5_000,
                        (Math.random()*30_000)+20_000,(int)(Math.random()*100),
                        (int)(Math.random()*300),new Random().nextBoolean(),
                        new Random().nextBoolean());
            }
            case 5->{
                return new wTowarowyP((Math.random()*10_000)+5_000,
                        (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],
                        wTowarowyP.generateRodzajTowaru(),wTowarowyP.generateDrzwi(),
                        wTowarowyP.generateKolor());
            }
            case 6->{
                return new wTowarowyC((Math.random()*10_000)+5_000,
                        (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyC.generateRodzajCiezkiegoTowaru());
            }
            case 7->{
                return new wChlodniczy((Math.random()*10_000)+5_000,
                        (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyP.generateRodzajTowaru(),
                        wChlodniczy.generateRodzajSpozywki(),wTowarowyP.generateDrzwi(), wTowarowyP.generateKolor());
            }
            case 8->{
                return new wMaterialyCiekle((Math.random()*10_000)+5_000,
                        (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyP.generateRodzajTowaru(),
                        wTowarowyP.generateDrzwi(), wTowarowyP.generateKolor(), new Random().nextBoolean());
            }
            case 9->{
                return new wMaterialyGazowe((Math.random()*10_000)+5_000,
                        (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyP.generateRodzajTowaru(),
                        wTowarowyP.generateDrzwi(), wTowarowyP.generateKolor(), (Math.random()*2)+1,wMaterialyGazowe.generateRodzajGazu());
            }
            case 10->{
                return new wMaterialyWybuchowe((Math.random()*10_000)+5_000,
                        (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyC.generateRodzajCiezkiegoTowaru(),
                        wMaterialyWybuchowe.generateRodzajMateriału());
            }
            case 11->{
                return new wMaterialyToksyczne((Math.random()*10_000)+5_000,
                        (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyC.generateRodzajCiezkiegoTowaru(),
                        wMaterialyToksyczne.generateTypToksyny());
            }
            default -> {}


        }
        return new wPasazerski((Math.random()*10_000)+5_000,
                (Math.random()*10_000)+5_000,(int)(Math.random()*300),
                wPasazerski.generateKlimatyzacja(),new Random().nextBoolean());

    }
    public static void stworzWagon() {
        String[] tab ={"ABC Inc.", "XYZ Corporation", "Smith and Sons", "Johnson & Johnson", "Acme Industries", "Big Bank",
            "Global Enterprises", "Tech Solutions", "Innovative Minds", "Peak Performance", "Dynamic Data", "Red Rock Software",
            "Globex Corporation", "Swift Logistics", "White Whale Inc."};
        Scanner in=new Scanner(System.in);
        System.out.println("Wybierz rodzaj wagonu(numer) do stworzenia");
        System.out.println("(1) Pasażerski | (2) Pocztowy | (3) Bagazowo-pocztowy | (4) Restauracyjny\n" +
                "(5) Towarowy Podstawowy | (6) Towarowy Ciężki | (7) Chłodniczy | (8) Materialy Ciekle\n" +
                "(9) Materialy Gazowe | (10) Materialy Wybuchowe | (11) Materialy Toksyczne | (12) Materialy ciekle gazowe ");
            switch (in.nextInt()){
                case 1->{
                    tempWagony.add(new wPasazerski((Math.random()*10_000)+5_000,
                            (Math.random()*30_000)+20_000,(int)(Math.random()*300),wPasazerski.generateKlimatyzacja(),new Random().nextBoolean()));

                }
                case 2->{
                    tempWagony.add(new wPocztowy((Math.random()*10_000)+5_000,
                            (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],Wagon.generateZabezpieczenia(),(int)(Math.random()*90)+10,
                            (int)(Math.random()*300),new Random().nextBoolean()));
                }
                case 3->{
                    tempWagony.add(new wBagazowoPocztowy((Math.random()*10_000)+5_000,
                            (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],(int)(Math.random()*300),new Random().nextBoolean(),
                            Wagon.generateZabezpieczenia()));
                }
                case 4->{
                    tempWagony.add(new wRestauracyjny((Math.random()*10_000)+5_000,
                            (Math.random()*30_000)+20_000,(int)(Math.random()*100),(int)(Math.random()*300),new Random().nextBoolean(),
                            new Random().nextBoolean()));
                }
                case 5->{
                    tempWagony.add(new wTowarowyP((Math.random()*10_000)+5_000,
                            (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyP.generateRodzajTowaru(),wTowarowyP.generateDrzwi(),
                            wTowarowyP.generateKolor()));
                }
                case 6->{
                    if(Math.random()>0.5){
                    wTowarowyC temp = new wTowarowyC((Math.random()*10_000)+5_000,
                            (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyC.generateRodzajCiezkiegoTowaru());
                        }
                    else {
                        tempWagony.add(new wTowarowyC((Math.random()*10_000)+5_000,
                                (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyC.generateRodzajCieklegoTowaru()));
                    }
                }
                case 7->{
                    tempWagony.add(new wChlodniczy((Math.random()*10_000)+5_000,
                            (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyP.generateRodzajTowaru(),
                            wChlodniczy.generateRodzajSpozywki(),wTowarowyP.generateDrzwi(), wTowarowyP.generateKolor()));
                }
                case 8->{
                    tempWagony.add(new wMaterialyCiekle((Math.random()*10_000)+5_000,
                            (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyP.generateRodzajTowaru(),
                            wTowarowyP.generateDrzwi(), wTowarowyP.generateKolor(), new Random().nextBoolean()));
                }
                case 9->{
                    tempWagony.add(new wMaterialyGazowe((Math.random()*10_000)+5_000,
                            (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyP.generateRodzajTowaru(),
                            wTowarowyP.generateDrzwi(), wTowarowyP.generateKolor(), (Math.random()*2)+1,wMaterialyGazowe.generateRodzajGazu()));
                }
                case 10->{
                    tempWagony.add(new wMaterialyWybuchowe((Math.random()*10_000)+5_000,
                            (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyC.generateRodzajCiezkiegoTowaru(),
                            wMaterialyWybuchowe.generateRodzajMateriału()));
                }
                case 11->{
                    tempWagony.add(new wMaterialyToksyczne((Math.random()*10_000)+5_000,
                            (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyC.generateRodzajCiezkiegoTowaru(),
                            wMaterialyToksyczne.generateTypToksyny()));
                }
                case 12->{
                    tempWagony.add(new wMaterialyCiekleToksyczne((Math.random()*10_000)+5_000,
                            (Math.random()*30_000)+20_000,tab[(int)(Math.random()*tab.length)],wTowarowyC.generateRodzajCieklegoTowaru(),
                            wMaterialyCiekleToksyczne.generateSystemAlarmowy(),new Random().nextBoolean()));
                }
                default->{
                    System.out.println("Bledny wybor - wyjscie do menu");
                }
            }
    }
    public static void stworzStacjeKolejowa(){
        new StacjaKolejowa("Stacja"+s++);
        System.out.println("Utworzono stacje");
    }
    public static void zapisDoPliku() {
        new Thread(() -> {
            try {
                PrintWriter zapis = new PrintWriter("Appstate.txt");
                Thread.sleep(5000);
                while(!Thread.interrupted()){
                    Lokomotywa.wszystkieLokomotywySortowanie.sort((o1, o2) -> {
                        if((o1.calaDlugoscDrogi-o1.aktualnaPrzebytaDroga)<=(o2.calaDlugoscDrogi-o2.aktualnaPrzebytaDroga))
                            return 1;
                        else
                            return -1;
                    });
                    for(Lokomotywa loco:Lokomotywa.wszystkieLokomotywySortowanie)
                    {
                        zapis.println(loco.nazwa+" trasa do konca -> "+(loco.calaDlugoscDrogi-loco.aktualnaPrzebytaDroga)+"\n"+loco.pokazWagony());
                        zapis.flush();
                    }
                    zapis.println("-------------------------------------------------------------------------------------------------------");
                    zapis.flush();
                    Thread.sleep(5000);
                }zapis.close();
            } catch (FileNotFoundException | InterruptedException e) {
                e.getMessage();
            }

        }).start();
    }
}