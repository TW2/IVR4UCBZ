package org.wingate.ivr.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * IVR is free for all, and you are free to fork this code or make a new one
 * I just want you to put a link or a thank in your app
 * <a href="https://github.com/TW2/IVR4UCBZ">IVR by TW2</a>
 * @author The Wingate 2940
 */
public class MainPanel extends JPanel {

    private int currentImage;
    private final Map<Integer, BufferedImage> images;
    private int yOffset;
    private Dimension currentImageSize;
    private int xExtra, yExtra;
    private boolean pressed;
    private Point pressedLocation;

    public MainPanel() {
        currentImage = 0;
        images = new HashMap<>();
        yOffset = 0;
        currentImageSize = new Dimension();
        xExtra = yExtra = 0;
        pressed = false;
        pressedLocation = new Point();

        addMouseWheelListener((e) -> {
            if(!images.isEmpty()){
                yOffset += e.getWheelRotation() < 0 ? -30 : 30;

                // Plus
                if(yOffset >= currentImageSize.height){
                    int index = Math.min(images.size() - 1, currentImage + 1);
                    if(index == currentImage + 1){
                        yOffset = yOffset - currentImageSize.height;
                        currentImage = index;
                        currentImageSize = new Dimension(images.get(index).getWidth(), images.get(index).getHeight());
                    }
                }

                // Minus
                if(yOffset < 0){
                    int index = Math.max(0, currentImage - 1);
                    if(index == currentImage - 1){
                        yOffset = yOffset + currentImageSize.height;
                        currentImage = index;
                        currentImageSize = new Dimension(images.get(index).getWidth(), images.get(index).getHeight());
                    }
                }

                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                pressedLocation = e.getPoint();
                pressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                pressed = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                Point draggedLocation = e.getPoint();
                if(pressed){
                    xExtra = draggedLocation.x - pressedLocation.x;
                    yExtra = draggedLocation.y - pressedLocation.y;
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // If index is OK
        if(!images.isEmpty() && images.size() - 1 >= currentImage && currentImage >= 0){
            BufferedImage i1 = images.get(currentImage);
            int x1 = (getWidth() - i1.getWidth()) / 2;
            g.drawImage(i1, x1 + xExtra, -yOffset + yExtra, null);

            // If index + 1 is OK
            if(images.size() - 1 >= currentImage + 1){
                BufferedImage i2 = images.get(currentImage + 1);
                int x2 = (getWidth() - i2.getWidth()) / 2;
                g.drawImage(i2, x2 + xExtra, -yOffset + i1.getHeight() + yExtra, null);
            }
        }
    }

    public void setup(File cbz){
        currentImage = 0;
        yOffset = 0;
        images.clear();

        int index = 0;

        try(ZipFile zip = new ZipFile(cbz)){
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while(entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();
                if(!entry.isDirectory()){
                    try(InputStream in = zip.getInputStream(entry)){
                        String n = entry.getName().toLowerCase();
                        if(n.endsWith(".jpg") || n.endsWith(".png") || n.endsWith(".bmp") || n.endsWith(".gif")){
                            images.put(index, ImageIO.read(in));
                            index++;
                        }
                    }
                }
            }

            if(!images.isEmpty()){
                currentImageSize = new Dimension(images.get(0).getWidth(), images.get(0).getHeight());
                repaint();
            }
        } catch (IOException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
