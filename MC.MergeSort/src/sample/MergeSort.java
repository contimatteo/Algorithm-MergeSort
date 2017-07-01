package sample;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.sql.Time;
import java.util.ArrayList;


public class MergeSort
{
    public int[] helper;
    public StackPane[] helperNodes;
    public Graphics graphics;


    // *****************************************************************************************************************
    // *****************************************************************************************************************
    // *****************************************************************************************************************
    // *****************************************************************************************************************

    // MOTION
    public void mergeSortMotion(int[] array, SequentialTransition sq, ArrayList<StackPane> list)
    {
        if(array.length < 2)
            // We consider the array already sorted, no change is done
            return;

        // The size of the sub-arrays . Constantly changing .
        int step = 1;
        // startL - start index for left sub-array
        // startR - start index for the right sub-array
        int startL, startR;

        while(step < array.length) {
            startL = 0;
            startR = step;
            while(startR + step < array.length)
            {
                graphics.consoleWriteOutput("startL"+startL);
                graphics.consoleWriteException(">> eccezzione");
                mergeSortArraysAlreadySortedMotion(array, list, startL, (startL + step), startR, (startR + step), sq);
                //System.out.printf("startL=%d, stopL=%d, startR=%d, stopR=%d", startL, startL + step, startR, startR + step);
                //System.out.println(" ");
                startL = startR + step;
                startR = startL + step;
            }
            //System.out.printf("- - - with step = %d", step);
            //System.out.println(" ");
            //if(startR < array.length && (startR + step) < array.length)
            if(startR < array.length)
            {
                graphics.consoleWriteOutput("startR"+startR);
                mergeSortArraysAlreadySortedMotion(array, list, startL, (startL + step), startR, (array.length), sq);
                //System.out.printf("* startL=%d, stopL=%d, startR=%d, stopR=%d", startL, startL + step, startR, array.length);
                //System.out.println(" ");
                //System.out.println(" ");
            }
            step *= 2;
        }
        graphics.consoleWriteOutput(" ARRAY ORDINATO CORRETTAMENTE");
        graphics.consoleWriteException(" PROGRAMMA TERMINATO CORRETTAMENTE");
    }

    // MOTION
    public void mergeSortArraysAlreadySortedMotion(int[] array, ArrayList<StackPane> list, int startL, int stopL, int startR, int stopR, SequentialTransition transition)
    {
        // Additional arrays needed for merging
        int[] right = new int[stopR - startR + 1];
        int[] left = new int[stopL - startL + 1];
        StackPane[] rightPane = new StackPane[stopR - startR + 1];
        StackPane[] leftPane = new StackPane[stopL - startL + 1];

        // Copy the elements to the additional arrays
        for (int i = 0, k = startR; i < (right.length - 1); ++i, ++k) {
            right[i] = array[k];
            rightPane[i]=list.get(k);
        }
        // Copy the elements to the additional arrays
        for (int i = 0, k = startL; i < (left.length - 1); ++i, ++k) {
            left[i] = array[k];
            leftPane[i]=list.get(k);
        }

        // Adding sentinel values
        right[right.length - 1] = Main.ARRAY_MAX_VALUE;
        left[left.length - 1] = Main.ARRAY_MAX_VALUE;

        // Merging the two sorted arrays into the initial one
        for (int k = startL, m = 0, n = 0; k < stopR; ++k) {
            if (left[m] <= right[n]) {
                array[k] = left[m];
                list.set(k, leftPane[m]);
                transition.getChildren().add(MergeSort.move(leftPane[m], k * Main.SPACING));
                m++;
            } else {
                array[k] = right[n];
                list.set(k, rightPane[n]);
                transition.getChildren().add(MergeSort.move(rightPane[n], k * Main.SPACING));
                n++;
            }
        }

        ParallelTransition moveUp = new ParallelTransition();
        for (int k = startL, m = 0, n = 0; k < stopR; ++k) {
            if (left[m] <= right[n]) {
                TranslateTransition moveNodeUp = new TranslateTransition();
                moveNodeUp.setNode(leftPane[m]);
                moveNodeUp.setDuration(Main.SPEED);
                // set final position of sorted elements
                moveNodeUp.setByY((-Main.SORT_GROUP_MOVE_DELTA) + (Main.SORT_GROUP_MARGIN_DELTA));
                moveUp.getChildren().add(moveNodeUp);
                m++;
            } else {
                TranslateTransition moveNodeUp = new TranslateTransition();
                moveNodeUp.setNode(rightPane[n]);
                moveNodeUp.setDuration(Main.SPEED);
                // set final position of sorted elements
                moveNodeUp.setByY((-Main.SORT_GROUP_MOVE_DELTA) + (Main.SORT_GROUP_MARGIN_DELTA));
                moveUp.getChildren().add(moveNodeUp);
                n++;
            }
        }
        transition.getChildren().add(moveUp);
    }

    // *****************************************************************************************************************
    // *****************************************************************************************************************
    // *****************************************************************************************************************
    // *****************************************************************************************************************

