package sample;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.sql.Time;
import java.util.ArrayList;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;


public class MergeSort
{
    public int[] helper;
    public StackPane[] helperNodes;
    public Graphics graphics;

// *********************************************************************************************************************
// *********************************************************************************************************************

    // MOTION
    public void mergeSortMotion(int[] array, SequentialTransition sq, ArrayList<StackPane> list)
    {
        // if array's size is 0 or 1
        if(array.length < 2)
            // We consider the array already sorted, no change is done
            return;

        // The size of the sub-arrays . Constantly changing .
        int step = 1;
        // startL - start index for left sub-array
        // startR - start index for the right sub-array
        int startL, startR;

        // step is the size of sub-arrays
        // step doubles every iteration
        // while the increment of step is '<' than array's length
        while(step < array.length)
        {
            // at each step, after the step increment, we re-set the index of each sub-arrays
            // left always start at 0
            startL = 0;
            // right always start at [step]
            startR = step;
            // while the lenght of right array is "<" than array's length
            while(startR + step < array.length)
            {
                // the right array end on = (startR + step)
                mergeSortArraysAlreadySortedMotion(array, list, startL, (startL + step), startR, (startR + step), sq);
                graphics.consoleWriteOutput("[startLeft="+startL+"]-[stopLeft="+(startL+step)+"]-[startRight="
                        +startR+"]-[stopRight="+(startR + step)+"]");
                // the next left array start at the end of last right array
                startL = startR + step;
                // the next right array start at the end current left array
                startR = startL + step;
            }
            graphics.consoleWriteOutput("- - - with step = " + step + " - - -" );

            // if the right vector contains a number of non-multiple elements of [step]
            if(startR < array.length)
            {
                // the right array ends on = array.length
                mergeSortArraysAlreadySortedMotion(array, list, startL, (startL + step), startR, (array.length), sq);
                graphics.consoleWriteOutput("[startLeft="+startL+"]-[stopLeft="+(startL+step)+"]-[startRight="
                        +startR+"]-[stopRight="+(startR + step)+"]");
            }
            step *= 2;
        }
        graphics.consoleWriteOutput("ARRAY ORDINATO CORRETTAMENTE");
        graphics.consoleWriteException("Nessuna eccezione generata durante l'ordinamento dell'array");
        new Thread(){
            @Override
            public void run(){
                Platform.runLater(() -> {
                    graphics.buttonStepByStep.setDisable(true);
                    graphics.buttonMotion.setDisable(true);
                });
            }
        }.start();
    }

