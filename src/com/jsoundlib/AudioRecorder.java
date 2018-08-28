package com.jsoundlib;

import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

public class AudioRecorder {
	private boolean recording = false;
	private AudioFormat af = new AudioFormat(44100, 16, 2, true, true);
	private ByteArrayOutputStream baos = new ByteArrayOutputStream();

	private Thread t;
	
	TargetDataLine tdl ;
	public AudioRecorder(boolean useDefaultAudioFormat, AudioFormat af) throws Exception{
		if(useDefaultAudioFormat) {
			this.af = af;
		}
		tdl = AudioSystem.getTargetDataLine(this.af);
		tdl.open();
	}
	public void Start() throws Exception {
		t= new Thread() {
			@Override
			public void run() {
				byte[] buffer = new byte[tdl.getBufferSize()];
				tdl.start();
				while(recording) {
					baos.write(buffer, 0, tdl.read(buffer, 0, buffer.length));
				}
				tdl.stop();
				tdl.close();
			}
		};
		recording = true;
		t.start();
	}
	public void Stop() {
		recording = false;
	}
	
	public AudioFile GenerateAudioFile() {
		return new AudioFile(baos.toByteArray(),af);
	}

}
