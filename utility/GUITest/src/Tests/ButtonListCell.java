package Tests;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;

public class ButtonListCell extends ListCell<String> {
	
    @Override
    public void updateItem(String obj, boolean empty) {
        super.updateItem(obj, empty);
        
        if(empty){
            setText(null);
            setGraphic(null);
        } 
        else{
            setText(obj.toString());
 
            Button butt = new Button();
            butt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("clicked");
                }                            
            });
            setGraphic(butt);
        }
    }
}
