package pl.project.train.simulator;

import java.util.Random;

public class wChlodniczy extends wTowarowyP implements SiecElektryczna {
    enum RodzajSpozywki{
        mieso,
        warzywa,
        owoce,
        lody
    }

    Runnable task;
    Enum spozywka;
    Enum rodzajDrzwi;
    boolean schlodzonyTowar;
    boolean ochrona;

    public wChlodniczy(double wagaNetto, double maksWagaBrutto, String nadawca, rodzajTowaru towar,RodzajSpozywki spozywka, drzwi rodzajDrzwi, kolor kolorWagonu) {
        super(wagaNetto, maksWagaBrutto, nadawca, towar, rodzajDrzwi, kolorWagonu);
        super.rodzajPrzewozonegoTowaru = spozywka;
        this.rodzajDrzwi = rodzajDrzwi;
        schlodzonyTowar = false;
        this.ochrona = new Random().nextBoolean();
        task = () -> {
            int i = (int) (Math.random() * 20) + 10;
            if (!schlodzonyTowar) {
                for (; i > 0; i--) {
                    System.out.println("Towar sie chlodzi " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                schlodzonyTowar = true;
                System.out.println("Towar schlodzony");
            } else {
                for (; i > 0; i--) {
                    System.out.println("Towar sie rozmraza " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                schlodzonyTowar = false;
                System.out.println("Towar rozmrozony");
            }
        };

    }

    public void schlodzTowar() {
        if (!schlodzonyTowar)
            new Thread(task).start();
        else System.out.println("Towar zostal juz schlodzony");
    }

    public void rozmrozTowar() {
        if (schlodzonyTowar)
            new Thread(task).start();
        else System.out.println("Towar zostal juz rozmrozony");
    }


    public void ukradnijTowar() {
        String[] zlapany = {"z", "l", "a", "p", "a", "n", "y"};
        if (ochrona) {
            if (Math.random() * 1 > 0.5)
                new Thread(() -> {
                    for (int i = 0; i < zlapany.length; i++) {
                        System.out.print(zlapany[i] + " ");
                    }
                }).start();
            else
                System.out.println("Masz szczescie, w pociagu nie ma ochrony");
        } else {
            System.out.println("Nikt cie nie przylapal");
            double temp=wagaBrutto-wagaNetto;
            wagaBrutto=wagaNetto;
            wagaNetto+=temp/2;
        }


    }
    public static RodzajSpozywki generateRodzajSpozywki() {
        RodzajSpozywki[] values = RodzajSpozywki.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
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
