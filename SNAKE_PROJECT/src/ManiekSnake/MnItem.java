package ManiekSnake;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MnItem extends StackPane {

	private static Font font;
	private String name;

	public MnItem(String name) {
		this.name = name;

		Rectangle reckt = new Rectangle(350, 50);
		reckt.setArcHeight(20);
		reckt.setArcWidth(20);

		LinearGradient gradient = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE,
				new Stop[] { new Stop(0, Color.LIGHTGREEN), new Stop(0.3, Color.BLACK), new Stop(0.8, Color.BLACK),
						new Stop(1, Color.LIGHTGREEN) });

		LinearGradient negGradient = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE,
				new Stop[] { new Stop(0, Color.BLACK), new Stop(0.3, Color.LIGHTGREEN), new Stop(0.8, Color.LIGHTGREEN),
						new Stop(1, Color.BLACK) });

		font = Font.loadFont(MnItem.class.getResource("Tr2n.ttf").toExternalForm(), 35);

		reckt.setFill(gradient);
		reckt.setVisible(false);


		Text text = new Text(name);
		text.setEffect(new GaussianBlur(2));
		text.setFill(Color.LIGHTGREEN);
		text.setFont(font);

		setAlignment(Pos.CENTER);
		getChildren().addAll(reckt, text);

		setOnMouseEntered(e -> {
			reckt.setVisible(true);
			text.setFill(Color.LIGHTGREEN);
		});

		setOnMouseExited(e -> {
			reckt.setVisible(false);
			text.setFill(Color.LIGHTGREEN);
		});

		setOnMousePressed(e -> {
			reckt.setFill(negGradient);
			
			text.setFill(Color.BLACK);
		});

		setOnMouseReleased(e -> {
			reckt.setFill(gradient);
			
			text.setFill(Color.LIGHTGREEN);
		});
	}

	public String getText() {
		return name;

	}
}