package ManiekSnake;

import java.io.Serializable;

public class Gamer implements Serializable {

	private String name;
	private Integer score;
	private Integer minute;
	private Integer sec;

	public Gamer(String name, Integer score, Integer minute, Integer sec) {
		this.name = name;
		this.score = score;
		this.minute = minute;
		this.sec = sec;
	}

	public Integer getScore() {
		return score;
	}

	public String toString() {
		return "NAME: " + name + " SCORE: " + score + " , TIME: " + minute + ":" + sec;
	}
}