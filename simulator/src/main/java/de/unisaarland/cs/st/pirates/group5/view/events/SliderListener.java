package de.unisaarland.cs.st.pirates.group5.view.events;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class SliderListener {

	private String value;
	private int defaultValue;
	
	public SliderListener(Slider slider, final Label label, final boolean even){
		this.defaultValue = (int) slider.getValue();
		
		slider.valueProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0,	Number oldV, Number newV) {
				value = String.format("%.0f", newV);
				Integer i = Integer.parseInt(value);
				if(even){
					if(i % 2 != 0)
						i++;
				}
				value = i.toString();
				label.setText(value);
			}
		});
	}
	
	public SliderListener(Slider slider, final Label label){
		this(slider, label, false);
	}
	
	public int get(){
		if(value != null)
			return Integer.parseInt(value);
		
		return defaultValue;
	}
}
