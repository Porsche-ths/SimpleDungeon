package skill.enemy;

import java.util.ArrayList;
import java.util.Arrays;

import audio.Audio;
import battle.gui.CharaPane;
import chara.base.Ally;
import chara.base.BaseCharacter;
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
	
	public BonySlash(BaseCharacter user) {
		super("BONYSLASH", user, new ArrayList<logic.rank>(Arrays.asList(logic.rank.first, logic.rank.second)), 0, 50, 2);
	}
	
	@Override
	public void playAnimation() {
		for(BaseCharacter e : targets) {
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
			CharaPane tmp = GameLogic.getCurrentStage().getStageCharaPane();
			GameLogic.getCurrentStage().getBattlePane().getChildren().remove(GameLogic.getCurrentStage().getStageCharaPane());
			GameLogic.getCurrentStage().getBattlePane().getChildren().add(0, animation);
			GameLogic.getCurrentStage().getBattlePane().showBattleText("SKELLY SOLDIER used BONY SLASH!");
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
