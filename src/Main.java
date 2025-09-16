import javafx.application.Application;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage s){
        s.setTitle("GoGo Query");
        s.setScene(new Login().loginScene(s));
        s.show();}


    public static void main(String[] args){
        launch(args);}

}
