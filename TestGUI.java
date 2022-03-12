import java.util.ArrayList;
import java.util.List;

import GUI.*;

public class TestGUI {
    public boolean sharp;
    public boolean flat;
    public boolean high;

    public int selectedLength = 0;

    MusicSequence base = new MusicSequence("testsequence.txt");
    MusicSequence ms = new MusicSequence(base);

    TestRenderer tr = new TestRenderer(ms);

    public ToggleButton sharpb, flatb;

    public List<ToggleButton> lengthButtons = new ArrayList<ToggleButton>();

    void pushNote(int note){
        if(selectedLength == 0) return;

        String symbolstring;
        switch(note) {
            case Staff.A: symbolstring = "A"; break;
            case Staff.B: symbolstring = "B"; break;
            case Staff.C: symbolstring = "C"; break;
            case Staff.D: symbolstring = "D"; break;
            case Staff.E: symbolstring = "E"; break;
            case Staff.F: symbolstring = "F"; break;
            case Staff.G: symbolstring = "G"; break;
            default: symbolstring = ""; break;
        }
        if (sharp) symbolstring += "#";
        if (flat) symbolstring += "b";
        symbolstring += high ? "6" : "5";
        for(int i = 1; i < selectedLength; i++){
            symbolstring += " #";
        }
        tr.PushSymbol(symbolstring);
    }

    void setUpGUI(GUI gui) {
        gui.queueButton("A", 0.1f, 0.8f, 0.075f, 0.075f, () -> {
            pushNote(Staff.A);
        });
        gui.queueButton("B", 0.2f, 0.8f, 0.075f, 0.075f, () -> {
            pushNote(Staff.B);
        });
        gui.queueButton("C", 0.3f, 0.8f, 0.075f, 0.075f, () -> {
            pushNote(Staff.C);
        });
        gui.queueButton("D", 0.4f, 0.8f, 0.075f, 0.075f, () -> {
            pushNote(Staff.D);
        });
        gui.queueButton("E", 0.5f, 0.8f, 0.075f, 0.075f, () -> {
            pushNote(Staff.E);
        });
        gui.queueButton("F", 0.6f, 0.8f, 0.075f, 0.075f, () -> {
            pushNote(Staff.F);
        });
        gui.queueButton("G", 0.7f, 0.8f, 0.075f, 0.075f, () -> {
            pushNote(Staff.G);
        });
        sharpb = gui.queueToggleButton("♯", 0.8f, 0.8f, 0.075f, 0.075f, (b) -> {
            if(b) {
                flatb.toggled = false;
                flat = false;
            }
            sharp = b;
        });
        flatb = gui.queueToggleButton("♭", 0.9f, 0.8f, 0.075f, 0.075f, (b) -> {
            if(b) {
                sharpb.toggled = false;
                sharp = false;
            }
            flat = b;
        });
        gui.queueButton("𝄽", 0.85f, 0.9f, 0.075f, 0.075f, () -> {
            String s = "-";
            for(int i = 1; i < selectedLength; i++) s += " -";
            tr.PushSymbol(s); return;
        });

        lengthButtons.add(gui.queueToggleButton("𝅝", 0.15f, 0.9f, 0.075f, 0.075f, (t) -> {
            disableAllExcept(0);
            selectedLength = 8;
        }));
        lengthButtons.add(gui.queueToggleButton("𝅗𝅥", 0.25f, 0.9f, 0.075f, 0.075f, (t) -> {
            disableAllExcept(1);
            selectedLength = 4;
        }));
        lengthButtons.add(gui.queueToggleButton("𝅘𝅥", 0.35f, 0.9f, 0.075f, 0.075f, (t) -> {
            disableAllExcept(2);
            selectedLength = 2;
        }));
        lengthButtons.add(gui.queueToggleButton("𝅘𝅥𝅮", 0.45f, 0.9f, 0.075f, 0.075f, (t) -> {
            disableAllExcept(3);
            selectedLength = 1;
        }));
        lengthButtons.add(gui.queueToggleButton("𝅘𝅥𝅯", 0.55f, 0.9f, 0.075f, 0.075f, (t) -> {
            disableAllExcept(4);
            selectedLength = 0;
        }));
        gui.queueToggleButton("^", 0.65f, 0.9f, 0.075f, 0.075f, (t) -> {
            high = t;
        });
        gui.queueToggleButton("3", 0.75f, 0.9f, 0.075f, 0.075f, (t) -> {
            
        });
        gui.queueButton("⏎", 0.95f, 0.9f, 0.075f, 0.075f, () -> {
            
        });
        gui.queueButton("⌫", 0.05f, 0.9f, 0.075f, 0.075f, () -> {
            tr.DeleteLastSymbol();
            System.out.println(tr.workingString.replace(" ", "_"));
        });
        gui.queueButton("▶", 0.15f, 0.725f, 0.075f, 0.05f, () -> {
            new MidiPlayer().PlaySequence(base, true, true);
        });
        gui.queueButton("ch", 0.25f, 0.725f, 0.075f, 0.05f, () -> {
            new MidiPlayer().PlaySequence(base, false, true);
        });
        
        gui.applyQueue();
    }

    void disableAllExcept(int exception){
        for(int i = 0; i < lengthButtons.size(); i++)
            if(i != exception)
                lengthButtons.get(i).toggled = false;
    }

    public void Render(java.awt.Graphics g, TestGame.GraphicPanel gp){
        tr.Render(g, gp);
    }
}