package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import jdk.nashorn.api.scripting.JSObject;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class Controller implements Initializable {
    public ArrayList<ArrayList<Button>>buttons;
    public Collection<Object> lista;
    public Object []xx=new Object[25];
    Invocable invocable;
    @FXML GridPane grid;
    @FXML ChoiceBox ChoseScript,ChoseC;
    String algorytmC="random";
    boolean isScript;
    public native int minimax(Object []list);
    public native int random(Object []list);

    public void load() throws FileNotFoundException, ScriptException {
        final File file = new File("JavaScripts");

        ChoseScript.getItems().removeAll();
        ChoseC.getItems().removeAll();
        // Create a new class loader with the directory
        System.out.println("załadowano :" );

        for (final File fileEntry : file.listFiles()) {
            String name = fileEntry.getName();
            System.out.println( name);
            ChoseScript.getItems().add(name);
        }
        ChoseC.getItems().add("minimax");
        ChoseC.getItems().add("random");


        isScript=false;
        System.loadLibrary("JNILibrary"); // libmyjni.so (Linux)

    }
    @FXML
    public void reload(ActionEvent event) throws FileNotFoundException, ScriptException {
        ScriptEngine  engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval((new FileReader("JavaScripts/"+String.valueOf(ChoseScript.getValue()))));
        invocable = (Invocable) engine;
       // System.out.println(minimax(xx));
        isScript=true;

    }
    @FXML
    public void reloadC(ActionEvent event)  {
       algorytmC= (String) ChoseC.getValue();

       isScript=false;
    }

    @FXML
    public void readDirectory(ActionEvent event )
    {
        final File file = new File("JavaScripts");

        ChoseScript.getItems().removeAll();
        ChoseC.getItems().removeAll();
        // Create a new class loader with the directory
        System.out.println("załadowano :" );

        for (final File fileEntry : file.listFiles()) {
            String name = fileEntry.getName();
            System.out.println( name);
            ChoseScript.getItems().add(name);
        }
        ChoseC.getItems().add("minimax");
        ChoseC.getItems().add("random");

    }

    public boolean isMove()
    {
        for(int i=0;i<25;i++)
        {
            if(xx[i]==null)return true;
        }
        return  false;
    }

    public void win()
    {
        for (int row = 0; row<5; row++)
        {
            if (xx[row*5+0]==xx[row*5+1] && xx[row*5+1]==xx[row*5+2] && xx[row*5+2]==xx[row*5+3])
            {                    System.out.println("wwwwwwwwwwwwww");

                if( xx[row*5+1]!=null ) {

                    System.out.println("lllllllllllllllll");
                    buttons.get((row * 5 + 0) / 5).get((row * 5 + 0) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
                    buttons.get((row * 5 + 1) / 5).get((row * 5 + 1) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
                    buttons.get((row * 5 + 2) / 5).get((row * 5 + 2) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
                    buttons.get((row * 5 + 3) / 5).get((row * 5 + 3) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
                }
            }
            if  (xx[row*5+3]==xx[row*5+4] && xx[row*5+1]==xx[row*5+2] && xx[row*5+2]==xx[row*5+3])
            {
                if( xx[row*5+1]==null )return;

                buttons.get((row*5+4) / 5).get((row*5+4) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
                buttons.get((row*5+1) / 5).get((row*5+1) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
                buttons.get((row*5+2) / 5).get((row*5+2) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
                buttons.get((row*5+3) / 5).get((row*5+3) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");

            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col<5; col++)
        {
            if (xx[0*5 +col]==xx[1*5+col] && xx[1*5+col]==xx[2*5+col] && xx[3*5+col]==xx[2*5+col] )
            {
                if( xx[1*5+col]==null)return;
                buttons.get((0*5 +col) / 5).get((0*5 +col) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
                buttons.get((1*5 +col) / 5).get((1*5 +col) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
                buttons.get((2*5 +col) / 5).get((2*5 +col) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
                buttons.get((3*5 +col) / 5).get((3*5 +col) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");

            }
                   if (xx[3*5 +col]==xx[4*5+col] && xx[1*5+col]==xx[2*5+col] && xx[3*5+col]==xx[2*5+col] )
            {
                if( xx[1*5+col]==null)return;

                buttons.get((4*5 +col) / 5).get((4*5 +col) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
                buttons.get((1*5 +col) / 5).get((1*5 +col) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
                buttons.get((2*5 +col) / 5).get((2*5 +col) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
                buttons.get((3*5 +col) / 5).get((3*5 +col) % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");

            }
        }
        // Checking for Diagonals for X or O victory.
        if (xx[0]==xx[6] && xx[12]==xx[18] && xx[12]==xx[6])
        {
            if( xx[0]==null )return;
            buttons.get(0 / 5).get(0 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(6 / 5).get(6 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(12 / 5).get(12 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(18 / 5).get(18 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");

        }
            if (xx[24]==xx[18] && xx[12]==xx[18] && xx[12]==xx[6])
        {
            if( xx[12]==null )return;

            buttons.get(24 / 5).get(24 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(6 / 5).get(6 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(12 / 5).get(12 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(18 / 5).get(18 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");

        }

        if(xx[1]==xx[7] && xx[7]==xx[13] && xx[13]==xx[19])

        {
            if( xx[7]==null )return;

            buttons.get(1 / 5).get(1 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(7 / 5).get(7 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(13 / 5).get(13 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(19 / 5).get(19 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");

        }
        if (xx[5]==xx[11] && xx[11]==xx[17] && xx[17]==xx[23])
        {
            if( xx[11]==null )return;

            buttons.get(5 / 5).get(5 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(11 / 5).get(11 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(17 / 5).get(17 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(23 / 5).get(23 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");

        }

        //goagonal z drugiej strony

        if (xx[4]==xx[8] && xx[12]==xx[8] && xx[12]==xx[16])
        {
            if( xx[12]==null )return;

            buttons.get(4 / 5).get(4 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(8 / 5).get(8 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(12 / 5).get(12 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(16 / 5).get(16 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");

        }
        if (xx[8]==xx[12] && xx[12]==xx[16] && xx[16]==xx[20])
        {
            if( xx[12]==null)return;

            buttons.get(20 / 5).get(20 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(8 / 5).get(8 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(12 / 5).get(12 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(16 / 5).get(16 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");

        }

        if(xx[3]==xx[7] && xx[7]==xx[11] && xx[11]==xx[15])

        {
            if( xx[7]==null )return;

            buttons.get(3 / 5).get(3 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(7 / 5).get(7 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(11 / 5).get(11 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(15 / 5).get(15 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");

        }
        if (xx[9]==xx[13] && xx[13]==xx[17] && xx[17]==xx[21])
        {
            if( xx[13]==null)return;

            buttons.get(9 / 5).get(9 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(13 / 5).get(13 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(17 / 5).get(17 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");
            buttons.get(21 / 5).get(21 % 5).setStyle("-fx-background-color:#AAAE2D;-fx-border-color:#000000");

        }

    }



        public void alg() throws ScriptException, NoSuchMethodException {

        // ;lista.add(null);lista.add(null);lista.add(null);
        if(isScript) {
            System.out.println("alg java : ");

            lista = Arrays.asList(xx);
            // JSObject result = (JSObject) invocable.invokeFunction("fun",lista);
            Double result = (Double) invocable.invokeFunction("fun", lista);

            int i = result.intValue();
            xx[i] = 'x';
            buttons.get(i / 5).get(i % 5).setText("x");
        }
        else
        {
            System.out.println("alg C : "+algorytmC);

            int reulst=-3;
            if (algorytmC.equals("random"))reulst=random(xx);
            if (algorytmC.equals("minimax"))reulst=minimax(xx);
            System.out.println("alg: "+reulst);
            if(reulst<0)return;
            xx[reulst] = 'x';
            buttons.get(reulst / 5).get(reulst % 5).setText("x");
        }
       // lista=  result.values();
        //xx=lista.toArray();
        /*for (Object el:lista
             ) {
            System.out.println(el);

        }*/
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("sssssssssss");

        try {
            load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        buttons=new ArrayList();
        for(int i=0;i<5;i++)
        {
            buttons.add(new ArrayList<Button>());
        }

        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++) {

                final Button btn=new Button();
                btn.setId(Integer.toString(i*5+j));
                btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                btn.setStyle("-fx-background-color:#0000;-fx-border-color:#000000");

                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        win();
                        btn.setStyle("-fx-background-color:#CD3E2D;-fx-border-color:#000000");
                        xx[Integer.parseInt(btn.getId())]='o';
                        btn.setText("o");

                       if( !isMove())return;
                        try {

                            alg();
                            win();
                        } catch (ScriptException e1) {
                            e1.printStackTrace();
                        } catch (NoSuchMethodException e1) {
                            e1.printStackTrace();
                        }


                    }
                });
                buttons.get(i).add(btn);
                grid.add(btn,j,i);
            }
        }
    }
}
