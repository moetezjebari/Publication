package org.example.Service;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.Model.Post;
import org.example.Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostService {
    int userId = 1;
    Connection cnx2;
    @FXML
    private TextField userIdField;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea text;
    @FXML
    private TextField imageUrlField;
    @FXML
    private TextField categorieField;
    @FXML
    private ImageView imageView ;
    @FXML
    private Button cancelImageButton;
    @FXML
    private StackPane imageContainer;
    private String selectedImagePath ;
    @FXML
    private VBox postsContainer;

    public PostService() {
        cnx2 = DatabaseConnection.getInstance().getCnx();
    }


    public static List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM posts";

        try (Connection conn = DatabaseConnection.getInstance().getCnx();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                posts.add(new Post(
                        rs.getInt("post_id"),
                        rs.getInt("user_id"),
                        rs.getString("image_url"),
                        rs.getString("title"),
                        rs.getString("categorie"),
                        rs.getString("content"),
                        rs.getString("created_at")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public void PostAdd(){

        String title = "titre";
        String content = text.getText();
        String imageUrl = imageUrlField.getText();
        String categorie = "formation";
        String query = "INSERT INTO posts (user_id, title, content, image_url, created_at, categorie) VALUES (?, ?, ?, ?, NOW(), ?)";
        try  {
            PreparedStatement st = cnx2.prepareStatement(query);
            System.out.println("click");
            st.setInt(1, userId);
            st.setString(2, title);
            st.setString(3, content);
            st.setString(4, imageUrl);
            st.setString(5, categorie);

            int rowsInserted = st.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Post ajouté avec succès !");
            }


        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Impossible d'ajouter le post.");
        }



    }
    public void PostDelete(int  PostId ){
        String query = "DELETE FROM posts WHERE post_id = ?";

        try (PreparedStatement stmt = cnx2.prepareStatement(query)) {
            stmt.setInt(1, PostId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION , " succes " , "Post DELETED");
            }else{
                showAlert(Alert.AlertType.WARNING , "erreur" , "Post non existant ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR , "SQL ERROR" , "Impossible de supprimer  le post.");
        }
    }
    public void PostUp(int PostId ){
        String title = "Up";
        String content =  "updated";
        String imageUrl = (selectedImagePath != null) ? selectedImagePath : imageUrlField.getText();
        String categorie = "formation";

        String query = "UPDATE posts SET title = ?, content = ?, image_url = ?, categorie = ? WHERE post_id = ?";

        try (PreparedStatement st = cnx2.prepareStatement(query)) {
            st.setString(1, title);
            st.setString(2, content);
            st.setString(3, imageUrl);
            st.setString(4, categorie);
            st.setInt(5, PostId);

            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Post mis à jour avec succès !");
            } else {
                showAlert(Alert.AlertType.WARNING, "Aucune modification", "Aucun post mis à jour.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Impossible de mettre à jour le post.");
        }
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}