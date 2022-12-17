package sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IdleSprite extends ImageView{
	
	public IdleSprite(String name) {
		Image img = new Image(ClassLoader.getSystemResource(name + "Idle.gif").toString());
		setImage(img);
		setSizeByName(name);
	}

	private void setSizeByName(String name) {
		if(name.equals("crusader")) {
			setFitHeight(200);
			setFitWidth(100);
		}
		if(name.equals("priest")) {
			setFitHeight(200);
			setFitWidth(110);
		}
		if(name.equals("ranger")) {
			setFitHeight(200);
			setFitWidth(110);
		}
		if(name.equals("rogue")) {
			setFitHeight(200);
			setFitWidth(125);
		}
		if(name.equals("skellySoldier")) {
			setFitHeight(180);
			setFitWidth(140);
		}
		if(name.equals("skellyArcher")) {
			setFitHeight(180);
			setFitWidth(120);
		}
		if(name.equals("hemomancer")) {
			setFitHeight(220);
			setFitWidth(140);
		}
		if(name.equals("executioner")) {
			setFitHeight(200);
			setFitWidth(180);
		}
		if(name.equals("darkLord")){
			setFitHeight(300);
			setFitWidth(250);
		}
		
	}
}
