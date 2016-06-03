/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untisthmscraper;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 *
 * @author fabian
 */
public abstract class ResultOpener {

    private static final String FILE = "out.html";

    public static void open() throws MalformedURLException, URISyntaxException, IOException {
        String currDir;
               currDir = System.getProperty("user.dir");
        File   file;        
            file = new File((currDir + System.getProperty("file.separator") + FILE));
                                                
        try {
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            Desktop.getDesktop().open(file);
        }
    }
}