    // MOTION
    public void mergeSortArraysAlreadySortedMotion(int[] array, ArrayList<StackPane> list, int startL, int stopL, int startR,
                                                   int stopR, SequentialTransition transition)
    {
        // Additional arrays needed for merging
        int[] right = new int[stopR - startR + 1];
        int[] left = new int[stopL - startL + 1];
        // additional arrays needed for transition
        StackPane[] rightPane = new StackPane[stopR - startR + 1];
        StackPane[] leftPane = new StackPane[stopL - startL + 1];

        // Copy the right elements to the additional right arrays
        for (int i = 0, k = startR; i < (right.length - 1); ++i, ++k) {
            right[i] = array[k];
            rightPane[i]=list.get(k);
        }
        // Copy the left elements to the additional left arrays
        for (int i = 0, k = startL; i < (left.length - 1); ++i, ++k) {
            left[i] = array[k];
            leftPane[i]=list.get(k);
        }

        // Adding sentinel values
        right[right.length - 1] = Main.ARRAY_MAX_VALUE;
        left[left.length - 1] = Main.ARRAY_MAX_VALUE;

        // Merging the two sorted arrays into the initial one
        graphics.consoleWriteOutput("Merge two sorted arrays into the initial one");
        for (int k = startL, m = 0, n = 0; k < stopR; ++k)
        {
            // k increment each iteration (regardless of which if's condition is run)
            if (left[m] <= right[n])
            {
                // if left element is '<' or '=' element
                // the min is left element
                array[k] = left[m];
                list.set(k, leftPane[m]);
                graphics.consoleWriteOutput("array["+k+"] = left["+m+"] = "+left[m]);
                // create transition for this cell
                // second parameter is the position of this element in the sorted array (=[k])
                // adds spacing of each cells
                transition.getChildren().add(MergeSort.move(leftPane[m], k * Main.SPACING));
                m++;
            }
            else
            {
                // if right element is '<' or '=' element
                // the min is right element
                array[k] = right[n];
                list.set(k, rightPane[n]);
                graphics.consoleWriteOutput("array["+k+"] = right["+m+"] = "+left[n]);
                // create transition for this cell
                // second parameter is the position of this element in the sorted array (=[k])
                // adds spacing of each cells
                transition.getChildren().add(MergeSort.move(rightPane[n], k * Main.SPACING));
                n++;
            }
        }

        // final transition which move-up each cells to new position
        ParallelTransition moveUp = new ParallelTransition();
        for (int k = startL, m = 0, n = 0; k < stopR; ++k)
        {
            // same check and iteraction of precedent loop
            if (left[m] <= right[n]) {
                TranslateTransition moveNodeUp = new TranslateTransition();
                // set the element
                moveNodeUp.setNode(leftPane[m]);
                // set duration
                moveNodeUp.setDuration(Main.SPEED);
                // set final position of sorted elements
                // SORT_GROUP_MOVE_DELTA is '>' than Main.SORT_GROUP_MARGIN_DELTA
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
        // adds all final cell transition to parent transition
        transition.getChildren().add(moveUp);
    }

// *********************************************************************************************************************
// *********************************************************************************************************************

    // STEP-BY-STEP
    public void mergeSortStep(int[] array, SequentialTransition sq, ArrayList<StackPane> list, int step)
    {
        graphics.buttonMotion.setDisable(true);
        // if array's size is 0 or 1
        if(array.length < 2)
            // We consider the array already sorted, no change is done
            return;

        // startL - start index for left sub-array
        // startR - start index for the right sub-array
        int startL, startR;

        // step is the size of sub-arrays
        // step doubles every iteration
        if(step < array.length)
        {
            startL = 0;
            startR = step;
            while(startR + step < array.length)
            {
                mergeSortArraysAlreadySortedStep(array, list, startL, (startL + step), startR, (startR + step), sq);
                graphics.consoleWriteOutput("[startLeft="+startL+"]-[stopLeft="+(startL+step)+"]-[startRight="
                        +startR+"]-[stopRight="+(startR + step)+"]");
                // the next left array start at the end of last right array
                startL = startR + step;
                startR = startL + step;
            }
            graphics.consoleWriteOutput("- - - with step = " + step + " - - - ");
            if(startR < array.length)
            {
                mergeSortArraysAlreadySortedStep(array, list, startL, (startL + step), startR, (array.length), sq);
                graphics.consoleWriteOutput("[startLeft="+startL+"]-[stopLeft="+(startL+step)+"]-[startRight="
                        +startR+"]-[stopRight="+(startR + step)+"]");
            }
        }
        else
        {
            graphics.consoleWriteOutput("ARRAY ORDINATO CORRETTAMENTE");
            graphics.consoleWriteException("PROGRAMMA TERMINATO CORRETTAMENTE");
            new Thread(){
                @Override
                public void run(){
                    Platform.runLater(() -> {
                        graphics.buttonStepByStep.setDisable(true);
                    });
                }
            }.start();
        }
    }

    // STEP-BY-STEP
    public void mergeSortArraysAlreadySortedStep(int[] array, ArrayList<StackPane> list, int startL, int stopL, int startR,
                                                 int stopR, SequentialTransition transition)
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
        graphics.consoleWriteOutput("Merge two sorted arrays into the initial one");
        for (int k = startL, m = 0, n = 0; k < stopR; ++k) {
            if (left[m] <= right[n]) {
                array[k] = left[m];
                list.set(k, leftPane[m]);
                graphics.consoleWriteOutput("array["+k+"] = left["+m+"] = "+left[m]);
                transition.getChildren().add(MergeSort.move(leftPane[m], k * Main.SPACING));
                m++;
            } else {
                array[k] = right[n];
                list.set(k, rightPane[n]);
                graphics.consoleWriteOutput("array["+k+"] = right["+m+"] = "+left[n]);
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

// *********************************************************************************************************************
// *********************************************************************************************************************

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
