package ManiekSnake;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	public static final int WIDTH = 36 * 30;
	public static final int HEIGTH = 28 * 30;
	private static Font font2;
	private MenuSpV menu, hall, gameGridChose, userSign, musicMenu;
	private HBox enter;
	private Scene scene, gameScene4, gameScene8, gameScene12, tempScene;
	private Stage primary;
	private static String name = "Unnamed";
	private MnItem back, backMusic, backSt, backUsr, gridChecked, addButton;
	private ComboBox<String> comboBox;
	private Snake sk4, sk8, sk12, tempSnake;
	private ListView<Gamer> listView;
	private ObservableList<String> types;
	private static ObservableList<Gamer> scors;
	private Dragboard db;
	public String filePath;
	public ArrayList<Gamer> saveTemp;
	public ArrayList<Gamer> loadTemp = new ArrayList<>();
	private TextField enterName, songUrl;
	private CheckBox wallsChoice;
	private MediaApp media = new MediaApp();

	public static void main(String[] args) {

		Application.launch(args);
	}

	private Parent createContent() {

		Pane root = new Pane();
		root.setPrefSize(WIDTH, HEIGTH);

		ImageView snakeBg = new ImageView();
		Image snk = new Image(Main.class.getResourceAsStream("snakeBack.jpg"));

		font2 = Font.loadFont(Main.class.getResource("Flynn.ttf").toExternalForm(), 50);
		listView = new ListView<Gamer>();

		listView.setId("lista");
		listView.setMouseTransparent(true);
		listView.setFocusTraversable(true);
		snakeBg.setImage(snk);
		root.getChildren().add(snakeBg);

		FadeTransition fd = new FadeTransition(Duration.millis(2000), enter);
		fd.setFromValue(0.2);
		fd.setToValue(1);
		fd.setCycleCount(Timeline.INDEFINITE);
		fd.setAutoReverse(true);
		fd.play();

		String earlyEnter = new String("Click here .....");
		enter = new HBox(0);
		enter.setAlignment(Pos.CENTER);
		enter.relocate(20, 540);
		Reflection refl = new Reflection(0.2, 0.5, 1, 0);
		GaussianBlur blur = new GaussianBlur(3);
		DropShadow drop = new DropShadow(30, Color.YELLOW);
		refl.setInput(drop);
		refl.setInput(blur);

		for (int i = 0; i < earlyEnter.length(); i++) {
			Text letter = new Text(earlyEnter.charAt(i) + "");
			letter.setEffect(refl);
			letter.setFont(font2);
			letter.setFill(Color.LIGHTGREEN);
			letter.setOpacity(0);
			enter.getChildren().add(letter);
			FadeTransition ftEnt = new FadeTransition(Duration.seconds(1), letter);
			ftEnt.setDelay(Duration.millis(i * 100));
			ftEnt.setToValue(1);
			ftEnt.setAutoReverse(true);
			ftEnt.setCycleCount(TranslateTransition.INDEFINITE);
			ftEnt.play();
		}

		enter.setOnMouseClicked(e -> {
			menu.show();
			enter.setVisible(false);
		});

		Label eska = new Label("S16449");
		eska.relocate(900, 550);

		eska.setId("eska");

		comboBox = new ComboBox<>();
		types = FXCollections.observableArrayList("EASY", "MEDIUM", "HARD");

		comboBox.setItems(types);
		comboBox.setOnAction(e -> {
			if (comboBox.getValue().equals("EASY")) {

				setScene(gameScene4, sk4);

			} else if (comboBox.getValue().equals("MEDIUM")) {

				setScene(gameScene8, sk8);

			} else {

				setScene(gameScene12, sk12);

			}

		});

		wallsChoice = new CheckBox("Wanna Walls ?");
		wallsChoice.setPadding(new Insets(10));
		wallsChoice.setSelected(false);
		wallsChoice.setOnAction(e -> {
			if (wallsChoice.isSelected()) {
				getType().setWalls(true);

			} else {
				getType().setWalls(false);
			}
		});
		comboBox.getSelectionModel().select(0);
		comboBox.setTooltip(new Tooltip("CHOSE DIFFICULTY"));
		comboBox.setPrefSize(350, 50);

		Label grdSize = new Label("Select Grid Size:");

		MnItem play = new MnItem("Play");

		addButton = new MnItem("Add Mp3");

		play.setOnMouseClicked(e -> {
			if (!enterName.getText().isEmpty()) {
				name = enterName.getText();
			} else {
				name = "Unnamed";
			}
			getType().setWalls(wallsChoice.isSelected());
			if (getType().getWalls()) {
				getType().setRed();
			} else {
				getType().setBlack();
			}
			getType().startSnake();
			media.play();

			primary.setScene(getScene());
			enterName.clear();

		});

		enterName = new TextField();
		enterName.setTooltip(new Tooltip("PLEASE ENTER YOUR NAME"));
		enterName.setId("plName");
		enterName.setMaxSize(350, 50);

		enterName.setOnAction(e -> {
			if (!enterName.getText().isEmpty()) {
				name = enterName.getText();
			} else {
				name = "Unnamed";
			}

		});
		songUrl = new TextField("Drop Mp3 Here");
		songUrl.setTooltip(new Tooltip("PLEASE DRAG AND DROP MP3 FILE"));
		songUrl.setMinSize(350, 200);
		songUrl.setAlignment(Pos.CENTER);
		songUrl.setEditable(false);
		songUrl.setId("mp3Field");
		songUrl.setOnDragOver(new EventHandler<DragEvent>() {

			public void handle(DragEvent event) {
				db = event.getDragboard();
				if (db.hasFiles()) {
					event.acceptTransferModes(TransferMode.COPY);

				} else {
					event.consume();
				}
			}
		});

		songUrl.setOnDragDropped(new EventHandler<DragEvent>() {

			public void handle(DragEvent event) {
				db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles()) {

					success = true;

					for (File file : db.getFiles()) {
						filePath = file.getAbsolutePath();
						if (filePath.endsWith(".mp3")) {
							songUrl.setId("mp3Accept");
							songUrl.setText("Now click Add Mp3");
						} else {
							songUrl.setId("mp3Wrong");
							songUrl.setText("Incorrect format");

						}
					}
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
		songUrl.setOnMouseClicked(e ->{
			songUrl.setText("Drop Mp3 Here");
			songUrl.setId("mp3Field");
		});
		addButton.setOnMouseClicked(e -> {
			if (filePath != null) {

				System.out.println("filepath : " + filePath.toString());
				/*
				 * Pattern correct = Pattern.compile(".mp3"); Matcher match =
				 * correct.matcher(filePath.toString());
				 */
				if (filePath.endsWith(".mp3")) {
					System.out.println("filepath : " + filePath.toString());
					media.addMp3(filePath.toString().replace("\\", "\\\\"));
					songUrl.setText("Drop Mp3 Here");
					songUrl.setId("mp3Field");
				} else {
					songUrl.setText("Drop Mp3 Here");
					songUrl.setId("mp3Field");
				}
			} else {
				songUrl.setText("Drop Mp3 Here");
				songUrl.setId("mp3Field");
			}
		});

		backUsr = new MnItem("BACK");
		backUsr.setOnMouseClicked(e -> {
			hall.hide();
			userSign.hide();
			musicMenu.hide();
			gameGridChose.show();
			menu.hide();
		});

		back = new MnItem("BACK");
		back.setOnMouseClicked(e -> {
			hall.hide();
			userSign.hide();
			musicMenu.hide();
			gameGridChose.hide();
			menu.show();
		});
		backMusic = new MnItem("BACK");
		backMusic.setOnMouseClicked(e -> {
			hall.hide();
			userSign.hide();
			gameGridChose.hide();
			menu.show();
			musicMenu.hide();
		});

		backSt = new MnItem("BACK");
		backSt.setOnMouseClicked(e -> {
			hall.hide();
			userSign.hide();
			musicMenu.hide();
			gameGridChose.hide();
			menu.show();
		});

		gridChecked = new MnItem("NEXT STEP");
		gridChecked.setOnMouseClicked(e -> {
			hall.hide();
			menu.hide();
			musicMenu.hide();
			gameGridChose.hide();
			userSign.show();
		});

		gameGridChose = new MenuSpV("DIFFICULTY:", comboBox, wallsChoice, gridChecked, backSt);
		gameGridChose.setVisible(false);
		gameGridChose.hide();

		userSign = new MenuSpV("ENTER NAME:", enterName, play, backUsr);
		userSign.setVisible(false);
		userSign.hide();

		hall = new MenuSpV("HALL OF FAME", listView, back);
		hall.setVisible(false);
		hall.hide();

		musicMenu = new MenuSpV("A U D I O", songUrl, addButton, backMusic);
		musicMenu.setVisible(false);
		musicMenu.hide();

		MnItem startItem = new MnItem("START GAME");
		startItem.setOnMouseClicked(e -> {
			hall.hide();
			menu.hide();
			userSign.hide();
			musicMenu.hide();
			gameGridChose.show();
		});

		MnItem hOfItem = new MnItem("HALL OF FAME");
		hOfItem.setOnMouseClicked(e -> {
			menu.hide();
			userSign.hide();
			gameGridChose.hide();
			musicMenu.hide();
			hall.show();
		});

		MnItem musicOpt = new MnItem("A U D I O");
		musicOpt.setOnMouseClicked(e -> {
			menu.hide();
			userSign.hide();
			gameGridChose.hide();
			musicMenu.show();

		});
		MnItem exitItem = new MnItem("EXIT");
		exitItem.setOnMouseClicked(e -> {
			closeProgram();
		});

		menu = new MenuSpV("SNAKE", startItem, hOfItem, musicOpt, exitItem);
		menu.setVisible(false);
		menu.hide();

		root.getChildren().addAll(menu, hall, gameGridChose, musicMenu, userSign); // brak
		// eski
		root.getChildren().add(enter);

		return root;
	}

	public void start(Stage primaryStage) throws Exception {

		primary = primaryStage;

		sk4 = new Snake(36, 26, 160, primary);
		sk8 = new Snake(36, 26, 100, primary);
		sk12 = new Snake(36, 26, 80, primary);

		gameScene4 = new Scene(sk4.snakeGame());
		gameScene8 = new Scene(sk8.snakeGame());
		gameScene12 = new Scene(sk12.snakeGame());
		setScene(gameScene4, sk4);
		primary.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		scene = new Scene(createContent());
		scene.setFill(Color.BLACK);

		scene.getStylesheets().add(Main.class.getResource("combos.css").toExternalForm());
		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.SPACE) {
				if (menu.isVisible()) {
					menu.hide();
					hall.hide();
					userSign.hide();
					gameGridChose.hide();
					enter.setVisible(true);
				} else {
					menu.show();
					hall.hide();
					userSign.hide();
					gameGridChose.hide();
					enter.setVisible(false);
				}
			}
		});

		Timeline deathCheck = new Timeline();
		deathCheck.setCycleCount(Timeline.INDEFINITE);
		KeyFrame cycle = new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (ExceptionWindow.getAnswer()) {

					getType().restartSnake();

				}
			}
		});
		deathCheck.getKeyFrames().add(cycle);
		deathCheck.play();
		gameScene4.getStylesheets().add(getClass().getResource("combos.css").toExternalForm());
		gameScene8.getStylesheets().add(getClass().getResource("combos.css").toExternalForm());
		gameScene12.getStylesheets().add(getClass().getResource("combos.css").toExternalForm());

		primary.setTitle("SNAKE");

		primary.setScene(scene);
		primary.setResizable(false);
		primary.show();
		loadState();
	}

	private void closeProgram() {

		boolean answer = CloseRequest.display("EXIT SNAKE ?", "Are You Sssssure ?", primary);
		if (answer) {
			saveState();
			primary.close();
			media.stop();
		}
	}

	public static String getName() {
		return name;
	}

	public static void setScore(String nick, Integer pts, Integer mins, Integer sec) {
		if (pts > 0) {
			scors.add(new Gamer(nick, pts, mins, sec));

			scors.sort((g1, g2) -> Integer.compare(g2.getScore(), g1.getScore()));
			if (scors.size() > 12) {
				scors.remove(scors.size() - 1);
			}
		}
	}

	public void saveState() {
		saveTemp = new ArrayList<Gamer>();
		saveTemp.addAll(scors);
		try {
			FileOutputStream scoreSave = new FileOutputStream("scoreState.save");
			ObjectOutputStream save = new ObjectOutputStream(scoreSave);
			save.writeObject(saveTemp);
			save.close();
			scoreSave.close();

		} catch (IOException ex) {
			ExceptionWindow.display("IOException", "IOException", primary);
		}
	}

	public void loadState() {

		try {

			FileInputStream scoreLoad = new FileInputStream("scoreState.save");
			ObjectInputStream load = new ObjectInputStream(scoreLoad);
			loadTemp = (ArrayList<Gamer>) load.readObject();
			load.close();
			scoreLoad.close();

		} catch (IOException ex) {
			ExceptionWindow.display("IOException", "Highscore list is empty.", primary);
		} catch (ClassNotFoundException cls) {
			ExceptionWindow.display("ClassNotFoudException", "ClassNotFoudException", primary);
		}
		scors = FXCollections.observableArrayList();
		scors.addAll(loadTemp);
		listView.setItems(scors);
	}

	private void setScene(Scene game, Snake type) {

		tempScene = game;
		tempSnake = type;

		game.setOnKeyPressed(e -> {
			if (type.move) {
				switch (e.getCode()) {
				case UP:
					if (type.direct != type.direct.DOWN) {
						type.direct = type.direct.UP;
					}
					break;
				case DOWN:
					if (type.direct != type.direct.UP) {
						type.direct = type.direct.DOWN;
					}
					break;
				case LEFT:
					if (type.direct != type.direct.RIGTH) {
						type.direct = type.direct.LEFT;
					}
					break;
				case RIGHT:
					if (type.direct != type.direct.LEFT) {
						type.direct = type.direct.RIGTH;
					}
					break;

				}
				type.move = false;
			}
			if (e.getCode().equals(KeyCode.A))

			{
				media.next();
			}
			if (e.getCode().equals(KeyCode.Z)) {
				media.prev();
			}
			if (e.getCode().equals(KeyCode.W)) {
				media.stop();
			}
		});

		game.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			final KeyCombination kC = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_ANY,
					KeyCombination.SHIFT_ANY);

			public void handle(KeyEvent event) {
				if (kC.match(event)) {
					media.stop();
					type.stopSnake();
					primary.setScene(scene);
					event.consume();
				}
			}
		});
	}

	private Scene getScene() {
		return tempScene;
	}

	private Snake getType() {
		return tempSnake;
	}

	public Stage getStage() {
		return primary;
	}

}