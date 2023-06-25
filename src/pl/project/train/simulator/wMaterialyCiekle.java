package pl.project.train.simulator;


public class wMaterialyCiekle extends wTowarowyP {

    boolean uszczelnienie;
    int temperatura;

    public wMaterialyCiekle(double wagaNetto, double maksWagaBrutto, String nadawca, rodzajTowaru towar, drzwi rodzajDrzwi, kolor kolorWagonu, boolean uszczelnienie) {
        super(wagaNetto, maksWagaBrutto, nadawca, towar, rodzajDrzwi, kolorWagonu);
        this.uszczelnienie = uszczelnienie;
        this.temperatura = (int)(Math.random()*40)+10;
    }

    public void sprawdzZawartosc()
    {
        new Thread(() -> {
            System.out.println("Otwieranie drzwi");
            for (int i = 5; i >0 ; i--) {
                System.out.print(i+" ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("O tu jest "+super.rodzajPrzewozonegoTowaru);

        }).start();
        if(!uszczelnienie && wagaNetto!=wagaBrutto)
            wagaBrutto-=Math.random()*10000+1000;
    }

    public void sprawdzCzyJestTowar()
    {
        new Thread(() -> {
            System.out.println("Sprawdzam");
            for (int i = 5; i >0 ; i--) {
                System.out.print(i+" ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(temperatura>30)
            {
                System.out.println("Towar wyparował");
                wagaBrutto=wagaNetto;
            }
            else if(temperatura>20){
                System.out.println("Towar wyparował w 30%");
               double tmp=(wagaBrutto-wagaNetto)*0.7;
               wagaBrutto=wagaNetto;
               wagaBrutto+=tmp;
            }
            else {
                System.out.println("Towar jest caly i zdrowy");
            }
        }).start();
    }


}
