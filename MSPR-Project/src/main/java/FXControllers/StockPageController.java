package FXControllers;

import EssentialClasses.User;
import Services.FirebaseService;
import javafx.event.ActionEvent;
import Utils.getEventSourceIdJFX;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class StockPageController implements Initializable {

    static User loggedUser;

    @FXML
    private AnchorPane StockPane;
     @FXML
    private AnchorPane apStock;


    public void OnStockCheckBoxClick(ActionEvent event) throws ExecutionException, InterruptedException {

        // retrieving id
        String sourceEvent = event.getSource().toString();
        System.out.println("\n id checkbox clicked ="+sourceEvent);

        // call to util function retrieving id from node datas
        String idCheckbox = getEventSourceIdJFX.getEventSourceId(sourceEvent);
        System.out.print(idCheckbox);

        CheckBox source = (CheckBox) event.getSource();

        // call FirebaseService in order to check if this item can be decreased

        ArrayList unavailableEquip = FirebaseService.getEquipementUnavailable();


        System.out.print("\n "+source.getId()+"\n !!!"+source.isSelected()+"\n");

        // opposite statement to usual procedure : if the checkbox is selected
        // before the click, the click will set it to unselected so it will
        // enter the else statement

            if(source.isSelected()){
                FirebaseService.UpdateAmountItem(source.getId(), false);
                FirebaseService.SetStockUser(loggedUser.getNom(), source.getId(), true);

            } else {
                FirebaseService.UpdateAmountItem(source.getId(), true);
                FirebaseService.SetStockUser(loggedUser.getNom(), source.getId(), false);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<String> userEquipement = new ArrayList<>();

        this.loggedUser = LoginPageController.loggedUser;

        try {
            System.out.print(FirebaseService.getUserEquipement(this.loggedUser));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            userEquipement = FirebaseService.getUserEquipement(this.loggedUser);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (userEquipement.size()!=0) {
            int count = 0;
            while(count< userEquipement.size()){
                System.out.print(userEquipement.get(count) + " |||  ");
                count++;
            }
        }
    }

}
