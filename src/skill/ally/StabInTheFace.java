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

public class StabInTheFace extends DamageSkill implements TargetSelectable {

	public StabInTheFace(BaseCharacter user) {
		super("stabInTheFace", user, new ArrayList<logic.rank>(Arrays.asList(logic.rank.first, logic.rank.second, logic.rank.third)),
				new ArrayList<logic.rank>(Arrays.asList(logic.rank.first, logic.rank.second)), 0, 90, 10);
	}

	@Override
	public void selectTarget() {
		for (Node n: GameLogic.getCurrentStage().getStageCharaPane().getChildren()) {
			n.setDisable(true);
		}
		for (Enemy e: GameLogic.getEnemies()) {
			if (e.getRank().equals(logic.rank.first) || e.getRank().equals(logic.rank.second)) {
				GameLogic.getCurrentStage().getStageCharaPane().getChildren().get(GameLogic.getEnemies().indexOf(e) + 5).setDisable(false);
			}
		}
	}@Override
	public void playAnimation() {
		for(BaseCharacter e : targets) {
			HBox animation = new HBox();
			animation.setPrefWidth(1400);
			animation.setPrefHeight(740);
			animation.setAlignment(Pos.CENTER);
			animation.setPadding(new Insets(200,0,50,0));
			Image img = new Image(ClassLoader.getSystemResource("rogueStab.gif").toString());
			ImageView iv = new ImageView(img);
			animation.setSpacing(-80);
			iv.setFitHeight(220);
			iv.setFitWidth(180);
			animation.getChildren().add(iv);
			if (!((Enemy) (e)).isAlive()) {
				animation.getChildren().add(new CorpseSprite(((Enemy) (e)).getClassName()));
			} else {
				animation.getChildren().add(new AttackedSprite(((Enemy) (e)).getClassName()));
			}
			CharaPane tmp = GameLogic.getCurrentStage().getStageCharaPane();
			GameLogic.getCurrentStage().getBattlePane().getChildren().remove(GameLogic.getCurrentStage().getStageCharaPane());
			GameLogic.getCurrentStage().getBattlePane().getChildren().add(0, animation);
			GameLogic.getCurrentStage().getBattlePane().showBattleText("ROGUE used STAB IN THE FACE!");
			Audio.rogueSkill2.stop();
			Audio.rogueSkill2.play();
			AnimationTimer timer = new AnimationTimer() {
				int time = 0;
				@Override
				public void handle(long arg0) {
					time += 1;
					if(time == 75) {
					GameLogic.getCurrentStage().getBattlePane().getChildren().remove(animation);
					GameLogic.getCurrentStage().getBattlePane().getChildren().add(0,tmp);

					}
					if(time == 100) {
						GameLogic.getCurrentStage().getBattlePane().removeBattleText();
						GameLogic.getCurrentStage().getBattlePane().showBattleText(getResult());
					}
					if(time == 175) {
						GameLogic.getCurrentStage().getBattlePane().removeBattleText();
						GameLogic.nextTurn();
					}
				};
			
			};
			timer.start();
			
		}
	}

}
