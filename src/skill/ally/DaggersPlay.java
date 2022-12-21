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

public class DaggersPlay extends DamageSkill implements TargetSelectable {

	public DaggersPlay(BaseCharacter user) {
		super("DaggersPlay", user, new ArrayList<logic.rank>(Arrays.asList(logic.rank.second, logic.rank.third, logic.rank.fourth)),
				new ArrayList<logic.rank>(Arrays.asList(logic.rank.second, logic.rank.third)), 0, 90, 5);
	}

	@Override
	public void selectTarget() {
		for (Node n: GameLogic.getCurrentStage().getStageCharaPane().getChildren()) {
			n.setDisable(true);
		}
		for (Enemy e: GameLogic.getEnemies()) {
			if (e.getRank().equals(logic.rank.second) || e.getRank().equals(logic.rank.third)) {
				GameLogic.getCurrentStage().getStageCharaPane().getChildren().get(GameLogic.getEnemies().indexOf(e) + 5).setDisable(false);
			}
		}
	}

	@Override
	public void cast() {
		getTargets().clear();
		for (Enemy e: GameLogic.getEnemies()) {
			if (e.getRank().equals(logic.rank.second) || e.getRank().equals(logic.rank.third)) {
				GameLogic.getCurrentSkill().getTargets().add(e);
			}
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
		Image img = new Image(ClassLoader.getSystemResource("rogueStab.gif").toString());
		ImageView iv = new ImageView(img);
		iv.setFitHeight(220);
		iv.setFitWidth(170);
		animation.getChildren().add(iv);
		animation.setSpacing(200);
		for(BaseCharacter e : GameLogic.getEnemies()) {
			if (e.getRank().equals(logic.rank.second) || e.getRank().equals(logic.rank.third)) {
				if (!((Enemy) (e)).isAlive()) {
					animation.getChildren().add(new CorpseSprite(((Enemy) (e)).getClassName()));
				} else {
					animation.getChildren().add(new AttackedSprite(((Enemy) (e)).getClassName()));
				}			}
		}
		CharaPane tmp = GameLogic.getCurrentStage().getStageCharaPane();
		GameLogic.getCurrentStage().getBattlePane().getChildren().remove(GameLogic.getCurrentStage().getStageCharaPane());
		GameLogic.getCurrentStage().getBattlePane().getChildren().add(0, animation);
		GameLogic.getCurrentStage().getBattlePane().showBattleText("ROGUE used DAGGERS PLAY!");
		Audio.rogueSkill1.stop();
		Audio.rogueSkill1.setVolume(10);
		Audio.rogueSkill1.play();

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
				if(time == 250 && result.length == 2) {
					GameLogic.getCurrentStage().getBattlePane().removeBattleText();
					GameLogic.getCurrentStage().getBattlePane().enableSkillMenu();
					GameLogic.nextTurn();
				}
			};
			
		
		};
		timer.start();
	}
}
