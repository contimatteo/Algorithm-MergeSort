package sample;

import javafx.animation.SequentialTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.ArrayList;

public class Graphics
{
    // *****************************************************************************************************************
    public  Pane                     containerStackPane;
    public  Button                   buttonStepByStep;
    public  Button                   buttonMotion;
    public  Button                   buttonExit;
    public  BorderPane               container;
    public  ArrayList<StackPane>     arrayCells;
    public  VBox                     arrayContainer;
    public  HBox                     consoleContainer;
    public  VBox                     consoleApplicationBox;
    public  VBox                     consoleExceptionBox;
    public  HBox                     buttonContainer;
    public  Scene                    scene;
    public  Stage                    stage;
    public  MergeSort                mergeTemp;
    public  HBox                     durationContainer;
    public  TextField                durationTransition;

    public  Label                    consoleOutputLabel;
    public  Label                    consoleExceptionLabel;
    public ScrollPane                consoleApplicationScroll;
    public ScrollPane                consoleExceptionScroll;
    public Graphics                  graphics;

    // for catching exception
    public Label exception;
    // *****************************************************************************************************************
    public  static  StringBuilder consoleText;
    public void createConsoleOutput()
    {
        consoleText=new StringBuilder();
        consoleText.append("CONSOLE FOR OUTPUT & DEBUG");
        consoleText.append("\n"); consoleText.append("\n");
        consoleOutputLabel = new Label(" ");
        consoleOutputLabel.setTextFill(Color.valueOf("rgb(146, 254, 147)"));
        consoleOutputLabel.setAlignment(Pos.TOP_LEFT);
        consoleOutputLabel.setText(consoleText.toString());
        consoleOutputLabel.setTextAlignment(TextAlignment.LEFT);
        consoleOutputLabel.setMinWidth((Main.windowWidth / 2) - 15);
        consoleOutputLabel.setWrapText(true);
    }

