import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MusicSequence {
    public int tempo, timeNumerator, timeDenominator;
    public boolean swing = true;

    public List<Note> melody, chords;

    List<Integer> CompareMelody(String compararor) {
        List<Note> notes = processMelody(compararor, 8);
        List<Integer> ret = new ArrayList<Integer>();
        for(Note note : notes){
            int b = 0;
            for(Note note1 : melody){
                if(note1.start == note.start) {
                    b = note1.note == note.note ? 2 : 1;
                    break;
                }
            }
            ret.add(b);
        }
        return ret;
    }

    List<Note> processMelody(String melody, int subdiv){
        List<Note> notes = new ArrayList<Note>();

        String [] tokens = melody.split(" (?![^\\[]*\\])");
        int sdPos = 0;

        for(int i = 0; i < tokens.length; i++){
            switch(tokens[i]) {
                case "-": break;
                case "#": 
                    notes.get(notes.size()-1).duration += swing ? (Staff.NOTE_ETRIPLET * (sdPos % 2 == 0 ? 2 : 1)) : Staff.NOTE_WHOLE / subdiv;
                    break;
                default:
                    if(tokens[i].startsWith("3[") || tokens[i].startsWith("6[")){
                        Scanner sc = new Scanner(tokens[i].replaceAll("(((3|6)\\[)|\\])", ""));
                        boolean is3 = tokens[i].startsWith("3");

                        String str;
                        for(int l = 0; l < (is3 ? 3 : 6); l++){
                            switch(str = sc.next()){
                                case "-": break;
                                case "#": notes.get(notes.size()-1).duration += Staff.NOTE_ETRIPLET; break;
                                default: notes.add(new Note(Staff.getNote(str), Staff.getPos(0, sdPos/2, 3, l), Staff.NOTE_ETRIPLET)); break;
                            }
                        }

                        sdPos += is3 ? 1 : 3;
                    }
                    else {
                        boolean onbeat = sdPos % 2 == 0;
                        int notestart = Staff.getPos(0, sdPos/2, swing ? 3 : 2, onbeat ? 0 : (swing ? 2 : 1));
                        int duration = swing ? (Staff.NOTE_ETRIPLET * (onbeat ? 2 : 1)) : Staff.NOTE_WHOLE / subdiv;
                        notes.add(new Note(Staff.getNote(tokens[i]), notestart, duration));
                    }
            }
            sdPos++;
        }

        return notes;
    }

    public List<Note> processChords(String chords, int subdiv){
        List<Note> notes = new ArrayList<Note>();

        int sdlength = Staff.NOTE_WHOLE / subdiv;
        String[] tokens = chords.split(" ");
        for(int i = 0; i < tokens.length; i++){
            switch(tokens[i]){
                case "-":
                    break;
                case "#": 
                    for(Note o : notes) if(o.start == (i-1) * sdlength) o.duration += sdlength;
                    break;
                default:
                    notes.addAll(Staff.getChord(tokens[i], i * sdlength, sdlength));
                    break;
            }
        }

        return notes;
    }

    public void loadMusicSequence(String sequence){
        Scanner seqscanner = new Scanner(sequence);

        while(seqscanner.hasNext()){
            String line = seqscanner.nextLine();
            String[] tokens = line.split(" ");

            switch(tokens[0].toLowerCase()){
                case "tempo":
                    tempo = Integer.valueOf(tokens[1]);
                    break;
                case "swing":
                    swing = tokens[1].contains("1");
                    break;
                case "timesignature":
                    String[] times = tokens[1].split("/");
                    timeNumerator = Integer.valueOf(times[0]);
                    timeDenominator = Integer.valueOf(times[1]);
                    break;
                case "chords": 
                    chords = processChords(line.substring(2 + tokens[0].length() + tokens[1].length()), Integer.valueOf(tokens[1]));
                    break;
                case "melody":
                    melody = processMelody(line.substring(2 + tokens[0].length() + tokens[1].length()), Integer.valueOf(tokens[1]));
                    break;
            }
        }
    }

    MusicSequence(String filepath){
        String seqstring = "";

        try (Scanner sc = new Scanner(new BufferedReader(new FileReader(new File(filepath))))) {
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                if(line.startsWith("#")) 
                    seqstring += line.substring(1) + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        loadMusicSequence(seqstring);
    }
}