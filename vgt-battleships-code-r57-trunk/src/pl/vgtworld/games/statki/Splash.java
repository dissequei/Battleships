/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.vgtworld.games.statki;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

/**
 *
 * @author danie
 */
public class Splash {
    
    private final int larguraImg = 800;
    private final int alturaImg = 600;
    private final int tempoSplash = 6000;
    private final String caminhoImg = "/pl/vgtworld/games/statki/img/splash.png";
    
    public Splash(){
        
        JWindow janelaSplash = new JWindow();
        
        janelaSplash.getContentPane().add(
            new JLabel(
                    "", new ImageIcon(getClass().getResource(caminhoImg)),
                    SwingConstants.CENTER)
        );
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        
        janelaSplash.setBounds(
                (dimension.width - larguraImg) / 2, 
                (dimension.height - alturaImg) / 2, 
                larguraImg,
                alturaImg);
        
        janelaSplash.setVisible(true);
        
        try{
            Thread.sleep(tempoSplash);
        }catch(InterruptedException e){}
        
        janelaSplash.dispose(); 
        
    }
        
}
