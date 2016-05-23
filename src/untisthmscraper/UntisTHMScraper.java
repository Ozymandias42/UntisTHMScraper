/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untisthmscraper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import untisthmscraper.UI.UI;

/**
 *
 * @author fabian
 */
public class UntisTHMScraper {
    public static void main(String[] args){
        try {
            UI ui = new UI();
        } catch (IOException ex) {
            Logger.getLogger(UntisTHMScraper.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
