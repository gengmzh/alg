/**
 * 
 */
package com.github.myron.audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * @author gengmaozhang01
 * @since 下午2:55:21
 */
public class JavaMIDI {

	private static final Logger LOGGER = LoggerFactory.getLogger(JavaMIDI.class);

	public static void main(String[] args) throws Exception {
		// device
		Sequencer sequencer = null;
		Synthesizer synthesizer = null;
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (MidiDevice.Info info : infos) {
			LOGGER.info("midiDevice info: {}", JSON.toJSONString(info));
			MidiDevice device = MidiSystem.getMidiDevice(info);
			if (device instanceof Sequencer) {
				sequencer = (Sequencer) device;
				LOGGER.info("sequencer: {}", sequencer);
			} else if (device instanceof Synthesizer) {
				synthesizer = (Synthesizer) device;
				LOGGER.info("synthesizer: {}", synthesizer);
			}
		}

		ShortMessage message = new ShortMessage();
		message.setMessage(ShortMessage.NOTE_ON, 0, 60, 93);
		Receiver receiver = MidiSystem.getReceiver();
		receiver.send(message, 1 * 1000);
		Thread.sleep(3 * 1000);

		// play
		LOGGER.info("start to play");
		sequencer = MidiSystem.getSequencer();
		LOGGER.info("sequencer: {}", sequencer.getDeviceInfo());
		sequencer.open();
		String midiFile = "E:\\download\\Media\\淚海.mid";
		InputStream midiStream = new BufferedInputStream(new FileInputStream(midiFile));
		sequencer.setSequence(midiStream);

		if (!(sequencer instanceof Synthesizer)) {
			// synthesizer = MidiSystem.getSynthesizer();
			// LOGGER.info("synthesizer: {}", synthesizer.getDeviceInfo());
			// synthesizer.open();
			sequencer.getTransmitter().setReceiver(MidiSystem.getReceiver());
		}

		sequencer.start();
		// Thread.sleep(10 * 1000);
		// sequencer.stop();
		// LOGGER.info("stop playing");

	}

}
