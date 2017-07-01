package sample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;

public class Input
{

    // 1: random array generated automatically
    public static void createInput(int numberOfElements, int maxNumberReached)
    {
        Main.arrayInput= new int[numberOfElements];
        try
        {
            Main.arrayInput=Utilities.createRandomArray(numberOfElements, maxNumberReached);
        }
        catch(Exception ex)
        {
            System.out.println("Eccezzione Generata --> " + ex.getLocalizedMessage());
        }
    }

    // 2: array generated by user input
    /*Input(int numberOfElements)
    {
        this.array= new int[numberOfElements];
        try
        {
            this.array=Utilities.inputArray(numberOfElements);
            if(Utilities.checkErrorWhileGeneratingArray(this.array))
            {
                System.out.println(" ************************** AVVISO **************************");
                System.out.println(" l'array inserito contiene elmeneti duplicati. Per testare al " +
                                   " meglio l'efficienza del software re-inserire una sequenza senza duplicati  ");
                System.out.println(" ************************************************************");
            }
        }
        catch(Exception ex)
        {
            System.out.println("Eccezzione Generata --> " + ex.getLocalizedMessage());
        }
    }*/

    // 2: array generated by user input
    public void createInput(int[] a)
    {
        Main.arrayInput= new int[a.length];
        for(int i=0; i<a.length; i++)
        {
            Main.arrayInput[i]=a[i];
        }
    }



    // 3: array readed by text file
    public static void createInput(File file)
    {
        int i=0;
        int cont=-1;
        String line = "";
        try
        {
            FileInputStream fis = new FileInputStream(file);
            FileInputStream fileinputStream = new FileInputStream(file);
            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fileinputStream));
            // set array dimension
            while (br.readLine()!= null)
            {
                cont++;
            }
            br.close();
            if(cont>40)
                cont=40;
            Main.arrayInput= new int[cont];
            line = "";
            while (i<cont)
            {
                try
                {
                    line = line = buffer.readLine();
                    Main.arrayInput[i] = Integer.parseInt(line);
                    //System.out.println(line);
                    i++;
                }
                catch (NumberFormatException ex)
                {
                    System.out.println("Eccezione generata dalla conversione dei valori nel file di testo --> " + ex.getLocalizedMessage());
                }
            }
            buffer.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Eccezione generata --> " + ex.getMessage());
        }
        catch (IOException ex) {
            System.out.println("Eccezione generata --> " + ex.getMessage());
        }
    }

    Input()
    {
        Main.arrayInput=new int[Main.ARRAY_LENGHT];
    }

}
