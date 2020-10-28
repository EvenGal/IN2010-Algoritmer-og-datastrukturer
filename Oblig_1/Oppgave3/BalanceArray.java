package Oppgave3;
/*Oppgave 3a*/
import java.util.ArrayList;


public class BalanceArray {
    public static void main(String[] args) {
        
        //Lager et objekt av klassen, slik at vi kan aksessere dens metoder
        BalanceArray balansertArray = new BalanceArray();
        //Lager en arraylist vi skal balansere
        ArrayList<Integer> sortertArray = new ArrayList<Integer>();
        
        //Kjoerer loekken 11 ganger, ettersom eksempelet benytter dette antallet

        for(int i = 0; i < 11; i++){
            sortertArray.add(i);
        }
        //Definerer slutten av listen
        int slutt = sortertArray.size()-1;
        //Balanserer listen
        balansertArray.balanser(sortertArray, 0, slutt);
    }
    //Metode for aa balansere arraylisten som plottes inn
    public void balanser(ArrayList<Integer> sortertArrayliste, int start, int slutt) {
        //Hvis listen har et antall lik 0, saa skal den bare returnere ingenting
        if (start > slutt){
            return;
        }
        //Hvis det er fler enn 0 antall elementer
        //saa skal listen splittes i to og printe ut midterse element (roten i hvert subtre)
        int midtersteNode = (start + slutt + 1)/2;
        System.out.println(sortertArrayliste.get(midtersteNode));
        
        //KJoerer metoden rekursivt for de to listene vi sitter igjen med
        //Foerst hoyre subtre
        balanser(sortertArrayliste, midtersteNode + 1, slutt);
        //Deretter det venstre
        balanser(sortertArrayliste, start, midtersteNode-1);
    }
}