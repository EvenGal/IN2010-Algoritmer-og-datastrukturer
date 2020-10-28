package Oppgave1;
/*(Oppgave 1a)*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

//Triple-ended-deque
public class Teque {
    //ArrayList for aa lagre alle verdiene
    private static ArrayList<Integer> liste = new ArrayList<Integer>();
    
    public static void main(String[] args) throws IOException {

        //Leser inn kommandoene
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //Printer ut tallet paa "get" kommandoen
        PrintWriter printWriter = new PrintWriter(System.out);
        int N = Integer.parseInt(bufferedReader.readLine());
       
       //Hovedloop som kjoerer antall N kommandoer som er gitt
        for(int i = 0; i < N; i++) {
            
            //Splitter kommando og tallet som settes inn i listen
            String[] linje = bufferedReader.readLine().split(" ");
            String kommando = linje[0];
            int tall = Integer.parseInt(linje[1]);

            //Kommandosenter
            switch(kommando){
                //Pusher bak
                case "push_back":
                    push_back(tall);
                    break;
                //Pusher foran
                case "push_front":
                    push_front(tall);
                    break;
                //Pusher i midten
                case "push_middle":
                    push_middle(tall);
                    break;
                //Henter elementet paa indeksen
                case "get":
                    printWriter.write(Integer.toString(get(tall)) + '\n');
                    break;
                //Alltid kjoer dette til slutt
                default:
            }      
        }
        //Flusher printwriter, slik at den ikke printer ut samme verdi to ganger
        printWriter.flush();
    }
    //metodene for aa fikse paa listen
    public static void push_back(int x){
        liste.add(x);
    }
    //Setter elementet foerst i listen
    public static void push_front(int x){
        liste.add(0, x);
    }
    //Setter elementet i midten av listen
    //Deler listen i to, deretter setter det inn paa den indeksen
    public static void push_middle(int x){
        int stoerrelseMidten = liste.size()/2;
        liste.add(stoerrelseMidten, x);
    }
    //Henter elementet paa indeksen
    public static int get(int i){
        if(liste.size() == 1){
            return liste.get(0);
        }else{
            return liste.get(i);
        }
    }
}