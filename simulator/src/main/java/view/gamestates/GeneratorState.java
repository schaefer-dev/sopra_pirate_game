package view.gamestates;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import view.events.GenerateEvent;
import view.events.HoverEvent;
import view.events.SliderListener;
import view.events.SwitchState;
import view.utility.GameState;
import view.utility.MapPreview;
import view.utility.Configuration;
import view.GUIController;

public class GeneratorState implements GameState {

	private GUIController manager;
	private BorderPane root;
	private String title = "Map Generator";
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText(title);
	
		Canvas mapPreview = new Canvas(manager.getStage().getWidth()/3.7, manager.getStage().getWidth()/3.7);
		GraphicsContext gc = mapPreview.getGraphicsContext2D();
		
		try{
			char[][] emptyMap = {{'.'}};
			MapPreview p = new MapPreview(emptyMap);
			p.draw(gc);
		}
		catch(Exception e){}
		
		Label mapSize = new Label("Map Size");
		mapSize.getStyleClass().add("menulabel");
		Slider mapSizeSlider = new Slider(Configuration.MAP_SIZE_MIN, Configuration.MAP_SIZE_MAX, Configuration.MAP_SIZE_MIN);
		mapSizeSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines width & height of the map"));
		mapSizeSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		mapSizeSlider.setMaxWidth(200);
		mapSizeSlider.setMajorTickUnit(2);
		mapSizeSlider.setMinorTickCount(2);
		mapSizeSlider.setSnapToTicks(true);
		Label mapSizeLabel = new Label(String.format("%.0f", mapSizeSlider.getValue()));
		mapSizeLabel.getStyleClass().add("menulabel");
		SliderListener msListener = new SliderListener(mapSizeSlider, mapSizeLabel, true);
		
		Label islandCount = new Label("Island Count");
		islandCount.getStyleClass().add("menulabel");
		Slider islandCountSlider = new Slider(Configuration.ISLAND_COUNT_MIN, Configuration.ISLAND_COUNT_MAX, Configuration.ISLAND_COUNT_MIN);
		islandCountSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Quantity of the islands"));
		islandCountSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		islandCountSlider.setMaxWidth(200);
		islandCountSlider.setMajorTickUnit(2);
		islandCountSlider.setSnapToTicks(true);
		Label islandCountLabel = new Label(String.format("%.0f", islandCountSlider.getValue()));
		islandCountLabel.getStyleClass().add("menulabel");
		SliderListener icListener = new SliderListener(islandCountSlider, islandCountLabel);
		
		Label islandSize = new Label("Island Size");
		islandSize.getStyleClass().add("menulabel");
		Slider islandSizeSlider = new Slider(Configuration.ISLAND_SIZE_MIN, Configuration.ISLAND_SIZE_MAX, Configuration.ISLAND_SIZE_MIN);
		islandSizeSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Average size of the islands"));
		islandSizeSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		islandSizeSlider.setMaxWidth(200);
		islandSizeSlider.setMajorTickUnit(2);
		islandSizeSlider.setSnapToTicks(true);
		Label islandSizeLabel = new Label(String.format("%.0f", islandSizeSlider.getValue()));
		islandSizeLabel.getStyleClass().add("menulabel");
		SliderListener isListener = new SliderListener(islandSizeSlider, islandSizeLabel);
		
		Button generate = new Button("Generate");
		generate.getStyleClass().add("menubutton");
		generate.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Generate a map with given values"));
		generate.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		generate.setOnAction(new GenerateEvent(manager.getConfiguration(), msListener, icListener, isListener, gc));
		
		Label formattingHelper = new Label("             ");

		GridPane grid = new GridPane();
		grid.getStyleClass().add("grid");
		grid.add(mapSize, 0, 0);
		grid.add(mapSizeSlider, 1, 0);
		grid.add(mapSizeLabel, 2, 0);
		grid.add(islandCount, 0, 1);
		grid.add(islandCountSlider, 1, 1);
		grid.add(islandCountLabel, 2, 1);
		grid.add(islandSize, 0, 2);
		grid.add(islandSizeSlider, 1, 2);
		grid.add(islandSizeLabel, 2, 2);
		grid.add(generate, 1, 3);
		grid.add(formattingHelper, 2, 3);
		
		HBox box = new HBox(20);
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(grid, mapPreview);
		
		Button back = new Button("< Map Type");
		back.getStyleClass().add("menubutton");
		back.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go back to map type selection"));
		back.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager));
		
		Button next = new Button("Game Settings >");
		next.getStyleClass().add("menubutton");
		next.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go to game settings"));
		next.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		next.setOnAction(new SwitchState(manager, new GameSettingsState(), true));
        		
		GridPane selection = new GridPane();
		selection.getStyleClass().add("grid");
		selection.add(back, 0, 0);
		selection.add(next, 6, 0);
		
		root = new BorderPane();
		root.setCenter(box);
		root.setBottom(selection);
		
		manager.getRoot().setCenter(root);
	}

	@Override
	public void exiting() {
		manager.getRoot().setCenter(null);
	}

	@Override
	public void concealing() {
		manager.getRoot().setCenter(null);
		manager.getConfiguration().generate = true;
	}

	@Override
	public void revealed() {
		manager.getTitleText().setText(title);
		manager.getRoot().setCenter(root);
	}
}
