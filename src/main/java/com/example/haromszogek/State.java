package com.example.haromszogek;

import lombok.Getter;

import java.util.ArrayList;

import static com.example.haromszogek.Pont.randomPont;

public class State {

    private ArrayList<Pont> haromszog = new ArrayList<>();
    private ArrayList<Pont> tobbiPont = new ArrayList<>();
    private double oldalA;
    private double oldalB;
    private double oldalC;
    private final int minTerulet = 750000;
    @Getter
    private int tickspeed = Integer.MAX_VALUE;



    public State(){
        haromszogbuild();
    }

    private boolean lehet3Szog(){
        if((oldalA + oldalB > oldalC) && (oldalB + oldalC > oldalA) && (oldalA + oldalC > oldalB))
            return true;
        return false;
    }

    private double tavolsag(Pont a, Pont b){
        return (Math.sqrt(Math.pow(b.getX() - a.getX(),2) + Math.pow(b.getY() - a.getY(),2)));
    }
    public ArrayList<Pont> getHaromszog(){
        return haromszog;
    }
    public ArrayList<Pont> getTobbiPont(){
        return tobbiPont;
    }
    private double terulet(){
        double keruletfele = (oldalA + oldalB + oldalC ) / 2;
        return Math.sqrt(keruletfele*(keruletfele-oldalA)*(keruletfele-oldalB)*(keruletfele-oldalC));

    }
    public void addPont(Pont pont,int i){
        if(bennevanMar(pont)){
            i--;
            return;
        }
        tobbiPont.add(pont);
    }

    public Pont felezoPont(Pont pont1, Pont pont2){
        int x = (pont1.getX() + pont2.getX())/2;
        int y = (pont1.getY() + pont2.getY())/2;
        return new Pont(x,y);
    }

    private boolean bennevanMar(Pont pont){
        return tobbiPont.contains(pont);
    }
    public void haromszogClear(){
        haromszog.clear();
    }
    public void tobbiPontClear(){
        tobbiPont.clear();
    }
    public void haromszogbuild(){
        while(true){
            for (int i = 0; i < 3; i++)
                haromszog.add(randomPont());
            this.oldalA = tavolsag(haromszog.get(0),haromszog.get(1));
            this.oldalB = tavolsag(haromszog.get(0),haromszog.get(2));
            this.oldalC = tavolsag(haromszog.get(1),haromszog.get(2));
            if(lehet3Szog() && terulet() > minTerulet)
                break;
            haromszogClear();
        }
        System.out.println(haromszog);
    }
}
