package com.example.haromszogek;


import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Controller {
    @FXML
    VBox root;
    @FXML
    Canvas canvas;
    State state;
    private AnimationTimer timer;
    private boolean runs = false;
    Color background = Color.BLACK;
    Color pontok = Color.WHITE;

    public void initialize(){
        state = new State();
        drawStart();
        initTimer();
    }
    public void initHandlers(Scene scene){
        scene.setOnKeyPressed(this::keyPressHandler);
    }
    private void keyPressHandler(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.SPACE){
            felezes(1000);
        }
        if(keyEvent.getCode() == KeyCode.E && !runs ){
            runs = true;
            timer.start();
            return;
        }
        if(keyEvent.getCode() == KeyCode.E && runs){
            runs = false;
            timer.stop();
            return;
        }
        if(keyEvent.getCode() == KeyCode.ENTER){
            felezes(100);
        }
        if(keyEvent.getCode() == KeyCode.W){
            felezes(10);
        }
        if(keyEvent.getCode() == KeyCode.R){
            restart();
        }
        if(keyEvent.getCode() == KeyCode.Q){
            System.exit(0);
        }
    }
    private GraphicsContext getGraphicsContext(){
        return canvas.getGraphicsContext2D();
    }


    public void drawStart(){
        var gc = getGraphicsContext();
        gc.setFill(background);
        gc.fillRect(0,0,App.width,App.height);
        gc.setFill(pontok);
        for(int i=0;i<state.getHaromszog().size();i++){
            gc.fillRect(state.getHaromszog().get(i).getX(),state.getHaromszog().get(i).getY(),1,1);
        }
        double [] xek = new double[3];
        double [] yek = new double[3];
        for(int i=0;i<state.getHaromszog().size();i++){
            xek[i] = state.getHaromszog().get(i).getX();
            yek[i] = state.getHaromszog().get(i).getY();
        }
        Polygon haromszog = new Polygon(xek[0],yek[0],xek[1],yek[1],xek[2],yek[2]);
        Pont pont = state.felezoPont(state.getHaromszog().get(1),state.getHaromszog().get(2));
        state.addPont(pont,0);
        System.out.println(state.getTobbiPont());
        gc.fillRect(pont.getX(), pont.getY(), 1,1);
        System.out.println("Benne van e a pont? "+haromszog.contains(pont.getX(),pont.getY()));
        System.out.println(haromszog.getPoints());
        root.getChildren().add(haromszog);


    }
    public void felezes(int hanyszor){
        for(int i=0;i<hanyszor;i++){
            var gc = getGraphicsContext();
            Pont valasztott = state.getHaromszog().get((int) (Math.random() * (2 - 0 + 1) + 0));
            Pont tobbivalasztott = state.getTobbiPont().get((int) (Math.random() * (state.getTobbiPont().size()-1 - 0 + 1) + 0));
            Pont felezoPont = state.felezoPont(valasztott, tobbivalasztott);
            state.addPont(felezoPont,i);
            gc.setFill(pontok);
            gc.fillRect(felezoPont.getX(), felezoPont.getY(), 1, 1);
        }

    }
    private void initTimer() {
        timer = new AnimationTimer(){
            long lastTick = 0;
            public void handle(long now){
                if(lastTick == 0){
                    lastTick = now;
                    felezes(1);
                    return;
                }
                if(now - lastTick > 1 / state.getTickspeed()){
                    lastTick = now;
                    felezes(1);
                }
            }
        };
    }

    public void restart() {
        var gc = getGraphicsContext();
        state.tobbiPontClear();
        state.haromszogClear();
        state.haromszogbuild();
        gc.setFill(background);
        gc.fillRect(0,0,App.width,App.height);
        drawStart();
    }
}