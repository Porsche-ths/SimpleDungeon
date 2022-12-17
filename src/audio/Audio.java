package audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Audio {
	public static final MediaPlayer click = new MediaPlayer(new Media(ClassLoader.getSystemResource("button.mp3").toExternalForm()));
	public static final MediaPlayer heal = new MediaPlayer(new Media(ClassLoader.getSystemResource("priestHealing.mp3").toExternalForm()));
	public static final MediaPlayer attack = new MediaPlayer(new Media(ClassLoader.getSystemResource("sword.mp3").toExternalForm()));
	public static final MediaPlayer rogueSkill1 = new MediaPlayer(new Media(ClassLoader.getSystemResource("rogueSkill1.mp3").toExternalForm()));
	public static final MediaPlayer rogueSkill2 = new MediaPlayer(new Media(ClassLoader.getSystemResource("rogueSkill2.mp3").toExternalForm()));
	public static final MediaPlayer stageClear = new MediaPlayer(new Media(ClassLoader.getSystemResource("stageClear.mp3").toExternalForm()));
	public static final MediaPlayer darkLord = new MediaPlayer(new Media(ClassLoader.getSystemResource("darkLordattack.mp3").toExternalForm()));
}