    // STEP-BY-STEP
    public void mergeSortStep(int[] array, SequentialTransition sq, ArrayList<StackPane> list, int step)
    {
        if(array.length < 2)
            // We consider the array already sorted, no change is done
            return;

        // startL - start index for left sub-array
        // startR - start index for the right sub-array
        int startL, startR;

        if(step < array.length) {
            startL = 0;
            startR = step;
            while(startR + step < array.length)
            {
                mergeSortArraysAlreadySortedStep(array, list, startL, (startL + step), startR, (startR + step), sq);
                //System.out.printf("startL=%d, stopL=%d, startR=%d, stopR=%d", startL, startL + step, startR, startR + step);
                //System.out.println(" ");
                startL = startR + step;
                startR = startL + step;
            }
            //System.out.printf("- - - with step = %d", step);
            //System.out.println(" ");
            //if(startR < array.length && (startR + step) < array.length)
            if(startR < array.length)
            {
                mergeSortArraysAlreadySortedStep(array, list, startL, (startL + step), startR, (array.length), sq);
                //System.out.printf("* startL=%d, stopL=%d, startR=%d, stopR=%d", startL, startL + step, startR, array.length);
                //System.out.println(" ");
                //System.out.println(" ");
            }
        }
        else
        {
            graphics.consoleWriteOutput(" ARRAY ORDINATO CORRETTAMENTE");
            graphics.consoleWriteException(" PROGRAMMA TERMINATO CORRETTAMENTE");
        }
    }

    // STEP-BY-STEP
    public void mergeSortArraysAlreadySortedStep(int[] array, ArrayList<StackPane> list, int startL, int stopL, int startR, int stopR, SequentialTransition transition)
    {
        // Additional arrays needed for merging
        int[] right = new int[stopR - startR + 1];
        int[] left = new int[stopL - startL + 1];
        StackPane[] rightPane = new StackPane[stopR - startR + 1];
        StackPane[] leftPane = new StackPane[stopL - startL + 1];

        // Copy the elements to the additional arrays
        for (int i = 0, k = startR; i < (right.length - 1); ++i, ++k) {
            right[i] = array[k];
            rightPane[i]=list.get(k);
        }
        // Copy the elements to the additional arrays
        for (int i = 0, k = startL; i < (left.length - 1); ++i, ++k) {
            left[i] = array[k];
            leftPane[i]=list.get(k);
        }

        // Adding sentinel values
        right[right.length - 1] = Main.ARRAY_MAX_VALUE;
        left[left.length - 1] = Main.ARRAY_MAX_VALUE;

        // Merging the two sorted arrays into the initial one
        for (int k = startL, m = 0, n = 0; k < stopR; ++k) {
            if (left[m] <= right[n]) {
                array[k] = left[m];
                list.set(k, leftPane[m]);
                transition.getChildren().add(MergeSort.move(leftPane[m], k * Main.SPACING));
                m++;
            } else {
                array[k] = right[n];
                list.set(k, rightPane[n]);
                transition.getChildren().add(MergeSort.move(rightPane[n], k * Main.SPACING));
                n++;
            }
        }

        ParallelTransition moveUp = new ParallelTransition();
        for (int k = startL, m = 0, n = 0; k < stopR; ++k)
        {
            if (left[m] <= right[n])
            {
                TranslateTransition moveNodeUp = new TranslateTransition();
                moveNodeUp.setNode(leftPane[m]);
                moveNodeUp.setDuration(Main.SPEED);
                // set final position of sorted elements
                moveNodeUp.setByY((-Main.SORT_GROUP_MOVE_DELTA) + (Main.SORT_GROUP_MARGIN_DELTA));
                moveUp.getChildren().add(moveNodeUp);
                m++;
            }
            else
            {
                TranslateTransition moveNodeUp = new TranslateTransition();
                moveNodeUp.setNode(rightPane[n]);
                moveNodeUp.setDuration(Main.SPEED);
                // set final position of sorted elements
                moveNodeUp.setByY((-Main.SORT_GROUP_MOVE_DELTA) + (Main.SORT_GROUP_MARGIN_DELTA));
                moveUp.getChildren().add(moveNodeUp);
                n++;
            }
        }
        transition.getChildren().add(moveUp);
    }

    // *****************************************************************************
    // *****************************************************************************
    // *****************************************************************************
    // ******************************************************************************************************************

    public static TranslateTransition move(StackPane sp, int X) {
        TranslateTransition t = new TranslateTransition();
        t.setNode(sp);
        t.setDuration(Main.SPEED);
        t.setToX(X);
        t.setToY(Main.SORT_GROUP_MOVE_DELTA);
        return t;
    }

    MergeSort(Graphics instanceOfGraphics)
    {
        helper = new int[Main.ARRAY_LENGHT];
        helperNodes = new StackPane[Main.ARRAY_LENGHT];
        graphics = new Graphics();
        graphics=instanceOfGraphics;
    }

}
