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

    public static int getNote(String notestring){
        int numstart = 2;
        int offset = 0;
        int note = 0;

        char nchar = notestring.charAt(1);
        if(nchar == 's' || nchar == '#') offset = 1;
        else if (nchar == 'b') offset = -1;
        else numstart = 1;

        switch(notestring.charAt(0)){
            case 'A': note = A; break;
            case 'B': note = B; break;
            case 'C': note = C; break;
            case 'D': note = D; break;
            case 'E': note = E; break;
            case 'F': note = F; break;
            case 'G': note = G; break;
        }
        return getNote(note + offset, Integer.parseInt(notestring.substring(numstart)));
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