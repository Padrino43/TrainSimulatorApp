package pl.project.train.simulator;

import java.util.Random;

public class wMaterialyCiekleToksyczne extends wTowarowyC implements CechyMaterialowCieklych {


    enum systemAlarmowy
    {
        CZUJNIK,
        ALARM,
        SYSTEM_POWIADAMIANIA_RATUNKOWEGO
    }
    Enum SystemAlarmowy;
    boolean systemPrzeciwpozarowy;

    public wMaterialyCiekleToksyczne(double wagaNetto, double maksWagaBrutto, String nadawca, rodzajCieklegoTowaru rodzajPrzewozonegoTowaru, systemAlarmowy SystemAlarmowy, boolean systemPrzeciwpozarowy) {
        super(wagaNetto,maksWagaBrutto, nadawca, rodzajPrzewozonegoTowaru);
        this.SystemAlarmowy = SystemAlarmowy;
        this.systemPrzeciwpozarowy = systemPrzeciwpozarowy;
    }

    private void samopodpalenie()
    {
        new Thread(()->{
            if (wagaBrutto!=wagaNetto){
            System.out.println("Towar sie zapalil samoczynnie");
            for (int i = 0; i < 30; i++) {
                try {
                    if(i>20)
                        System.out.println("Podpalenie stage 3");
                    else if (i>10) {
                        System.out.println("Podpalenie stage 2");
                        if (systemPrzeciwpozarowy){
                            System.out.println("Wykryto dym-gaszenie");
                            for (int j = 0; j < 5; j++) {
                                System.out.print(".");
                            }
                            if (new Random().nextBoolean()){
                                System.out.println("Towar zostal ugaszony");
                            }
                            else {System.out.println("Towar wypalil sie w 20%");
                                double tmp=wagaBrutto-wagaNetto;
                                tmp*=0.80;
                                wagaBrutto-=tmp;
                            }
                            Thread.currentThread().interrupt();
                        }
                    }
                    else System.out.println("Podpalenie stage 1");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
                System.out.println("Towar sie spalil");
            wagaBrutto=wagaNetto;
            }

        }).start();
    }

    public void wyproznijWagon()
    {
        if(Math.random()>0.5)
            System.out.println("Material toksyczny wplynal do rzeki i zatrul rzeke");
        else
            System.out.println("Wylany material zostal porzucony bez zadnych konsekwencji");
    }
    public static systemAlarmowy generateSystemAlarmowy() {
        systemAlarmowy[] values = systemAlarmowy.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }


    @Override
    public void sprawdzZawartosc() {

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
        if(Math.random()>0.5)
            samopodpalenie();

    }

    @Override
    public void sprawdzCzyJestTowar() {

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
