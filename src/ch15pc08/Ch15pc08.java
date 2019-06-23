package ch15pc08;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * Digital Clock that acts as a stopwatch
 * @author FRANK.OLSON
 */
public class Ch15pc08 extends Application {
    // Enum
    private enum Status {
        STOPPED, PAUSE, RUNNING
    }
    
    // Variables and fields
    Status currentStatus = Status.STOPPED;
    DigitalClockWorker digitalClockWorker;
    
    /**
     * Method to start the application
     * @param primaryStage Stage The stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        // Set the Title of the application
        primaryStage.setTitle("Digital Clock");
        
        // Instantiate and set components
        Text textSceneTitle = new Text("Game Clock");
        textSceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        TextField textfieldTime = new TextField();
        textfieldTime.setEditable(false);
        textfieldTime.setText("00:00:00.000");
        
        Button buttonStartStop = new Button();
        buttonStartStop.setText("Start");
        
        ToggleButton togglebuttonPause = new ToggleButton();
        togglebuttonPause.setText("Pause");
        togglebuttonPause.setDisable(true);
        
        Button buttonReset = new Button();
        buttonReset.setText("Reset");
        buttonReset.setDisable(true);
        
        Button buttonQuit = new Button();
        buttonQuit.setText("Quit");
        
        // Handle the Start/Stop button events
        buttonStartStop.setOnAction(arg0 -> {
            if (currentStatus == Status.STOPPED) {
                // Change the text of the Start/Stop button
                buttonStartStop.setText("Stop");
                
                // Enable the Pause and Reset buttons
                togglebuttonPause.setDisable(false);
                buttonReset.setDisable(false);
                
                // Set the current status to running
                currentStatus = Status.RUNNING;
                
                // Instantiate the DigitalClockWorker class
                digitalClockWorker = new DigitalClockWorker();
                
                // Bind the DigitalClockWorker Pause property to the toggle button Pause
                togglebuttonPause.selectedProperty().bindBidirectional(digitalClockWorker.pauseProperty());
                
                // Instantiate a Thread with the DigitalClockWorker
                Thread thread = new Thread(digitalClockWorker);
                
                // Bind the digitalClockWorker message property to the textfield Time
                textfieldTime.textProperty().bind(digitalClockWorker.messageProperty());
                
                // Set the thread Daemon and start it
                thread.setDaemon(true);
                thread.start();
            } else if (currentStatus == Status.RUNNING) {
                // Change the text of the Start/Stop button
                buttonStartStop.setText("Start");
                
                // Disable the Pause toggle button
                togglebuttonPause.setDisable(true);
                
                // Set the current status to stopped
                currentStatus = Status.STOPPED;
                
                // Stop the digitalClockWorker and set it to null
                digitalClockWorker.stop();
                digitalClockWorker = null;
            }
        });
        
        // Handle the Reset button events
        buttonReset.setOnAction(arg0 -> {
            // Unbind the textfield Time and reset text
            textfieldTime.textProperty().unbind();
            textfieldTime.setText("00:00:00.000");
            
            // If the clock is running, stop it and reset the fields
            if (currentStatus == Status.RUNNING) {
                // Change the text of the Start/Stop button
                buttonStartStop.setText("Start");
                
                // Disable the Pause and Reset buttons
                togglebuttonPause.setDisable(true);
                buttonReset.setDisable(true);
                
                // Set the current status to stopped
                currentStatus = Status.STOPPED;
                
                // Stop the digitalClockWorker and set it to null
                digitalClockWorker.stop();
                digitalClockWorker = null;
            }
        });
        
        // Handle the Quit button events
        buttonQuit.setOnAction(arg0 -> {
            primaryStage.close();
        });
        
        // Instantiate HBox and add components
        HBox hbox = new HBox();
        hbox.getChildren().addAll(buttonStartStop, togglebuttonPause, buttonReset, buttonQuit);
        
        // Instantiate GridPane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        grid.add(textSceneTitle, 0, 1);
        grid.add(textfieldTime, 0, 2);
        grid.add(hbox, 0, 3);
        
        // Instantiate Scene and add to primary stage
        Scene scene = new Scene(grid, 400, 120);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
