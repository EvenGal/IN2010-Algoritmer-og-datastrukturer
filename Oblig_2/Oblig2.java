import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


//Hovedklassen for execution
class Oblig2 {
    public static void main(String[] args) throws IOException {
        //FInner filen fra terminalen
        String filename = null;

        if(args.length > 0){
            filename = args[0];
        }else{
            System.out.println("Feil kompilering av programmet!");
            return;
        }

        //Innlesning av fil

        File fil = new File(filename);

        Planlegger planlegger = null;

        try {
            planlegger = Planlegger.innlesFil(fil);
        } catch (FileNotFoundException e) {
            System.out.println("Filen finnes ikke!");

            System.exit(1);
        }

        //Starter programmet (Hovedsenter)
        System.out.println();
        planlegger.cyclicPath();
        planlegger.print();
        System.out.println();
        planlegger.skrivUtProsjektInformasjon();
    }
}
