package skill.ally;

import java.util.ArrayList;
import java.util.Arrays;

import audio.Audio;
import battle.gui.CharaPane;
import chara.base.Ally;
import chara.base.Chara;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import logic.GameLogic;
import skill.base.HealSkill;
import skill.base.TargetSelectable;
import sprites.IdleSprite;

public class DivineGrace extends HealSkill implements TargetSelectable {

	public DivineGrace(Chara user) {
		super("DivineGrace", user, new ArrayList<logic.rank>(Arrays.asList(logic.rank.third, logic.rank.fourth)), 4, 5);
	}

	@Override
	public void selectTarget() {
		for (Node n: GameLogic.currentStage.getStageCharaPane().getChildren()) {
			n.setDisable(true);
		}
		for (Ally a: GameLogic.team) {
			GameLogic.currentStage.getStageCharaPane().getChildren().get(GameLogic.team.indexOf(a) + 4 - GameLogic.team.size()).setDisable(false);
		}
	}

	@Override
	public void playAnimation() {
		for (Chara e : targets) {
			HBox animation = new HBox();
			StackPane healBox = new StackPane();
			healBox.setAlignment(Pos.CENTER);
			animation.setPrefWidth(1400);
			animation.setPrefHeight(740);
			animation.setAlignment(Pos.CENTER);
			animation.setPadding(new Insets(200, 0, 50, 0));
			Image img = new Image(ClassLoader.getSystemResource("priestHealing.gif").toString());
			ImageView iv = new ImageView(img);
			animation.setSpacing(0);
			iv.setFitHeight(200);
			iv.setFitWidth(100);
			animation.getChildren().add(iv);
			if (e != getUser()) {
				healBox.getChildren().add(new IdleSprite(((Ally) (e)).getClassName()));
				healBox.getChildren().add(new ImageView(new Image(ClassLoader.getSystemResource("healing.gif").toString())));
			}
			animation.getChildren().add(healBox);
			CharaPane tmp = GameLogic.currentStage.getStageCharaPane();
			GameLogic.currentStage.getBattlePane().getChildren().remove(GameLogic.currentStage.getStageCharaPane());
			GameLogic.currentStage.getBattlePane().getChildren().add(0, animation);
			GameLogic.currentStage.getBattlePane().showBattleText("PRIEST used DIVINE GRACE");
			Audio.heal.stop();
			Audio.heal.play();
			AnimationTimer timer = new AnimationTimer() {
				int time = 0;

				@Override
				public void handle(long arg0) {
					time += 1;
					if (time == 75) {
						GameLogic.getCurrentStage().getBattlePane().getChildren().remove(animation);
						GameLogic.getCurrentStage().getBattlePane().getChildren().add(0, tmp);
					}
					if(time == 100) {
						GameLogic.currentStage.getBattlePane().removeBattleText();
						GameLogic.currentStage.getBattlePane().showBattleText(getResult());
					}
					if(time == 175) {
						GameLogic.currentStage.getBattlePane().removeBattleText();
						GameLogic.nextTurn();
					}
				};

			};
			timer.start();

		}
	}

}
