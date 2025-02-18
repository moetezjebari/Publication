package org.example.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import org.example.Model.Post;
import org.example.Service.PostService;
import java.io.IOException;
import java.util.List;

public class PostListController {
    @FXML private VBox postsContainer; // Conteneur pour afficher les publications

    public void initialize() {
        loadPostsFromDatabase();
    }

    private void loadPostsFromDatabase() {
        List<Post> posts = PostService.getAllPosts(); // Récupère les posts depuis la base
        postsContainer.getChildren().clear(); // Vide le VBox avant de recharger

        for (Post post : posts) {
            try {
                // Charge PostItem.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/View/PostItem.fxml"));
                Parent postNode = loader.load();

                // Récupère le contrôleur de PostItem
                PostItemController controller = loader.getController();
                controller.setPostData(post); // Envoie les données au PostItem

                // Ajoute l'élément dans le VBox
                postsContainer.getChildren().add(postNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
