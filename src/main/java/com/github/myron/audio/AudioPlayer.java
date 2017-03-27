/**
 * 
 */
package com.github.myron.audio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;

/**
 * @author gengmaozhang01
 * @since 下午9:13:42
 */
public class AudioPlayer {

	private final Logger logger = LoggerFactory.getLogger(AudioPlayer.class);

	public AudioPlayer() {
	}

	public void play(String file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		Preconditions.checkNotNull(file, "file is required");
		File audioFile = new File(file);
		if (!audioFile.exists()) {
			throw new FileNotFoundException("audio file " + file + " doesn't exist");
		}

		logger.info("play {}, starts...", audioFile.getName());
		AudioFileFormat audioFileFormat = AudioSystem.getAudioFileFormat(audioFile);
		logger.info("audioFile format: {}, encoding: {}", JSON.toJSONString(audioFileFormat), audioFileFormat
				.getFormat().getEncoding());
		// AudioFileFormat.Type type = audioFileFormat.getType();
		// if (!AudioSystem.isFileTypeSupported(type)) {
		// throw new UnsupportedAudioFileException(type + " isn't supported");
		// }
		// logger.info("support {}[{}]", type, type.getExtension());

		// 转换mp3文件编码
		AudioFormat audioFormat = audioFileFormat.getFormat();
		if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
			audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat.getSampleRate(), 16,
					audioFormat.getChannels(), audioFormat.getChannels() * 2, audioFormat.getSampleRate(), false);
		}

		DataLine.Info clipInfo = new DataLine.Info(Clip.class, audioFormat);
		if (!AudioSystem.isLineSupported(clipInfo)) {
			throw new UnsupportedAudioFileException("Clip isn't supported");
		}
		logger.info("support [{}]", JSON.toJSONString(clipInfo));

		Clip clip = (Clip) AudioSystem.getLine(clipInfo);
		clip.addLineListener(new LineEventListener(audioFile.getName()));
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
		if (audioFormat != audioFileFormat.getFormat()) {
			audioInputStream = AudioSystem.getAudioInputStream(audioFormat, audioInputStream);
		}
		clip.open(audioInputStream);
		// logger.info("controls: {}", JSON.toJSONString(clip.getControls()));
		// BooleanControl mute = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
		// mute.setValue(false);
		// FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		// volume.setValue(volume.getMaximum());
		clip.start();

		long millis = 1000;
		do {
			try {
				Thread.sleep(millis);
				if (millis > 10) {
					millis /= 2;
				}
			} catch (InterruptedException ie) {
				break;
			}
		} while (clip.isActive());
		clip.stop();
		clip.close();
	}

	public void play(String audioName, InputStream inputStream, AudioFormat audioFormat)
			throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		Preconditions.checkNotNull(inputStream, "inputStream is required");
		Preconditions.checkNotNull(audioFormat, "audioFormat is required");

		DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
		if (!AudioSystem.isLineSupported(lineInfo)) {
			throw new UnsupportedAudioFileException("SourceDataLine isn't supported");
		}
		logger.info("support {}", JSON.toJSONString(lineInfo));

		SourceDataLine dataLine = (SourceDataLine) AudioSystem.getLine(lineInfo);
		dataLine.addLineListener(new LineEventListener(audioName));
		dataLine.open(audioFormat);
		dataLine.start();
		// 必须是缓冲的，否则报mark/reset not supported
		BufferedInputStream audioStream = new BufferedInputStream(inputStream);
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);

		int frameSize = audioInputStream.getFormat().getFrameSize();
		if (frameSize == AudioSystem.NOT_SPECIFIED) {
			frameSize = 1;
		}
		byte[] buffer = new byte[1024 * frameSize];
		int len = 0;
		while ((len = audioInputStream.read(buffer)) != -1) {
			dataLine.write(buffer, 0, len);
		}
		dataLine.drain();
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
			// LOGGER.info("recieve event: {} of {}", event.getType(), event.getLine());
			if (event.getType() == LineEvent.Type.OPEN) {
				logger.info("open {}", this.audioName);
			} else if (event.getType() == LineEvent.Type.START) {
				logger.info("start to play {}", this.audioName);
			} else if (event.getType() == LineEvent.Type.STOP) {
				DataLine dataLine = (DataLine) event.getLine();
				dataLine.stop();
				dataLine.close();
				logger.info("stop playing {}", this.audioName);
			} else if (event.getType() == LineEvent.Type.CLOSE) {
				event.getLine().close();
				logger.info("close {}", this.audioName);
			}
		}

	}

}
