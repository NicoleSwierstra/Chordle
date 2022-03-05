// Java program showing how to change the instrument type
import javax.sound.midi.*;
import java.util.*;

class Staff {
    public final static int NOTE_WHOLE = 96;
    public final static int NOTE_HALF = NOTE_WHOLE / 2;
    public final static int NOTE_QUARTER = NOTE_WHOLE / 4;
    public final static int NOTE_EIGHTH = NOTE_WHOLE / 8;
    public final static int NOTE_SIXTEENTH = NOTE_WHOLE / 16;

    public final static int NOTE_HTRIPLET = NOTE_WHOLE / 3;
    public final static int NOTE_QTRIPLET = NOTE_HALF / 3;
    public final static int NOTE_ETRIPLET = NOTE_QUARTER / 3;
    public final static int NOTE_STRIPLET = NOTE_EIGHTH / 3;

    public final static int C = 0;
    public final static int Cs = 1;
    public final static int D = 2;
    public final static int Eb = 3;
    public final static int E = 4;
    public final static int F = 5;
    public final static int Fs = 6;
    public final static int G = 7;
    public final static int Ab = 8;
    public final static int A = 9;
    public final static int Bb = 10;
    public final static int B = 11;

    //note numbers start at c# and go up chromatically
    public static int getNote(int note, int octave) {
        return (octave * 12) + note + 12;
    }

    public static int getPos(int measure, int beat, int sub, int subp){
        return (NOTE_WHOLE * measure) + (NOTE_QUARTER * beat) +
            subp * (sub == 2 ? NOTE_EIGHTH : (sub == 3 ? NOTE_ETRIPLET : (sub == 4 ? NOTE_SIXTEENTH : (sub == 6 ? NOTE_STRIPLET : 0))));
    }
}
 
public class MidiPlayer {
    public static void main(String[] args)
    {
        MidiPlayer player = new MidiPlayer();
 
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the instrument to be played");
        int instrument = in.nextInt();
 
        player.setUpPlayer(instrument);
    }
 
    public void setUpPlayer(int instrument)
    {
 
        try {
 
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            Sequence sequence = new Sequence(Sequence.PPQ, Staff.NOTE_QUARTER);
            Track track = sequence.createTrack();
            sequencer.setTempoInBPM(150);
 
            // Set the instrument type
            track.add(makeEvent(192, 1, instrument, 0, 0));

            AddNote(Staff.getNote(Staff.F,  4), 1, Staff.NOTE_WHOLE, 100, track); //F4
            AddNote(Staff.getNote(Staff.D,  4), 1, Staff.NOTE_WHOLE, 100, track); //D4
            AddNote(Staff.getNote(Staff.Bb, 3), 1, Staff.NOTE_WHOLE, 100, track); //Bb3
            AddNote(Staff.getNote(Staff.G,  3), 1, Staff.NOTE_WHOLE, 100, track); //G3

            AddNote(Staff.getNote(Staff.E,  4), Staff.NOTE_WHOLE, Staff.NOTE_WHOLE, 100, track); //E4
            AddNote(Staff.getNote(Staff.Bb, 3), Staff.NOTE_WHOLE, Staff.NOTE_WHOLE, 100, track); //Bb3
            AddNote(Staff.getNote(Staff.G,  3), Staff.NOTE_WHOLE, Staff.NOTE_WHOLE, 100, track); //G3
            AddNote(Staff.getNote(Staff.C,  3), Staff.NOTE_WHOLE, Staff.NOTE_WHOLE, 100, track); //C3

            AddNote(Staff.getNote(Staff.G, 4), Staff.NOTE_WHOLE * 2, Staff.NOTE_WHOLE * 2, 100, track); //G4
            AddNote(Staff.getNote(Staff.D, 4), Staff.NOTE_WHOLE * 2, Staff.NOTE_WHOLE * 2, 100, track); //D4
            AddNote(Staff.getNote(Staff.C, 4), Staff.NOTE_WHOLE * 2, Staff.NOTE_WHOLE * 2, 100, track); //C4
            AddNote(Staff.getNote(Staff.A, 3), Staff.NOTE_WHOLE * 2, Staff.NOTE_WHOLE * 2, 100, track); //A3
            AddNote(Staff.getNote(Staff.F, 3), Staff.NOTE_WHOLE * 2, Staff.NOTE_WHOLE * 2, 100, track); //F3

            AddNote(Staff.getNote(Staff.Bb, 5), Staff.getPos(0, 0, 3, 2), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.D,  6), Staff.getPos(0, 1, 3, 0), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.F,  6), Staff.getPos(0, 1, 3, 2), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.A,  6), Staff.getPos(0, 2, 3, 0), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.Bb, 6), Staff.getPos(0, 2, 3, 1), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.A,  6), Staff.getPos(0, 2, 3, 2), Staff.NOTE_QUARTER, 100, track);

            AddNote(Staff.getNote(Staff.E,  6), Staff.getPos(0, 3, 3, 2), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.G,  6), Staff.getPos(1, 0, 3, 0), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.Ab, 6), Staff.getPos(1, 0, 3, 1), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.G,  6), Staff.getPos(1, 0, 3, 2), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.F,  6), Staff.getPos(1, 1, 3, 0), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.D,  6), Staff.getPos(1, 1, 3, 2), Staff.NOTE_ETRIPLET, 100, track);

            AddNote(Staff.getNote(Staff.Bb, 5), Staff.getPos(1, 2, 3, 0), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.C,  6), Staff.getPos(1, 2, 3, 2), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.Cs, 6), Staff.getPos(1, 3, 3, 1), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.D,  6), Staff.getPos(1, 3, 3, 2), Staff.NOTE_ETRIPLET, 100, track);
            AddNote(Staff.getNote(Staff.Eb, 6), Staff.getPos(2, 0, 3, 0), Staff.NOTE_ETRIPLET, 100, track);

            AddNote(Staff.getNote(Staff.F, 6), Staff.getPos(2, 2, 3, -1), Staff.NOTE_ETRIPLET, 100, track);

            sequencer.setSequence(sequence);
            sequencer.start();
 
            while (true) {
 
                if (!sequencer.isRunning()) {
                    sequencer.close();
                    System.exit(1);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void AddNote(int note, int tick, int length, int velocity, Track track){
        track.add(makeEvent(144, 1, note, velocity, tick)); //on
        track.add(makeEvent(128, 1, note, velocity, tick + length)); //off
    }
 
    public MidiEvent makeEvent(int command, int channel,
                               int note, int velocity, int tick)
    {
 
        MidiEvent event = null;
 
        try {
 
            ShortMessage a = new ShortMessage();
            a.setMessage(command, channel, note, velocity);
 
            event = new MidiEvent(a, tick);
        }
        catch (Exception ex) {
 
            ex.printStackTrace();
        }
        return event;
    }
}