package ManiekSnake;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ExceptionWindow {

	public static boolean answer = false;

	public static void display(String title, String messege, Stage primary) {
		Stage question = new Stage();
		question.initOwner(primary);
		question.initModality(Modality.WINDOW_MODAL);
		question.setTitle(title);
		question.setMinWidth(300);

		
		Button ok = new Button("Ok");

		
		ok.setEffect(new GaussianBlur(2));

		ok.setOnAction(e -> {

			answer = true;
			question.close();

		});

		Label messegeLabel = new Label();
		messegeLabel.setText(messege);
		messegeLabel.setEffect(new GaussianBlur(2));

		ImageView snakeBg = new ImageView();

		VBox vBox = new VBox(10);
		vBox.getChildren().addAll(snakeBg, messegeLabel, ok);

		vBox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vBox);
		scene.getStylesheets().add(CloseRequest.class.getResource("combos.css").toExternalForm());
		question.initStyle(StageStyle.TRANSPARENT);
		scene.setFill(Color.TRANSPARENT);
		question.setScene(scene);
		
		question.show();
	}

	public static boolean ifButtonClicked() {
		boolean button = false;

		if (answer) {
			button = true;
		}
		return button;
	}

	public static boolean getAnswer() {
		return answer;
	}

	public static void changeAnswer() {
		answer = !answer;
	}
}