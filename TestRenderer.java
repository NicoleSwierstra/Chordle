import java.awt.*;

public class TestRenderer {
    public String[] strings;
    String workingString = "- Bb5 D6 F6 3[A6 Bb6 A6] # E6 3[G6 G#6 G6] F6 D6 6[Bb5 # C6 # C#6 D6] Eb6 # # F6 # # # #";
    public MusicSequence ms;
    MusicSequence raw;
    
    public int current;

    Font f = new Font("Sans Serif", 0, 20);

    TestRenderer(MusicSequence mus){
        strings = new String[6];
        ms = mus;
        raw = new MusicSequence(mus);
        raw.swing = false;
        raw.melody = raw.processMelody(workingString, 8);
    }

    public void PushSymbol(String symbol){
        workingString += (workingString.length() > 0 ? " " : "") + symbol;
        ms.melody = ms.processMelody(workingString, 8);
        raw.melody = raw.processMelody(workingString, 8);
    }

    public void DeleteLastSymbol(){
        int lastChar = workingString.length() - 1;
        while(lastChar > 0){
            if(workingString.charAt(lastChar) != '#' && workingString.charAt(lastChar) != ' '){
                System.out.println(lastChar);
                while(workingString.charAt(lastChar) != ' ' && lastChar > 1){
                    lastChar--;
                }
                if(lastChar > 1)
                {
                    workingString = workingString.substring(0, lastChar);
                    ms.melody = ms.processMelody(workingString, 8);
                    raw.melody = raw.processMelody(workingString, 8);
                    return;
                }
                else workingString = "";
            }
            lastChar--;
        }
    }

    void DrawTitleBadge(Graphics g, TestGame.GraphicPanel gp){
        int midpoint = gp.panewidth/2;
        int fontsize = gp.paneheight/10;

        Font titleFont = new Font("Arial Rounded MT", 1, fontsize);
        FontMetrics fm = g.getFontMetrics(titleFont);
        int length = fm.stringWidth("Chordle");
        g.setFont(titleFont);

        g.drawString("Chordle", midpoint - length/2, fm.getAscent() + 10);
    }

    void drawMeasures(Graphics g, TestGame.GraphicPanel gp, int number, float lengthMultiplier, float starty, float offset){
        for(int i = 0; i < number; i++){
            int o = gp.toIntV(starty + (offset * i));
            g.drawLine(gp.toIntH(0.1f), o, gp.toIntH(0.9f), o);
        }
        
        for(int i = 0; i <= raw.lengthM; i++){
            int sx = gp.toIntH(0.1f) + (int)(lengthMultiplier *  Staff.NOTE_WHOLE * i);
            g.drawLine(sx, gp.toIntV(starty), sx, gp.toIntV(starty + (offset * (number - 1))));
        }
        g.drawLine(gp.toIntH(0.895f), gp.toIntV(starty), gp.toIntH(0.895f), gp.toIntV(starty + (offset * (number - 1))));
    }

    public void Render(Graphics g, TestGame.GraphicPanel gp){
        g.setColor(Color.white);
        DrawTitleBadge(g, gp);

        float lengthMultiplier = (float)gp.toIntH(0.8f) / (float)(Staff.NOTE_WHOLE * raw.lengthM);
       
        float offset = 0.085f;
        for(int i = 0; i < 6; i++) drawMeasures(g, gp, 5, lengthMultiplier, 0.20f + (offset * i), 0.01f);

        g.setFont(f);
        if(raw.melody == null) return;

        for(Note n : raw.melody){
            System.out.println(n.start);
            boolean sharp = false, flat = false;
            int note = 0;

            switch(n.note % 12){
                case Staff.C : note = 0; break;
                case Staff.Cs: note = 0; sharp = true; break;
                case Staff.D : note = 1; break;
                case Staff.Eb: note = 2; flat = true; break;
                case Staff.E : note = 2; break;
                case Staff.F : note = 3; break;
                case Staff.Fs: note = 3; sharp = true; break;
                case Staff.G : note = 4; break;
                case Staff.Ab: note = 5; flat = true; break;
                case Staff.A : note = 5; break;
                case Staff.Bb: note = 6; flat = true; break;
                case Staff.B : note = 6; break;
            }

            note += ((n.note / 12) - 5) * 7;

            int xOrigin = gp.toIntH(0.1f) + (int)(n.start * lengthMultiplier), 
                yOrigin = gp.toIntV(0.245f) - gp.toIntV(note * 0.005f);

            g.fillRect(xOrigin, yOrigin, (int)(n.duration * lengthMultiplier) - 1, gp.toIntV(0.01f));
            if(flat)
                g.drawString("♭", xOrigin - 10, yOrigin);
            else if(sharp)
                g.drawString("♯", xOrigin - 10, yOrigin);
        }
    }
}
