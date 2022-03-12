// Java program showing how to change the instrument type
import java.io.File;
import javax.sound.midi.*;

public class MidiPlayer {    
    public void PlaySequence(MusicSequence ms, boolean melody, boolean chords){
        try{
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setTempoInBPM(ms.tempo);

            Sequence sequence = new Sequence(Sequence.PPQ, Staff.NOTE_QUARTER);
            Track track = sequence.createTrack();
            Track track2 = sequence.createTrack();

            track2.add(makeEvent(192, 1, 0, 0, 0));
            track.add(makeEvent(192, 1, 0, 0, 0));

            for(Note note : ms.melody) {
                AddNote(note, 1, track);
            }
            for(Note note : ms.chords) {
                AddNote(note, 1, track2);
            }

            sequencer.setSequence(sequence);
            sequencer.setTrackMute(0, !melody);
            sequencer.setTrackMute(1, !chords);

            new Thread(() -> {
                sequencer.start();
 
                while (true) {
                    if (!sequencer.isRunning()) {
                        sequencer.close();
                        break;
                    }
                }
            }).start();

            //int[] fileTypes = MidiSystem.getMidiFileTypes(sequence);
            //
            //MidiSystem.write(sequence, fileTypes[0], new File("test.midi"));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    public void AddNote(int channel, int note, int tick, int length, int velocity, Track track){
        track.add(makeEvent(144, channel, note, velocity, tick)); //on
        track.add(makeEvent(128, channel, note, velocity, tick + length)); //off
    }

    public void AddNote(int note, int tick, int length, int velocity, Track track){
        AddNote(1, note, tick, length, velocity, track);
    }

    public void AddNote(Note note, int channel, Track track) {
        AddNote(channel, note.note, note.start, note.duration, 100, track);
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