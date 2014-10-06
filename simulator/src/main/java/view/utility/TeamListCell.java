package view.utility;

import view.gamestates.TeamSettingsState2;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


public class TeamListCell extends ListCell<String> {

	private final String DEFAULT_NAME = "Choose Your Captain";
	private TeamSettingsState2 root;
	private Configuration config;
	private String currentValue;
	
	public TeamListCell(TeamSettingsState2 root, Configuration config) {
		this.root = root;
		this.config = config;
		this.currentValue = DEFAULT_NAME;
	}
	
	 @Override
    public void updateItem(String obj, boolean empty) {
        super.updateItem(obj, empty);
        
        if(empty){
            setText(null);
            setGraphic(null);
        } 
        else{
            setText(obj.toString());
 
            ColorPicker picker = new ColorPicker();
    		picker.setStyle("-fx-color-label-visible: false;");    		
            
    		ObservableList<String> captains = FXCollections.observableArrayList(config.getCaptainNames());

    		final ComboBox<String> captainChooser = new ComboBox<String>(captains);
    		root.addChooser(captainChooser);
    		captainChooser.setValue(DEFAULT_NAME);

    		
    		captainChooser.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
					if(newValue == null)
						captainChooser.setValue(oldValue);
					else if(oldValue != null && oldValue != DEFAULT_NAME){
						config.getCaptainNames().add(oldValue);
						//config.getCurrentCaptainName().remove(oldValue);
					}
						
					
					
					currentValue = newValue;
					config.getCaptainNames().remove(newValue);
					config.getCurrentCaptainName().add(newValue);
					config.getCurrentCaptainName().remove(captainChooser.getValue());
					
					System.out.println(config.getCurrentCaptainName().size());
				}
			});
    		
    		captainChooser.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					captainChooser.setItems(FXCollections.observableArrayList(config.getCaptainNames()));
					captainChooser.setValue(currentValue);
				}
			});

            HBox box = new HBox(20);
            box.getChildren().addAll(picker, captainChooser);
            
            setGraphic(box);   
        }
    }
}
