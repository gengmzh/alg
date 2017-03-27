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
import javax.sound.sampled.UnsupportedAudioFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;

/**
 * @author gengmaozhang01
 * @since 上午10:37:36
 */
public class AudioConvertor {

	private final Logger logger = LoggerFactory.getLogger(AudioConvertor.class);

	public AudioConvertor() {
	}

	public void convert(String inFile, String outFile, AudioFileFormat.Type type, AudioFormat audioFormat)
			throws UnsupportedAudioFileException, IOException {
		Preconditions.checkNotNull(inFile, "inFile is required");
		Preconditions.checkNotNull(outFile, "outFile is required");
		Preconditions.checkNotNull(type, "type is required");

		File inAudioFile = new File(inFile);
		Preconditions.checkState(inAudioFile.exists() && inAudioFile.isFile(), "inFile doesn't exist or isn't a file");

		Preconditions.checkState(outFile.endsWith(type.getExtension()), "outFile type is wrong");
		File outAudioFile = new File(outFile);
		File _parent = new File(outAudioFile.getParent());
		if (!_parent.exists()) {
			_parent.mkdirs();
		}
		outAudioFile.createNewFile();
		Preconditions.checkState(outAudioFile.exists() && outAudioFile.isFile(), "outFile isn't file");

		// 相同类型直接拷贝
		AudioFileFormat inputFileFormat = AudioSystem.getAudioFileFormat(inAudioFile);
		logger.info("input audio format: {}", JSON.toJSONString(inputFileFormat));
		if (inputFileFormat.getType() == type && audioFormat == null) {
			Files.copy(inAudioFile, inAudioFile);
			return;
		}

		// 音频转换
		AudioInputStream inputStream = AudioSystem.getAudioInputStream(inAudioFile);
		if (!AudioSystem.isFileTypeSupported(type, inputStream)) {
			throw new UnsupportedAudioFileException(type + " isn't supported");
		}
		if (audioFormat != null) {
			if (!AudioSystem.isConversionSupported(audioFormat, inputFileFormat.getFormat())) {
				throw new UnsupportedAudioFileException("not support " + audioFormat);
			}
			inputStream = AudioSystem.getAudioInputStream(audioFormat, inputStream);
		}

		// inputStream.reset(); //mark/reset not supported
		logger.info("support {}[{}]", type, type.getExtension());

		logger.info("start to write audio file {}", outFile);
		AudioSystem.write(inputStream, type, outAudioFile);
		logger.info("stop writing audio file {}", outFile);

		inputStream.close();
	}

}
