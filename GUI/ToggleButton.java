package GUI;

import java.awt.*;

public class ToggleButton extends GUIElement {
    String label;
    ToggleButtonInterface tbi;
    public boolean toggled;

    ToggleButton(String label, float x, float y, float width, float height, ToggleButtonInterface tbi){
        super(x, y, width, height);
        this.label = label;
        this.tbi = tbi;
        toggled = false;
    }

    void Render(Graphics g, GUI gui, int scw, int sch, float xmouse, float ymouse){
        int xmin = (int)(x * scw) - (int)(width * scw)/2, 
            ymin = (int)(y * sch) - (int)(height * sch)/2, 
            bw = (int)(width * scw), 
            bh = (int)(height * sch);
        
        if(gui.checkIntersect(this, false, xmouse, ymouse)) 
            g.setColor(new Color(255, 0, 0, 125));
        else if(toggled) 
            g.setColor(new Color(125, 125, 125, 125));
        else g.setColor(new Color(255, 255, 255, 125));
        
        ((Graphics2D)g).fillRoundRect(xmin, ymin, bw, bh, 10, 10);
        g.setColor(new Color(0, 0, 0));
        ((Graphics2D)g).drawRoundRect(xmin, ymin, bw, bh, 10, 10);
        
        Font font = new Font("Sans Serif", 0, bh);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = xmin + (bw - metrics.stringWidth(label)) / 2;
        int y = ymin + ((bh - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(label, x, y);
    }
}