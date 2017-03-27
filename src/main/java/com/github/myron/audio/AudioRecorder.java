/**
 * 
 */
package com.github.myron.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;

/**
 * @author gengmaozhang01
 * @since 下午12:40:28
 */
public class AudioRecorder {

	private final Logger logger = LoggerFactory.getLogger(AudioRecorder.class);

	public AudioRecorder() {
	}

	public void record(File audioFile, long seconds, AudioFormat audioFormat) throws UnsupportedAudioFileException,
			LineUnavailableException, IOException {
		Preconditions.checkNotNull(audioFormat, "audioFormat is required");
		Preconditions.checkNotNull(audioFile, "audioFile is required");

		DataLine.Info lineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
		if (!AudioSystem.isLineSupported(lineInfo)) {
			throw new UnsupportedAudioFileException("TargetDataLine isn't supported");
		}
		logger.info("support {}", JSON.toJSONString(lineInfo));

		TargetDataLine dataLine = (TargetDataLine) AudioSystem.getLine(lineInfo);
		dataLine.addLineListener(new LineEventListener(audioFile.getName()));
		dataLine.open(audioFormat);
		Thread recorder = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					dataLine.start();
					AudioInputStream inputStream = new AudioInputStream(dataLine);
					AudioSystem.write(inputStream, AudioFileFormat.Type.WAVE, audioFile);
				} catch (Exception ex) {
					logger.error("record failed", ex);
				}
			}

		});
		recorder.start();

		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception ex) {
			logger.error("sleep interrupted", ex);
		}

		dataLine.stop();
		dataLine.close();
	}

	class LineEventListener implements LineListener {

		private String audioName;

		public LineEventListener(String audioName) {
			this.audioName = audioName;
		}

		@Override
		public void update(LineEvent event) {
			if (event.getType() == LineEvent.Type.OPEN) {
				logger.info("open {}", this.audioName);
			} else if (event.getType() == LineEvent.Type.START) {
				logger.info("start to record {}", this.audioName);
			} else if (event.getType() == LineEvent.Type.STOP) {
				DataLine dataLine = (DataLine) event.getLine();
				dataLine.stop();
				dataLine.close();
				logger.info("stop recording {}", this.audioName);
			} else if (event.getType() == LineEvent.Type.CLOSE) {
				event.getLine().close();
				logger.info("close {}", this.audioName);
			}
		}

	}

}
