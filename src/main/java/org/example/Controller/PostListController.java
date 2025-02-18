package org.example.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.Parent;
import org.example.Model.Post;
import org.example.Service.PostService;
import java.io.IOException;
import java.util.List;

public class PostListController {
    @FXML private GridPane postsContainer;
    private int currentRow = 0;
    private int currentColumn = 0;
    private final int MAX_COLUMNS = 2; // Nombre de colonnes dans la grille

    public void initialize() {
        loadPostsFromDatabase();
    }

    private void loadPostsFromDatabase() {

        List<Post> posts = PostService.getAllPosts();
        postsContainer.getChildren().clear();

        for (Post post : posts) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PostItem.fxml"));
                Parent postNode = loader.load();

                PostItemController controller = loader.getController();
                System.out.println("Loading posts from database");
                controller.setPostData(post);

                // Ajoute l'élément dans le GridPane avec les coordonnées
                postsContainer.add(postNode, currentColumn, currentRow);

                // Mise à jour des coordonnées pour le prochain post
                currentColumn++;
                if (currentColumn >= MAX_COLUMNS) {
                    currentColumn = 0;
                    currentRow++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}