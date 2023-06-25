package pl.project.train.simulator;

import java.util.Random;

public
    class wPasazerski extends Wagon implements SiecElektryczna {

    protected int iloscOsob;

    enum Klimatyzacja
    {
        grzanie,
        chlodzenie,
        AC;
    }
    protected Enum opcjaKlimatyzacji;
    protected boolean wifiwPrzedzialach;

    public wPasazerski(double wagaNetto, double maksWagaBrutto, int iloscOsob, Klimatyzacja opcjaKlimatyzacji, boolean wifiwPrzedzialach) {
        super(wagaNetto, maksWagaBrutto);
        this.iloscOsob=iloscOsob;
        this.opcjaKlimatyzacji = opcjaKlimatyzacji;
        this.wifiwPrzedzialach = wifiwPrzedzialach;
    }

    public void zepsujKlimatyzacje()
    {
        if (opcjaKlimatyzacji!=null){
        opcjaKlimatyzacji=null;
        System.out.println("Klimatyzacja zostala zepsuta!");}
        else
        System.out.println("Klimatyzacja jest juz zepsuta!");
    }

    public void zepsujSiec() {
        if(wifiwPrzedzialach)
        {
           wifiwPrzedzialach=false;
            System.out.println("Wifi zepsute");
        }
        else System.out.println("Wifi w przedziale juz jest zepsute");
    }
    public static Klimatyzacja generateKlimatyzacja() {
        Klimatyzacja[] values = Klimatyzacja.values();
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
