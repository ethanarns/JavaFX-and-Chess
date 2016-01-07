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
		clickSound = new AudioClip(new File("sounds/click0.wav").toURI().toString());
		errorSound = new AudioClip(new File("sounds/error0.wav").toURI().toString());
		placeSound = new AudioClip(new File("sounds/placeSuccess.wav").toURI().toString());
		slashSound = new AudioClip(new File("sounds/slash.wav").toURI().toString());
	}
	
	public void click(){
		clickSound.play();
	}
	
	public void error(){
		errorSound.play();
	}
	
	public void place(){
		placeSound.play();
	}
	
	public void slash(){
		slashSound.play();
	}

}
