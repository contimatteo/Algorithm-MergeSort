package sample;

import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;


import java.util.ArrayList;

public class Main extends Application {

    // *****************************************************************************************************************
    public static void main(String[] args) {    launch(args);   }
    // *****************************************************************************************************************

    public  static            int           SPACING                     = 35;
    public  static            int           ARRAY_CELL_DIMENSION        = 30;
    public  static  final     int           SORT_GROUP_MOVE_DELTA       = 105;
    public  static  final     int           SORT_GROUP_MARGIN_DELTA     = 35;
    public  static            Duration      SPEED                       = Duration.millis(200);
    public  static            int           ARRAY_LENGHT                = 40;
    public  static            int           ARRAY_MAX_VALUE             = 400;

    public  static            int           windowWidth                 = 0;
    public  static            int           windowHeight                = 0;
    public  static            int           windowX                     = 0;
    public  static            int           windowY                     = 0;

    public  static            int[]         arrayInput                  = {};
    public  static            boolean       stopExecution               = false;
    public  static            String        fileName                    = "default";

    // *****************************************************************************************************************
    //public Input input;
    public Stage dialog;
    public Stage firstStage;
    Graphics graphics;

    public void exitDialog()
    {

    }

    int scelta=0;
    public static void sceltaInputOptions(int scelta)
    {
        switch(scelta)
        {

            case 1:
                Input.createInput(ARRAY_LENGHT, ARRAY_MAX_VALUE);
                break;

            case 2:
                try
                {
                    File dir = new File(".");
                    File file = new File(dir.getCanonicalPath() + File.separator + Main.fileName + ".txt");
                    Input.createInput(file);
                }
                catch (FileNotFoundException ex)
                {
                    Main.sceltaInputOptions(1);
                    System.out.println("Eccezzione generata nel trovare il file --> " + ex.getMessage());
                }
                catch (IOException ex) {
                    Main.sceltaInputOptions(1);
                    System.out.println("Eccezzione generata --> " + ex.getMessage());
                }
                break;

            default:
                //sceltaInputOptions();
                break;
        }
    }

    //*****************************************************************************************************************
    // MAIN FUNCTION - change this for execute procedure after the program show the stage
    public void launcProgram(Stage primaryStage)
    {
        // stop event handler for showing stage
        primaryStage.setOnShown(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                return;
            }
        });
        if(stopExecution=true) {
            if (arrayInput.length > 0) {
                try {
                    // GRAFICA
                    graphics = new Graphics();
                    graphics.createGraphics(arrayInput, primaryStage);
                    // ALGORITMO
                    MergeSort algorithm = new MergeSort(graphics);
                } catch (RuntimeException ex) {
                    System.out.println("Eccezzione generata --> " + ex.getLocalizedMessage());
                }
            }
        }
    }
    //*****************************************************************************************************************

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        firstStage=primaryStage;
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        // set window dimension
        windowWidth  = (int)bounds.getWidth();
        windowHeight = (int)bounds.getHeight();
        windowX      = (int)bounds.getMinX();
        windowY      = (int)bounds.getMinY();
        // set array's cells spacing
        //ARRAY_CELL_DIMENSION = (int)(windowWidth/(ARRAY_LENGHT+(ARRAY_LENGHT/2)));
        // set array's cells dimension
        //SPACING  = (ARRAY_CELL_DIMENSION)+(ARRAY_CELL_DIMENSION/5);

        SequentialTransition transition = new SequentialTransition();
        // options window
        dialog = new Stage();
        Options options = new Options(primaryStage, dialog);

        // when input is done
        primaryStage.setOnShown(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                primaryStage.close();
                launcProgram(primaryStage);
            }
        });
    }
}
