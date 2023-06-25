package pl.project.train.simulator;

import java.util.Random;

public class wMaterialyGazowe extends wTowarowyP {

    double cisnienie;
    enum rodzajGazu
    {
        tlen,
        azot,
        wodór,
        hel
    }
    Enum rodzajPrzewozonegoGazu;

    public wMaterialyGazowe(double wagaNetto, double maksWagaBrutto, String nadawca, rodzajTowaru towar, drzwi rodzajDrzwi, kolor kolorWagonu, double cisnienie, rodzajGazu rodzajPrzewozonegoGazu) {
        super(wagaNetto, maksWagaBrutto, nadawca, towar, rodzajDrzwi, kolorWagonu);
        this.cisnienie = cisnienie;
        this.rodzajPrzewozonegoGazu = rodzajPrzewozonegoGazu;
    }

    public void wlejPodejrzanaSubstancje()
    {
       new Thread(() -> {
           System.out.print("Wlewanie ");
               for (int i = 0; i < 5; i++) {
                   System.out.print(".");
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
           if(Math.random()>0.5)
           {
               System.out.println("Towar wybuchl");
           }
           else System.out.println("Nic sie nie stalo");

       }).start();
    }

    public void wyswietlCisnienie()
    {
        double tmp=cisnienie*((Math.random()*2)+1);
        if(tmp>4.00)
        {
            System.out.println("Cisnienie za duze dla towaru"+tmp);
        }
        else
            System.out.println("Cisnienie w normie "+tmp);
    }

    public static rodzajGazu generateRodzajGazu() {
        rodzajGazu[] values = rodzajGazu.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }




}
