package battle.gui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.ImagePattern;
import logic.GameLogic;

public class BattleStage  {
	private int stageNumber;
	private CharaPane stageCharaPane ;
	private BattlePane battlePane ;
	public BattleStage(int n) {
		stageNumber = n;
		String stage = "";
		if(GameLogic.getStage() == 1) {
			stage = "stageOne.png";
		}
		if(GameLogic.getStage() == 2) {
			stage = "stageTwo.png";
		}
		if(GameLogic.getStage() == 3) {
			stage = "stageThree.png";
		}
		if(GameLogic.getStage() == 4) {
			stage = "stageFour.png";
		}
		if(GameLogic.getStage() == 5) {
			stage = "stageFive.png";
		}
		Image bg = new Image(ClassLoader.getSystemResource(stage).toString());
		stageCharaPane = initializeCharaPane();
		battlePane = new BattlePane(stageCharaPane);
		battlePane.setBackground(new Background(new BackgroundFill(new ImagePattern(bg), CornerRadii.EMPTY,Insets.EMPTY)));
	}
	private CharaPane initializeCharaPane() {
		CharaPane stageCharaPane = new CharaPane(GameLogic.getTeam(),GameLogic.getVillains().get(stageNumber));
		return stageCharaPane;
	}
	public int getStageNumber() {
		return stageNumber;
	}
	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}
	public CharaPane getStageCharaPane() {
		return stageCharaPane;
	}
	public void setStageCharaPane(CharaPane stageCharaPane) {
		this.stageCharaPane = stageCharaPane;
	}
	public BattlePane getBattlePane() {
		return battlePane;
	}
	public void setBattlePane(BattlePane battlePane) {
		this.battlePane = battlePane;
	}
}
