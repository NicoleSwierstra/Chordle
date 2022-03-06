import java.util.Scanner;

public class Main{
    public static void main(String[] args)
    {
        MidiPlayer player = new MidiPlayer();
 
        MusicSequence ms = new MusicSequence("testsequence.txt");
        player.PlaySequence(ms);

        Scanner input = new Scanner(System.in);

        int rounds = 0;
        while(rounds < 6){
            String in = input.nextLine();
            
            rounds++;
        }
    }
}