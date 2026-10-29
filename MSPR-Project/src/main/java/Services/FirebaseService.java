package Services;

import EssentialClasses.Item;
import EssentialClasses.User;
import com.google.api.client.util.DateTime;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firestore.v1beta1.Document;
import com.sun.org.apache.xalan.internal.xsltc.dom.ArrayNodeListIterator;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

   synchronized public static User getUser(String prenom) throws ExecutionException, InterruptedException {

        Firestore dbFirestore = FirestoreClient.getFirestore();
        System.out.print("--------------- " + dbFirestore + " ----------- \n");
         List<User> users = new ArrayList<User>();

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = dbFirestore.collection("users")
                .document("Desktop").collection("Employe").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            System.out.println(" ----- "+document.getId() + " => " + (User)document.toObject(User.class));
            users.add(document.toObject(User.class));
        }

        for (User user : users) {
            if (user.getPrenom().equals(prenom)) {
                return user;
            } else {
                return null;
            }
        }
        return new User();
    }



    static public ArrayList<String> getUserEquipement(User currentUser) throws ExecutionException, InterruptedException {

        Firestore dbFirestore = FirestoreClient.getFirestore();
        System.out.print("--------------- " + dbFirestore + " ----------- \n");
        List<User> users = new ArrayList<User>();

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = dbFirestore.collection("users")
                .document("Desktop").collection("Employe").get();

        // future.get() blocks on response

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            System.out.print("\n "+document.getData()+"\n");
            System.out.println(" ----- " + document.getId() + " => " + (User) document.toObject(User.class));
            users.add(document.toObject(User.class));
        }

        ArrayList<String> table = null;

        System.out.print(currentUser.getPrenom() + "/!\\ \n");
        for(User user : users) {
            if (user.getPrenom().equals(currentUser.getPrenom())) {
                System.out.print(user.getEquipement());
                return user.getEquipement();
            }
        }
        return null;
    }

    // returns a table which contains the items unavailable
    // based on associated stock amount equal or not to 0

    static public ArrayList<String> getEquipementUnavailable() throws ExecutionException, InterruptedException {

        Firestore dbFirestore = FirestoreClient.getFirestore();
        System.out.print("--------------- " + dbFirestore + " ----------- \n");
        ArrayList<String> unavailableItems = new ArrayList<String>();

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = dbFirestore.collection("local").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {

            Long stockAmount = (Long) document.get("Num");
            String itemName = document.get("Nom").toString();

            if (stockAmount<=0){
                unavailableItems.add(itemName);
            }
        }

        return unavailableItems;
    }

    public static void UpdateAmountItem(String idItem, boolean increaseAmount) throws ExecutionException, InterruptedException {


       System.out.println("!!!!"+idItem+"\n");

        Firestore dbFirestore = FirestoreClient.getFirestore();
        System.out.print("--------------- " + dbFirestore + " ----------- \n");

        ArrayList<Item> items = new ArrayList<>();
        DocumentReference docRefLocalItem = null;

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = dbFirestore.collection("local").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            System.out.println(" ----- "+document.getId() + " => " + (Item)document.toObject(Item.class));
            items.add(document.toObject(Item.class));
            if(document.toObject(Item.class).getNom().equals(idItem)){
                docRefLocalItem = document.getReference();
            }
            System.out.println("!!!"+docRefLocalItem);
        }

        Long incrementedNum = 0L;

        for (Item item : items){
            if(item.getNom().equals(idItem)){
                System.out.println("Item sélectionné : "+item.getNom()+"\n");
                if(increaseAmount){
                     incrementedNum = item.getNum() + 1;
                } else {
                     incrementedNum = item.getNum() - 1;
                }

            }
        }

        // (async) Update one field
        ApiFuture<WriteResult> future2 = docRefLocalItem.update("Num", incrementedNum);
        WriteResult result = future2.get();
        System.out.println("Write result: " + result.toString());

    }


    // la fonction renvoie l'utilisateur pour pouvoir l'update pour la partie session
    public static User SetStockUser(String userName, String idItem, boolean isAdded) throws ExecutionException, InterruptedException {

        Firestore dbFirestore = FirestoreClient.getFirestore();
        System.out.print("--------------- " + dbFirestore + " ----------- \n");
        List<User> users = new ArrayList<User>();
        DocumentReference docRefLocalItem = null;

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = dbFirestore.collection("users")
                .document("Desktop").collection("Employe").get();

        // future.get() blocks on response

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            System.out.print("\n "+document.getData()+"\n");
            System.out.println(" ----- " + document.getId() + " => " + (User) document.toObject(User.class));
            users.add(document.toObject(User.class));
            if(document.toObject(User.class).getNom().equals(userName)){
                docRefLocalItem = document.getReference();
            }
        }

        ArrayList<String> newEquipement = new ArrayList();
        User newUser = new User();

        for(User user : users){
            if(user.getNom().equals(userName)){
                newEquipement = user.getEquipement();
                if(isAdded){
                    if(!newEquipement.contains(idItem)){
                        newEquipement.add(idItem);
                        user.setEquipement(newEquipement);
                        newUser = user;
                    }
                } else {
                    if(newEquipement.contains(idItem)){
                        newEquipement.remove(idItem);
                        user.setEquipement(newEquipement);
                        newUser = user;
                    }
                }
            }

            // (async) Update one field
            ApiFuture<WriteResult> future2 = docRefLocalItem.update("equipement", newEquipement);
            WriteResult result = future2.get();
            System.out.println("Write result: " + result.toString());
        }
        return newUser;
    }

}
