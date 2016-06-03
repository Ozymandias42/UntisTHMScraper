/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untisthmscraper;

/**
 *
 * @author fabian
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class UntisTable extends Thread{

    private final String[] urls;

    public UntisTable(String[] urls) {
        this.urls = urls;
    }

    public        String   HTMLGen() throws IOException {
        String   setProperty = System.setProperty("jsse.enableSNIExtension", "false");
        Document fdoc        = createDoc();

        for (String link : this.urls) {

            Document doc = Jsoup.connect(link).get();

            //Get Table and Tablename and mash both together
            Elements els    = doc.getElementsByTag("table");
            Elements tname  = doc.getElementsByTag("font");
            Elements tfield = doc.select("body table tbody tr td table tbody tr td");
            String   tnames = tname.get(2).text();
                     
                     els.get(1).attr("class", "table table-striped table-bordered");
                     tfield.get(0).prependElement("b").prependText((tnames));

            //Node table = els.get(1).clone();
            Element table = els.get(1);

            Elements outerDiv = fdoc.getElementsByAttributeValue("class", "container-fluid");
            Element innerDiv = outerDiv.get(0).appendElement("div");
            innerDiv.addClass("col-md-6");
            innerDiv.appendChild(table);
            //System.out.println(fdoc);
        }

        return fdoc.toString();

    }
    /** Creates the skeleton of an HTML File, including Header body and a <div> 
     * box in the body.
     * Used later to append recompiled HTML-Data previously generated with HTMLGen().
     * @see HTMLGen()
    *@return Document
    */
    private       Document createDoc() {
        Document fdoc = new Document("myDoc");
                 fdoc.appendElement("head");
                 fdoc.appendElement("body");
        
        Element head = fdoc.head();
                       fdoc.body().prependElement("div");
                       fdoc.body().getElementsByTag("div").get(0).attr("class", "container-fluid");

        Element title = head.appendElement("title");
                title.appendText("Custom Untis Plan Ansicht");

        Element meta = head.appendElement("meta");
                meta.attr("charset", "UTF-8");

        Element link = head.appendElement("link");
                link.attr("rel", "stylesheet");
                link.attr("href", "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css");

        return fdoc;
    }
    public static void     createOutputFile(String output) throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter writer = new PrintWriter("out.html", "UTF-8")) {
            writer.write(output);
        }
    }
}
