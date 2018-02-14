/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Text notificationText;
    
    @FXML
    private Button task1Button;
    
    @FXML
    private Button task2Button;
    
    @FXML
    private Button task3Button;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        System.out.println("start task 1");
        
        if (task1 == null) {
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
           
            task1.start();
        }else {
            task1.end();
            task1 = null;
           
        }
        
        notificationTextSetter();
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
        }
            
        textArea.appendText(message + "\n");
        notificationTextSetter();
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        System.out.println("start task 2");
        if (task2 == null) {
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
                if(message.equals("Task2 done.")){
                    task2 = null;
                    notificationTextSetter();
                }
            });
            
            
            task2.start();
        }else {
            task2.end();
            task2 = null;
           
        }

        notificationTextSetter();
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        System.out.println("start task 3");
        if (task3 == null) {
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
                
                if(evt.getNewValue().equals("Task3 done.")){
                    task3 = null;
                    notificationTextSetter();
                }
            });
            
            
            task3.start();
        }else {
            task3.end();
            task3 = null;
            
        }
        
        notificationTextSetter(); 
        
    }
    
    public void notificationTextSetter(){
        // 0 0 0
        if(task1 == null && task3 == null && task2 == null){
            notificationText.setText("No Tasks Running");
            task1Button.setText("Start Task 1");
            task2Button.setText("Start Task 2");
            task3Button.setText("Start Task 3");
        }
        //0 0 1
        else if(task1 == null && task3 == null && task2 != null){
            notificationText.setText("Task 2 Running");
            task1Button.setText("Start Task 1");
            task2Button.setText("End Task 2");
            task3Button.setText("Start Task 3");
        }
        // 0 1 0
        else if(task1 == null && task3 != null && task2 == null){
            notificationText.setText("Task 3 Running");
            task1Button.setText("Start Task 1");
            task2Button.setText("Start Task 2");
            task3Button.setText("End Task 3");
        }
        // 0 1 1
        else if(task1 == null && task3 != null && task2 != null){
            notificationText.setText("Task 2 and Task 3 Running");
            task1Button.setText("Start Task 1");
            task2Button.setText("End Task 2");
            task3Button.setText("End Task 3");
        }
        // 1 0 0
        else if(task1 != null && task3 == null && task2 == null){
            notificationText.setText("Task 1 Running");
            task1Button.setText("End Task 1");
            task2Button.setText("Start Task 2");
            task3Button.setText("Start Task 3");
        }
        // 1 0 1
        else if(task1 != null && task3 == null && task2 != null){
            notificationText.setText("Task 1 and Task 2 Running");
            task1Button.setText("End Task 1");
            task2Button.setText("End Task 2");
            task3Button.setText("Start Task 3");
        }
        // 1 1 0
        else if(task1 != null && task3 != null && task2 == null){
            notificationText.setText("Task 1 and Task 3 Running");
            task1Button.setText("End Task 1");
            task2Button.setText("Start Task 2");
            task3Button.setText("End Task 3");
        }
        // 1 1 1
        else if(task1 != null && task3 != null && task2 != null){
            notificationText.setText("All Tasks Running");
            task1Button.setText("End Task 1");
            task2Button.setText("End Task 2");
            task3Button.setText("End Task 3");
        }
    }
}
