package com.jsoundlib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class AudioFile {
	private AudioInputStream ais;

	
	public void Play() throws Exception{
		SourceDataLine sdl = AudioSystem.getSourceDataLine(ais.getFormat());
		sdl.open();
		sdl.start();
		byte[] tba = ToByteArray();
		sdl.write(tba, 0, tba.length);
		sdl.stop();
		sdl.close();
	}
	
	public AudioFile(File audioFile) throws Exception{
		ais = AudioSystem.getAudioInputStream(audioFile);

	}
	public byte[] ToByteArray() throws Exception{
		int c = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[(int) ais.getFrameLength()];
		while((c = ais.read(buffer, 0, buffer.length))!=-1) {
			baos.write(buffer, 0, c);
		}
		return baos.toByteArray();
	}
	
	public void SaveTo(File out, Type type) throws Exception{
		AudioSystem.write(ais, type, out);
	}
	
	public AudioFile(byte[] byteArraySource, AudioFormat format) {
		ais = new AudioInputStream(new ByteArrayInputStream(byteArraySource), format, byteArraySource.length);
	}

}
