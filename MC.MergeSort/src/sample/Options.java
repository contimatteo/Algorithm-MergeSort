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
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


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
    public  Button      btn;

    //******************************************************************************************************************
    int size, method; String file;
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

        Label fileNameLabel = new Label("imposta il nome del file di testo che hai creato(senza estensione)");
        fileNameLabel.setTextFill(Color.AZURE);
        fileNameLabel.setAlignment(Pos.CENTER);
        TextField fileInputName = new TextField();
        fileInputName.setText("default");
        fileInputName.setMaxWidth(150);
        // disable because default options is = 1
        fileInputName.setDisable(true);
        // add elements to container after the creation of start button
        // because event handler use instance of start button and
        // at this point btn not exist

        VBox container3 = new VBox();
        container3.setAlignment(Pos.CENTER);
        container3.setPrefWidth((Main.windowWidth/2)-15);
        container3.setSpacing(15);
        Label buttonInstruction = new Label("click su start per avviare il programma");
        buttonInstruction.setTextFill(Color.AZURE);
        buttonInstruction.setAlignment(Pos.CENTER);
        btn = new Button();
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
                    file = fileInputName.getText();
                }
                catch (NumberFormatException ex)
                {   }
                if((method>0)&&(method<3)&&(size>0)&&(size<=40))
                {
                    Main.fileName=file;
                    Main.ARRAY_LENGHT=size;
                    Main.ARRAY_MAX_VALUE = size * 5;
                    File file = new File(Main.fileName + ".txt");
                    if(method==2) {
                        try {
                            File dir = new File(".");
                            file = new File(dir.getCanonicalPath() + File.separator + Main.fileName + ".txt");
                        }
                        catch (IOException ex) {
                            System.out.println("Eccezzione generata --> " + ex.getMessage());
                        }
                        finally
                        {
                            if(file.getTotalSpace()==0)
                                // method equals to 2 but file input.txt not found
                                method = 1;
                        }
                    }

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

        inputMethod.textProperty().addListener((observable, oldValue, newValue) ->
        {
            try
            {
                btn.setDisable(false);
                int option = Integer.parseInt(newValue);
                if(option==1)
                    fileInputName.setDisable(true);
                if(option==2)
                    fileInputName.setDisable(false);
                if((option<1)||(option>2))
                    btn.setDisable(true);

            }
            catch (NumberFormatException ex)
            {   }
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });
        container2.getChildren().addAll(arrayTitle, arraySubTitle, inputMethod, fileNameLabel, fileInputName);
        container3.getChildren().addAll(buttonInstruction, btn);

        inputContainer.getChildren().addAll(container1, container2, container3);
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
        info.append("INFORMAZIONI UTILI");
        info.append("\n"); info.append("\n");
        info.append("-------------------------------------------");
        info.append("\n");info.append("\n");
        info.append("Per un'esecuzione ottimale del programma si consiglia di verificare che: ");
        info.append("\n"); info.append("\n");
        info.append("sia presente il un file chiamato 'input.txt' collocato nella directory in cui si sta eseguendo il programma");
        info.append("\n");info.append("\n");
        info.append("si ricorda che la presenza di questo file non impedisce l'esecuzione del programma ma non ");
        info.append("\n");
        info.append("permette di generare l'array di partenza a partire da un file di testo (opzione 2)");
        info.append("\n"); info.append("\n");
        info.append("Inoltre nel caso di input da file di testo verranno considerati al massimo i primi 40 numeri");
        info.append("\n"); info.append("\n");
        info.append("Nel caso in cui venisse scelta l'opzione due ma il file non fosse presente l'algoritmo passerà");
        info.append("\n");
        info.append("direttamente all'opzione 1 (array generato casualmente)");
        info.append("\n"); info.append("\n");
        info.append("Per entrambe le opzioni è possibile decidere la dimensione (minore di 40) dell'array generato");
        info.append("\n"); info.append("\n");info.append("\n");
        info.append("-------------------------------------------");
        info.append("\n");info.append("\n");
        info.append("ATTENZIONE !! \n"); info.append("\n");info.append("\n");
        info.append("Istruzioni per la creazione del file di testo:");
        info.append("\n"); info.append("\n");
        info.append("IL FILE DEVE AVERE ESTENSIONE TXT");
        info.append("\n"); info.append("\n");
        info.append("ogni riga rappresenta una posizione dell'array quindi per inserire un numero basta inserirlo in ");
        info.append("\n");
        info.append("una riga vuota e andare a capo. I numeri verranno inseriti nell'array nell'ordine in cui sono");
        info.append("\n");
        info.append("stati inseriti nel file (l'elemento nella prima riga del file sarà in posizione zero nell'array)");
        info.append("\n"); info.append("\n");
        info.append("TERMINARE IL FILE DI TESTO CON UNO ZERO SU UNA NUOVA RIGA");
        info.append("\n");
        info.append("se non si effettua questa operazione l'algoritmo scarterà automaticamente l'ultimo numero");
        info.append("\n");
        return info;
    }

}
