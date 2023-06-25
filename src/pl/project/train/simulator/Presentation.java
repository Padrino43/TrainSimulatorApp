package pl.project.train.simulator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Presentation {

    static ArrayList<Wagon> tempWagony = new ArrayList<>();
    static ArrayList<Wagon> przykladoweWagony = new ArrayList<>();
    static boolean odpalonaSymulacja = false;
    static int s = 0;
    static int l = 0;
    static String[] tab = {"ABC Inc.", "XYZ Corporation", "Smith and Sons", "Johnson & Johnson", "Acme Industries", "Big Bank",
            "Global Enterprises", "Tech Solutions", "Innovative Minds", "Peak Performance", "Dynamic Data", "Red Rock Software",
            "Globex Corporation", "Swift Logistics", "White Whale Inc."};

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                System.out.println(StacjaKolejowa.stacje);
                stworzStacje();
                System.out.println(StacjaKolejowa.stacje);
                System.out.println(Lokomotywa.wszystkieLokomotywy);
                Thread.sleep(4000);
                stworzPociagi();
                Thread.sleep(2000);
                System.out.println(Lokomotywa.wszystkieLokomotywy);
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
        try {
            MENU();
        } catch (MaksUciagLocPrzekroczonyException | MaksIloscWagonowException | MaksymalnaIloscWagonowElekException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void MENU() throws MaksUciagLocPrzekroczonyException, MaksymalnaIloscWagonowElekException, MaksIloscWagonowException {
        System.out.println("WITAMY W SYMULATORZE KOLEI");
        Scanner in = new Scanner(System.in);
        Lokomotywa locoTemp;


        //TWORZENIE LOKOMOTYWY
        System.out.println(Lokomotywa.wszystkieLokomotywy);
        stworzLokomotywe();
        System.out.println("Stworzono lokomotywe");
        System.out.println(Lokomotywa.wszystkieLokomotywy);

        //WYBRANIE LOKOMOTYWY
        System.out.println("Wybierz indeks lokomotywy");
        int i = 0;
        for (Lokomotywa e :
                Lokomotywa.wszystkieLokomotywy) {
            System.out.print("(" + i++ + ")" + e.nazwa + ", ");
        }
        System.out.println();
        int n = in.nextInt();
        if (Lokomotywa.wszystkieLokomotywy.size() > n && 0 <= n) {
            locoTemp = Lokomotywa.wszystkieLokomotywy.get(n);
            System.out.println("Wybrano lokomotywe");
            System.out.println(locoTemp.nazwa);
        }


        //DODANIE WAGONU DO LOKOMOTYWY
        locoTemp = Lokomotywa.wszystkieLokomotywy.get(in.nextInt());
        System.out.println(locoTemp.wagony);
        if (!tempWagony.isEmpty() && locoTemp != null) {
            System.out.println("Wybierz indeks wagonu");
             i = 0;
            for (Wagon e :
                    tempWagony) {
                System.out.print("(" + i++ + ")" + e + "\n");
            }
             n = in.nextInt();
            System.out.println("Podaj wage zaladunku(double)");
            if (tempWagony.size() > n && 0 <= n) {
                locoTemp.dodajWagon(tempWagony.get(n), in.nextDouble());
                tempWagony.remove(n);
                System.out.println("Dodano wagon");

            } else System.out.println("Podano wagon poza przedzialem");
        } else {
            System.out.println("Nie wybrano lokomotywy lub brak wagonow do dodania");
        }
        System.out.println(locoTemp.wagony);

        //USUWANIE WAGONU Z LOKOMOTYWY
        System.out.println(locoTemp.wagony);
        locoTemp = Lokomotywa.wszystkieLokomotywy.get(in.nextInt());
        if (locoTemp != null) {
            System.out.println("Wybierz indeks wagonu");
             i = 0;
            for (Wagon e :
                    locoTemp.wagony) {
                System.out.print("(" + i++ + ")" + e + "\n");
            }
            n = in.nextInt();
            if (locoTemp.wagony.size() > n && 0 <= n)
                locoTemp.usunWagon(locoTemp.wagony.get(n));
            else System.out.println("Lokomotywa nie ma wagonow");
        } else System.out.println("Nie wybrano lokomotywy");
        System.out.println(locoTemp.wagony);

        //ZALADOWANIE LUB ROZLADOWANIE LOKOMOTYWY
        System.out.println(locoTemp.wagony);
        locoTemp = Lokomotywa.wszystkieLokomotywy.get(in.nextInt());
        if (locoTemp != null) {
            System.out.println("Wybierz indeks wagonu");
             i = 0;
            for (Wagon e :
                    locoTemp.wagony) {
                System.out.print("(" + i++ + ")" + e + "\n");
            }
             n = in.nextInt();
            if (locoTemp.wagony.size() > n && 0 <= n) {
                if (locoTemp.wagony.get(n).wagaBrutto < locoTemp.wagony.get(n).maksWagaBrutto) {
                    System.out.println("Podaj wage zaladunku(double)");
                    locoTemp.wagony.get(n).zaladuj(in.nextDouble());
                } else {
                    System.out.println("Rozladowano wagon");
                    locoTemp.wagony.get(n).rozladuj();
                }

            } else System.out.println("Podano wagon poza przedzialem");
        } else {
            System.out.println("Nie wybrano lokomotywy");
        }
        System.out.println(locoTemp.wagony);

        //WYSWIETLENIE WAGONOW
        System.out.println(locoTemp.wagony);
        locoTemp = Lokomotywa.wszystkieLokomotywy.get(in.nextInt());
        if (locoTemp != null) {
            if (!locoTemp.wagony.isEmpty()) {
                 i = 0;
                for (Wagon e :
                        locoTemp.wagony) {
                    System.out.print("(" + i++ + ")" + e + "\n");
                }
            } else System.out.println("Lokomotywa nie posiada wagonow");
        } else System.out.println("Nie wybrano lokomotywy");
        System.out.println(locoTemp.wagony);

        //USUWANIE WYBRANEJ LOKOMOTYWY
        System.out.println(Lokomotywa.wszystkieLokomotywy);
        locoTemp = Lokomotywa.wszystkieLokomotywy.get(in.nextInt());
        if (locoTemp != null)
            System.out.println("Wybierz indeks lokomotywy");
         i = 0;
        for (Lokomotywa e :
                Lokomotywa.wszystkieLokomotywy) {
            System.out.print("(" + i++ + ")" + e.nazwa + ", ");
        }
         n = in.nextInt();
        if (Lokomotywa.wszystkieLokomotywy.size() > n && 0 <= n)
            Lokomotywa.wszystkieLokomotywy.remove(n);
        Lokomotywa.wszystkieLokomotywySortowanie.remove(n);
        System.out.println("Usunieto lokomotywe");
        System.out.println(Lokomotywa.wszystkieLokomotywy);

        //WYSWIETLANIE RAPORTU LOKOMOTYWY
        locoTemp = Lokomotywa.wszystkieLokomotywy.get(in.nextInt());
        locoTemp.raportPociagu();
        new Thread(locoTemp).start();
        if (locoTemp != null)
            Lokomotywa.wszystkieLokomotywy.get(locoTemp.idLokomotywy).raportPociagu();
        else System.out.println("Nie wybrano lokomotywy");


        //POKAZANIE TRASY LOKOMOTYWY
        locoTemp = Lokomotywa.wszystkieLokomotywy.get(in.nextInt());
        if (locoTemp != null)
            Lokomotywa.wszystkieLokomotywy.get(locoTemp.idLokomotywy).pokazTrase();
        else System.out.println("Nie wybrano lokomotywy");


        //METODA STWORZ WAGON, KTORY TWORZY WAGON DO KOLEKCJI TYMCZASOWEJ
        System.out.println(tempWagony);
        stworzWagon();
        System.out.println(tempWagony);

        //USUWANIE WAGONU Z KOLEKCJI TYMCZASOWEJ
        System.out.println(tempWagony);
         i = 0;
        for (Wagon e :
                tempWagony) {
            System.out.print("(" + i++ + ")" + e.getClass().getSimpleName() + ", ");
        }
        System.out.println();
         n = in.nextInt();
        if (tempWagony.size() > n && 0 <= n) {
            tempWagony.remove(n);
            System.out.println("Usunieto wagon");
        }
        System.out.println(tempWagony);

        //WYWOLANIE METODY TWORZACA NASTEPNE STACJE
        System.out.println(StacjaKolejowa.stacje);
        stworzStacjeKolejowa();
        System.out.println(StacjaKolejowa.stacje);


        //USUWANIE STACJI
        System.out.println(StacjaKolejowa.stacje);
         i = 0;
        for (StacjaKolejowa e :
                StacjaKolejowa.stacje) {
            System.out.print("(" + i++ + ")" + e.nazwaStacji + ", ");
        }
        System.out.println();
         n = in.nextInt();
        if (StacjaKolejowa.stacje.size() > n && 0 <= n) {
            StacjaKolejowa.stacje.remove(n);
            System.out.println("Usunieto stacje");
        }
        System.out.println(StacjaKolejowa.stacje);


        //TWORZENIE POLACZENIA MIEDZY STACJAMI
        StacjaKolejowa.pokazWszystkiePolaczenia();
        System.out.println("Wybierz kolejno indeksy stacji do polaczenia");
        i = 0;
        for (
                StacjaKolejowa e :
                StacjaKolejowa.stacje) {
            System.out.print("(" + i++ + ")" + e.nazwaStacji + ", ");
        }
        System.out.println();
        n = in.nextInt();
        int k = in.nextInt();
        if ((StacjaKolejowa.stacje.size() > n && 0 <= n) && n != k && (StacjaKolejowa.stacje.size() > k && 0 <= k)) {
            StacjaKolejowa.stacje.get(n).dodajPolaczenie(StacjaKolejowa.stacje.get(k), (int) (Math.random() * 9000) + 1000);
        } else System.out.println("Bledny wybor stacji");
        StacjaKolejowa.pokazWszystkiePolaczenia();

        //POKAZANIE WSZYSTKICH STACJI
        StacjaKolejowa.pokazWszystkieStacje();

        //POKAZANIE WSZYSTKICH POLACZEN STACJI
        StacjaKolejowa.pokazWszystkiePolaczenia();


        //URUCHAMIANIE SYMULACJI
        if (!odpalonaSymulacja) {
            symulacja();
            System.out.println("Symulacja wystartowala");
        } else System.out.println("Symulacja juz dziala!");

        //WYJSCIE Z APLIKACJI
        System.out.println("WYJSCIE");
        System.exit(0);
    }

    //TWORZENIE 100 STACJI NA POTRZEBY SYMULACJI
    public static void stworzStacje() {
        for (; s < 100; s++) {
            new StacjaKolejowa("Stacja" + s);
        }
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10; j++) {
                StacjaKolejowa sk = StacjaKolejowa.stacje.get((int) (Math.random() * StacjaKolejowa.stacje.size()));
                StacjaKolejowa.stacje.get(i).dodajPolaczenie(sk, (int) (Math.random() * 10_000) + 5_000);
            }
        }
    }


    //TWORZENIE 25 POCIAGOW Z LOSOWA ILOSCIA WAGONOW
    public static void stworzPociagi() throws MaksUciagLocPrzekroczonyException, MaksymalnaIloscWagonowElekException, MaksIloscWagonowException {
        for (; l < 25; l++) {
            new Lokomotywa("A" + l, StacjaKolejowa.stacje.get((int) (Math.random() * 100)), StacjaKolejowa.stacje.get((int) (Math.random() * 100)),
                    (int) (Math.random() * 90_000_000) + 10_000_000, (int) (Math.random() * 10) + 10,
                    (int) (Math.random() * 5) + 5);
            int n = (int) ((Math.random() * 5) + 5);
            for (int j = 0; j < n; j++) {
                Lokomotywa.wszystkieLokomotywy.get(l).dodajWagon(losujWagon(), Math.random() * 30_000 + 20_000);
            }
        }
        System.out.println();
        zapisDoPliku();
    }


    //LOSOWANIE WAGONOW DO SYMULACJI
    public static Wagon losujWagon() {
        switch ((int) (Math.random() * 10) + 1) {
            case 1 -> {
                return new wPasazerski((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, (int) (Math.random() * 300),
                        wPasazerski.generateKlimatyzacja(), new Random().nextBoolean());
            }
            case 2 -> {
                return new wPocztowy((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)],
                        Wagon.generateZabezpieczenia(), (int) (Math.random() * 90) + 10,
                        (int) (Math.random() * 300), new Random().nextBoolean());
            }
            case 3 -> {
                return new wBagazowoPocztowy((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)],
                        (int) (Math.random() * 300), new Random().nextBoolean(),
                        Wagon.generateZabezpieczenia());
            }
            case 4 -> {
                return new wRestauracyjny((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, (int) (Math.random() * 100),
                        (int) (Math.random() * 300), new Random().nextBoolean(),
                        new Random().nextBoolean());
            }
            case 5 -> {
                return new wTowarowyP((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)],
                        wTowarowyP.generateRodzajTowaru(), wTowarowyP.generateDrzwi(),
                        wTowarowyP.generateKolor());
            }
            case 6 -> {
                return new wTowarowyC((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyC.generateRodzajCiezkiegoTowaru());
            }
            case 7 -> {
                return new wChlodniczy((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyP.generateRodzajTowaru(),
                        wChlodniczy.generateRodzajSpozywki(), wTowarowyP.generateDrzwi(), wTowarowyP.generateKolor());
            }
            case 8 -> {
                return new wMaterialyCiekle((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyP.generateRodzajTowaru(),
                        wTowarowyP.generateDrzwi(), wTowarowyP.generateKolor(), new Random().nextBoolean());
            }
            case 9 -> {
                return new wMaterialyGazowe((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyP.generateRodzajTowaru(),
                        wTowarowyP.generateDrzwi(), wTowarowyP.generateKolor(), (Math.random() * 2) + 1, wMaterialyGazowe.generateRodzajGazu());
            }
            case 10 -> {
                return new wMaterialyWybuchowe((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyC.generateRodzajCiezkiegoTowaru(),
                        wMaterialyWybuchowe.generateRodzajMateriału());
            }
            case 11 -> {
                return new wMaterialyToksyczne((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyC.generateRodzajCiezkiegoTowaru(),
                        wMaterialyToksyczne.generateTypToksyny());
            }
            default -> {
            }


        }
        return new wPasazerski((Math.random() * 10_000) + 5_000,
                (Math.random() * 10_000) + 5_000, (int) (Math.random() * 300),
                wPasazerski.generateKlimatyzacja(), new Random().nextBoolean());

    }

    //URUCHOMIENIE SYMULACJI
    public static void symulacja() {
        if (!odpalonaSymulacja) {
            for (Lokomotywa loco : Lokomotywa.wszystkieLokomotywy) {
                new Thread(loco).start();
            }
            odpalonaSymulacja = true;
        }
    }


    //TWORZENIE LOKOMOTYWY
    public static void stworzLokomotywe() {
        new Thread(new Lokomotywa("A" + (l++),
                StacjaKolejowa.stacje.get((int) (Math.random() * StacjaKolejowa.stacje.size())),
                StacjaKolejowa.stacje.get((int) (Math.random() * StacjaKolejowa.stacje.size())),
                ((int) (Math.random() * 40_000) + 50_000),
                ((int) (Math.random() * 10) + 10),
                ((int) (Math.random() * 5) + 5)
        )).start();
    }


    //TWORZENIE WAGONU
    public static void stworzWagon() {
        String[] tab = {"ABC Inc.", "XYZ Corporation", "Smith and Sons", "Johnson & Johnson", "Acme Industries", "Big Bank",
                "Global Enterprises", "Tech Solutions", "Innovative Minds", "Peak Performance", "Dynamic Data", "Red Rock Software",
                "Globex Corporation", "Swift Logistics", "White Whale Inc."};
        Scanner in = new Scanner(System.in);
        System.out.println("Wybierz rodzaj wagonu(numer) do stworzenia");
        System.out.println("(1) Pasażerski | (2) Pocztowy | (3) Bagazowo-pocztowy | (4) Restauracyjny\n" +
                "(5) Towarowy Podstawowy | (6) Towarowy Ciężki | (7) Chłodniczy | (8) Materialy Ciekle\n" +
                "(9) Materialy Gazowe | (10) Materialy Wybuchowe | (11) Materialy Toksyczne | (12) Materialy ciekle gazowe ");
        switch (in.nextInt()) {
            case 1 -> {
                tempWagony.add(new wPasazerski((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, (int) (Math.random() * 300), wPasazerski.generateKlimatyzacja(), new Random().nextBoolean()));

            }
            case 2 -> {
                tempWagony.add(new wPocztowy((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], Wagon.generateZabezpieczenia(), (int) (Math.random() * 90) + 10,
                        (int) (Math.random() * 300), new Random().nextBoolean()));
            }
            case 3 -> {
                tempWagony.add(new wBagazowoPocztowy((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], (int) (Math.random() * 300), new Random().nextBoolean(),
                        Wagon.generateZabezpieczenia()));
            }
            case 4 -> {
                tempWagony.add(new wRestauracyjny((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, (int) (Math.random() * 100), (int) (Math.random() * 300), new Random().nextBoolean(),
                        new Random().nextBoolean()));
            }
            case 5 -> {
                tempWagony.add(new wTowarowyP((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyP.generateRodzajTowaru(), wTowarowyP.generateDrzwi(),
                        wTowarowyP.generateKolor()));
            }
            case 6 -> {
                if (Math.random() > 0.5) {
                    wTowarowyC temp = new wTowarowyC((Math.random() * 10_000) + 5_000,
                            (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyC.generateRodzajCiezkiegoTowaru());
                } else {
                    tempWagony.add(new wTowarowyC((Math.random() * 10_000) + 5_000,
                            (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyC.generateRodzajCieklegoTowaru()));
                }
            }
            case 7 -> {
                tempWagony.add(new wChlodniczy((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyP.generateRodzajTowaru(),
                        wChlodniczy.generateRodzajSpozywki(), wTowarowyP.generateDrzwi(), wTowarowyP.generateKolor()));
            }
            case 8 -> {
                tempWagony.add(new wMaterialyCiekle((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyP.generateRodzajTowaru(),
                        wTowarowyP.generateDrzwi(), wTowarowyP.generateKolor(), new Random().nextBoolean()));
            }
            case 9 -> {
                tempWagony.add(new wMaterialyGazowe((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyP.generateRodzajTowaru(),
                        wTowarowyP.generateDrzwi(), wTowarowyP.generateKolor(), (Math.random() * 2) + 1, wMaterialyGazowe.generateRodzajGazu()));
            }
            case 10 -> {
                tempWagony.add(new wMaterialyWybuchowe((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyC.generateRodzajCiezkiegoTowaru(),
                        wMaterialyWybuchowe.generateRodzajMateriału()));
            }
            case 11 -> {
                tempWagony.add(new wMaterialyToksyczne((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyC.generateRodzajCiezkiegoTowaru(),
                        wMaterialyToksyczne.generateTypToksyny()));
            }
            case 12 -> {
                tempWagony.add(new wMaterialyCiekleToksyczne((Math.random() * 10_000) + 5_000,
                        (Math.random() * 30_000) + 20_000, tab[(int) (Math.random() * tab.length)], wTowarowyC.generateRodzajCieklegoTowaru(),
                        wMaterialyCiekleToksyczne.generateSystemAlarmowy(), new Random().nextBoolean()));
            }
            default -> {
                System.out.println("Bledny wybor - wyjscie do menu");
            }
        }
    }


    //STWORZENIE STACJI KOLEJOWEJ
    public static void stworzStacjeKolejowa() {
        new StacjaKolejowa("Stacja" + s++);
        System.out.println("Utworzono stacje");
    }


    //WATEK ZAPISU DO PLIKU
    public static void zapisDoPliku() {
        new Thread(() -> {
            try {
                PrintWriter zapis = new PrintWriter("Appstate.txt");
                Thread.sleep(5000);
                while (!Thread.interrupted()) {
                    Lokomotywa.wszystkieLokomotywySortowanie.sort((o1, o2) -> {
                        if ((o1.calaDlugoscDrogi - o1.aktualnaPrzebytaDroga) <= (o2.calaDlugoscDrogi - o2.aktualnaPrzebytaDroga))
                            return 1;
                        else
                            return -1;
                    });
                    for (Lokomotywa loco : Lokomotywa.wszystkieLokomotywySortowanie) {
                        zapis.println(loco.nazwa + " trasa do konca -> " + (loco.calaDlugoscDrogi - loco.aktualnaPrzebytaDroga) + "\n" + loco.pokazWagony());
                        zapis.flush();
                    }
                    zapis.println("-------------------------------------------------------------------------------------------------------");
                    zapis.flush();
                    Thread.sleep(5000);
                }
                zapis.close();
            } catch (FileNotFoundException | InterruptedException e) {
                e.getMessage();
            }

        }).start();
    }

}
