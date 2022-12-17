package skill.ally;

import java.util.ArrayList;
import java.util.Arrays;

import audio.Audio;
import battle.gui.CharaPane;
import chara.base.Ally;
import chara.base.BaseCharacter;
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

public class DivineComfort extends HealSkill implements TargetSelectable {

	public DivineComfort(BaseCharacter user) {
		super("DivineComfort", user, new ArrayList<logic.rank>(Arrays.asList(logic.rank.second, logic.rank.third, logic.rank.fourth)), 1, 3);
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
	public void cast() {
		getTargets().clear();
		for (Ally hero: GameLogic.team) {
			GameLogic.currentSkill.getTargets().add(hero);
		}
		super.cast();
	}
	@Override
	public void playAnimation() {
		HBox animation = new HBox();
		
		animation.setPrefWidth(1400);
		animation.setPrefHeight(740);
		animation.setAlignment(Pos.CENTER);
		animation.setPadding(new Insets(200,0,50,0));
		Image img = new Image(ClassLoader.getSystemResource("priestHealing.gif").toString());
		ImageView iv = new ImageView(img);
		iv.setFitHeight(200);
		iv.setFitWidth(100);
		animation.getChildren().add(iv);
		animation.setSpacing(100);
		for(BaseCharacter e : GameLogic.team) {
			if (e != getUser()) {
				StackPane healBox = new StackPane();
				healBox.setAlignment(Pos.CENTER);
				healBox.getChildren().add(new IdleSprite(((Ally) (e)).getClassName()));
				healBox.getChildren().add(new ImageView(new Image(ClassLoader.getSystemResource("healing.gif").toString())));
				animation.getChildren().add(healBox);

			}
		}
		CharaPane tmp = GameLogic.currentStage.getStageCharaPane();
		GameLogic.currentStage.getBattlePane().getChildren().remove(GameLogic.currentStage.getStageCharaPane());
		GameLogic.currentStage.getBattlePane().getChildren().add(0, animation);
		GameLogic.currentStage.getBattlePane().showBattleText("PRIEST used DIVINE COMFORT!");
		Audio.heal.stop();
		Audio.heal.play();

		AnimationTimer timer = new AnimationTimer() {
			int time = 0;
			
			@Override
			public void handle(long arg0) {
				time += 1;
				String[] result = getResult().split(",");
				if(time == 75) {
				GameLogic.getCurrentStage().getBattlePane().getChildren().remove(animation);
				GameLogic.getCurrentStage().getBattlePane().getChildren().add(0,tmp);
				
				}
				if(time == 100) {
					GameLogic.currentStage.getBattlePane().removeBattleText();
					GameLogic.currentStage.getBattlePane().showBattleText(result[0]);
				}
				if(time == 175) {
					if (result.length > 1) {
						GameLogic.currentStage.getBattlePane().removeBattleText();
						GameLogic.currentStage.getBattlePane().showBattleText(result[1]);
					} else if (result.length == 1) {
						GameLogic.currentStage.getBattlePane().removeBattleText();
						GameLogic.getCurrentStage().getBattlePane().enableSkillMenu();
						GameLogic.nextTurn();
					}
				}
				if(time == 250) {
					if (result.length > 2) {
						GameLogic.currentStage.getBattlePane().removeBattleText();
						GameLogic.currentStage.getBattlePane().showBattleText(result[2]);
					} else if (result.length == 2) {
						GameLogic.currentStage.getBattlePane().removeBattleText();
						GameLogic.getCurrentStage().getBattlePane().enableSkillMenu();
						GameLogic.nextTurn();
					}
				}
				if(time == 325) {
					if (result.length > 3) {
						GameLogic.currentStage.getBattlePane().removeBattleText();
						GameLogic.currentStage.getBattlePane().showBattleText(result[3]);
					} else if (result.length == 3) {
						GameLogic.currentStage.getBattlePane().removeBattleText();
						GameLogic.getCurrentStage().getBattlePane().enableSkillMenu();
						GameLogic.nextTurn();
					}
				}
				if(time == 400 && result.length == 4) {
					GameLogic.currentStage.getBattlePane().removeBattleText();
					GameLogic.getCurrentStage().getBattlePane().enableSkillMenu();
					GameLogic.nextTurn();
				}
				
			};
		
		};
		timer.start();
	}
}