    public void consoleWriteOutput(String msg)
    {
        consoleText.append('\t' + ">> " + msg);
        consoleText.append("\n");
        consoleText.append("\n");
        consoleOutputLabel.setText(consoleText.toString());
        consoleOutputLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {

                consoleOutputLabel.setText(consoleText.toString());
                //System.out.println(consoleText.toString());
            }
        });
    }

    public  static  StringBuilder exceptionText;
    public void createConsoleException()
    {
        exceptionText=new StringBuilder();
        exceptionText.append("CONSOLE FOR EXCEPTIONS");
        exceptionText.append("\n"); exceptionText.append("\n");
        consoleExceptionLabel = new Label(" ");
        consoleExceptionLabel.setTextFill(Color.valueOf("rgb(211, 111, 95)"));
        consoleExceptionLabel.setAlignment(Pos.TOP_LEFT);
        consoleExceptionLabel.setText(exceptionText.toString());
        consoleExceptionLabel.setTextAlignment(TextAlignment.LEFT);
        consoleExceptionLabel.setMinWidth((Main.windowWidth / 2) - 15);
        consoleExceptionLabel.setWrapText(true);
    }

    public void consoleWriteException(String msg)
    {
        exceptionText.append(">> " + msg);
        exceptionText.append("\n");
        exceptionText.append("\n");
        consoleExceptionLabel.setText(exceptionText.toString());
        consoleExceptionLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {

                consoleExceptionLabel.setText(exceptionText.toString());
                //System.out.println(consoleText.toString());
            }
        });
    }

    //******************************************************************************************************************

    int step=1; int time=0;
    // BUTTON STEP
    public SequentialTransition mergeSortOneStep(int array[], ArrayList<StackPane> list, SequentialTransition seqTrans)
    {
        if(step==1)
            consoleWriteOutput("Modalità : STEP BY STEP");

        mergeTemp.mergeSortStep(array, seqTrans, list, step);
        step *= 2;

        return seqTrans;
    }

    // BUTTON MOTION
    public SequentialTransition mergeSortAllElements(int array[], ArrayList<StackPane> list, SequentialTransition seqTrans)
    {
        consoleWriteOutput("Modalità : MOTION");
        mergeTemp.mergeSortMotion(array, seqTrans, list);
        return seqTrans;
    }

    //******************************************************************************************************************

    private StackPane createValueSingleNode(int position, int number)
    {
        // create graphics of one cell
        // set size (square)
        Rectangle   arraySingelCell = new Rectangle(Main.ARRAY_CELL_DIMENSION, Main.ARRAY_CELL_DIMENSION);
        arraySingelCell.setFill(Color.valueOf("#92fe9d"));
        // value of array's cell
        Text arrayCellValue = new Text(String.valueOf(number));
        // create cell'ìs container
        StackPane   cell = new StackPane();
        cell.setPrefSize(arraySingelCell.getWidth(), arraySingelCell.getHeight());
        cell.setId(String.valueOf(number));
        cell.getChildren().addAll(arraySingelCell, arrayCellValue);
        cell.setAlignment(Pos.CENTER);
        cell.setTranslateX(Main.SPACING * position);
        return cell;
    }

    public void setContainerStackPane(int[] array)
    {
        // container for generated array
        containerStackPane = new Pane();
        arrayCells = new ArrayList<>();
        for (int i = 0; i < (array.length); i++)
        {
            // create graphics of each array cell
            StackPane stackPane = createValueSingleNode(i, array[i]);
            // and add this to <ArrayList<StackPane>>
            arrayCells.add(stackPane);
        }
        containerStackPane.getChildren().addAll(arrayCells);
    }

    public void setButtonStepByStep(int[] array)
    {
        // step-by-step button
        exception = null;
        buttonStepByStep = new Button("Step-by-Stepy");
        buttonStepByStep.setStyle("-fx-background-color: #00c9ff;");
        // handle <onCLick> event
        buttonStepByStep.setOnAction(event ->
        {
            try
            {
                // create the transition for this step
                SequentialTransition transition1 = new SequentialTransition();
                // launch the function to sorting the array elements
                transition1 = mergeSortOneStep(array, arrayCells, transition1);
                // disable motion button
                buttonStepByStep.setDisable(true);
                // when the sorting is finished (for this step)
                // launch the main transition which contains all sorting process transition
                transition1.play();
                transition1.setOnFinished(event1 -> buttonStepByStep.setDisable(false));
                exception=new Label("Tutto ok");
            }
             catch(NullPointerException ex)
            {
                System.out.println("Eccezione generata --> " + ex.getLocalizedMessage() + " - Caused by: " + ex.getCause());
            }
            finally
            {
                if(exception==null)
                    consoleWriteException("Eccezione generata --> Causate nella generazione dell'animazione per il bottone Step-by-Step");
                else
                    exception = null;
            }
        });
    }

    public void setButtonMotion(int[] array)
    {
        // motion button
        buttonMotion = new Button("Motion");
        buttonMotion.setStyle("-fx-background-color: #00c9ff;");
        // handle <onCLick> event
        buttonMotion.setOnAction(event ->
        {
            try
            {
                // create the transition
                SequentialTransition transition2 = new SequentialTransition();
                // launch the function to sorting the array elements
                transition2 = mergeSortAllElements(array, arrayCells, transition2);
                // when the sorting is finished
                // launch the main transition which contains all sorting process transition
                transition2.play();
                transition2.setOnFinished(event1 -> buttonStepByStep.setDisable(false));
                exception = new Label("Titto ok");
            }
            catch(NullPointerException ex)
            {
                System.out.println("Eccezione generata --> " + ex.getLocalizedMessage() + " - Caused by: " + ex.getCause());
            }
            finally
            {
                if(exception==null)
                {
                    consoleWriteException("Eccezione generata --> Causate nella generazione dell'animazione per il bottone Motion");
                    setButtonMotion(array);
                }
                else
                    exception = null;
            }
        });
    }

    public void setDurationTransition()
    {
        // create the input for update transition's duration
        Label durationText = new Label();
        durationText.setText("Imposta la velocità dell'animazione (in millisecondi):");
        durationText.setTextFill(Color.AZURE);
        durationContainer = new HBox();
        durationContainer.setMinWidth(100);
        durationContainer.setSpacing(10);
        durationTransition = new TextField();
        durationTransition.setText("200");
        durationTransition.setMinWidth(50);
        // add listener to handle the change of transition duration's value
        durationTransition.textProperty().addListener((observable, oldValue, newValue) ->
        {
            try
            {
                // try to convert the new value from <string> to <int>
                time = Integer.parseInt(newValue);
                Main.SPEED=Duration.millis(time);
                exception = new Label("Ok");
            }
            catch (NumberFormatException ex1)
            {
                consoleWriteException(ex1.getMessage() + "Caused by: " + ex1.getCause());
            }
            catch(NullPointerException ex2)
            {
                consoleWriteException(ex2.getMessage() + "Caused by: " + ex2.getCause());
            }
            finally
            {
                if(exception==null)
                    consoleWriteException("Eccezione generata --> Causata nell'impostazione della durata della transizione");
                else
                    exception = null;
            }

        });
        durationContainer.getChildren().addAll(durationText, durationTransition);
    }

    public void setButtonExit()
    {
        buttonExit = new Button("Exit");
        buttonExit.setStyle("-fx-background-color: #00c9ff;");
        buttonExit.setOnAction(event -> {
            closeProgram();
        });
    }

    public void setContainers()
    {
        container = new BorderPane();
        container.setTop(arrayContainer);
        container.setCenter(consoleContainer);
        container.setBottom(buttonContainer);
    }

    public void setArrayContainer()
    {
        // top
        arrayContainer = new VBox();
        Label arrayLabel = new Label(" ARRAY GENERATO ");
        arrayLabel.setTextFill(Color.AZURE);
        arrayLabel.setPrefHeight(25);
        arrayContainer.setPadding(new Insets(15, 15, 15, 15));
        arrayContainer.setSpacing(10);
        arrayContainer.setPrefHeight((Main.SPACING * 7));
        arrayContainer.setStyle("-fx-background-color: rgb(43, 43, 43);");
        arrayContainer.getChildren().addAll(arrayLabel, containerStackPane);
    }

    public void setConsoleContainer()
    {
        setConsoleApplication();
        setConsoleException();
        consoleContainer = new HBox();
        consoleContainer.setPadding(new Insets(15, 15, 15, 15));
        consoleContainer.setSpacing(15);
        consoleContainer.setStyle("-fx-background-color: rgb(60, 63, 65);");
        consoleContainer.getChildren().addAll(consoleApplicationScroll, consoleExceptionScroll);
    }

    public void setConsoleApplication()
    {
        consoleApplicationBox = new VBox();
        consoleApplicationBox.setPrefWidth((Main.windowWidth/2)-15);
        consoleApplicationBox.setMinHeight((Main.windowHeight-(arrayContainer.getHeight()+buttonContainer.getHeight()+250)));
        consoleApplicationBox.setStyle("-fx-background-color:rgb(43, 43, 43); -fx-padding:20px;");
        consoleApplicationBox.setAlignment(Pos.TOP_LEFT);
        consoleApplicationBox.getChildren().add(consoleOutputLabel);
        // scroll
        consoleApplicationScroll = new ScrollPane();
        consoleApplicationScroll.setContent(consoleApplicationBox);
        consoleApplicationScroll.setFitToWidth(true);
    }

    public void setConsoleException()
    {
        consoleExceptionBox = new VBox();
        consoleExceptionBox.setPrefWidth((Main.windowWidth/2)-15);
        consoleExceptionBox.setMinHeight((Main.windowHeight-(arrayContainer.getHeight()+buttonContainer.getHeight()+250)));
        consoleExceptionBox.setStyle("-fx-background-color:rgb(43, 43, 43); -fx-padding:20px;");
        consoleExceptionBox.setAlignment(Pos.TOP_RIGHT);
        consoleExceptionBox.getChildren().add(consoleExceptionLabel);
        // scroll
        consoleExceptionScroll = new ScrollPane();
        consoleExceptionScroll.setContent(consoleExceptionBox);
        consoleExceptionScroll.setFitToWidth(true);
    }

    public void setButtonContainer()
    {
        // bottom
        buttonContainer = new HBox();
        buttonContainer.setPadding(new Insets(15, 15, 15, 15));
        buttonContainer.setSpacing(20);
        buttonContainer.setPrefHeight((50));
        buttonContainer.setStyle("-fx-background-color: rgb(43, 43, 43);");
        buttonContainer.getChildren().addAll(durationContainer, buttonMotion, buttonStepByStep, buttonExit);
    }

    public void setScene()
    {
        scene = new Scene(container, Main.windowWidth, Main.windowHeight);
    }

    public void setStage(Stage s)
    {
        stage = s;
        stage.setTitle("MC.Merge-Sort");
        stage.setResizable(false);
        stage.setX(Main.windowX);
        stage.setY(Main.windowY);
        stage.setWidth(Main.windowWidth);
        //stage.setHeight(Main.windowHeight-42);
        stage.setHeight(Main.windowHeight);
        stage.setScene(scene);
        stage.show();
    }

    public void closeProgram()
    {
        this.stage.close();
    }

    public Stage createGraphics(int[] array, Stage s)
    {
        createConsoleOutput();
        createConsoleException();
        // instance of MergeSort class
        // created for launch sorting (and animation) on buttons click
        mergeTemp=new MergeSort(this);
        setContainerStackPane(array);
        setButtonStepByStep(array);
        setButtonMotion(array);
        setDurationTransition();
        setButtonExit();
        setArrayContainer();
        setButtonContainer();
        setConsoleContainer();
        setContainers();
        setScene();
        setStage(s);
        return stage;
    }

}
