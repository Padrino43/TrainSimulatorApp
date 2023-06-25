package pl.project.train.simulator;

import java.util.Random;

public class wMaterialyWybuchowe extends wTowarowyC {
    enum RodzajMateriału {
        DYNAMIT,
        PROCH,
        NITROGLICERYNA,
        TNT,
        PETN,
        RDX,
        HMX,
        C4
    }
    Enum rodzajMaterialu;
    double wspolczynnikBezpiecznejOdleglosci;

    public wMaterialyWybuchowe(double wagaNetto, double maksWagaBrutto, String nadawca, rodzajCiezkiegoTowaru rodzajPrzewozonegoTowaru, RodzajMateriału rodzajMaterialu) {
        super(wagaNetto, maksWagaBrutto, nadawca, rodzajPrzewozonegoTowaru);
        this.rodzajMaterialu = rodzajMaterialu;
        wspolczynnikBezpiecznejOdleglosci=0.0;
    }

    public void zasymulujWybuch() {
        if (wspolczynnikBezpiecznejOdleglosci == 0.0)
            wspolczynnikBezpiecznejOdleglosci *= (Math.random() * 40) + 10;
        else

            System.out.println("Juz wygenerowano bezpieczna odlegosc " + wspolczynnikBezpiecznejOdleglosci);
    }

    public void skradzTowar()
    {
        if(wspolczynnikBezpiecznejOdleglosci<=25 && wagaNetto!=wagaBrutto)
        {
            System.out.println("Skradziono towar bez konsekwencji");
        }
        else if (wagaNetto!=wagaBrutto)
            new Thread(() -> {
                System.out.print("Pssss");
                for (int i = 0; i < 5; i++) {
                    System.out.print(".");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("BUUUUUUUUUUUUUM");
                wagaBrutto=wagaNetto;
            }).start();
        else System.out.println("Towaru nie ma w wagonie");
    }
    public static RodzajMateriału generateRodzajMateriału() {
        RodzajMateriału[] values = RodzajMateriału.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }
}
