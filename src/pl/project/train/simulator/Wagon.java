package pl.project.train.simulator;

import java.util.Random;

public
    abstract class Wagon {

    private static int liczbaWagonow=1;
    protected String numerBocznyWagonu="CA";
    protected double wagaNetto;
    protected double wagaBrutto;
    protected double maksWagaBrutto;


    protected
        Wagon(double wagaNetto, double maksWagaBrutto) {
        numerBocznyWagonu += liczbaWagonow;
        liczbaWagonow++;
        if(maksWagaBrutto<wagaNetto){
        this.wagaBrutto=wagaNetto;
        this.wagaNetto=wagaNetto;
        this.maksWagaBrutto=wagaNetto;}
        else {
            this.wagaBrutto=wagaNetto;
            this.wagaNetto=wagaNetto;
            this.maksWagaBrutto=maksWagaBrutto;
        }
    }


    protected
        void zaladuj(double waga)
    {
        if(waga+wagaBrutto<maksWagaBrutto){
        wagaBrutto+=waga;
       } else{
        wagaBrutto=maksWagaBrutto;}

    }
    protected
        void rozladuj()
    {
        wagaBrutto=wagaNetto;
    }

    public double getWagaBrutto() {
        return wagaBrutto;
    }

    public enum Zabezpieczenia {

        klodka,
        plomba,
        odcisk_palca,
        klucz,
        skan_oka
    }

    public static Zabezpieczenia generateZabezpieczenia() {
        Zabezpieczenia[] values = Zabezpieczenia.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "(" +this.getClass().getSimpleName() +
                " - " + numerBocznyWagonu + ", wagaNetto=" + wagaNetto +
                ", wagaBrutto/maksWagaBrutto=" +
                ((wagaBrutto==wagaNetto)?"Brak załadunku/"+maksWagaBrutto+")":wagaBrutto+"/"+maksWagaBrutto+")");
    }
}
