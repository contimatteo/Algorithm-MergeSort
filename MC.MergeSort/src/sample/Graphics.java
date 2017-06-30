package sample;

import com.sun.scenario.effect.Merge;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import java.util.TimerTask;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
    public  HBox                     arrayContainer;
    public  HBox                     consoleContainer;
    public  VBox                     consoleApplication;
    public  HBox                     consoleException;
    public  HBox                     buttonContainer;
    public  Scene                    scene;
    public  Stage                    stage;
    public  MergeSort                mergeTemp;
    public  HBox                     durationContainer;
    public  TextField                durationTransition;

    public  Label                    console;
    public ScrollPane                consoleApplicationScroll;
    // *****************************************************************************************************************
    public  static  StringBuilder consoleText;
    public void createConsole()
    {
        consoleText=new StringBuilder();
        consoleText.append("CONSOLE");
        consoleText.append("\n"); consoleText.append("\n");
        console = new Label(" ");
        console.setTextFill(Color.AZURE);
        console.setAlignment(Pos.TOP_LEFT);
        console.setText(consoleText.toString());
        console.setTextAlignment(TextAlignment.LEFT);
        console.setMinWidth((Main.windowWidth / 2) - 15);
        console.setWrapText(true);
    }

    public void consoleWrite(String msg)
    {
        consoleText.append(msg);
        consoleText.append("\n");
        consoleText.append("\n");
        console.setText(consoleText.toString());
        console.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {

                console.setText(consoleText.toString());
                System.out.println(consoleText.toString());
            }
        });
    }

    public void consoleWriteDelay(String msg) {
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("hello");
            }
        });

        timer.start();
    }

    //******************************************************************************************************************

    int step=1; int time=0;
    // BUTTON STEP
    public SequentialTransition mergeSortOneStep(int array[], ArrayList<StackPane> list, SequentialTransition seqTrans)
    {
        mergeTemp.mergeSortStep(array, seqTrans, list, step);
        step *= 2;

        return seqTrans;
    }

    // BUTTON MOTION
    public SequentialTransition mergeSortAllElements(int array[], ArrayList<StackPane> list, SequentialTransition seqTrans)
    {
        mergeTemp.mergeSortMotion(array, seqTrans, list);
        return seqTrans;
    }

    private StackPane createValueSingleNode(int position, int number)
    {
        //int num = random.nextInt(10);
        Rectangle   arraySingelCell = new Rectangle(Main.ARRAY_CELL_DIMENSION, Main.ARRAY_CELL_DIMENSION);
        arraySingelCell.setFill(Color.valueOf("#92fe9d"));

        Text        arrayCellValue = new Text(String.valueOf(number));

        StackPane   cell = new StackPane();
        cell.setPrefSize(arraySingelCell.getWidth(), arraySingelCell.getHeight());
        cell.setId(String.valueOf(number));
        cell.getChildren().addAll(arraySingelCell, arrayCellValue);
        //cell.setAlignment(arrayCellValue, Pos.CENTER);
        cell.setAlignment(Pos.CENTER);
        cell.setTranslateX(Main.SPACING * position);
        return cell;
    }

    public void setContainerStackPane(int[] array)
    {
        containerStackPane = new Pane();
        arrayCells = new ArrayList<>();
        for (int i = 0; i < (array.length); i++) {
            StackPane stackPane = createValueSingleNode(i, array[i]);
            arrayCells.add(stackPane);
        }
        containerStackPane.getChildren().addAll(arrayCells);
    }

    public void setButtonStepByStep(int[] array)
    {
        buttonStepByStep = new Button("Step-by-Stepy");
        buttonStepByStep.setStyle("-fx-background-color: #00c9ff;");
        buttonStepByStep.setOnAction(event -> {
            SequentialTransition    transition = new SequentialTransition();
            transition = mergeSortOneStep(array, arrayCells, transition);
            buttonStepByStep.setDisable(true);
            transition.play();
            transition.setOnFinished(event1 -> buttonStepByStep.setDisable(false));
            buttonStepByStep.setDisable(false);
        });
    }
    public void setButtonMotion(int[] array)
    {
        buttonMotion = new Button("Motion");
        buttonMotion.setStyle("-fx-background-color: #00c9ff;");
        buttonMotion.setOnAction(event ->
        {
            SequentialTransition    transition = new SequentialTransition();
            transition = mergeSortAllElements(array, arrayCells, transition);
            buttonMotion.setDisable(true);
            transition.play();
            transition.setOnFinished(event1 -> buttonStepByStep.setDisable(false));
            buttonMotion.setDisable(false);
        });
    }

    public void setDurationTransition()
    {
        Label durationText = new Label();
        durationText.setText("Imposta la velocità dell'animazione (in millisecondi):");
        durationText.setTextFill(Color.AZURE);
        durationContainer = new HBox();
        durationContainer.setMinWidth(100);
        durationContainer.setSpacing(10);
        durationTransition = new TextField();
        durationTransition.setText("200");
        durationTransition.setMinWidth(50);
        durationTransition.textProperty().addListener((observable, oldValue, newValue) ->
        {
            try
            {
                time = Integer.parseInt(newValue);
                Main.SPEED=Duration.millis(time);
            }
            catch (NumberFormatException ex)
            {   }
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
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
        arrayContainer = new HBox();
        arrayContainer.setPadding(new Insets(15, 15, 15, 15));
        arrayContainer.setSpacing(10);
        arrayContainer.setPrefHeight((Main.SPACING * 4));
        arrayContainer.setStyle("-fx-background-color: rgb(43, 43, 43);");
        arrayContainer.getChildren().addAll(containerStackPane);
    }

    public void setConsoleContainer()
    {
        setConsoleApplication();
        inputApplication();
        consoleContainer = new HBox();
        consoleContainer.setPadding(new Insets(15, 15, 15, 15));
        consoleContainer.setSpacing(15);
        consoleContainer.setStyle("-fx-background-color: rgb(60, 63, 65);");
        consoleContainer.getChildren().addAll(consoleApplicationScroll, consoleException);
    }

    public void setConsoleApplication()
    {
        consoleApplication = new VBox();
        consoleApplication.setPrefWidth((Main.windowWidth/2)-15);
        consoleApplication.setMinHeight((Main.windowHeight-(arrayContainer.getHeight()+buttonContainer.getHeight()+250)));
        consoleApplication.setStyle("-fx-background-color:rgb(43, 43, 43); -fx-padding:20px;");
        consoleApplication.setAlignment(Pos.TOP_LEFT);
        consoleApplication.getChildren().add(console);
        // scroll
        consoleApplicationScroll = new ScrollPane();
        consoleApplicationScroll.setContent(consoleApplication);
        consoleApplicationScroll.setFitToWidth(true);
    }

    public void inputApplication()
    {
        consoleException = new HBox();
        consoleException.setPrefWidth((Main.windowWidth/2)-15);
        consoleException.setStyle("-fx-background-color:rgb(43, 43, 43);");
        consoleException.setAlignment(Pos.CENTER_RIGHT);
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
        scene = new Scene(container, 800, 400);
    }

    public void setStage(Stage s)
    {
        stage = s;
        stage.setTitle("MC.Merge-Sort");
        stage.setResizable(false);
        stage.setX(Main.windowX);
        stage.setY(Main.windowY);
        stage.setWidth(Main.windowWidth);
        stage.setHeight(Main.windowHeight-42);
        stage.setScene(scene);
        stage.show();
    }

    public void closeProgram()
    {
        this.stage.close();
    }

    public Stage createGraphics(int[] array, Stage s)
    {
        createConsole();
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
