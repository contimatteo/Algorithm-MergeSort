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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;


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
        // set at center the HBox which contains the two main window (input, tutorial)
        container = new BorderPane();
        container.setCenter(mainContainer);
    }

    public void setStage()
    {
        // set the stage
        myStage.setTitle("MC.Merge-Sort");
        myStage.setResizable(false);
        myStage.setX(Main.windowX);
        myStage.setY(Main.windowY);
        myStage.setWidth(Main.windowWidth);
        myStage.setHeight(Main.windowHeight);
        dialogScene = new Scene(container, 300, 200);
        myStage.setScene(dialogScene);
        myStage.show();
    }

    public void setOptionsContainer()
    {
        // Options Container
        optionsContainer = new VBox();
        optionsContainer.setPrefWidth((Main.windowWidth/2)-15);
        optionsContainer.setStyle("-fx-background-color:rgb(43, 43, 43);");
        optionsContainer.setAlignment(Pos.CENTER_RIGHT);
        optionsContainer.setSpacing(10);
    }

    public void setOptionsContainerTutorial()
    {
        // Info for generate a correct input txt files
        tutorial = new Label();
        tutorial.setTextFill(Color.AZURE);
        tutorial.setAlignment(Pos.CENTER);
        // retrieve the text of "tutorial"
        String text = (getTutorialText()).toString();
        tutorial.setText(text);
        tutorial.setTextAlignment(TextAlignment.CENTER);
        tutorial.setMinWidth((Main.windowWidth/2)-15);
        tutorial.setWrapText(true);
        optionsContainer.getChildren().add(tutorial);
    }


    public void setInputContainer()
    {
        // container for input options
        inputContainer = new VBox();
        inputContainer.setPrefWidth((Main.windowWidth/2)-15);
        inputContainer.setStyle("-fx-background-color:rgb(43, 43, 43);");
        inputContainer.setAlignment(Pos.CENTER_LEFT);
        inputContainer.setSpacing(30);
    }

    public void setInputContainerDetail()
    {
        // container --> size of array
        VBox container1 = new VBox();
        container1.setAlignment(Pos.CENTER);
        container1.setPrefWidth((Main.windowWidth/2)-15);
        container1.setSpacing(25);
        Label inputTitle = new Label("SCEGLIERE LA DIMENSIONE DELL'ARRAY");
        Label inputSubTitle = new Label(" attenzione per ragioni grafiche non è possibile inserire " +
                                             " una dimensione superiore a " + Integer.toString(Main.ARRAY_LENGHT));
        inputTitle.setTextFill(Color.AZURE);
        inputSubTitle.setTextFill(Color.AZURE);
        inputTitle.setAlignment(Pos.CENTER);
        // input for retrieve array's size
        inputArraySize = new TextField ();
        inputArraySize.setText("20");
        inputArraySize.setMaxWidth(150);
        container1.getChildren().addAll(inputTitle, inputSubTitle, inputArraySize);

        // container --> handle input options
        VBox container2 = new VBox();
        container2.setAlignment(Pos.CENTER);
        container2.setPrefWidth((Main.windowWidth/2)-15);
        container2.setSpacing(25);
        Label arrayTitle = new Label("SCEGLIERE UN METODO DI INPUT");
        Label arraySubTitle = new Label(" attenzione inserire un numero compreso tra 1 e 3 \n" +
                " 1: generazione automatica di un array random \n" +
                " 2: ricavo l'array dal file input.txt \n " +
                " 3: inserimento manuale dei numeri (dimensione < "+ Integer.toString(Main.ARRAY_LENGHT) +")");
        arrayTitle.setTextFill(Color.AZURE);
        arraySubTitle.setTextFill(Color.AZURE);
        arrayTitle.setAlignment(Pos.CENTER);
        // input for retrieve the input options
        inputMethod = new TextField ();
        inputMethod.setText("1");
        inputMethod.setMaxWidth(150);
        // input for retrieve the textfile's name
        Label fileNameLabel = new Label("imposta il nome del file di testo che hai creato(senza estensione)");
        fileNameLabel.setTextFill(Color.AZURE);
        fileNameLabel.setAlignment(Pos.CENTER);
        TextField fileInputName = new TextField();
        fileInputName.setText("default");
        fileInputName.setMaxWidth(150);
        // disable because default options is = 1
        fileInputName.setDisable(true);
        // add elements to container2 after the creation of start button
        // because event handler use instance of start button and
        // at this point btn not exist

        // container --> start the program
        VBox container3 = new VBox();
        container3.setAlignment(Pos.CENTER);
        container3.setPrefWidth((Main.windowWidth/2)-15);
        container3.setSpacing(15);
        Label buttonInstruction = new Label("click su start per avviare il programma");
        buttonInstruction.setTextFill(Color.AZURE);
        buttonInstruction.setAlignment(Pos.CENTER);
        // button for launch program
        btn = new Button();
        btn.setText(" Start ");
        btn.setMinWidth(50);
        btn.setAlignment(Pos.CENTER);
        size =Main.ARRAY_LENGHT;   method = 1;
        btn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                // default --> activate the button
                btn.setDisable(true);
                try
                {
                    // get array's size in input
                    size = Integer.parseInt(inputArraySize.getText());
                    // get input's option in input
                    method = Integer.parseInt(inputMethod.getText());
                    // get textfile's name in input
                    file = fileInputName.getText();
                }
                catch (NumberFormatException ex)
                {
                    System.out.println("Eccezione generata nel recuperare lunghezza dell'array, opzione di input e nome del file --> " + ex.getMessage());
                }
                // check if input are correct
                if((method>0)&&(method<4)&&(size>0)&&(size<=Main.ARRAY_LENGHT))
                {
                    // set file name
                    Main.fileName=file;
                    // set array's size
                    Main.ARRAY_LENGHT=size;
                    // set array max value
                    Main.ARRAY_MAX_VALUE = size * 5;
                    // input option = 2 --> textfile
                    if(method==2)
                    {
                        // check if file exist
                        File file = new File(Main.fileName + ".txt");
                        try {
                            File dir = new File(".");
                            file = new File(dir.getCanonicalPath() + File.separator + Main.fileName + ".txt");
                        }
                        catch (IOException ex)
                        {
                            System.out.println("Eccezzione generata --> " + ex.getMessage());
                        }
                        finally
                        {
                            // if exception is generated show error message
                            if(file.getTotalSpace()==0) {
                                // method equals to 2 but file input.txt not found
                                // set method = 1 and continue the program
                                method = 1;
                                Alert alert = new Alert(AlertType.WARNING);
                                alert.setTitle("Attenzione");
                                alert.setHeaderText("Il file scelto non è stato trovato");
                                alert.setContentText("L'algoritmo procederà automaticamente generando un vettore causale");
                                alert.showAndWait();
                            }
                        }
                    }
                    // input option = 2 --> textfile
                    if((method==3))
                    {
                        // show dialog for input array's numbers
                        TextInputDialog dialog = new TextInputDialog("1-2");
                        dialog.setTitle("Inserimento manuale ");
                        dialog.setHeaderText("Inserisci i numeri separati da un '-' ");
                        Optional<String> result = dialog.showAndWait();
                        int[] a = null;
                        // <boolean> for check if all goes ok
                        boolean allGoesOk=false;
                        // when dialog is closed with ok
                        if (result.isPresent())
                        {
                            // get string of numbers
                            String stringArray = result.get();
                            // delete spaces
                            stringArray = stringArray.replaceAll("\\s+","");
                            // split array on '-'
                            String[] numbersOfArray = stringArray.split("-");
                            if(numbersOfArray.length>1)
                            {
                                // check array input size
                                int length = numbersOfArray.length;
                                // not allow array's size > Main.ARRAY_LENGHT
                                if(length>Main.ARRAY_LENGHT)
                                    length=Main.ARRAY_LENGHT;
                                // instance my array
                                a = new int[length];
                                for (int i = 0; i < a.length; i++)
                                {
                                    // for each element splitted try conversion from <string> to <int>
                                    try
                                    {
                                        a[i] = Integer.parseInt(numbersOfArray[i]);
                                    }
                                    catch (NumberFormatException ex)
                                    {
                                        System.out.println("Eccezione generata nella conversione dei numeri in input nel dialog box" + ex.getMessage());
                                    }
                                    finally
                                    {
                                        // error while converting numbers
                                        if (a.length < 2)
                                        {
                                            Alert alert = new Alert(AlertType.WARNING);
                                            alert.setTitle("Attenzione");
                                            alert.setHeaderText("Errore nell'inserimento dell'array");
                                            alert.setContentText("Non è possibile creare l'array inserito controllare bene di aver inserito solo numeri " +
                                                                 "e di aver rispettato tutte le specifiche. (inserire almeno 2 numeri)");
                                            alert.showAndWait();
                                        }
                                        // conversion successful
                                        if(a.length > 1)
                                            allGoesOk = true;
                                    }
                                }
                            }
                        }
                        // conversion successful
                        if(allGoesOk)
                        {
                            Main.sceltaInputOptions(method, a);
                            myStage.close();
                            myPrimaryStage.show();
                            return;
                        }
                        // error on converting numbers
                        else
                        {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Attenzione");
                            alert.setHeaderText("Errore nell'inserimento dell'array");
                            alert.setContentText("E' possibile inserire un array di soli numeri");
                            alert.showAndWait();
                            // allow user to change input options and re-click on start button
                            btn.setDisable(false);
                            // call the function and block the launch of the graphics interface due to an input eraser
                            Main.sceltaInputOptions(0, null);
                        }
                    }
                    // input option is 1 or 2
                    else
                    {
                        Main.sceltaInputOptions(method, null);
                        myStage.close();
                        myPrimaryStage.show();
                        return;
                    }
                }
                else
                {
                    // wrong input options or size
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Attenzione");
                    alert.setHeaderText("Input non validi");
                    alert.setContentText("Leggere attentamente le istruzioni e assicurarsi di non inserire una dimensione superiore a " +
                            Integer.toString(Main.ARRAY_LENGHT)  +" e" +
                                         " e di scegliere tra le opzioni [1, 2, 3]");
                    alert.showAndWait();
                    // allow user to change input options and re-click on start button
                    btn.setDisable(false);
                    return;
                }
            }
        });

        // handle the input options event (set listener to text property)
        inputMethod.textProperty().addListener((observable, oldValue, newValue) ->
        {
            try
            {
                // default --> allow click on start
                btn.setDisable(false);
                // parse from <string> to <int> the option selected
                int option = Integer.parseInt(newValue);
                if(option==1)
                {
                    // show input size
                    inputArraySize.setDisable(false);
                    // hide input file
                    fileInputName.setDisable(true);
                }
                if(option==2)
                {
                    // hide input size
                    inputArraySize.setDisable(true);
                    // show input file
                    fileInputName.setDisable(false);
                }
                if(option==3)
                {
                    // hide input size
                    inputArraySize.setDisable(true);
                    // hide input file
                    fileInputName.setDisable(true);
                }

                // if option is incorrect block the start button
                if((option<1)||(option>3))
                    btn.setDisable(true);

            }
            catch (NumberFormatException ex)
            {
                System.out.println("Eccezione generata -->" + ex.getMessage());
            }
        });
        // add input to their parent container
        container2.getChildren().addAll(arrayTitle, arraySubTitle, inputMethod, fileNameLabel, fileInputName);
        // add button to his parent container
        container3.getChildren().addAll(buttonInstruction, btn);
        // set the input container
        inputContainer.getChildren().addAll(container1, container2, container3);
    }

    public void createGraphics()
    {
        setOptionsContainer();
        setOptionsContainerTutorial();
        setInputContainer();
        setInputContainerDetail();
        // prevent NullPointerException
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
        info.append("\n");
        info.append("-------------------------------------------");
        info.append("\n");
        info.append("Per un'esecuzione ottimale del programma si consiglia di verificare che: ");
        info.append("\n"); info.append("\n");
        info.append("sia presente il un file '.txt' collocato nella directory in cui si sta eseguendo il programma");
        info.append("\n");info.append("\n");
        info.append("si ricorda che la presenza di questo file non impedisce l'esecuzione del programma ma non ");
        info.append("\n");
        info.append("permette di generare l'array di partenza a partire da un file di testo (opzione 2)");
        info.append("\n"); info.append("\n");
        info.append("Nel caso di input da file di testo verranno considerati al massimo i primi"+ Integer.toString(Main.ARRAY_LENGHT) +"numeri");
        info.append("\n"); info.append("\n");
        info.append("Nel caso di input da tastiera verranno considerati i primi "+ Integer.toString(Main.ARRAY_LENGHT) +" numeri");
        info.append("\n"); info.append("\n");
        info.append("Per tutte le opzioni è possibile decidere la dimensione (minore di "+ Integer.toString(Main.ARRAY_LENGHT) +") dell'array generato");
        info.append("\n"); info.append("\n");
        info.append("-------------------------------------------");
        info.append("\n");
        info.append("ATTENZIONE !! \n"); info.append("\n");
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