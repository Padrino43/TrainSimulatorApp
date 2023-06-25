package pl.project.train.simulator;

import java.util.Random;

public
    class wTowarowyC extends Wagon{

    private String nadawca;
    Enum rodzajPrzewozonegoTowaru;


    enum rodzajCiezkiegoTowaru
    {
        rury_stalowe,
        maszyna_budowlana,
        maszyna_rolnicza,
        ciezarowka,
        brak

    }
    enum rodzajCieklegoTowaru{
        KWAS_SIARKOWY,
        KWAS_AZOTOWY,
        ACETON,
        CHLOROFORM,
        TRIKLORETEN,
        FENOL
    }

    public wTowarowyC(double wagaNetto, double maksWagaBrutto, String nadawca, rodzajCiezkiegoTowaru rodzajPrzewozonegoTowaru) {
        super(wagaNetto, maksWagaBrutto);
        this.nadawca = nadawca;
        this.rodzajPrzewozonegoTowaru = rodzajPrzewozonegoTowaru;
    }
    public wTowarowyC(double wagaNetto, double maksWagaBrutto, String nadawca, rodzajCieklegoTowaru rodzajPrzewozonegoTowaru) {
        super(wagaNetto,maksWagaBrutto);
        this.nadawca = nadawca;
        this.rodzajPrzewozonegoTowaru = rodzajPrzewozonegoTowaru;
    }

    public void spuscPaliwozTowaru()
    {
        if(rodzajPrzewozonegoTowaru!=rodzajCiezkiegoTowaru.rury_stalowe
        &&rodzajPrzewozonegoTowaru!=rodzajCiezkiegoTowaru.brak){
            new Thread(() -> {
                if(new Random().nextBoolean())
                {
                    System.out.println("Spuszczanie paliwa");
                    for (int i = 5; i >0; i--) {
                        System.out.print(i+" ");
                    }
                    System.out.println("Paliwo spuszczone");
                }
                else
                    System.out.println("W tym sprzecie nie ma paliwa");
            });
        }
        else System.out.println("Tam nie ma paliwa, co chcesz spuscic?");
    }

    public void sprawdzHamulce()
    {
        if(Math.random()*1>0.5){
            System.out.println("Testowanie hamulcy");
            new Thread(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Hamulce sprawne");
            }).start();
        }
        else
        {
            System.out.println("Hamulce nie sa sprawne");
        }
    }
    public static rodzajCiezkiegoTowaru generateRodzajCiezkiegoTowaru() {
        rodzajCiezkiegoTowaru[] values = rodzajCiezkiegoTowaru.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }
    public static rodzajCieklegoTowaru generateRodzajCieklegoTowaru() {
        rodzajCieklegoTowaru[] values = rodzajCieklegoTowaru.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }





}
