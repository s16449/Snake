package ManiekSnake;

import java.io.File;
import java.util.LinkedList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MediaApp {
	private String temp;
	private ObservableList<MediaPlayer> lista;
	private int number;

	public MediaApp() {
		lista = FXCollections.observableArrayList();

		number = (int) (Math.random() * lista.size());

		for (MediaPlayer selection : lista) {
			selection.setCycleCount(MediaPlayer.INDEFINITE);
		}
	}

	public void play() {
		if (!lista.isEmpty()) {
			lista.get(number).play();
		}
	}

	public void stop() {
		if (!lista.isEmpty()) {
			lista.get(number).stop();
		}
	}

	public void next() {
		if (!lista.isEmpty()) {
			if (number < lista.size() - 1) {
				lista.get(number).stop();

				number++;

				lista.get(number).play();
			} else {
				lista.get(number).stop();

				number = 0;

				lista.get(number).play();
			}
		}
	}

	public void prev() {
		if (!lista.isEmpty()) {
			if (number == 0) {
				lista.get(number).stop();

				number = lista.size() - 1;

				lista.get(number).play();
			} else {
				lista.get(number).stop();

				number--;

				lista.get(number).play();
			}
		}
	}

	public void addMp3(String song) {
		temp = song;
		lista.add(new MediaPlayer(new Media(new File(temp).toURI().toString())));
	}
}
