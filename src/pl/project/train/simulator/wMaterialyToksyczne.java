package pl.project.train.simulator;

import java.util.Random;

public class wMaterialyToksyczne extends wTowarowyC {

    enum TypToksyny {
        AZBEST,
        BENZEN,
        KADM,
        CHLOROWODOR,
        FORMALDEHYD
    }

    TypToksyny typToksyny;
    private double stezenieProduktu;
    private String stanTechniczny;


    public wMaterialyToksyczne(double wagaNetto, double maksWagaBrutto, String nadawca, rodzajCiezkiegoTowaru rodzajPrzewozonegoTowaru, TypToksyny typToksyny) {
        super(wagaNetto, maksWagaBrutto, nadawca, rodzajPrzewozonegoTowaru);
        this.typToksyny = typToksyny;
    }

    public void sprawdzStezenie()
    {
        stezenieProduktu*=1.95;
        if(stezenieProduktu>5 && !stanTechniczny.equals("sprawny")){
            System.out.println("Grozi wyciekiem substancji toksycznej");
            if(Math.random()>0.5)wagaBrutto=wagaNetto;}
        else System.out.println("Wszystko sprawne");
    }
    public void sprawdzStanTechniczny() {
        String[] stany = {"sprawny", "wymaga naprawy", "wymaga przeglądu", "wymaga czyszczenia"};
        stanTechniczny = stany[(int) (Math.random() * stany.length)];
        System.out.println("Stan techniczny wagonu z toksycznymi materiałami typu " + typToksyny + ": " + stanTechniczny + ".");
    }
    public static TypToksyny generateTypToksyny() {
        TypToksyny[] values = TypToksyny.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }
}
