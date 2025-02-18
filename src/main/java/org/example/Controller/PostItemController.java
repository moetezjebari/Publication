package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.Model.Post;

public class PostItemController {
    @FXML private ImageView userAvatar;
    @FXML private Label titleLabel;
    @FXML private Label contentLabel;
    @FXML private Label categoryLabel;
    @FXML private ImageView postImage;
    @FXML private Button editButton;
    @FXML private Button deleteButton;

    private Post post; // Stocke les données du post

    public void setPostData(Post post) {
        if (post == null) return;

        this.post = post;
        titleLabel.setText(post.getTitle() != null ? post.getTitle() : "");
        contentLabel.setText(post.getContent() != null ? post.getContent() : "");
        categoryLabel.setText(post.getCategory() != null ? post.getCategory() : "");

        // Vérification de l'image
        if (post.getImage() != null && !post.getImage().isEmpty()) {
            try {
                postImage.setImage(new Image(post.getImage()));
                postImage.setVisible(true);
            } catch (Exception e) {
                postImage.setVisible(false);
            }
        } else {
            postImage.setVisible(false);
        }
    }
}
