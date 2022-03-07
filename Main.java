import java.util.List;
import java.util.Scanner;
import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class Main{
    public static void main(String[] args)
    {
        if(System.getProperty("os.name").startsWith("Windows"))
        {
            // Set output mode to handle virtual terminal sequences
            Function GetStdHandleFunc = Function.getFunction("kernel32", "GetStdHandle");
            DWORD STD_OUTPUT_HANDLE = new DWORD(-11);   
            HANDLE hOut = (HANDLE)GetStdHandleFunc.invoke(HANDLE.class, new Object[]{STD_OUTPUT_HANDLE});

            DWORDByReference p_dwMode = new DWORDByReference(new DWORD(0));
            Function GetConsoleModeFunc = Function.getFunction("kernel32", "GetConsoleMode");
            GetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, p_dwMode});

            int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
            DWORD dwMode = p_dwMode.getValue();
            dwMode.setValue(dwMode.intValue() | ENABLE_VIRTUAL_TERMINAL_PROCESSING);
            Function SetConsoleModeFunc = Function.getFunction("kernel32", "SetConsoleMode");
            SetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, dwMode});
        }


        MidiPlayer player = new MidiPlayer();
 
        MusicSequence ms = new MusicSequence("testsequence.txt");

        Scanner input = new Scanner(System.in);

        int rounds = 0;
        while(rounds < 6){
            player.PlaySequence(ms);
            String in = input.nextLine();

            List<Integer> matches = ms.CompareMelody(in);
            EraseLastAndReplace(in, matches);
            
            rounds++;
        }
        input.close();
    }

    static void eraseLast(){
        System.out.println("\033[1A\033[2K");
    }

    static void EraseLastAndReplace(String line, List<Integer> matches){
        eraseLast();
        String[] tokens = line.split(" ");
        int tindex = 0;
        for(int i = 0; i < matches.size(); i++){
            while(tokens[tindex].startsWith("-") || tokens[tindex].startsWith("#")){
                System.out.print(tokens[tindex] + " ");
                tindex++;
            }
            if(matches.get(i) == 2) System.out.print("\033[42;30m");
            else if(matches.get(i) == 1) System.out.print("\033[43;30m");

            System.out.print(tokens[tindex] + " ");
            tindex++;
            System.out.print("\033[40;37m");
        }
        System.out.print("\n");
    }
}