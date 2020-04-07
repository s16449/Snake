package ManiekSnake;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

class MenuSpV extends StackPane {

	private VBox vBox, itemBox;
	Rectangle reckt = new Rectangle(350, 600);
	
	public MenuSpV(String title, MnItem... items) {

		
		reckt.setOpacity(0.3);

		DropShadow ds = new DropShadow(7, 5, 0, Color.BLACK);
		ds.setSpread(0.8);
		reckt.setEffect(ds);

		LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
				new Stop(0, Color.LIGHTGREEN), new Stop(0.5, Color.DARKGREEN), new Stop(1, Color.LIGHTGREEN) });

		Text text = new Text(title);
		text.setId("title");
		text.setEffect(new DropShadow(10, 3,9, Color.YELLOWGREEN));
		text.setFill(gradient);

		Line horiSep = new Line();
		horiSep.setEndX(200);
		horiSep.setStroke(Color.LIGHTGREEN);
		horiSep.setOpacity(0.4);

		Line vertSep = new Line();
		vertSep.setStartX(300);
		vertSep.setEndX(300);
		vertSep.setEndY(650);
		vertSep.setStroke(Color.LIGHTGREEN);
		vertSep.setOpacity(0.4);

		vBox = new VBox();
		vBox.setAlignment(Pos.TOP_CENTER);
		vBox.setPadding(new Insets(60, 0, 0, 0));
		vBox.getChildren().addAll(text, horiSep);
		vBox.getChildren().addAll(items);
		
		//itemBox = new VBox();
		//itemBox.getChildren().addAll(items);
	
		setAlignment(Pos.TOP_RIGHT);
		getChildren().addAll(reckt, vertSep, vBox);
	}

	public MenuSpV(String title, Parent pr1, Parent pr, MnItem... mn) {
	
		reckt.setOpacity(0.3);
		DropShadow ds = new DropShadow(7, 5, 0, Color.BLACK);
		ds.setSpread(0.8);

		reckt.setEffect(ds);

		Text text = new Text(title);
		text.setId("title2");
		text.setFill(Color.LIGHTGREEN);
		text.setEffect(new GaussianBlur(2));

		Line horiSep = new Line();
		horiSep.setEndX(200);
		horiSep.setStroke(Color.LIGHTGREEN);
		horiSep.setOpacity(0.4);

		Line vertSep = new Line();
		vertSep.setStartX(300);
		vertSep.setEndX(300);
		vertSep.setEndY(650);
		vertSep.setStroke(Color.LIGHTGREEN);
		vertSep.setOpacity(0.4);

		vBox = new VBox();
		vBox.setId("lista");
		vBox.setAlignment(Pos.TOP_CENTER);
		vBox.setPadding(new Insets(60, 0, 0, 0));
		vBox.getChildren().addAll(text, horiSep, pr1, pr);
		vBox.getChildren().addAll(mn);

		setAlignment(Pos.TOP_RIGHT);
		getChildren().addAll(reckt, vertSep, vBox);

	}

	public VBox getVb() {
		return vBox;
	}

	public void show() {
		setVisible(true);
		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
		tt.setToX(0);
		tt.play();
	}

	public void hide() {
		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
		tt.setOnFinished(e -> setVisible(false));
		tt.setToX(-350);
		tt.play();
	}
}