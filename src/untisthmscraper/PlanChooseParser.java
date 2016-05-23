/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untisthmscraper;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author fabian
 */
public class PlanChooseParser extends Thread{

    private static final String MAINURL = "http://homepages-fb.thm.de/plaene/stundenplan/Kla1.htm";
    private final URL url = new URL("http://homepages-fb.thm.de/plaene/stundenplan/Kla1.htm");
    private final Document wdoc;
    private final LinkedHashMap<String, String> items;
    public final String[] kuerzel;

    public PlanChooseParser() throws IOException {
        Document tmp = null;
        try{
        System.setProperty("jsse.enableSNIExtension", "false");
           tmp = Jsoup.connect(MAINURL).get();
           //this.wdoc  = Jsoup.connect(MAINURL).get();

        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, 
                    "Connection to Untis Failed. Page seems to be Down.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
            tmp = new Document("failed");
            System.exit(0);
        }
        finally{
            this.wdoc = tmp;
        }
        this.items = getItems();
        this.kuerzel = computeKuerzel();
    }
    public String[] getKuerzel(){return this.kuerzel;}
    private String[] computeKuerzel() {
        ArrayList<String> kuerzel1;
        kuerzel1 = new ArrayList<>();
        ArrayList<String> kuerzel2 = new ArrayList<>();

        Set<Map.Entry<String, String>> entrySet = items.entrySet();

        entrySet.stream().forEach((Map.Entry<String, String> m) -> {
            kuerzel1.add(m.getKey());
        });

        kuerzel1.stream().forEach((Consumer<? super String>) (s) -> {
            try {
                kuerzel2.add(s.substring(0, 3));
            } catch (StringIndexOutOfBoundsException e) {
            }
        });

        HashSet set = new HashSet<>(kuerzel2);

        ArrayList<String> kuerzel3 = new ArrayList<>(set);
        kuerzel3.sort(null);
        String[] subList = new String[kuerzel3.size()];
        
        kuerzel3.stream().forEach(new Consumer<String>() {
            int i=0;
            public void accept(String s) {
                subList[i] = s;
                i++;
            }
        });
        

        return subList;
    }
    
    private LinkedHashMap<String, String> getItems() {
        Map<String, String> imap;
        imap = new LinkedHashMap<>();
        Elements links = wdoc.select("body center table tbody tr td a");

        links.stream().forEach((Element link) -> {
            try {
                Object put = imap.put(link.text(), (url.getProtocol() + "://"
                        + url.getAuthority()+"/plaene/stundenplan/"+link.attr("href")));
            } catch (NullPointerException e) {
            }
            //System.out.println(link.text()+" \t "+url.getProtocol()+"://"+url.getAuthority()+url.getPath()+link.attr("href"));
        });
        return (LinkedHashMap<String, String>) imap;
    }
    public String[] getPlaene(String s) {
        
        Set<String> keySet = this.items.keySet();
        Iterator<String> iterator = keySet.iterator();
        String[] output = new String[10];
        int i = 0;
        while(iterator.hasNext()) {
            String item = iterator.next();
            if(item.startsWith(s)){
                
                output[i]= item;
                i++;
                
            }
        }
     return output;   
    }
    public String[] getLinks(String[] inputList) {
        
        String[] output = new String[inputList.length];
        int i = 0;
        for(String s : inputList) {
            output[i] = this.items.get(s);
            //System.out.println(this.items.get(s));
            i++;
        }
        
        return output;
    }

}
