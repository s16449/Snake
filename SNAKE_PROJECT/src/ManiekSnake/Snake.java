package ManiekSnake;

import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Snake {

	private Label score, scoreValue, timer, mins, colon, seconds, exitInfo, info;

	public enum Directions {
		UP, DOWN, LEFT, RIGTH;
	}

	private BorderPane border;
	public int cellSize = 30;
	public int snakeWidth;
	public int snakeHeigth;
	public Node snakeTail;
	public Directions direct = Directions.RIGTH;
	public boolean move = false;
	public boolean isRun = false;
	private Integer count = 0;
	private Timeline timeline = new Timeline();
	public Timeline time = new Timeline();
	private Rectangle fruit, snakeFirstElement, snakePart, fancyFruit;
	private ObservableList<Node> snakeParts;
	private double millis;
	private boolean walls;
	private Stage stage;
	private Integer second = 0;
	private Integer minuts = 0;
	private ColumnConstraints col1, col2, timeCol1, timeCol2, timeCol3, timeCol4;
	private GridPane gPane;
	private GridPane made;
	private MediaApp media = new MediaApp();
	private KeyFrame kFrame, timeFrame;
	private int point;

	public Snake(int valW, int valH, double millis, Stage primary) {
		snakeWidth = valW * cellSize;
		snakeHeigth = valH * cellSize;
		this.millis = millis;
		stage = primary;

	}

	public Parent snakeGame() {

		Pane root = new Pane();
		root.setPrefSize(snakeWidth, snakeHeigth);

		time.setCycleCount(Timeline.INDEFINITE);

		score = new Label("SCORE: ");

		scoreValue = new Label("00");

		GridPane scoreView = new GridPane();

		GridPane timeView = new GridPane();
		exitInfo = new Label("For exit press: CTRL + SHIFT + Q");

		timer = new Label("TIME: ");

		mins = new Label("00");

		colon = new Label(":");

		seconds = new Label("00");
		made = new GridPane();

		info = new Label("Made by S16449");
		info.setAlignment(Pos.BASELINE_LEFT);

		exitInfo.setAlignment(Pos.BASELINE_RIGHT);
		exitInfo.setPadding(new Insets(0, 0, 0, snakeWidth / 7));
		GridPane.setConstraints(info, 0, 0);
		GridPane.setConstraints(exitInfo, 1, 0);

		made.getChildren().addAll(info, exitInfo);
		made.getStyleClass().add("grid");

		timeCol1 = new ColumnConstraints(107);

		timeCol2 = new ColumnConstraints(52);

		timeCol3 = new ColumnConstraints(8);

		timeCol4 = new ColumnConstraints(52);

		scoreView.getChildren().addAll(score, scoreValue);

		GridPane.setConstraints(score, 0, 0);
		GridPane.setConstraints(scoreValue, 1, 0);

		GridPane.setConstraints(timer, 0, 0);
		GridPane.setConstraints(mins, 1, 0);
		GridPane.setConstraints(colon, 2, 0);
		GridPane.setConstraints(seconds, 3, 0);

		timer.setAlignment(Pos.CENTER_RIGHT);
		mins.setAlignment(Pos.CENTER);
		colon.setAlignment(Pos.CENTER);
		seconds.setAlignment(Pos.CENTER);

		timeView.getChildren().addAll(timer, mins, colon, seconds);
		timeView.getColumnConstraints().addAll(timeCol1, timeCol2, timeCol3, timeCol4);

		gPane = new GridPane();
		col1 = new ColumnConstraints();
		col1.setPercentWidth(45);
		col2 = new ColumnConstraints();
		col2.setPercentWidth(55);

		gPane.getColumnConstraints().addAll(col1, col2);
		gPane.setMinHeight(30);
		scoreView.setAlignment(Pos.TOP_LEFT);

		timeView.setAlignment(Pos.BASELINE_RIGHT);

		gPane.getChildren().addAll(scoreView, timeView);
		// gPane.setStyle("-fx-border-color: red");
		GridPane.setConstraints(scoreView, 0, 0);
		GridPane.setConstraints(timeView, 1, 0);

		gPane.getStyleClass().add("grid");

		border = new BorderPane();
		border.setTop(gPane);
		border.setCenter(root);
		border.setBottom(made);
		made.setMinHeight(30);
		Group snake = new Group();
		snakeParts = snake.getChildren();

		fruit = new Rectangle(cellSize, cellSize);
		fruit.setFill(Color.WHITE);
		fruit.setTranslateX((int) (Math.random() * (snakeWidth - cellSize)) / cellSize * cellSize);
		fruit.setTranslateY((int) (Math.random() * (snakeHeigth - cellSize)) / cellSize * cellSize);
		fruit.setArcHeight(15);
		fruit.setArcWidth(15);

		fancyFruit = new Rectangle(cellSize, cellSize);
		fancyFruit.setFill(Color.BLACK);
		fancyFruit.setTranslateX((int) (Math.random() * (snakeWidth - cellSize)) / cellSize * cellSize);
		fancyFruit.setTranslateY((int) (Math.random() * (snakeHeigth - cellSize)) / cellSize * cellSize);
		fancyFruit.setArcHeight(15);
		fancyFruit.setArcWidth(15);

		FillTransition ft = new FillTransition(Duration.millis(1000), fancyFruit, Color.BLACK, Color.YELLOWGREEN);
		ft.setCycleCount(Timeline.INDEFINITE);
		ft.setAutoReverse(true);
		ft.play();

		timeFrame = new KeyFrame(Duration.seconds(1), e -> {
			second++;
			if(fancyFruit.isVisible())
			{
				if(second>millis*0.3)
				{
					fancyFruit.setVisible(false);
				}
			}
			if (second < 10) {
				seconds.setText(0 + Integer.toString(second));
			} else {
				seconds.setText(Integer.toString(second));
			}
			if (second == 59) {
				minuts++;
				if (minuts < 10) {
					mins.setText(0 + Integer.toString(minuts));
				} else {
					mins.setText(Integer.toString(minuts));
				}
				second = 00;
			}
		});

		kFrame = new KeyFrame(Duration.millis(millis), e -> {

			boolean changeTail = snakeParts.size() > 1;

			snakeTail = changeTail ? snakeParts.remove(snakeParts.size() - 1) : snakeParts.get(0);

			double tailX = snakeTail.getTranslateX();
			double tailY = snakeTail.getTranslateY();

			switch (direct) {
			case UP:
				snakeTail.setTranslateX(snakeParts.get(0).getTranslateX());
				snakeTail.setTranslateY(snakeParts.get(0).getTranslateY() - cellSize);
				break;
			case DOWN:
				snakeTail.setTranslateX(snakeParts.get(0).getTranslateX());
				snakeTail.setTranslateY(snakeParts.get(0).getTranslateY() + cellSize);
				break;
			case LEFT:
				snakeTail.setTranslateX(snakeParts.get(0).getTranslateX() - cellSize);
				snakeTail.setTranslateY(snakeParts.get(0).getTranslateY());
				break;
			case RIGTH:
				snakeTail.setTranslateX(snakeParts.get(0).getTranslateX() + cellSize);
				snakeTail.setTranslateY(snakeParts.get(0).getTranslateY());
				break;

			}

			move = true;

			if (changeTail) {
				snakeParts.add(0, snakeTail);
			}

			try {
				snakeParts.forEach((item) -> {
					if (item != snakeTail && snakeTail.getTranslateX() == item.getTranslateX()
							&& snakeTail.getTranslateY() == item.getTranslateY()) {
						stopSnake();
						Main.setScore(Main.getName(), count, minuts, second);
					}
				});
			} catch (Exception exc) {
				ExceptionWindow.display("AUTOCANIBAL ALERT", "Your Score: " + count + ", Time " + minuts + ":" + second,
						stage);
				stopSnake();
			}

			if (walls) {
				if (snakeTail.getTranslateX() < 0 || snakeTail.getTranslateX() >= snakeWidth
						|| snakeTail.getTranslateY() < 0 || snakeTail.getTranslateY() >= snakeHeigth) {

					Main.setScore(Main.getName(), count, minuts, second);
					ExceptionWindow.display("YOU HIT THE WALL !",
							"Your Score: " + count + ", Time " + minuts + ":" + second, stage);
					stopSnake();

				}
			} else {
				if (snakeTail.getTranslateX() < 0) {
					snakeTail.setTranslateX(snakeWidth - cellSize);
				}
				if (snakeTail.getTranslateX() >= snakeWidth) {
					snakeTail.setTranslateX(0);
				}
				if (snakeTail.getTranslateY() < 0) {
					snakeTail.setTranslateY(snakeHeigth - cellSize);
				}
				if (snakeTail.getTranslateY() >= snakeHeigth) {

					snakeTail.setTranslateY(0);
				}
			}

			if ((snakeTail.getTranslateX() == fruit.getTranslateX()
					&& snakeTail.getTranslateY() == fruit.getTranslateY())
					|| (snakeTail.getTranslateX() == fancyFruit.getTranslateX()
							&& snakeTail.getTranslateY() == fancyFruit.getTranslateY())) {

				if ((snakeTail.getTranslateX() == fancyFruit.getTranslateX()
						&& snakeTail.getTranslateY() == fancyFruit.getTranslateY())) {
					if (fancyFruit.isVisible()) {
						count += 3;
						fancyFruit.setVisible(false);
						if (snakeParts.size() > 1)
							snakeParts.remove(snakeParts.size() - 1);
						snakeParts.remove(snakeParts.size() - 1);
					}
				} else {
					count++;
				}

				point = (int) (Math.random() * 8);
				if (point == 2) {

					if (!fancyFruit.isVisible()) {
						fancyFruit.setVisible(true);
					}
				}

				snakeParts.forEach((item) -> {

					while (item.getTranslateX() == fruit.getTranslateX()
							&& item.getTranslateY() == fruit.getTranslateY()
							|| (fancyFruit.getTranslateX() == fruit.getTranslateX()
									&& fancyFruit.getTranslateY() == fruit.getTranslateY())) {

						setFruit();
					}

					if (fancyFruit.isVisible()) {
						while ((fancyFruit.getTranslateX() == fruit.getTranslateX()
								&& fancyFruit.getTranslateY() == fruit.getTranslateY())
								|| (item.getTranslateX() == fancyFruit.getTranslateX()
										&& item.getTranslateY() == fancyFruit.getTranslateY())) {

							fancyFruit.setVisible(false);
							setFancyFruit();

						}
					}

				});
				snakePart = new Rectangle(cellSize, cellSize);
				snakePart.setTranslateX(tailX);
				snakePart.setTranslateY(tailY);
				snakePart.setArcHeight(10);
				snakePart.setArcWidth(10);
				snakePart.setFill(Color.LIGHTGREEN);
				if (count < 10) {
					scoreValue.setText(0 + Integer.toString(count));
				} else {
					scoreValue.setText(Integer.toString(count));
				}
				snakeParts.add(snakePart);
			}
		});

		timeline.getKeyFrames().add(kFrame);
		time.getKeyFrames().add(timeFrame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		fancyFruit.setVisible(false);
		root.getChildren().addAll(fruit, fancyFruit, snake);

		return border;
	}

	public void restartSnake() {
		stopSnake();
		ExceptionWindow.answer = false;
		second = 0;
		seconds.setText(Integer.toString(second));
		minuts = 0;
		mins.setText(Integer.toString(minuts));
		count = 0;
		scoreValue.setText(Integer.toString(count));
		startSnake();
	}

	public void startSnake() {

		direct = Directions.RIGTH;
		count = 0;
		scoreValue.setText(0 + Integer.toString(count));
		second = 0;
		seconds.setText(0 + Integer.toString(second));
		minuts = 0;
		mins.setText(0 + Integer.toString(minuts));
		snakeFirstElement = new Rectangle(cellSize, cellSize);
		snakeFirstElement.setArcHeight(10);
		snakeFirstElement.setArcWidth(10);
		snakeFirstElement.setFill(Color.LIGHTGREEN);
		snakeParts.add(snakeFirstElement);
		timeline.play();
		time.play();
		isRun = true;
	}

	public void stopSnake() {
		isRun = false;
		timeline.stop();
		time.pause();
		snakeParts.clear();

	}

	public void setWalls(boolean choice) {
		this.walls = choice;
	}

	public boolean getWalls() {
		return walls;
	}

	public void setRed() {
		border.setStyle("-fx-border-color: red");
		gPane.setStyle("-fx-border-color: red");
		made.setStyle("-fx-border-color: red");
	}

	public void setBlack() {
		border.setStyle("-fx-border-color: lightgreen");
		gPane.setStyle("-fx-border-color: lightgreen");
		made.setStyle("-fx-border-color: lightgreen");
	}

	public void setFruit() {
		fruit.setTranslateX((int) (Math.random() * (snakeWidth - cellSize)) / cellSize * cellSize);
		fruit.setTranslateY((int) (Math.random() * (snakeHeigth - cellSize)) / cellSize * cellSize);
	}

	public void setFancyFruit() {
		fancyFruit.setTranslateX((int) (Math.random() * (snakeWidth - cellSize)) / cellSize * cellSize);
		fancyFruit.setTranslateY((int) (Math.random() * (snakeHeigth - cellSize)) / cellSize * cellSize);
	}

}