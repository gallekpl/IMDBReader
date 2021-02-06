package pl.com.harta;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PrimaryController{

    @FXML
    private TextField searchField;
    @FXML
    private TextArea ratingTextArea;
    @FXML
    private ProgressBar pb;
    private String str;






    private FilmSearch fs = new FilmSearch();
   /* @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }*/
    Task<Void> task = new Task<>() {
       @Override
       protected Void call() throws Exception {
           StringBuilder sb = new StringBuilder();
           String ratingString;
           Document docRating;
           Element elementRating;

           int progress = 0;
           int listSize = fs.list.size();
           for (HashMap.Entry<String, String> entry : fs.list.entrySet()) {
               if (isCancelled()) {
                   updateMessage("Cancelled");
                   break;
               }

               docRating = Jsoup.connect("https://www.imdb.com" + entry.getKey()).get();
               elementRating = docRating.getElementsByClass("imdbRating").before("pro-logo-main-tittle").first();


               if (elementRating == null) {
                   ratingString = "N/A";
               } else {
                   ratingString = elementRating.text();
                   ratingString = ratingString.split(" ")[0];
               }
               sb.append(ratingString).append(" ----> ").append(entry.getValue()).append("\n");
               progress = progress + 1;
               updateProgress(progress, listSize);
           }
           str = sb.toString();
           ratingTextArea.setText(str);
           updateProgress(0, listSize);
           return null;
       }
   };
    @FXML
    private void setTextArea() throws IOException {
        Thread t1 = new Thread(task);
        fs.setSearchString(searchField.getText());
        fs.getResult();
        pb.progressProperty().bind(task.progressProperty());
        t1.start();
    }




}
