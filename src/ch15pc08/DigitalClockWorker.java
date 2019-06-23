package ch15pc08;
import java.time.Duration;
import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;

/**
 * 
 * @author frank
 */
public class DigitalClockWorker extends Task<Void> {
    // Fields
    private BooleanProperty stop = new SimpleBooleanProperty(false);
    private LocalDateTime startDateTime;
    private LocalDateTime stopDateTime;
    private BooleanProperty pause = new SimpleBooleanProperty(false);
    
    /**
     * 
     * @return
     * @throws Exception 
     */
    @Override
    protected Void call() throws Exception {
        
        // Instantiate startDateTime
        startDateTime = LocalDateTime.now();
        
        // Loop to increment time
        while (!stop.getValue()) {
            if (!pause.getValue()) {
                // Instantiate stopDateTime, Duration, and variables
                stopDateTime = LocalDateTime.now();
                Duration duration = Duration.between(startDateTime, stopDateTime);
                
                long hours = Math.max(0, duration.toHours());
                long minutes = Math.max(0, duration.toMinutes() - 60 * duration.toHours());
                long seconds = Math.max(0, duration.getSeconds() - 60 * duration.toMinutes());
                long millis = Math.max(0, duration.toMillis() - 1000 *duration.getSeconds());
                
                String format = "%02d";
                
                updateMessage(String.format(format, hours) + ":" +
                        String.format(format, minutes) + ":" +
                        String.format(format, seconds) + "." +
                        String.format(format, millis));
                Thread.sleep(3);
            }
        }
        return null;
    }
    
    // Accessors, Mutators, and Methods
    /**
     * Get the pause value
     * @return boolean The pause value
     */
    public boolean getPause() {
        return pause.getValue();
    }
    
    /**
     * Get the stop value
     * @return boolean The stop value
     */
    public boolean getStop() {
        return stop.getValue();
    }
    
    /**
     * Get the startDateTime
     * @return LocalDateTime The startDateTime
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    
    /**
     * Get the stopDateTime
     * @return LocalDateTime The stopDateTime
     */
    public LocalDateTime getStopDateTime() {
        return stopDateTime;
    }
    
    /**
     * Get the pause property
     * @return BooleanProperty The pause property
     */
    public BooleanProperty pauseProperty() {
        return pause;
    }
    
    /**
     * Get the stop property
     * @return BooleanProperty The stop property
     */
    public BooleanProperty stopProperty() {
        return stop;
    }
    
    /**
     * Set the pause value
     * @param pause boolean The pause value
     */
    public void setPause(boolean pause) {
        this.pause.setValue(pause);
    }
    
    /**
     * Set the stop value
     * @param stop boolean The stop value
     */
    public void setStop(boolean stop) {
        this.stop.setValue(stop);
    }
    
    /**
     * Stop the clock
     */
    public void stop() {
        setStop(true);
    }
}
