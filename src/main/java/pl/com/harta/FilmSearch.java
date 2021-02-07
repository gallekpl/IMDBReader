package pl.com.harta;


import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class FilmSearch {

    Document doc;
    String searchString;
    HashMap<String, String> list = new HashMap<>();


    public void getResult() throws IOException {
        if(searchString.contains("\"")) {
            searchString = searchString.split("\"")[1].split("\"")[0];
            doc = Jsoup.connect("https://www.imdb.com/find?q="+searchString+"&s=tt&exact=true").get();
        } else {
            doc = Jsoup.connect("https://www.imdb.com/find?q=" + searchString + "&s=tt").get();
        }
        List<Element> resultText = doc.getElementsByClass("result_text");
        list.clear();
        for (Element res:resultText) {
            String linkRes = res.child(0).attr("href").split("\\?ref")[0];
            if (res.text().toLowerCase().matches("(.*)"+searchString.toLowerCase()+"(.*)")) {
                list.put(linkRes, res.text());
            }
        }
    }



    public FilmSearch() {
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

}

