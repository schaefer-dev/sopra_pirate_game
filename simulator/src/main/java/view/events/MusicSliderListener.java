package view.events;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;

public class MusicSliderListener extends SliderListener {

	public MusicSliderListener(Slider slider, Label label, final MediaPlayer player) {
		super(slider, label);
		slider.valueProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0,	Number oldV, Number newV) {
				player.setVolume(newV.doubleValue()/100);
			}
				
		});
	}

}
