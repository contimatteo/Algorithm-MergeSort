package sample;

/**
 * Created by @author Conti Matteo on {2 July 2017}.
 */

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
    public  static            int           ARRAY_LENGHT                = 35;
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
    public Graphics graphics;


    int scelta=0;
    public static void sceltaInputOptions(int scelta, int[] case3Array)
    {
        // field ARRAY in Input class contains the array
        // This regardless of the option chosen
        switch(scelta)
        {
            case 1:
                // create a random array (without duplicates)
                stopExecution=false;
                // set max input size & max_value
                Input.createInput(ARRAY_LENGHT, ARRAY_MAX_VALUE);
                break;

            case 2:
                // retrieve input from txt file
                stopExecution=false;
                try
                {
                    File dir = new File(".");
                    File file = new File(dir.getCanonicalPath() + File.separator + Main.fileName + ".txt");
                    // call static function in Input class for generating the array from the txt file
                    Input.createInput(file);
                }
                catch (FileNotFoundException ex)
                {
                    // if the program reach this point an internal error has occurred
                    // because this control is already taken and the program reach this point only if file is in the
                    // directory. Recall function with options 1 to avoid problems
                    Main.sceltaInputOptions(1, null);
                    System.out.println("Eccezione generata nel trovare il file --> " + ex.getMessage());
                }
                catch (IOException ex)
                {
                    // I/O exception
                    Main.sceltaInputOptions(1, null);
                    System.out.println("Eccezione generata --> " + ex.getMessage());
                }
                break;

            case 3:
                // input from keyboard
                stopExecution=false;
                // the case3Array is already created in Options class
                if(case3Array!=null)
                    // copy the array into the field array in Input class
                    Input.createInput(case3Array);
                else
                    // to prevent the program from crashing
                    // call not reachable by the program
                    sceltaInputOptions(1, null);

                break;

            default:
                // this case is used when an input error is generated
                // this option by the <boolean> statement block the application
                // to create the graphics and launch the algorithm
                stopExecution=true;
                break;
        }
    }

    //*****************************************************************************************************************
    // MAIN FUNCTION - change this for execute procedure after the program show the stage
    public void launcProgram(Stage primaryStage)
    {
        // stop event handler for showing stage
        primaryStage.setOnShown(new EventHandler<WindowEvent>() { public void handle(WindowEvent e) {
                return;
            }});
        // if stopExecution and array's size
        if(!stopExecution) {
            if (arrayInput.length > 0) {
                try
                {
                    // create the graphics
                    // create an instance of primaryStage and adds elements
                    graphics = new Graphics();
                    // launch the creation of the array
                    // each cell is a green rectangle shape
                    graphics.createGraphics(arrayInput, primaryStage);
                    // call class to implement the algorithm for sorting the array
                    MergeSort algorithm = new MergeSort(graphics);
                }
                catch (RuntimeException ex1)
                {
                    System.out.println("Eccezzione generata --> " + ex1.getLocalizedMessage());
                }
            }
        }
    }
    //*****************************************************************************************************************

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // boolean for stopping execution
        stopExecution=false;
        // firstStage is the start menu stage
        firstStage=primaryStage;
        // get info about screen of the laptop
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        // set window dimension and position
        windowWidth  = (int)bounds.getWidth();
        windowHeight = (int)bounds.getHeight();
        windowX      = (int)bounds.getMinX();
        windowY      = (int)bounds.getMinY();
        /* ***********************************************************************
        // SET THIS FOR A RESPONSIVE VIEW
        set array's cells spacing
        ARRAY_CELL_DIMENSION = (int)(windowWidth/(ARRAY_LENGHT+(ARRAY_LENGHT/2)));
        set array's cells dimension
        SPACING  = (ARRAY_CELL_DIMENSION)+(ARRAY_CELL_DIMENSION/5);
        create an instance of javafx transition
        ************************************************************************ */

        SequentialTransition transition = new SequentialTransition();
        // instance the start menu variable (Type: <Stage>)
        dialog = new Stage();
        // instance start menu and graphics
        // show tips and options to launch the program
        // pass primaryStage --> algorithm graphics interface
        // pass dialog       --> start menu graphics interface
        Options options = new Options(primaryStage, dialog);

        // when input options is selected the Option class activate (show) the primaryStage
        // Event Handler for primaryStage <onShown> event
        primaryStage.setOnShown(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e)
            {
                // close the stage where is instanced the graphics of start menu
                primaryStage.close();
                try
                {
                    // call Main function who launch the UI, UX and the handler of sorting
                    launcProgram(primaryStage);
                }
                catch(NullPointerException ex)
                {
                    // primaryStage --> null pointer
                    System.out.println("Eccezione generata nel lancio dell'interfaccia grafica dell'algortimo (puntatore non trovato) --> " + ex.getMessage());
                }

            }
        });
    }
}
