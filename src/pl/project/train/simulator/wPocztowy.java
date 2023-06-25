package pl.project.train.simulator;

import java.util.Random;

public
    class wPocztowy extends Wagon implements SiecElektryczna{

    private String nadawcaWagonu;
    private Enum zabezpieczenia;
    private int maksPaczekiListow;
    private int iloscPaczekiListow;
    private boolean ochroniarz;
    private String nadawca;
    private String odbiorca;
    private String tresc;
    public wPocztowy(double wagaNetto, double maksWagaBrutto, String nadawcaWagonu, Enum zabezpieczenia, int maksPaczekiListow,int iloscPaczekiListow, boolean ochroniarz) {
        super(wagaNetto, maksWagaBrutto);
        this.nadawcaWagonu = nadawcaWagonu;
        this.zabezpieczenia = zabezpieczenia;
        this.maksPaczekiListow = maksPaczekiListow;
        if(iloscPaczekiListow>maksPaczekiListow)
        this.iloscPaczekiListow=maksPaczekiListow;
        else
        this.iloscPaczekiListow=iloscPaczekiListow;
        this.ochroniarz = ochroniarz;
    }

    public void wyslijList(String odbiorca,String nadawca,String tresc)
    {
        if((!odbiorca.isEmpty()||!nadawca.isEmpty()||!tresc.isEmpty())&&iloscPaczekiListow<maksPaczekiListow
        &&(this.nadawca.isEmpty()&&this.odbiorca.isEmpty()&&this.tresc.isEmpty()))
        {
            wagaBrutto+=0.1;
            iloscPaczekiListow++;
            this.odbiorca=odbiorca;
            this.nadawca=nadawca;
            this.tresc=tresc;
            System.out.println("List został wysłany");
        }
        else
            System.out.println("Żadne z pól nie może być puste!");

    }
    public void odbierzList(String odbiorca)
    {
        if((odbiorca.isEmpty()||!(odbiorca!=this.odbiorca))&&iloscPaczekiListow!=0)
        {
            wagaBrutto-=0.1;
            iloscPaczekiListow--;
            System.out.println(this.nadawca+" wysłał list o treści:\n"+this.tresc);
            this.nadawca="";this.odbiorca="";this.tresc="";
        }
        else
            System.out.println("Błędny odbiorca");

    }

    public static wPasazerski.Klimatyzacja generateKlimatyzacja() {
        wPasazerski.Klimatyzacja[] values = wPasazerski.Klimatyzacja.values();
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
