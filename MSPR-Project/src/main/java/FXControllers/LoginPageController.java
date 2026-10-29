package FXControllers;

import EssentialClasses.User;
import Services.FirebaseService;
import Utils.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class LoginPageController implements Initializable {

    static User loggedUser;

    @FXML
    private AnchorPane ap;
    @FXML
    private ImageView currentFrame;

    // stuff commented is for video handling


//    // a timer for acquiring the video stream
//    private ScheduledExecutorService timer;
//    // the OpenCV object that realizes the video capture
//    private VideoCapture capture = new VideoCapture();
//    // a flag to change the button behavior
//    private boolean cameraActive = false;
//    // the id of the camera to be used
//    private static int cameraId = 0;
//
//    public void initialize(URL location, ResourceBundle resources) {
//        // Faire les initialisations avant affichage ici.
//
//
//        if (!this.cameraActive) {
//            // start the video capture
//            this.capture.open(cameraId);
//
//            // is the video stream available?
//            if (this.capture.isOpened()) {
//                this.cameraActive = true;
//
//                // grab a frame every 33 ms (30 frames/sec)
//                Runnable frameGrabber = new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // effectively grab and process a single frame
//                        Mat frame = grabFrame();
//                        // convert and show the frame
//                        Image imageToShow = Utils.mat2Image(frame);
//                        updateImageView(currentFrame, imageToShow);
//                    }
//                };
//
//                this.timer = Executors.newSingleThreadScheduledExecutor();
//                this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
//
//                // update the button content
//                // this.button.setText("Stop Camera");
//            } else {
//                // log the error
//                System.err.println("Impossible to open the camera connection...");
//            }
//        } else {
//            // the camera is not active at this point
//            this.cameraActive = false;
//            // update again the button content
//            //this.button.setText("Start Camera");
//
//            // stop the timer
//            this.stopAcquisition();
//        }
//    }


    public void onButtonActivated(ActionEvent actionEvent) {
        System.out.print("yoyo la boulette");
        System.exit(0);
    }

    public void onLoginClick(ActionEvent actionEvent) throws ExecutionException, InterruptedException, IOException {


        Stage primaryStage = (Stage) ap.getScene().getWindow();
        String prenom = "Victor";

        System.out.println("this is the primary Staaaage\n" + primaryStage.getTitle());

        User user = FirebaseService.getUser(prenom);
        this.loggedUser = user;

        System.out.print("-------- this is supposed to be a user " + user.getPrenom());

        // instanciation of  stockpage with user title

        Parent root = FXMLLoader.load(getClass().getResource("../sample/StockPage.fxml"));
        primaryStage.setTitle("Welcome " + user.getPrenom());
        primaryStage.setScene(new Scene(root, 640, 400));
        primaryStage.show();

        // retrieving Stockpage's Stockpane and iterating on each child in order to verify
        // if the stock for each item is enough for user's selection

        System.out.print("\n !!!");
        AnchorPane stockPane = (AnchorPane) primaryStage.getScene().getRoot().lookup("#StockPane");
        System.out.print(stockPane.getChildren());
        System.out.print(stockPane.getChildren().getClass()+"\n !!! \n ");

        ArrayList<String> EquipementUnavailable = FirebaseService.getEquipementUnavailable();
        ArrayList<String> possessedEquipement = FirebaseService.getUserEquipement(this.loggedUser);

        for(Node node : stockPane.getChildren()){
            if(EquipementUnavailable.contains(node.getId()) && !(possessedEquipement.contains(node.getId()))){
                node.setDisable(true);
                node.setStyle("-fx-background-color: grey");
            }

            if(possessedEquipement.contains(node.getId())){
                CheckBox checkboxNode = (CheckBox) node;
                checkboxNode.setSelected(true);
            }
        }
        System.out.print("\n !!!");

        return;
    }

    // erase this method if you want to uncomment video handling

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


// methods used to control video stream on ImageView

//    /**
//     * Get a frame from the opened video stream (if any)
//     *
//     * @return the {@link Mat} to show
//     */
//    private Mat grabFrame() {
//        // init everything
//        Mat frame = new Mat();
//
//        // check if the capture is open
//        if (this.capture.isOpened()) {
//            try {
//                // read the current frame
//                this.capture.read(frame);
//
//                // if the frame is not empty, process it
//                if (!frame.empty()) {
//                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
//                }
//
//            } catch (Exception e) {
//                // log the error
//                System.err.println("Exception during the image elaboration: " + e);
//            }
//        }
//
//        return frame;
//    }

//    /**
//     * Update the {@link ImageView} in the JavaFX main thread
//     *
//     * @param view
//     *            the {@link ImageView} to update
//     * @param image
//     *            the {@link Image} to show
//     */
//    private void updateImageView(ImageView view, Image image)
//    {
//        Utils.onFXThread(view.imageProperty(), image);
//    }
//
//    /**
//     * On application close, stop the acquisition from the camera
//     */
//    protected void setClosed()
//    {
//        this.stopAcquisition();
//    }
//
//    /**
//     * Stop the acquisition from the camera and release all the resources
//     */
//    private void stopAcquisition()
//    {
//        if (this.timer!=null && !this.timer.isShutdown())
//        {
//            try
//            {
//                // stop the timer
//                this.timer.shutdown();
//                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
//            }
//            catch (InterruptedException e)
//            {
//                // log any exception
//                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
//            }
//        }
//
//        if (this.capture.isOpened())
//        {
//            // release the camera
//            this.capture.release();
//        }
//    }

}