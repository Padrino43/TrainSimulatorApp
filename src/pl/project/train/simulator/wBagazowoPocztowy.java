package pl.project.train.simulator;

public class wBagazowoPocztowy extends Wagon {

    private String nadawca;
    private int maksIloscWszystkiego;
    private int iloscPocztyiBagazu;
    private boolean miejsceDlaZwierzat;
    private Enum zabezpieczenia;

    public wBagazowoPocztowy(double wagaNetto, double maksWagaBrutto, String nadawca, int iloscPocztyiBagazu, boolean miejsceDlaZwierzat, Enum zabezpieczenia) {
        super(wagaNetto, maksWagaBrutto);
        this.nadawca = nadawca;
        if(iloscPocztyiBagazu>maksIloscWszystkiego)
        this.iloscPocztyiBagazu = maksIloscWszystkiego;
        else this.iloscPocztyiBagazu=iloscPocztyiBagazu;
        this.miejsceDlaZwierzat = miejsceDlaZwierzat;
        this.zabezpieczenia = zabezpieczenia;
    }

    public void zmienZabezpieczenie(Zabezpieczenia temp)
    {
        if(temp!=zabezpieczenia){
        zabezpieczenia=temp;
        System.out.println("Zabezpieczenie zmienione");}
        else System.out.println("To zabezpieczenie juz jest");
    }

    public void zwierzeDajGlos()
    {
        if(miejsceDlaZwierzat)
        {
            int random=(int)(Math.random()*5+1);
            switch(random)
            {
                case 1:
                    System.out.println("Hau Hau");break;
                case 2:
                    System.out.println("Miau Miau");break;
                case 3:
                    System.out.println("Uga Buga Uga Buga");break;
                case 4:
                    System.out.println("psssssssss");break;
                case 5:
                    System.out.println("Witam, jakis chlop mnie zamknal w klatce, nie wiem co tu robie");break;
                default:break;
            }
        }
        else System.out.println("Brak miejsca dla zwierząt!");
    }





}
