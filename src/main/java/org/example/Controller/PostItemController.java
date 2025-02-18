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


        this.post = post;
        titleLabel.setText(post.getTitle());
        contentLabel.setText(post.getContent());
        categoryLabel.setText(post.getCategory());

        // Charger l'image du post si elle existe
        if (post.getImage() != null && !post.getImage().isEmpty()) {
            postImage.setImage(new Image(post.getImage()));
            postImage.setVisible(true);
        } else {
            postImage.setVisible(false);
        }

        // Charger l'avatar (par défaut ou depuis la base)


            userAvatar.setImage(new Image("/src/main/resources/images/p.png")); // Avatar par défaut

    }
}
