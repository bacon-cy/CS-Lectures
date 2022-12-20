package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Play extends Application{
	boolean goUp, goDown, goLeft, goRight;
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/helloTaipei.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
			//Stage Setting
			stage.setTitle("QAQ");
//			stage.setWidth(420);
//			stage.setHeight(420);
			stage.setResizable(true);
//			stage.setX(50);
//			stage.setY(50);
//			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//		        @Override
//		        public void handle(KeyEvent event) {
//		            switch (event.getCode()) {
//		            	case UP: goUp = true; break;
//		            	case DOWN: goDown = true; break;
//		            	case LEFT: goLeft = true; break;
//		            	case RIGHT: goRight = true; break;
//		            }
//		        }
//		    });
//			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
//		        @Override
//		        public void handle(KeyEvent event) {
//		            switch (event.getCode()) {
//		            	case UP: goUp = false; break;
//		            	case DOWN: goDown = false; break;
//		            	case LEFT: goLeft = false; break;
//		            	case RIGHT: goRight = false; break;
//		            }
//		        }
//		    });
			
			stage.setScene(scene);
			stage.show();
			
//			AnimationTimer timer = new AnimationTimer() {
//				@Override
//				public void handle(long now) {
//					int dx =0, dy=0;
//					if(goUp) dy++;
//					if(goDown) dy--;
//					if(goLeft) dx--;
//					if(goRight) dx++;
//					
//					movePacMan(dx, dy);
//				}
//			};
//			timer.start();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
//	private void movePacMan(int dx, int dy) {
//		if(dx ==0 && dy==0) return;
//		
//	}
}
