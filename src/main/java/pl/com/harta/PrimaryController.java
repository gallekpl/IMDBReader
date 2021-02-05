package pl.com.harta;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    TextField searchField;
    @FXML
    TextArea ratingTextArea;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    private void setTextArea() throws IOException {
        FilmSearch fs = new FilmSearch();
        fs.setSearchString(searchField.getText());
        fs.getResult();
        ratingTextArea.setText(fs.getRating());
    }

}
