package skill.enemy;

import java.util.ArrayList;
import java.util.Arrays;

import audio.Audio;
import battle.gui.CharaPane;
import chara.base.Ally;
import chara.base.Chara;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import logic.GameLogic;
import skill.base.DamageSkill;
import sprites.AttackedSprite;

public class BonySlash extends DamageSkill {
	
	public BonySlash(Chara user) {
		super("BONYSLASH", user, new ArrayList<logic.rank>(Arrays.asList(logic.rank.first, logic.rank.second)), 0, 50, 2);
	}
	
	@Override
	public void playAnimation() {
		for(Chara e : targets) {
			HBox animation = new HBox();
			animation.setPrefWidth(1400);
			animation.setPrefHeight(740);
			animation.setAlignment(Pos.CENTER);
			animation.setPadding(new Insets(200,0,50,0));
			Image img = new Image(ClassLoader.getSystemResource("skellySoldierAttack.gif").toString());
			ImageView iv = new ImageView(img);
			animation.setSpacing(-50);
			iv.setFitHeight(170);
			iv.setFitWidth(170);
			animation.getChildren().add(new AttackedSprite(((Ally)(e)).getClassName()));
			animation.getChildren().add(iv);
			CharaPane tmp = GameLogic.currentStage.getStageCharaPane();
			GameLogic.currentStage.getBattlePane().getChildren().remove(GameLogic.currentStage.getStageCharaPane());
			GameLogic.currentStage.getBattlePane().getChildren().add(0, animation);
			GameLogic.currentStage.getBattlePane().showBattleText("SKELLY SOLDIER used BONY SLASH!");
			Audio.attack.stop();
			Audio.attack.play();

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
