package pl.project.train.simulator;

import java.util.Random;

public
    class wTowarowyP extends Wagon{
    private String nadawca;
    Enum rodzajPrzewozonegoTowaru;
    Enum rodzajDrzwi;
    Enum kolorWagonu;

    enum rodzajTowaru
    {
        piasek,
        zwir,
        sol,
        wegiel,
        paliwo,
        alkohol,
        mleko,
        brak

    }
    enum drzwi
    {
        automatyczne,
        reczne,
        brak

    }
    enum kolor
    {
        czarny,
        zolty,
        niebieski,
        czerwony,
        zielony

    }


    public wTowarowyP(double wagaNetto, double maksWagaBrutto, String nadawca, rodzajTowaru towar,drzwi rodzajDrzwi,kolor kolorWagonu) {
        super(wagaNetto, maksWagaBrutto);
        this.nadawca = nadawca;
        this.rodzajPrzewozonegoTowaru = towar;
        this.rodzajDrzwi = rodzajDrzwi;
        this.kolorWagonu=kolorWagonu;
    }

    public void otworzDrzwi()
    {
        if(rodzajDrzwi==drzwi.automatyczne)
            System.out.println("Otwieranie drzwi");
        if(rodzajDrzwi==drzwi.brak)
            System.out.println("Brak drzwi do otworzenia");
        if(rodzajDrzwi==drzwi.reczne)
            System.out.println("Otwieranie drzwi");
    }
    public void zamknijDrzwi()
    {
        if(rodzajDrzwi!=drzwi.brak)
            System.out.println("Zamykanie drzwi");
        else System.out.println("Wagon nie posiada drzwi");
    }

    public void przemalujPociag(kolor kolor)
    {
        if(kolor!=this.kolorWagonu)
            new Thread(() -> {
                System.out.println("Przemalowywanie pociagu");
                for (int i = 5; i >0; i--) {
                    System.out.print(i+" ");
                }
                System.out.println("Pociag przemalowany");
                kolorWagonu=kolor;
            }).start();
        else System.out.println("Pociag ma juz ten kolor");
    }

    public static rodzajTowaru generateRodzajTowaru() {
        rodzajTowaru[] values = rodzajTowaru.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }
    public static drzwi generateDrzwi() {
        drzwi[] values = drzwi.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }
    public static kolor generateKolor() {
        kolor[] values = kolor.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }



}
