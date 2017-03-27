/**
 * 
 */
package com.github.myron.audio;

import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * @author gengmaozhang01
 * @since 下午4:44:30
 */
public class JavaSound {

	private static final Logger LOGGER = LoggerFactory.getLogger(JavaSound.class);

	static void playByClip(String audioFile) throws Exception {
		AudioPlayer player = new AudioPlayer();
		player.play(audioFile);
	}

	static void playBySourceDataLine(String audioFile) throws Exception {
		File inputFile = new File(audioFile);
		AudioFileFormat audioFileFormat = AudioSystem.getAudioFileFormat(inputFile);

		AudioPlayer player = new AudioPlayer();
		player.play(inputFile.getName(), new FileInputStream(inputFile), audioFileFormat.getFormat());
	}

	static void record(String audioFile, long seconds) throws Exception {
		File inputFile = new File("E:\\download\\上海滩_铃声之家cnwav.wav");
		AudioFileFormat audioFileFormat = AudioSystem.getAudioFileFormat(inputFile);

		AudioRecorder recorder = new AudioRecorder();
		recorder.record(new File(audioFile), seconds, audioFileFormat.getFormat());
	}

	/**
	 * @author gengmaozhang01
	 * @since 下午4:44:31
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		for (Mixer.Info mixInfo : mixInfos) {
			LOGGER.info(mixInfo.getName() + ", " + mixInfo.getVersion() + ", " + mixInfo.getVendor() + ", "
					+ mixInfo.getDescription());
			Mixer mixer = AudioSystem.getMixer(mixInfo);
			LOGGER.info("sourceLineInfo: {}", JSON.toJSONString(mixer.getSourceLineInfo()));
			LOGGER.info("targetLineInfo: {}", JSON.toJSONString(mixer.getTargetLineInfo()));
			LOGGER.info("controls: {}", JSON.toJSONString(mixer.getControls()));
			System.out.println();
		}
		LOGGER.info("audioFile types: {}", JSON.toJSONString(AudioSystem.getAudioFileTypes()));
		System.out.println();
		System.out.println();

		// control
		// AudioControl control = new AudioControl();
		// control.setVolume(1.00F);

		// convert
		String inFile = "E:\\download\\楊千嬅 再見二丁目.wav";
		String outFile = "E:\\download\\楊千嬅 再見二丁目.wav.aif";
		AudioConvertor convertor = new AudioConvertor();
		AudioFormat audioFormat = new AudioFormat(44.1f * 1000, 16, 1, true, true);
		convertor.convert(inFile, outFile, AudioFileFormat.Type.AIFF, audioFormat);

		// play
		String audioFile = "E:\\download\\寂寞列车.mp3";
		playByClip(audioFile);
		// playBySourceDataLine(audioFile);

		// record
		// AudioFileFormat.Type[] types = AudioSystem.getAudioFileTypes();
		// LOGGER.info("audioTypes: {}", JSON.toJSONString(types));
		// audioFile = "E:\\download\\audio-record.wav";
		// record(audioFile, 10);
	}

}
