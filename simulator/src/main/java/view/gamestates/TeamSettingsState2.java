package view.gamestates;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import view.GUIController;
import view.utility.GameState;
import view.utility.Team;
import view.utility.TeamListCell;

public class TeamSettingsState2 implements GameState {

	private GUIController manager;
	private ListView<String> list;
	private ObservableList<String> captains;
	private List<ComboBox<String>> choosers;
	
	private String title = "Team Settings";
	
	
	public List<ComboBox<String>> getChoosers(){
		return choosers;
	}
	
	public void addChooser(ComboBox<String> chooser){
		choosers.add(chooser);
	}
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText(title);

		list = new ListView<String>();
		list.getStyleClass().add("maplist-view");
		
		captains = FXCollections.observableArrayList(manager.getConfiguration().getCaptainNames());
		choosers = new LinkedList<ComboBox<String>>();
		
		final TeamSettingsState2 state = this;
		list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

			@Override
			public ListCell<String> call(ListView<String> arg0) {
				return new TeamListCell(state, manager.getConfiguration());
			}
		});
		
		ObservableList<String> tatata = FXCollections.observableArrayList ("", "", "", "", "", "", "");
		
		list.setItems(tatata);
		
		manager.getRoot().setCenter(list);
	}

	@Override
	public void exiting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void concealing() {
		// TODO Auto-generated method stub

	}

	@Override
	public void revealed() {
		// TODO Auto-generated method stub

	}

}
