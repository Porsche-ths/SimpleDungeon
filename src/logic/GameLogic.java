package logic;

import java.util.ArrayList;
import java.util.Comparator;

import app.Main;
import audio.Audio;
import battle.gui.BattleStage;
import chara.base.Ally;
import chara.base.BaseCharacter;
import chara.base.Enemy;
import chara.enemy.DarkLord;
import chara.enemy.Executioner;
import chara.enemy.Hemomancer;
import chara.enemy.SkellyArcher;
import chara.enemy.SkellySoldier;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import skill.base.BaseSkill;

public class GameLogic {
	
	private static ArrayList<Ally> team;
	private static ArrayList<Enemy> enemies;
	private static ArrayList<ArrayList<Enemy>> villains;
	private static ArrayList<BaseCharacter> queue;
	private static int stage;
	private static boolean isGameEnd;
	private static boolean isWin;
	private static boolean isStageCleared;
	private static BattleStage currentStage;
	private static BaseCharacter currentCharacter;
	private static BaseSkill currentSkill;
	
	public static void newGame() {
		isStageCleared = false;
		isGameEnd = false;
		isWin = false;
		addEnemiesToVillains();
		stage = 1;
		beginStage(stage);
	}
	
	public static void endGame() {

		Font font = Font.loadFont(ClassLoader.getSystemResourceAsStream("MINECRAFT_FONT.ttf"), 60);
		Font font2 = Font.loadFont(ClassLoader.getSystemResourceAsStream("MINECRAFT_FONT.ttf"), 80);
		VBox screen = new VBox();
    	screen.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY,Insets.EMPTY)));

		screen.setAlignment(Pos.CENTER);
		HBox button = new HBox();
		Text restart = new Text("RESTART");
		restart.setFill(Color.WHITE);
		restart.setFont(font);
		Text quit = new Text("QUIT");
		quit.setFont(font);
		quit.setFill(Color.WHITE);
		button.setAlignment(Pos.CENTER);
		screen.setSpacing(100);
		screen.setPrefHeight(680);
		screen.setPrefWidth(1400);
		button.getChildren().add(restart);
		button.getChildren().add(quit);
		button.setSpacing(100);

		
		Scene scene = new Scene(screen);
		restart.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				team = new ArrayList<Ally>();
				Main.charSelect.resetSelectedChara();
				Main.switchToMainMenu();
			}
			
		});
		quit.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				Platform.exit();
			}
			
		});
		if (isWin) {
			Text text = new Text("YOU WIN!!");
			text.setFont(font2);
			text.setFill(Color.WHITE);
			screen.getChildren().add(text);
			screen.getChildren().add(button);

		} else {
			Text text = new Text("GIT GUD");
			text.setFont(font2);
			text.setFill(Color.WHITE);
			screen.getChildren().add(text);
			screen.getChildren().add(button);
		}
		Main.stage.setScene(scene);
	}
	
	public static void beginStage(int i) {
		StackPane trans = createStageTrans(i);
		Scene root = new Scene(trans);
		Main.stage.setScene(root);
		if(i != 1) {
			Audio.stageClear.stop();
			Audio.stageClear.play();
		}
		AnimationTimer timer = new AnimationTimer() {
			int time = 0;
			@Override
			public void handle(long arg0) {
				time += 1;
				if(time == 100) {
					enemies = villains.get(i-1);
					currentStage = new BattleStage(i-1);
					Scene stageScene = new Scene(GameLogic.getCurrentStage().getBattlePane());
					for (Ally each: team) {
						GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(each,100);
					}
					for (Enemy each: enemies) {
						GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(each,100);
					}
					Main.stage.setScene(stageScene);
					generateQueue();
					nextTurn();
				}
			}
		};
		timer.start();
		
	}
	
	public static void nextTurn() {
		
		if (isGameEnd) {
			endGame();
		} else if (isStageCleared) {
			if (stage == 5) {
				setGameEnd(true);
				setWin(true);
				endGame();
			} else {
				isStageCleared = false;
				stage++;
				beginStage(stage);
			}
		} else {
			if (queue.isEmpty()) generateQueue();
			currentCharacter = queue.get(0); queue.remove(0);
			if(currentCharacter instanceof Ally) {
				GameLogic.getCurrentStage().getBattlePane().enableSkillMenu();
			}
			else {
				GameLogic.getCurrentStage().getBattlePane().disableSkillMenu();

			}
			currentCharacter.beginTurn();
		}
		
	}
	
	public static  void generateQueue() {
		
		queue = new ArrayList<BaseCharacter>(8);
		for (BaseCharacter ally: team) {
			ally.setCalculatedSpd(randomRange(1, 8) + ally.getSpd());
			queue.add(ally);
		}
		
		for (BaseCharacter enemy: enemies) {
			enemy.setCalculatedSpd(randomRange(1, 8) + enemy.getSpd());
			queue.add(enemy);
		}
		
		queue.sort(new SpeedComparator());
		
	}
	
	public static int randomInt() {
		//random integer less than 100
		double randNumber = Math.random();
		return (int) (randNumber * 100);

	}
	
	public static int randomRange(int a, int b) {
		//random int from a to b *include both a and b*
		int d = (b - a + 1);
		return (int) (Math.random() * d) + a;
	}
	
	private static void addEnemiesToVillains() {
		villains = new ArrayList<ArrayList<Enemy>>();
		ArrayList<Enemy> stageOne = new ArrayList<Enemy>();
		SkellySoldier s11 = new SkellySoldier("Soldier11");
		s11.setRank(rank.first);
		SkellySoldier s12 = new SkellySoldier("Soldier12");
		s12.setRank(rank.second);
		stageOne.add(s11);
		stageOne.add(s12);

		
		ArrayList<Enemy> stageTwo = new ArrayList<Enemy>();
		SkellySoldier s21 = new SkellySoldier("Soldier21");
		s21.setRank(rank.first);
		SkellySoldier s22 = new SkellySoldier("Soldier22");
		s22.setRank(rank.second);
		SkellyArcher a21 = new SkellyArcher("Archer21");
		a21.setRank(rank.third);
		SkellyArcher a22 = new SkellyArcher("Archer22");
		a22.setRank(rank.fourth);
		stageTwo.add(s21);
		stageTwo.add(s22);
		stageTwo.add(a21);
		stageTwo.add(a22);
		
		ArrayList<Enemy> stageThree = new ArrayList<Enemy>();
		Executioner e31 = new Executioner("Executioner31");
		e31.setRank(rank.first);
		Hemomancer h31 = new Hemomancer("Hemomancer31");
		h31.setRank(rank.second);
		stageThree.add(e31);
		stageThree.add(h31);
		
		ArrayList<Enemy> stageFour = new ArrayList<Enemy>();
		Executioner e41 = new Executioner("Executioner41");
		e41.setRank(rank.first);
		Hemomancer h41 = new Hemomancer("Hemomancer41");
		h41.setRank(rank.second);
		SkellyArcher a41 = new SkellyArcher("Archer41");
		a41.setRank(rank.third);
		SkellyArcher a42 = new SkellyArcher("Archer42");
		a42.setRank(rank.fourth);
		stageFour.add(e41);
		stageFour.add(h41);
		stageFour.add(a41);
		stageFour.add(a42);
		ArrayList<Enemy> stageFive = new ArrayList<Enemy>();
		DarkLord boss = new DarkLord("DarkLord");
		boss.setRank(rank.first);
		stageFive.add(boss);


		villains.add(stageOne);
		villains.add(stageTwo);
		villains.add(stageThree);
		villains.add(stageFour);
		villains.add(stageFive);
	}
	
	public static StackPane createStageTrans(int i) {
		
    	StackPane s = new StackPane();
    	s.setPrefWidth(1400);
    	s.setPrefHeight(680);
    	s.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY,Insets.EMPTY)));
		Font font = Font.loadFont(ClassLoader.getSystemResourceAsStream("MINECRAFT_FONT.ttf"), 80);
		s.setAlignment(Pos.CENTER);
		Text t = new Text("STAGE " + i);
		t.setFill(Color.WHITE);
		t.setFont(font);
		s.getChildren().add(t);

    	return s;
    }
	
	public void addHero(Ally hero) {
		team.add(hero);
	}
	
	public void removeHero(Ally hero) {
		team.remove(hero);
	}
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}
	
	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}

	public static ArrayList<Ally> getTeam() {
		return team;
	}

	public static void setTeam(ArrayList<Ally> team) {
		GameLogic.team = team;
	}

	public static ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public static void setEnemies(ArrayList<Enemy> enemies) {
		GameLogic.enemies = enemies;
	}

	public static ArrayList<ArrayList<Enemy>> getVillains() {
		return villains;
	}

	public static void setVillains(ArrayList<ArrayList<Enemy>> villains) {
		GameLogic.villains = villains;
	}

	public static ArrayList<BaseCharacter> getQueue() {
		return queue;
	}

	public static void setQueue(ArrayList<BaseCharacter> q) {
		GameLogic.queue = q;
	}

	public static int getStage() {
		return stage;
	}

	public static void setStage(int stage) {
		GameLogic.stage = stage;
	}

	public static boolean isGameEnd() {
		return isGameEnd;
	}

	public static void setGameEnd(boolean isGameEnd) {
		GameLogic.isGameEnd = isGameEnd;
	}

	public static boolean isWin() {
		return isWin;
	}

	public static void setWin(boolean isWin) {
		GameLogic.isWin = isWin;
	}

	public static boolean isStageCleared() {
		return isStageCleared;
	}

	public static void setStageCleared(boolean isStageCleared) {
		GameLogic.isStageCleared = isStageCleared;
	}

	public static BattleStage getCurrentStage() {
		return currentStage;
	}

	public static void setCurrentStage(BattleStage currentStage) {
		GameLogic.currentStage = currentStage;
	}

	public static BaseCharacter getCurrentCharacter() {
		return currentCharacter;
	}

	public static void setCurrentCharacter(BaseCharacter currentCharacter) {
		GameLogic.currentCharacter = currentCharacter;
	}

	public static BaseSkill getCurrentSkill() {
		return currentSkill;
	}

	public static void setCurrentSkill(BaseSkill currentSkill) {
		GameLogic.currentSkill = currentSkill;
	}

}


class SpeedComparator implements Comparator<BaseCharacter>{
    
    @Override
    public int compare(BaseCharacter c1, BaseCharacter c2) {
        if (c1.getCalculatedSpd() < c2.getCalculatedSpd()) return 1;
        if (c1.getCalculatedSpd() > c2.getCalculatedSpd()) return -1;
        return 0;
    }
    
}
