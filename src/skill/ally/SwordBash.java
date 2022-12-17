package skill.ally;

import java.util.ArrayList;
import java.util.Arrays;

import audio.Audio;
import battle.gui.CharaPane;
import chara.base.BaseCharacter;
import chara.base.Enemy;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import logic.GameLogic;
import skill.base.DamageSkill;
import skill.base.TargetSelectable;
import sprites.AttackedSprite;
import sprites.CorpseSprite;

public class SwordBash extends DamageSkill implements TargetSelectable {

	public SwordBash(BaseCharacter user) {
		super("SwordBash", user, new ArrayList<logic.rank>(Arrays.asList(logic.rank.first, logic.rank.second)), -50, 90, 0);
	}

	@Override
	public void selectTarget() {
		for (Node n : GameLogic.currentStage.getStageCharaPane().getChildren()) {
			n.setDisable(true);
		}
		for (Enemy e : GameLogic.enemies) {
			if (e.getRank().equals(logic.rank.first) || e.getRank().equals(logic.rank.second)) {
				GameLogic.currentStage.getStageCharaPane().getChildren().get(GameLogic.enemies.indexOf(e) + 5)
						.setDisable(false);
			}
		}
	}

	@Override
	public void playAnimation() {
		for (BaseCharacter e : targets) {
			HBox animation = new HBox();
			animation.setPrefWidth(1400);
			animation.setPrefHeight(740);
			animation.setAlignment(Pos.BOTTOM_CENTER);
			animation.setPadding(new Insets(150, 0, 25, 0));
			Image img = new Image(ClassLoader.getSystemResource("crusaderSkill1.gif").toString());
			ImageView iv = new ImageView(img);
			animation.setSpacing(-80);
			iv.setFitHeight(250);
			iv.setFitWidth(250);
			animation.getChildren().add(iv);
			if (!((Enemy) (e)).isAlive()) {
				animation.getChildren().add(new CorpseSprite(((Enemy) (e)).getClassName()));
			} else {
				animation.getChildren().add(new AttackedSprite(((Enemy) (e)).getClassName()));
			}
			CharaPane tmp = GameLogic.currentStage.getStageCharaPane();
			GameLogic.currentStage.getBattlePane().getChildren().remove(GameLogic.currentStage.getStageCharaPane());
			GameLogic.currentStage.getBattlePane().getChildren().add(0, animation);
			GameLogic.currentStage.getBattlePane().showBattleText("CRUSADER used SWORD BASH!");
			Audio.attack.stop();
			Audio.attack.play();
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
