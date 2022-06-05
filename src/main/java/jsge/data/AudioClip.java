package jsge.data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineEvent.Type;

public class AudioClip {
	
	String audioName;
	AudioInputStream input;
	AudioInputStream def;
	byte[] audioBuffer =  new byte[10000000];
	Clip audioClip;
	
	private String pathToFile;
	
	public AudioClip(String pathToFile) {
		this.pathToFile = pathToFile;
		try {
		input = AudioSystem.getAudioInputStream(new File(pathToFile));
		//audioBuffer = input.readAllBytes();
		//def = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audioBuffer));
		audioClip = AudioSystem.getClip();
		
		audioClip.addLineListener(new LineListener() {
			
			@Override
			public void update(LineEvent event) {
				if(event.getType().equals(Type.STOP)){
					System.out.println("END");
					audioClip.drain();
					audioClip.flush();
					audioClip.close();
		}
				
			}
		});
		}
		catch(Exception e) {
			System.out.println("AudioClip: Warning - The clip has failed to load.");
			
			
			return;
		}
	}
	
	//testing
	
	public void playTest(){
		if(audioClip.isOpen()) {
			return;
		}
		
		try {
			resetAudioStream();
			audioClip.open(input);
			audioClip.start();
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	private void resetAudioStream() throws UnsupportedAudioFileException, IOException {
		//Try to fit everythinh into byte array, resetting the stream losses audio quality ???? and loading a new file everytime is wasteful
		this.input.reset();
	}

}
