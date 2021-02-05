package pl.com.harta;


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
        for (Element res:resultText) {
            String lnk = res.child(0).attr("href");
            String linkRes = lnk.split("\\?ref")[0];
            if (res.text().toLowerCase().matches("(.*)"+searchString.toLowerCase()+"(.*)")) {
                list.put(linkRes, res.text());
            }
        }
    }

    public String getRating() throws IOException {
        StringBuilder sb = new StringBuilder();
        String ratingString;
        Document docRating;
        Element elementRating;
        for (HashMap.Entry<String, String> entry : list.entrySet()) {

            docRating = Jsoup.connect("https://www.imdb.com" + entry.getKey()).get();
            elementRating = docRating.getElementsByClass("imdbRating").before("pro-logo-main-tittle").first();

            if (elementRating==null) {
                ratingString = "N/A";
            } else {
                ratingString = elementRating.text();
                ratingString = ratingString.split(" ")[0];
            }
            sb.append(entry.getValue()).append("--->").append(ratingString).append("\n");

        }
        return sb.toString();
    }

    public FilmSearch() {
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public HashMap<String, String> getLinks() {
        return list;
    }
}

