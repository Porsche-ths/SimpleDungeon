package app;

import charaselect.gui.CharSelect;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import logic.GameLogic;
import startmenu.gui.MainMenu;

public class Main extends Application {
	public static Stage stage;
	public static Scene mainMenuScene, charSelectScene;
	public static CharSelect charSelect;
	public static MainMenu mainMenu;

	@Override
	public void start(Stage primaryStage) throws Exception {
		AudioClip music = new AudioClip(ClassLoader.getSystemResource("soundtrack.mp3").toString());
		music.setVolume(0.5);
		music.setCycleCount(Timeline.INDEFINITE);
		music.play();
		mainMenu = new MainMenu();
		charSelect = new CharSelect();
		mainMenuScene = new Scene(mainMenu);
		charSelectScene = new Scene(charSelect);
		stage = primaryStage;
		stage.setTitle("SIMPLE DUNGEON");
		stage.setScene(mainMenuScene);
		stage.show();
	}

	public static void switchToCharSelect() {
		stage.setScene(charSelectScene);
	}

	public static void switchToMainMenu() {
		MainMenu.fadeTitleText();
		stage.setScene(mainMenuScene);
	}

	public static void switchToBattleStage() {
		GameLogic.newGame();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
	
}
