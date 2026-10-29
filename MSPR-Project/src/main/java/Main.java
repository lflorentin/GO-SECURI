import Services.FirebaseInitialize;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.opencv.core.Core;

public class Main extends Application {

//    static {
//        nu.pattern.OpenCV.loadShared();
//        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
//    }

    @Override
    public void start(Stage primaryStage) throws Exception{

//        nu.pattern.OpenCV.loadShared(); //add this
//        Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
//        System.out.println("mat = " + mat.dump());

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("img/logo.png")));
        Parent root = FXMLLoader.load(getClass().getResource("sample/LoginPage.fxml"));
        primaryStage.setTitle("Login to Continue");
        primaryStage.setScene(new Scene(root, 640, 400));
        primaryStage.show();
        new FirebaseInitialize().Initialize();
    }

    public static void main(String[] args) {


        // load the native OpenCV library
//        System.out.printf("java.library.path: %s%n",
//                System.getProperty("java.library.path"));
//        System.setProperty("java.library.path", System.getProperty("basedir")+"/lib/");
//
//        System.out.printf("java.library.path: %s%n",
//                System.getProperty("java.library.path"));
//        System.loadLibrary("opencv_java320");
//        launch();

        launch(args);
    }
}
