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

public class RavenousFeast extends DamageSkill {
	
	private String healResult;
	
	public RavenousFeast(Chara user) {
		super("RAVENOUSFEAST", user, new ArrayList<logic.rank>(Arrays.asList(logic.rank.first, logic.rank.second, 
				logic.rank.third, logic.rank.fourth)), 0, 80, 5);
	}
	
	@Override
	public void cast() {
		healResult = "";
		for (Chara each: targets) {
			if (isHit(each)) {
				
				int damageDeal = computeDamage(each);
				((Ally) each).setHp(each.getHp() - damageDeal);
				result = "It dealt "+ damageDeal + " damage!";
				
				int selfHeal = (int) (damageDeal * 0.5);
				healResult = "Also restore " + selfHeal + " HP!";
				user.setHp(user.getHp() + selfHeal);
				GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(user,100);
				if(each.getHp()!=0) {
				GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(each,100);
				}
			} else {
				result = "You dodged!";
			}
		}
		targets.clear();
	}
	@Override
	public void playAnimation() {
		for(Chara e : targets) {
			HBox animation = new HBox();
			animation.setPrefWidth(1400);
			animation.setMaxHeight(250);
			animation.setAlignment(Pos.CENTER);
			animation.setPadding(new Insets(150,0,25,0));
			Image img = new Image(ClassLoader.getSystemResource("hemomancerAttack.gif").toString());
			ImageView iv = new ImageView(img);
			animation.setSpacing(-50);
			iv.setFitHeight(270);
			iv.setFitWidth(250);
			animation.getChildren().add(new AttackedSprite(((Ally)(e)).getClassName()));
			animation.getChildren().add(iv);
			CharaPane tmp = GameLogic.currentStage.getStageCharaPane();
			GameLogic.currentStage.getBattlePane().getChildren().remove(GameLogic.currentStage.getStageCharaPane());
			GameLogic.currentStage.getBattlePane().getChildren().add(0, animation);
			GameLogic.currentStage.getBattlePane().showBattleText("HEMOMANCER used RAVENOUS FEAST!");
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
						GameLogic.currentStage.getBattlePane().showBattleText(result);
					}
					if(time == 175 && !healResult.equals("")) {
						GameLogic.currentStage.getBattlePane().removeBattleText();
						GameLogic.currentStage.getBattlePane().showBattleText(healResult);
					}
					if(time == 250 && !healResult.equals("")) {
						GameLogic.currentStage.getBattlePane().removeBattleText();
						GameLogic.nextTurn();
					}
					if(time == 175 && healResult.equals("")) {
						GameLogic.currentStage.getBattlePane().removeBattleText();
						GameLogic.nextTurn();
					}
				};
			
			};
			timer.start();
			
		}
	}
	

}
