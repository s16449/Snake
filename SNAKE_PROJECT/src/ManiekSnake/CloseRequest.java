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

public class CloseRequest {

	public static boolean answer;

	public static boolean display(String title, String messege, Stage primary) {
		Stage question = new Stage();
		question.initOwner(primary);
		question.initModality(Modality.APPLICATION_MODAL);
		question.setTitle(title);
		question.setMinWidth(300);

	

		Button yes = new Button("Yes");
		Button no = new Button("No");

		yes.setEffect(new GaussianBlur(2));
		no.setEffect(new GaussianBlur(2));

		yes.setOnAction(e -> {
			answer = true;
			question.close();
		});

		no.setOnAction(e -> {
			answer = false;
			question.close();
		});

		Label messegeLabel = new Label();
		messegeLabel.setText(messege);
		messegeLabel.setEffect(new GaussianBlur(2));
		

		ImageView snakeBg = new ImageView();

		VBox vBox = new VBox(10);
		vBox.getChildren().addAll(snakeBg, messegeLabel, yes, no);

		vBox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vBox, Color.BLACK);
		scene.getStylesheets().add(CloseRequest.class.getResource("combos.css").toExternalForm());
		question.initStyle(StageStyle.TRANSPARENT);
		scene.setFill(Color.TRANSPARENT);
		question.setScene(scene);
		question.showAndWait();

		return answer;
	}
}