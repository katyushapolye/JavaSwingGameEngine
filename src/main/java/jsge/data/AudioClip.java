package jsge.data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

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
	Clip audioClip;
	float volume = 1.0f;
	boolean isLooping = false;
	
	File audioFile;
	byte[] audioBuffer;

	public AudioClip(String pathToFile) {
		try {
			audioFile = new File(pathToFile);
			//Creating binary data
			audioBuffer = Files.readAllBytes(Paths.get(pathToFile));
			input = AudioSystem.getAudioInputStream(audioFile);
			
			//audioBuffer = getAudioBufferFromAudioStream(input);
			resetAudioStream();
			audioClip =  AudioSystem.getClip();
			audioClip.addLineListener(new LineListener() {

				@Override
				public void update(LineEvent event) {
					if (event.getType().equals(Type.STOP)) {
						//System.out.println("END");
						//audioClip.drain();
						//audioClip.flush();
						audioClip.close();
					}

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("AudioClip: Warning - The clip has failed to load the audio data");

			return;
		}
	}

	// testing

	public void play() {
		if (audioClip.isOpen()) {
			return;
		}

		try {
			resetAudioStream();
			audioClip.open(input);
			audioClip.start();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	public void stop() {
		try {
			this.audioClip.stop();
		}
		catch(Exception e) {
			
		}
	}

	
	//
	private void resetAudioStream() throws UnsupportedAudioFileException, IOException {
		// Try to fit everythinh into byte array, resetting the stream losses audio
		// quality ???? and loading a new file everytime is wasteful
		
		input = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audioBuffer));
		//input = AudioSystem.getAudioInputStream(audioFile);
		
		
	}
	
	
	//Look into flexible wav header, cant keep up with all the possible ones
	/*
	private byte[] getAudioBufferFromAudioStream(AudioInputStream input) throws IOException {
		byte[] wavHeader =
				// Intel's little endian hex notation
				{82, 73, 70, 70, // RIFF STRING
				00, 00, 00, 00, // Filesize - To be set later along with datasize
				87, 65, 86, 69, // Wav ascii string
				102, 109, 116, 32, // "fmt " trailing space
				16, 00, 00, 00, // wav flags
				01, 00, 01, 00, // wav flags
				34, 86, 00, 00,
				34, 86, 00, 00,
				01, 00, 8, 00,
				100, 97, 116, 97,
				00, 00, 00, 00, // data size
				// start data bytes
				};
		byte[] wavData = input.readAllBytes();
		int wavDataLenght = wavData.length;
		byte[] filesize = ByteBuffer.allocate(4).putInt(Integer.reverseBytes(wavDataLenght + 36)).array();
		byte[] datasize = ByteBuffer.allocate(4).putInt(Integer.reverseBytes(wavDataLenght )).array();
		for (int i = 0; i < 4; i++) {
			wavHeader[4 + i] = filesize[i];
		}
		for (int i = 0; i < 4; i++) {
			wavHeader[40 + i] = datasize[i];
		}
		audioBuffer = new byte[44+wavDataLenght];
		input.reset();
		System.arraycopy(wavHeader,0,audioBuffer, 0, 44);
		System.arraycopy(wavData,0, audioBuffer,44, wavDataLenght);
		return audioBuffer;
		

	
	}
	*/

}
