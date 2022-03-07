import java.util.*;

public class Staff {
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

    public final static Chord[] chordTypes = {
        new Chord("5",          "7"),
        new Chord(",M",         "4,7"),
        new Chord("-,m",        "3,7"),
        new Chord("o,dim",      "3,6"),
        new Chord("+,aug",      "4,8"),
        new Chord("6",          "4,7,9"),
        new Chord("m6,-6",      "3,7,9"),
        new Chord("7",          "4,7,10"),
        new Chord("M7,maj7",    "4,7,11"),
        new Chord("m7,-7,mi7",  "3,7,10"),
        new Chord("o7,dim7",    "3,6,9"),
        new Chord("o/7,hd7",    "3,6,10"),
        new Chord("9",          "4,7,10,14"),
        new Chord("M9,maj9",    "4,7,11,14"),
        new Chord("mi9,-9,m9",  "3,7,10,14"),
        new Chord("6/9,69",     "4,7,9,14"),
    };

    public static Note makeNote(int note, int start, int duration){
        return new Note(note, start, duration);
    }

    //note numbers start at c# and go up chromatically
    public static int getNote(int note, int octave) {
        return (octave * 12) + note;
    }

    public static int getPos(int measure, int beat, int sub, int subp){
        return (NOTE_WHOLE * measure) + (NOTE_QUARTER * beat) +
            subp * (sub == 2 ? NOTE_EIGHTH : (sub == 3 ? NOTE_ETRIPLET : (sub == 4 ? NOTE_SIXTEENTH : (sub == 6 ? NOTE_STRIPLET : 0))));
    }

    static int[] extractNoteFromString(String string) {
        int offset = 0;
        int note = 0;
        int length = 2;

        if(string.charAt(1) == 's' || string.charAt(1) == '#') offset = 1;
        else if (string.charAt(1) == 'b') offset = -1;
        else length = 1;

        switch(string.charAt(0)){
            case 'A': note = A; break;
            case 'B': note = B; break;
            case 'C': note = C; break;
            case 'D': note = D; break;
            case 'E': note = E; break;
            case 'F': note = F; break;
            case 'G': note = G; break;
        }

        return new int[]{note + offset, length};
    }

    public static int getNote(String notestring){
        int[] note = extractNoteFromString(notestring);
        return getNote(note[0], Integer.parseInt(notestring.substring(note[1])));
    }

    public static List<Note> getChord(String source, int start, int duration){
        int[] noteReturn = extractNoteFromString(source);
        String typestring = source.substring(noteReturn[1]);
        
        Chord type = null;
        for (Chord chord : chordTypes) {
            for(String s : chord.suffixes){
                if(s.contentEquals(typestring)){
                    type = chord;
                }
            }
        }

        int startingNote = getNote(noteReturn[0], 3);

        List<Note> notes = new ArrayList<Note>();
        for(int offset : type.offsets){
            notes.add(new Note(startingNote + offset, start, duration));
        }

        return notes;
    }
}

class Note {
    int note, start, duration;

    public Note(int note, int start, int duration){
        this.note = note;
        this.start = start;
        this.duration = duration;
    }
}

class Chord {
    public String[] suffixes;
    public int[] offsets;

    Chord(String cssuffixes, String csoffsets){
        suffixes = cssuffixes.replace(" ", "").split(",");

        String[] cso = csoffsets.replace(" ", "").split(",");
        offsets = new int[cso.length];
        for (int i = 0; i < cso.length; i++) offsets[i] = Integer.parseInt(cso[i]);
    }
}
