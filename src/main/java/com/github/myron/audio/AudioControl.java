/**
 * 
 */
package com.github.myron.audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gengmaozhang01
 * @since 下午5:34:56
 */
public class AudioControl {

	private final Logger logger = LoggerFactory.getLogger(AudioControl.class);

	public AudioControl() {
	}

	// TODO: doesn't work
	public void setVolume(float volume) {
		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		// System.out.println("There are " + mixers.length + " mixer info objects");
		for (Mixer.Info mixerInfo : mixers) {
			// System.out.println("Mixer name: " + mixerInfo.getName());
			logger.info("mixer {}", mixerInfo.getName());
			Mixer mixer = AudioSystem.getMixer(mixerInfo);
			Line.Info[] lineInfos = mixer.getTargetLineInfo(); // target, not source
			for (Line.Info lineInfo : lineInfos) {
				// System.out.println("  Line.Info: " + lineInfo);
				Line line = null;
				boolean opened = true;
				try {
					line = mixer.getLine(lineInfo);
					opened = line.isOpen() || line instanceof Clip;
					if (!opened) {
						line.open();
					}
					FloatControl volCtrl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
					// System.out.println("    volCtrl.getValue() = " + volCtrl.getValue());
					volCtrl.setValue(volCtrl.getMaximum() * 100);
					logger.info("set volume with {}", volCtrl.getValue());
				} catch (Exception iaEx) {
					// System.out.println("    " + iaEx);
					logger.error("try to set volume failed", iaEx);
				} finally {
					if (line != null && !opened) {
						line.close();
					}
				}
			}
		}
	}

}
