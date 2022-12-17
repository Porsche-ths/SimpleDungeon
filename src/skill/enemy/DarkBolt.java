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
import javafx.scene.layout.StackPane;
import logic.GameLogic;
import skill.base.DamageSkill;
import sprites.AttackedSprite;

public class DarkBolt extends DamageSkill {

	public DarkBolt(BaseCharacter user) {
		super("DARKBOLT", user, new ArrayList<logic.rank>(Arrays.asList(logic.rank.first, logic.rank.second, 
				logic.rank.third, logic.rank.fourth)), 0, 85, 0);
	}
	
	@Override
	public void playAnimation() {
		for(BaseCharacter e : targets) {
			HBox animation = new HBox();
			StackPane targetBox = new StackPane();
			targetBox.setAlignment(Pos.CENTER);
			animation.setPrefWidth(1400);
			animation.setPrefHeight(740);
			animation.setAlignment(Pos.CENTER);
			animation.setPadding(new Insets(200,0,50,0));
			Image img = new Image(ClassLoader.getSystemResource("darkLordAttack.gif").toString());
			ImageView iv = new ImageView(img);
			animation.setSpacing(100);
			iv.setFitHeight(300);
			iv.setFitWidth(250);
			targetBox.getChildren().add(new AttackedSprite(((Ally) (e)).getClassName()));
			targetBox.getChildren().add(new ImageView(new Image(ClassLoader.getSystemResource("darkLordBolt.gif").toString())));
			animation.getChildren().add(targetBox);
			animation.getChildren().add(iv);

			CharaPane tmp = GameLogic.currentStage.getStageCharaPane();
			GameLogic.currentStage.getBattlePane().getChildren().remove(GameLogic.currentStage.getStageCharaPane());
			GameLogic.currentStage.getBattlePane().getChildren().add(0, animation);
			GameLogic.currentStage.getBattlePane().showBattleText("DARK LORD used DARK BOLT!");
			Audio.darkLord.stop();
			Audio.darkLord.play();

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
