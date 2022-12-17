package battle.gui;

import java.util.ArrayList;

import chara.base.Ally;
import chara.base.BaseCharacter;
import chara.base.Enemy;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.GameLogic;
import sprites.CorpseSprite;
import sprites.IdleSprite;


public class CharaPane extends HBox {
	private ArrayList<Ally> team;
	private ArrayList<Enemy> opponents;

	public CharaPane(ArrayList<Ally> team, ArrayList<Enemy> opponents) {
		this.team = team;
		this.opponents = opponents;
		setPrefWidth(880);
		setAlignment(Pos.CENTER);
		setPrefHeight(83);
		setSpacing(5);
		setPadding(new Insets(200,0,50,0));
		addCharToPane();
		
	}
	public void addCharToPane() {
		if(team.size() < 4) {
			for(int i = 0; i < 4-team.size();i++) {
				Image blank = new Image("blank.png");
				ImageView blankIdle = new ImageView(blank);
				blankIdle.setFitHeight(120);
				blankIdle.setFitWidth(140);
				getChildren().add(blankIdle);
			}
		}
		for(Ally a : team) {
			ImageView idle = new IdleSprite(a.getClassName());
			VBox charaBox = new VBox();
			StackPane hp = initializeHpBar(100, a);
			
			charaBox.getChildren().add(idle);
			charaBox.setMaxHeight(200);
			charaBox.setMaxWidth(150);
			charaBox.getChildren().add(hp);
			charaBox.setAlignment(Pos.CENTER);
			
			charaBox.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event arg0) {
					
					GameLogic.getCurrentStage().getBattlePane().disableSkillMenu();
					disableCharaBox();
					GameLogic.getCurrentSkill().getTargets().add(a);
					GameLogic.getCurrentSkill().playAnimation();
					GameLogic.getCurrentSkill().cast();
					charaBox.setDisable(true);

				}
				
			});
			charaBox.setDisable(true);
			
			getChildren().add(charaBox);
		}
		Image blank = new Image("blank.png");
		ImageView blankIdle = new ImageView(blank);
		blankIdle.setFitHeight(120);
		blankIdle.setFitWidth(140);
		getChildren().add(blankIdle);
		for(Enemy a : opponents) {
			VBox charaBox = new VBox();
			ImageView idle = new ImageView();

			if(a.isAlive()) {
				 idle = new IdleSprite(a.getClassName());
				charaBox.setAlignment(Pos.CENTER);

			}
			else {
				idle = new CorpseSprite(a.getClassName());
				charaBox.setAlignment(Pos.BOTTOM_CENTER);

				
			}
			StackPane hp = initializeHpBar(100, a);
			
			charaBox.getChildren().add(idle);
			charaBox.setMaxHeight(200);
			charaBox.setMaxWidth(150);
			charaBox.getChildren().add(hp);

			charaBox.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event arg0) {
					
					GameLogic.getCurrentSkill().getTargets().add(a);
					GameLogic.getCurrentStage().getBattlePane().disableSkillMenu();
					disableCharaBox();
					GameLogic.getCurrentSkill().playAnimation();
					GameLogic.getCurrentSkill().cast();
				}
				
			});
			charaBox.setDisable(true);
			
			getChildren().add(charaBox);
		}
		if(opponents.size() < 4) {
			for(int i = 0; i < 4-team.size();i++) {
				Image blank2 = new Image(ClassLoader.getSystemResource("blank.png").toString());
				ImageView blankIdle2 = new ImageView(blank2);
				blankIdle2.setFitHeight(120);
				blankIdle2.setFitWidth(140);
				getChildren().add(blankIdle2);

			}
		}

	}
	public StackPane initializeHpBar(int width,BaseCharacter c) {
		StackPane hpBar = new StackPane();
		Rectangle hp = new Rectangle(0,0,(c.getHp()/c.getMaxHp()*(width)),10);
		hpBar.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
		hpBar.setMaxWidth(width);
		hpBar.setMaxHeight(15);
		hp.setFill(Color.GREEN);
		hpBar.setAlignment(Pos.CENTER_LEFT);
		hpBar.getChildren().add(hp);
		return hpBar;
	}
	public void updateHpBar(BaseCharacter c, double defaultWidth) {
		if (c instanceof Ally || c.getHp() != 0) {
			int n = 0;
	
			switch(c.getRank()) {
			case first:
				n = c instanceof Ally? 3:5; break;
			case second:
				n = c instanceof Ally? 2:6; break;
			case third:
				n = c instanceof Ally? 1:7; break;
			case fourth:
				n = c instanceof Ally? 0:8; break;
			}
			VBox v = (VBox)(getChildren().get(n));
			StackPane s = (StackPane)(v.getChildren().get(1));
			Rectangle r = (Rectangle)(s.getChildren().get(0));
			r.setWidth((((double)c.getHp())/(double)(c.getMaxHp()))*defaultWidth);
		}
	}
	private void disableCharaBox() {
		for(Node n : this.getChildren()) {
			n.setDisable(true);
		}
	}

}
