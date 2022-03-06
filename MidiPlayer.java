// Java program showing how to change the instrument type
import javax.sound.midi.*;

public class MidiPlayer {
 
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
            AddNote(Staff.getNote(Staff.Eb, 6), Staff.getPos(2, 0, 3, 0), Staff.NOTE_HALF, 100, track);

            AddNote(Staff.getNote(Staff.F, 6), Staff.getPos(2, 2, 3, -1), Staff.NOTE_HALF, 100, track);

            sequencer.setSequence(sequence);
            sequencer.start();
 
            while (true) {
                if (!sequencer.isRunning()) {
                    sequencer.close();
                    break;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    
    public void PlaySequence(MusicSequence ms){
        try{
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setTempoInBPM(ms.tempo);

            Sequence sequence = new Sequence(Sequence.PPQ, Staff.NOTE_QUARTER);
            Track track = sequence.createTrack();

            track.add(makeEvent(192, 1, 0, 0, 0));


            for(Note note : ms.melody) {
                AddNote(note, track);
                System.out.println(note.note + ": " + note.start/8);
            }

            sequencer.setSequence(sequence);
            new Thread(() -> {
                sequencer.start();
 
                while (true) {
                    if (!sequencer.isRunning()) {
                        sequencer.close();
                        break;
                    }
                }
            }).start();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    public void AddNote(int note, int tick, int length, int velocity, Track track){
        track.add(makeEvent(144, 1, note, velocity, tick)); //on
        track.add(makeEvent(128, 1, note, velocity, tick + length)); //off
    }

    public void AddNote(Note note, Track track) {
        AddNote(note.note, note.start + 10, note.duration, 100, track);
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