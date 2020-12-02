package com.runningcode.tcs;

import java.io.FileInputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * @声音类
 * @author Nightcat
 * 用来播放音效
 */
public class SoundPlay {

	/**
	 * @播放一个声音
	 * @param path 声音文件地址
	 * @return
	 */
	private static boolean play(String path) {
		try {
			FileInputStream fileau = new FileInputStream(path);
			AudioStream au = new AudioStream(fileau);
			AudioPlayer.player.start(au);
		} catch (Exception e) {
			System.out.println("can`t find" + path);
		}
		return false;
	}
	
	/*
	private static boolean loopbgm(String path) {
		try {
			FileInputStream fileau = new FileInputStream(path);
			AudioStream bgmusic = new AudioStream(fileau);
			AudioData ad = bgmusic.getData();
			ContinuousAudioDataStream cads = new ContinuousAudioDataStream(ad);
			AudioPlayer.player.start(cads);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}*/

	public static void playEat() {
		play("./resource/sounds/eat.au");
	}

	public static void playTurn() {
		play("./resource/sounds/trun.au");
	}

	public static void playMove() {
		// playOne("./resource/sounds/eat.au");
	}

	public static void playStar() {
		play("./resource/sounds/start.au");
	}

	public static void playGameOver() {
		play("./resource/sounds/gameover.au");
	}
	
	/*
	public static void playBGM() {
		loopbgm("./resource/sounds/bgm.au");
	}*/

}
