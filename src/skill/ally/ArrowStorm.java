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

public class ArrowStorm extends DamageSkill implements TargetSelectable {

	public ArrowStorm(BaseCharacter user) {
		super("ArrowStorm", user, new ArrayList<logic.rank>(Arrays.asList(logic.rank.third, logic.rank.fourth)),
				new ArrayList<logic.rank>(Arrays.asList(logic.rank.first, logic.rank.second, logic.rank.third, logic.rank.fourth)), -70, 85, 0);
	}

	@Override
	public void selectTarget() {
		for (Node n: GameLogic.getCurrentStage().getStageCharaPane().getChildren()) {
			n.setDisable(true);
		}
		for (Enemy e: GameLogic.getEnemies()) {
			GameLogic.getCurrentStage().getStageCharaPane().getChildren().get(GameLogic.getEnemies().indexOf(e) + 5).setDisable(false);
		}
	}

	@Override
	public void cast() {
		getTargets().clear();
		for (Enemy e: GameLogic.getEnemies()) {
			GameLogic.getCurrentSkill().getTargets().add(e);
		}
		super.cast();
	}
	@Override
	public void playAnimation() {
		HBox animation = new HBox();
		animation.setPrefWidth(1400);
		animation.setPrefHeight(740);
		animation.setAlignment(Pos.BOTTOM_CENTER);
		animation.setPadding(new Insets(150,0,25,0));
		Image img = new Image(ClassLoader.getSystemResource("rangerSkill.gif").toString());
		ImageView iv = new ImageView(img);
		iv.setFitHeight(220);
		iv.setFitWidth(150);
		animation.getChildren().add(iv);
		animation.setSpacing(100);
		for(BaseCharacter e : GameLogic.getEnemies()) {
			if (!((Enemy) (e)).isAlive()) {
				animation.getChildren().add(new CorpseSprite(((Enemy) (e)).getClassName()));
			} else {
				animation.getChildren().add(new AttackedSprite(((Enemy) (e)).getClassName()));
			}
		}
		CharaPane tmp = GameLogic.getCurrentStage().getStageCharaPane();
		GameLogic.getCurrentStage().getBattlePane().getChildren().remove(GameLogic.getCurrentStage().getStageCharaPane());
		GameLogic.getCurrentStage().getBattlePane().getChildren().add(0, animation);
		GameLogic.getCurrentStage().getBattlePane().showBattleText("RANGER used ARROW STORM!");
		Audio.attack.stop();
		Audio.attack.play();

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
					GameLogic.getCurrentStage().getBattlePane().removeBattleText();
					GameLogic.getCurrentStage().getBattlePane().showBattleText(result[0]);
				}
				if(time == 175) {
					if (result.length > 1) {
						GameLogic.getCurrentStage().getBattlePane().removeBattleText();
						GameLogic.getCurrentStage().getBattlePane().showBattleText(result[1]);
					} else if (result.length == 1) {
						GameLogic.getCurrentStage().getBattlePane().removeBattleText();
						GameLogic.getCurrentStage().getBattlePane().enableSkillMenu();
						GameLogic.nextTurn();
					}
				}
				if(time == 250) {
					if (result.length > 2) {
						GameLogic.getCurrentStage().getBattlePane().removeBattleText();
						GameLogic.getCurrentStage().getBattlePane().showBattleText(result[2]);
					} else if (result.length == 2) {
						GameLogic.getCurrentStage().getBattlePane().removeBattleText();
						GameLogic.getCurrentStage().getBattlePane().enableSkillMenu();
						GameLogic.nextTurn();
					}
				}
				if(time == 325) {
					if (result.length > 3) {
						GameLogic.getCurrentStage().getBattlePane().removeBattleText();
						GameLogic.getCurrentStage().getBattlePane().showBattleText(result[3]);
					} else if (result.length == 3) {
						GameLogic.getCurrentStage().getBattlePane().removeBattleText();
						GameLogic.getCurrentStage().getBattlePane().enableSkillMenu();
						GameLogic.nextTurn();
					}
				}
				if(time == 400 && result.length == 4) {
					GameLogic.getCurrentStage().getBattlePane().removeBattleText();
					GameLogic.getCurrentStage().getBattlePane().enableSkillMenu();
					GameLogic.nextTurn();
				}
			};
		
		};
		timer.start();
	}

}
