package sample;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;



/**
 * Created by matteoconti on 30/06/17.
 */
public class Options
{
    public  BorderPane  container;
    public  Scene       dialogScene;
    public  HBox        mainContainer;
    public  VBox        inputContainer;
    public  VBox        optionsContainer;
    public  Stage       myStage;
    public  Stage       myPrimaryStage;
    public  TextField   inputArraySize;
    public  TextField   inputMethod;
    public  Label       tutorial;

    //******************************************************************************************************************
    int size, method;
    //******************************************************************************************************************

    public void setContainer()
    {
        container = new BorderPane();
        container.setCenter(mainContainer);
    }

    public void setStage()
    {
        myStage.setTitle("MC.Merge-Sort");
        myStage.setResizable(false);
        myStage.setX(Main.windowX);
        myStage.setY(Main.windowY);
        myStage.setWidth(Main.windowWidth);
        myStage.setHeight(Main.windowHeight-42);
        dialogScene = new Scene(container, 300, 200);
        myStage.setScene(dialogScene);
        myStage.show();
        //return stage;
    }

    public void setOptionsContainer()
    {
        optionsContainer = new VBox();
        optionsContainer.setPrefWidth((Main.windowWidth/2)-15);
        optionsContainer.setStyle("-fx-background-color:rgb(43, 43, 43);");
        optionsContainer.setAlignment(Pos.CENTER_RIGHT);
        optionsContainer.setSpacing(10);
    }

    public void setOptionsContainerTutorial()
    {
        String text = (getTutorialText()).toString();
        tutorial = new Label();
        tutorial.setTextFill(Color.AZURE);
        tutorial.setAlignment(Pos.CENTER);
        tutorial.setText(text);
        tutorial.setTextAlignment(TextAlignment.CENTER);
        tutorial.setMinWidth((Main.windowWidth/2)-15);
        tutorial.setWrapText(true);
        optionsContainer.getChildren().add(tutorial);
    }


    public void setInputContainer()
    {
        inputContainer = new VBox();
        inputContainer.setPrefWidth((Main.windowWidth/2)-15);
        inputContainer.setStyle("-fx-background-color:rgb(43, 43, 43);");
        inputContainer.setAlignment(Pos.CENTER_LEFT);
        inputContainer.setSpacing(100);
    }

    public void setInputContainerDetail()
    {
        VBox container1 = new VBox();
        container1.setAlignment(Pos.CENTER);
        container1.setPrefWidth((Main.windowWidth/2)-15);
        container1.setSpacing(25);
        Label inputTitle = new Label("SCEGLIERE LA DIMENSIONE DELL'ARRAY");
        Label inputSubTitle = new Label(" attenzione per ragioni grafiche non è possibile inserire " +
                " una dimensione superiore a 40 ");
        inputTitle.setTextFill(Color.AZURE);
        inputSubTitle.setTextFill(Color.AZURE);
        inputTitle.setAlignment(Pos.CENTER);
        // input array's size
        inputArraySize = new TextField ();
        inputArraySize.setText("40");
        inputArraySize.setMaxWidth(150);
        container1.getChildren().addAll(inputTitle, inputSubTitle, inputArraySize);
        //inputArraySize.clear();

        VBox container2 = new VBox();
        container2.setAlignment(Pos.CENTER);
        container2.setPrefWidth((Main.windowWidth/2)-15);
        container2.setSpacing(25);
        Label arrayTitle = new Label("SCEGLIERE UN METODO DI INPUT");
        Label arraySubTitle = new Label(" attenzione inserire un numero compreso tra 1 e 2 \n" +
                " 1: generazione automatica di un array random \n" +
                " 2: ricavo l'array dal file input.txt ");
        arrayTitle.setTextFill(Color.AZURE);
        arraySubTitle.setTextFill(Color.AZURE);
        arrayTitle.setAlignment(Pos.CENTER);
        inputMethod = new TextField ();
        inputMethod.setText("1");
        inputMethod.setMaxWidth(150);
        container2.getChildren().addAll(arrayTitle, arraySubTitle, inputMethod);
        //inputMethod.clear();

        Button btn = new Button();
        btn.setText(" Start ");
        btn.setMinWidth(50);
        btn.setAlignment(Pos.CENTER);
        size = 40;   method = 1;
        btn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                btn.setDisable(true);
                try
                {
                    size = Integer.parseInt(inputArraySize.getText());
                    method = Integer.parseInt(inputMethod.getText());
                }
                catch (NumberFormatException ex)
                {   }
                if((method>0)&&(method<3)&&(size>0)&&(size<=40))
                {
                    Main.ARRAY_LENGHT=size;
                    Main.ARRAY_MAX_VALUE = size * 5;
                    Main.sceltaInputOptions(method);
                    myStage.close();
                    myPrimaryStage.show();
                    return;
                }
                {
                    System.out.println("input non validi");
                    btn.setDisable(false);
                    return;
                }
            }
        });
        inputContainer.getChildren().addAll(container1, container2, btn);
    }

    public void createGraphics()
    {
        setOptionsContainer();
        setOptionsContainerTutorial();
        setInputContainer();
        setInputContainerDetail();
        if(inputContainer!=null && optionsContainer!=null)
        {
            mainContainer = new HBox();
            mainContainer.setPadding(new Insets(15, 15, 15, 15));
            mainContainer.setSpacing(15);
            mainContainer.setStyle("-fx-background-color: rgb(60, 63, 65);");
            mainContainer.getChildren().addAll(inputContainer, optionsContainer);
        }
        setContainer();
        setStage();
    }


    Options(Stage primary, Stage dialog)
    {
        myPrimaryStage = primary;
        myStage = dialog;
        myStage.initOwner(primary);
        createGraphics();
    }

//**********************************************************************************************************************
//**********************************************************************************************************************
//**********************************************************************************************************************
//**********************************************************************************************************************

    public static StringBuilder getTutorialText()
    {
        StringBuilder info = new StringBuilder();
        info.append("INFORMAZIONI UTILI \n");
        info.append("\n");
        info.append("-------------------------------------------");
        info.append("\n");info.append("\n");
        info.append("Per un'esecuzione ottimale del programma si consiglia di verificare che: \n");info.append("\n");
        info.append("sia presente il un file chiamato 'input.txt' collocato nella directory in cui si sta eseguendo il programma");
        info.append("\n");
        info.append("( si ricorda che la presenza di questo file non impedisce l'esecuzione del programma ma non ");
        info.append("\n");
        info.append("permette di generare l'array di partenza a partire da un file di testo ) ");
        info.append("\n");
        return info;
    }

}
