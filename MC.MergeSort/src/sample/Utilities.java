package sample;

import java.util.*;

/**
 * Created by matteoconti on 27/06/17.
 */
public class Utilities {

    public static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

    public static void printArray(int[] array)
    {
        System.out.println(" ");
        System.out.println(" *********************** STAMPA ARRAY ***********************");
        for (int i = 0; i < (array.length); i++)
            System.out.println(" posizione = " + i  + " --> " + "[" + array[i] + "] ");
        System.out.println(" ************************************************************");
        System.out.println(" ");
    }




    static public boolean isContained(int[] a, int n)
    {
        boolean response = false;
        for (int i = 0; i < (a.length); i++) {
            if(a[i]==n)
            {
                //System.out.println("ho trovato un problema in posizione [" + (i) + "] per il numero [" + n +"]");
                return true;
            }
        }
        return response;
    }

    static public boolean checkErrorWhileGeneratingArray(int[] a)
    {
        boolean response = false;
        for (int i = 0; i < (a.length-1); i++)
        {
            for (int j= 1; j < (a.length); j++)
            {
                if(i!=j)
                    if (a[i]==a[j])
                        return true;
            }
        }
        return response;
    }


    static public int[] createRandomArray(int numberOfElements, int maxNumberReached)
    {
        int n=0;
        int[] duplicate = new int[numberOfElements];
        int[] array = new int[numberOfElements];
        duplicate[0]=0;
        if(numberOfElements>0)
        {
            for (int i = 0; i < (numberOfElements); i++)
            {
                n = (int) (Math.random() * (maxNumberReached) + 1);
                if(!(isContained(duplicate, n)))
                {
                    array[i] = n;
                    duplicate[i] = n;
                }
                else
                    i--;
            }
            System.out.println(" ");
            if(checkErrorWhileGeneratingArray(array))
            {
                clearConsole();
                System.out.print("ERRORE NELLA GENERAZIONE DI UN ARRAY DI NUMERI CASUALI");
                createRandomArray(numberOfElements, maxNumberReached);
            }
            //printArray(array);
        }
        return array;
    }

    public static int[] inputArray(int numberOfElements)
    {
        int[] array = new int[numberOfElements];
        int n=0;
        Scanner input = new Scanner(System.in);
        if(numberOfElements>0) {
            System.out.println("Inserisci " + numberOfElements + " numeri interi");
            for (int i = 0; i < (numberOfElements); i++) {
                try {
                    System.out.print("input>>");
                    n = input.nextInt();
                    array[i]=n;
                }
                catch(Exception ex)
                {
                    System.out.println("Eccezzione generata nell'inserimento di un numero in posizione = " + (i-1));
                    System.out.println("Eccezzione --> " + ex.getLocalizedMessage());
                    break;
                }
            }
        }
        //printArray(array);
        return array;
    }

}
