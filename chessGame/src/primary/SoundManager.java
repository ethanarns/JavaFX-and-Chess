package primary;

import java.io.File;

import javafx.scene.media.AudioClip;

public class SoundManager {

	private AudioClip clickSound;
	private AudioClip errorSound;
	private AudioClip placeSound;
	private AudioClip slashSound;

	/**
	 * A class made as a utility for playing certain sounds. Simplifies things
	 * to do in the Main class, and also better organizationally.
	 */
	public SoundManager(){
		clickSound = new AudioClip(new File("./sounds/click0.wav").toURI().toString());
		errorSound = new AudioClip(new File("./sounds/error0.wav").toURI().toString());
		placeSound = new AudioClip(new File("./sounds/placeSuccess.wav").toURI().toString());
		slashSound = new AudioClip(new File("./sounds/slash.wav").toURI().toString());
	}

	/**
	 * Plays a click sound
	 */
	public void click(){
		clickSound.play();
	}

	/**
	 * Plays an error sound
	 */
	public void error(){
		errorSound.play();
	}

	/**
	 * Plays a sound used for placement
	 */
	public void place(){
		placeSound.play();
	}

	/**
	 * Plays a slashing sound
	 */
	public void slash(){
		slashSound.play();
	}

	/**
	 * Sets sound volume to custom value
	 * @param vol   double between 0.0 and 1.0 representing volume
	 */
	public void setVolume(double vol){
		clickSound.setVolume(vol);
		placeSound.setVolume(vol);
		slashSound.setVolume(vol);
		errorSound.setVolume(vol);
		click();
	}
}
