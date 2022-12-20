package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
	@Override
	public void start(Stage stage) throws Exception{
		Group root = new Group();
		Scene scene = new Scene(root,Color.ALICEBLUE);
		
		// Icon Setting
		// Put the png under "src" folder, remember to press "refresh" to load your photo
		Image icon = new Image("iconsss.png");
		stage.getIcons().add(icon);
		
		//Text Setting (before Stage Setting)
		Text text = new Text();
		text.setText("QAQ");
		text.setX(210);
		text.setY(210);
		text.setFont(Font.font("Verdana",50));
		text.setFill(Color.BLACK);
		root.getChildren().add(text);
		
		//Line
		Line line = new Line();
		line.setStartX(200);
		line.setStartY(200);
		line.setEndX(500);
		line.setEndY(200);
		line.setStrokeWidth(0.5);
		line.setStroke(Color.RED);
		line.setOpacity(0.5);
		line.setRotate(45);
		root.getChildren().add(line);
		
		//Rectangle
		Rectangle rectangle = new Rectangle();
		rectangle.setX(100);
		rectangle.setY(100);
		rectangle.setWidth(100);
		rectangle.setHeight(100);
		rectangle.setFill(Color.BLUE);
		rectangle.setStroke(Color.BLACK);
		root.getChildren().add(rectangle);
		
		//Triangle(Polygon)
		Polygon triangle = new Polygon();
		triangle.getPoints().setAll(
				200.0,200.0,
				300.0,300.0,
				200.0,300.0
				);
		triangle.setFill(Color.YELLOW);
		root.getChildren().add(triangle);
		
		//Circle
		Circle circle = new Circle();
		circle.setCenterX(350);
		circle.setCenterY(350);
		circle.setRadius(50);
		circle.setFill(Color.ORANGE);
		root.getChildren().add(circle);
		
		//Insert ImageView
		Image image = new Image("®øªi¶ô.png");
		ImageView imageView = new ImageView(image);
		imageView.setX(400);
		imageView.setY(400);
		root.getChildren().add(imageView);
		
		
		
		//Stage setting
		stage.setTitle("QAQ");
		stage.setWidth(420);
		stage.setHeight(420);
		stage.setResizable(true);
		stage.setX(50);
		stage.setY(50);
		stage.setScene(scene);
		//Stage Full Screen Mode
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("You should press esc to excape.");
		stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Esc"));
		
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
