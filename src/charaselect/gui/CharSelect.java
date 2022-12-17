package charaselect.gui;

import java.util.ArrayList;

import app.Main;
import audio.Audio;
import chara.ally.Crusader;
import chara.ally.Priest;
import chara.ally.Ranger;
import chara.ally.Rogue;
import chara.base.Ally;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import logic.GameLogic;
import logic.rank;

public class CharSelect extends StackPane {
	
	private GridPane selectedCharaBox;
	private HBox selectableCharaBox;
	private StackPane crusaderBox,rangerBox,priestBox,rogueBox;
	private ImageView resetButton,confirmButton,backButton;
	private HBox buttonPanel;

	public CharSelect() {
		GameLogic.setTeam(new ArrayList<Ally>());
		setPrefWidth(1400);
		setPrefHeight(680);
		setAlignment(Pos.CENTER);
		Image textImg = new Image(ClassLoader.getSystemResource("heroesSelectText.png").toString());
		ImageView text = new ImageView(textImg);
		getChildren().add(text);
		setAlignment(text,Pos.TOP_CENTER);
		Image bg = new Image(ClassLoader.getSystemResource("charSelectBG.png").toString());
		setBackground(new Background(new BackgroundFill(new ImagePattern(bg),CornerRadii.EMPTY, Insets.EMPTY)));

		
		addSelectedCharaBox();
		addSelectableCharaBox();
		addOnMouseClicked();
//		=======================Button Panel=======================
		buttonPanel = new HBox();
		getChildren().add(buttonPanel);
		buttonPanel.setPadding(new Insets(0,0,50,0));
		buttonPanel.setSpacing(50);;
		buttonPanel.setMaxHeight(10);
		buttonPanel.setMaxWidth(800);
		buttonPanel.setAlignment(Pos.CENTER);
		setAlignment(buttonPanel,Pos.BOTTOM_CENTER);
		addBackButton();
		addResetButton();
		addConfirmButton();
		resetSelectedChara();
//		=======================Test=================================

	}
	private void addSelectableCharaBox() {
		selectableCharaBox = new HBox();
		selectableCharaBox.setMaxWidth(400);
		selectableCharaBox.setMaxHeight(400);
		selectableCharaBox.setSpacing(30);
//		============Crusader Box==============
		crusaderBox = new StackPane();
		crusaderBox.setMaxHeight(120);
		crusaderBox.setMaxWidth(120);
		Image crusader = new Image(ClassLoader.getSystemResource("crusaderBox.png").toString());
		ImageView crusaderSquare = new ImageView(crusader);
		crusaderSquare.setFitHeight(120);
		crusaderSquare.setFitWidth(120);
		crusaderBox.getChildren().add(crusaderSquare);
		selectableCharaBox.getChildren().add(crusaderBox);
//		============Priest Box=================
		priestBox = new StackPane();
		priestBox.setMaxHeight(120);
		priestBox.setMaxWidth(120);
		Image priest = new Image(ClassLoader.getSystemResource("priestBox.png").toString());
		ImageView priestSquare = new ImageView(priest);
		priestSquare.setFitHeight(120);
		priestSquare.setFitWidth(120);
		priestBox.getChildren().add(priestSquare);
		selectableCharaBox.getChildren().add(priestBox);
////		============Ranger Box=================
		rangerBox = new StackPane();
		rangerBox.setMaxHeight(120);
		rangerBox.setMaxWidth(120);
		Image ranger = new Image(ClassLoader.getSystemResource("rangerBox.png").toString());
		ImageView rangerSquare = new ImageView(ranger);
		rangerSquare.setFitHeight(120);
		rangerSquare.setFitWidth(120);
		rangerBox.getChildren().add(rangerSquare);
		selectableCharaBox.getChildren().add(rangerBox);
////		============Rogue Box=================
		rogueBox = new StackPane();
		rogueBox.setMaxHeight(120);
		rogueBox.setMaxWidth(120);
		Image rogue = new Image(ClassLoader.getSystemResource("rogueBox.png").toString());
		ImageView rogueSquare = new ImageView(rogue);
		rogueSquare.setFitHeight(120);
		rogueSquare.setFitWidth(120);
		selectableCharaBox.getChildren().add(rogueBox);
		rogueBox.getChildren().add(rogueSquare);
		getChildren().add(selectableCharaBox);
		setAlignment(selectableCharaBox,Pos.BOTTOM_CENTER);

	}
	private void addSelectedCharaBox() {
		selectedCharaBox = new GridPane() ;
		selectedCharaBox.setMaxWidth(800);
		selectedCharaBox.setMaxHeight(400);
		selectedCharaBox.setHgap(30);
		selectedCharaBox.setAlignment(Pos.TOP_CENTER);
		getChildren().add(selectedCharaBox);
	}
	private void addOnMouseClicked() {
		crusaderBox.setOnMouseClicked(new EventHandler <Event>() {
			@Override
			public void handle(Event arg0) {
				charaBoxHandler("crusader",new Crusader(Integer.toString(4 - GameLogic.getTeam().size())));
			}
			
		});
		priestBox.setOnMouseClicked(new EventHandler <Event>() {
			@Override
			public void handle(Event arg0) {
				charaBoxHandler("priest",new Priest(Integer.toString(4 - GameLogic.getTeam().size())));
			}
			
		});
		rangerBox.setOnMouseClicked(new EventHandler <Event>() {
			@Override
			public void handle(Event arg0) {
				charaBoxHandler("ranger",new Ranger(Integer.toString(4 - GameLogic.getTeam().size())));
			}
			
		});
		rogueBox.setOnMouseClicked(new EventHandler <Event>() {
			@Override
			public void handle(Event arg0) {
				charaBoxHandler("rogue",new Rogue(Integer.toString(4 - GameLogic.getTeam().size())));
			}
			
		});
		
	}
	private StackPane createClassBox(String className) {
		StackPane classBox = new StackPane();
		classBox.setMaxHeight(120);
		classBox.setMaxWidth(120);
		Image classImg = new Image(ClassLoader.getSystemResource(className + "Box.png").toString());
		ImageView classSquare = new ImageView(classImg);
		classSquare.setFitHeight(120);
		classSquare.setFitWidth(120);
		classBox.getChildren().add(classSquare);
		return classBox;
	}
	public void resetSelectedChara() {
		for(int i = 0;i<4;i++) {
			StackPane box = new StackPane();
			box.setMaxHeight(120);
			box.setMaxWidth(120);
			Image charaBox = new Image(ClassLoader.getSystemResource("charaBox.png").toString());
			ImageView emptyBox = new ImageView(charaBox);
			emptyBox.setFitHeight(120);
			emptyBox.setFitWidth(120);
			box.getChildren().add(emptyBox);
			selectedCharaBox.add(box,i,0);
		}
		GameLogic.getTeam().clear();
		disableConfirmButton();
		
	}
	private void charaBoxHandler(String className,Ally a) {
		Audio.click.stop();
		Audio.click.play();
		int n = GameLogic.getTeam().size();
		if(n<4) {
			enableConfirmButton();
			GameLogic.getTeam().add(a);
			selectedCharaBox.add(createClassBox(className),n,0);
			}
		else {
			resetSelectedChara();
		}
		
	}
	private void addBackButton() {
		Image inactivatedBack = new Image(ClassLoader.getSystemResource("inactivatedBack.png").toString());
		Image activatedBack = new Image(ClassLoader.getSystemResource("activatedBack.png").toString());
		backButton = new ImageView(inactivatedBack);
		backButton.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				Audio.click.stop();
				Audio.click.play();
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								backButton.setImage(activatedBack);
							}
							
						});
						try {
							Thread.sleep(250);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								backButton.setImage(inactivatedBack);
								Main.switchToMainMenu();
							}
							
						});

					}
					
				});
				t.start();

			}
			
		});
		buttonPanel.getChildren().add(backButton);
		
	}
	private void addResetButton() {

		
		Image inactivatedReset = new Image(ClassLoader.getSystemResource("inactivatedReset.png").toString());
		Image activatedReset = new Image(ClassLoader.getSystemResource("activatedReset.png").toString());
		resetButton = new ImageView(inactivatedReset);
		resetButton.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				Audio.click.stop();
				Audio.click.play();
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								resetButton.setImage(activatedReset);
							}
							
						});
						try {
							Thread.sleep(250);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								resetSelectedChara();
								resetButton.setImage(inactivatedReset);

							}
							
						});

					}
					
				});
				t.start();

			}
			
		});
		buttonPanel.getChildren().add(resetButton);
		
	}
	private void addConfirmButton() {
		
		Image inactivatedConfirm = new Image("inactivatedConfirm.png");
		Image activatedConfirm = new Image("activatedConfirm.png");
		
		confirmButton = new ImageView(inactivatedConfirm);
		confirmButton.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				Audio.click.stop();
				Audio.click.play();
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								confirmButton.setImage(activatedConfirm);
							}
							
						});
						try {
							Thread.sleep(250);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								confirmButton.setImage(inactivatedConfirm);
								switch(GameLogic.getTeam().size()) {
								case 4:
									GameLogic.getTeam().get(0).setRank(rank.fourth); 
									GameLogic.getTeam().get(1).setRank(rank.third); 
									GameLogic.getTeam().get(2).setRank(rank.second); 
									GameLogic.getTeam().get(3).setRank(rank.first); 
									break;
								case 3:
									GameLogic.getTeam().get(0).setRank(rank.third); 
									GameLogic.getTeam().get(1).setRank(rank.second); 
									GameLogic.getTeam().get(2).setRank(rank.first); 
									 break;
								case 2:
									GameLogic.getTeam().get(0).setRank(rank.second); 
									GameLogic.getTeam().get(1).setRank(rank.first); 

									break;
								case 1:
									GameLogic.getTeam().get(0).setRank(rank.first); break;
								}
								Main.switchToBattleStage();

							}
							
						});

					}
					
				});
				t.start();

			}
			
		});
		buttonPanel.getChildren().add(confirmButton);
		
	}
	private void disableConfirmButton() {
		Image disabledConfirm = new Image(ClassLoader.getSystemResource("disabledConfirm.png").toString());
		confirmButton.setDisable(true);
		confirmButton.setImage(disabledConfirm);
		
	}
	private void enableConfirmButton() {
		Image inactivatedConfirm = new Image(ClassLoader.getSystemResource("inactivatedConfirm.png").toString());
		confirmButton.setDisable(false);
		confirmButton.setImage(inactivatedConfirm);
		
	}
	
}
