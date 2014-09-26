package view.events;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class HoverEvent implements EventHandler<MouseEvent> {

	private String value;
	private Text text;
	
	public HoverEvent(Text text, String value){
		this.value = value;
		this.text = text;
	}
	
	@Override
	public void handle(MouseEvent arg0) {
		text.setText(value);
	}
}
