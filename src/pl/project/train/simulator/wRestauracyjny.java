package pl.project.train.simulator;

import java.util.Random;

public class wRestauracyjny extends Wagon implements SiecElektryczna {

    private int iloscStolikow;
    private int iloscMiejscSiedzacych;
    private boolean kuchnia;
    private boolean asortymentAlkoholowy;

    public wRestauracyjny(double wagaNetto, double maksWagaBrutto, int iloscStolikow, int iloscMiejscSiedzacych, boolean kuchnia, boolean asortymentAlkoholowy) {
        super(wagaNetto, maksWagaBrutto);
        this.iloscStolikow = iloscStolikow;
        this.iloscMiejscSiedzacych = iloscMiejscSiedzacych;
        this.kuchnia = kuchnia;
        this.asortymentAlkoholowy = asortymentAlkoholowy;
    }
    enum jedzenie
    {
        SCHAB,
        RYBA,
        MIELONE,
        PULPECIKI,
        GRZYB_ZE_SCIANY
    }

    public void podajJedzenie(jedzenie jesc)
    {
        if(kuchnia){
        switch(jesc)
        {
            case SCHAB:
                System.out.println("Podano schab ");break;
            case RYBA:
                System.out.println("Podano rybę");break;
            case MIELONE:
                System.out.println("Podano mielonego");break;
            case PULPECIKI:
                System.out.println("Podano pulpeciki");break;
            case GRZYB_ZE_SCIANY:
                System.out.println("Niestety nie jest do zjedzenia :(");break;
            default:
                System.out.println("Brak opcji w jadlospisie");
        }}
        else
            System.out.println("Nie posiadamy gotowanych dań!");
    }
    enum alkohol
    {
        PIWO,
        WINO,
        WODKA,
        RUM,
        KOCIOLEK
    }

    public void podajAlhohol(alkohol alko)
    {
        if(asortymentAlkoholowy)
        switch(alko)
        {
            case PIWO:
                System.out.println("Podano piwo");break;
            case WINO:
                System.out.println("Podano wino");break;
            case WODKA:
                System.out.println("Podano wodke");break;
            case RUM:
                System.out.println("Podano rum");break;
            case KOCIOLEK:
                System.out.println("Nie posiadamy toalety na skutki tego trunku");break;
            default:
                System.out.println("Brak opcji alkoholowej");
        }
        else System.out.println("Brak alkoholu na pokładzie");
    }

    public static jedzenie generatejedzenie() {
        jedzenie[] values =jedzenie.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }
    public static alkohol generatealkohol() {
        alkohol[] values =alkohol.values();
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
