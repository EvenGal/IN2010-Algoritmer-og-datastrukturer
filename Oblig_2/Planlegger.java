import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;


//KLasse for aa planlegge programmet
class Planlegger{

    private int numberOfTasks;
    private HashMap<Integer, Task> mapOfTasks;
    private int shortestAmountOfTime = 0;
    private boolean cyclicPath = false;
    private String cyclic = ""; 
    int tid;

    //Konstruktoer
    Planlegger(int numberOfTasks, HashMap<Integer, Task> mapOfTasks){
        this.numberOfTasks = numberOfTasks;
        this.mapOfTasks = mapOfTasks;

    }
    
    //metode for innelsning av fil
    public static Planlegger innlesFil(File fil) throws FileNotFoundException{

        Scanner filleser = new Scanner(fil);
        String l;
        l = filleser.nextLine();
        int numberOfTasks = Integer.parseInt(l);
        HashMap<Integer, Task> taskMapper = new HashMap<Integer, Task>();
        
        //Sjekker om filen har linjer
        while(filleser.hasNextLine()){
            l = filleser.nextLine();
        
        //Sjekker om filen har tomme linjer
        while(l.isEmpty()){
            l = filleser.nextLine();
        }

        //Deler opp linjene i de forskjellige variablene som inngaar i Task
        String[] partitions = l.split("\\s+");

        int id = Integer.parseInt(partitions[0]);

        String name = partitions[1];

        int time = Integer.parseInt(partitions[2]);

        int staff = Integer.parseInt(partitions[3]);

        //Lager et map med depending edges, altsaa tasks som depender paa tasken
        ArrayList<Integer> depEdges2 = new ArrayList<Integer>();

        //Legger disse i listen
        for(int i = 0; i < partitions.length - 5; i++){

            depEdges2.add(Integer.parseInt(partitions[i+4]));
        }
        //Lager en task av innlest informasjon
        Task taskForceOne = new Task(id, name, time, staff, depEdges2.size(), depEdges2);

        //Legger denne til i mappet
        taskMapper.put(id, taskForceOne);
        
    }

    //Her legges alle outerEdges paa tasken
    for(Task task: taskMapper.values()){


        for(int i: task.dependencies){
            taskMapper.get(i).outerEdges.add(task);
            taskMapper.get(i).numberOfOutEdges++;
            taskMapper.get(i).numberOutEdges++;
        }
    }
    //LEgger til inneredges paa tasken
    for(Task task1: taskMapper.values()){
        for(Task task2: task1.outerEdges){
            task2.InnerEdges.add(task1);
        }
    }

    //Filleser ferdig --> close
    filleser.close();

    //Returnerer en klasse av planlegger, ferdig innlest og klar
    return new Planlegger(numberOfTasks, taskMapper);
    
}

    //Finner ut om det finnes en syklis path
    public boolean cyclicPath(){
        reset();

        for(Task task: mapOfTasks.values()){
            if(task.cntPredecessors > 0){
                if(cyclicPathSearch(task, "")){
                    return true;
                }
            }
        }
        return false;
    }
    //Algoritme fra pensum. Benytter topologisk sortering
    public boolean topologicSort(){
        //Reseter grafen
        this.reset();
        Task taskelement;
        int teller = 0;
        LinkedList<Task> elementQueue = new LinkedList<Task>();

        //Setter alle earliest start til 0
        for(Task task: mapOfTasks.values()){
            task.earliestStart = 0;
        //Hvis ingen cntpredecessors er earliest start = 0
            if(task.cntPredecessors == 0){
                task.earliestStart = 0;
                //Legger til tasken i koen over taskselementer
                elementQueue.add(task);
            }
        }
        //Hvis koen ikke er tom, saa skal foerste element fjernes og fjerne cntpredecessor til denne tasken
        while(!elementQueue.isEmpty()){

            taskelement = elementQueue.removeFirst(); //Velger og foerste element i koen

            teller++;

            for(Task task: taskelement.outerEdges){

                task.cntPredecessors--;

                //Sjekker om earliest start er mindre enn tiden som har gaatt med taskens tid
                if(task.earliestStart < (taskelement.earliestStart + taskelement.time)){

                    task.earliestStart = taskelement.earliestStart + taskelement.time;
                }
                //Sjekker om tasken som avsluttes naa er lengre en shortest time for tasken
                if(task.earliestStart + task.time > this.shortestAmountOfTime){
                    this.shortestAmountOfTime = task.earliestStart + task.time;
                }
                //Hvis det er ingen predecessors legges tasken i koen
                if(task.cntPredecessors == 0){
                    elementQueue.add(task);
                }
            }   
        }

        //lager en ny koe
        elementQueue = new LinkedList<Task>();


        //Hvis ingen andre task er avhengige av denne tasken, saa legges den inn

        for(Task task: mapOfTasks.values()){

            task.latestStart = this.shortestAmountOfTime;

            if(task.numberOfOutEdges == 0){

                task.latestStart = this.shortestAmountOfTime-task.time;

                elementQueue.add(task);
            }
        }

        //Sjekker gjennom elementkoen om den ikke er tom
        //Setter currentask til foerste element i koen
        //Hvis inneredge av tasken er mindre enn tasken skal tasken som settes til slutt vaere lik denne currenten
        while(!elementQueue.isEmpty()){

            Task currentTask = elementQueue.remove();

            for(Task task: currentTask.InnerEdges){
                if(task.latestStart > (currentTask.latestStart - task.time)){

                    task.latestStart = currentTask.latestStart - task.time;
                }

                task.numberOfOutEdges--;

                //Hvis ingen outerEdges, skal elementet i koen
                if(task.numberOfOutEdges == 0){
                    elementQueue.add(task);
                }
            }
        }


        //Sjekk for aa hindre at et element er lagt til flere ganger i koen

        if(teller < this.numberOfTasks){
            this.cyclicPath = true;

            return false;
        }
        //Setter slack for hver oppgave
        for(Task task: mapOfTasks.values()){
            task.slack = task.latestStart - task.earliestStart;
        }
        //Hvis ja
        return true;
    }

    //Soeker etter om det er en syklisk gjennomkjoering
    public boolean cyclicPathSearch(Task task, String string){
        
        //Status
        if(task.currentStatus == "search ongoing!"){
            task.currentStatus = "search ongoing!";

            string += task.id;
            this.cyclic = string;
            this.cyclicPath = true;

            return true;
        }

        //Hvis soeket er ferdig, saa finnes ingen sykliske paths
        if(task.currentStatus == "search completed!"){
            
            return false;
        }
        
        //Hvis ingen, fortsett soek
        if(task.currentStatus == "no current cycle!"){

            task.currentStatus = "search ongoing!";

            string += task.id;

            //Rekursiv soek
            for(Task outerEdges: task.outerEdges){
                if(cyclicPathSearch(outerEdges, string)){
                    return true;
                }
            }

            //Ferdig, da skal soeket avsluttes
            task.currentStatus = "search completed!";
        }

        return false;
    }



    //Reseter grafen for hver nye prosjekt som legges til
    public void reset(){
        this.cyclicPath = false;
        this.shortestAmountOfTime = 0;

        //Setter alt til aa vaere 0 eller default
        for(Task task: mapOfTasks.values()){
            task.earliestStart = 0;
            task.latestStart = 0;
            task.slack = 0;
            task.numberOfOutEdges = task.numberOutEdges;
            task.currentStatus = "no current cycle!";
        } 
    }

    //PRinter informasjonen til brukeren
    public void print(){

        //Sorterer foerst grafen
        if(!this.topologicSort()){
            return;
        }

        int currentStaff = 0;


        for(int i=0; i <= shortestAmountOfTime; i++){

            boolean checker = false;

            //Skriver ut tid, id
            for(Task task: mapOfTasks.values()){


                if(task.earliestStart == i){

                    if(!checker){
                        System.out.println("Time (current): " + i);
                        checker = true;
                    }

                    System.out.println("Starting projectID: " + task.id);

                    currentStaff += task.staff;
                }

                //Sjekker om oppgaven slutter
                else if(task.earliestStart + task.time == i){
                    if(!checker){
                        System.out.println("Time (current): " + i);
                        checker = true;
                    }
                    System.out.println("Finished taskID: " + task.id);

                    currentStaff -= task.staff;
                }
            }

            //HVis checker er true, skriv ut staff for tasken
            if(checker){

                System.out.println("Staff (current): " + currentStaff);
                System.out.println("------------------");
            }
        }
        //Slutt print for aa finne korteste tid for prosjektet
        System.out.println("*** SHORTEST POSSIBLE PROJECT EXECUTION TIME: " + shortestAmountOfTime + " ***");

    }

    //"Frontend" for brukeren
    public void skrivUtProsjektInformasjon(){

        System.out.println("**** PROJECT INFORMATION ****");

        if(this.cyclicPath){

            //Hvis syklisk, prosjektet shuttes ned
            System.out.println("Project path is cyclic, shutting down..");
            System.out.println("The tasks in the cyclic path: " + this.cyclic);
        }

        for(Task task: mapOfTasks.values()){

            //Skriver ut all informasjon
            System.out.println("------------------");
            System.out.println("ID: " + task.id);
            System.out.println("Name: " + task.name);
            System.out.println("Manpower required: " + task.staff);
            System.out.println("Time required: " + task.time);

            //HVis ikke syklisk
            if(!cyclicPath()){

                //Informasjon om hver oppgave
                System.out.println("Slack: " + task.slack);
                System.out.println("Critical task: " + (task.slack == 0));
                System.out.println("Earliest start time: " + task.earliestStart);
                System.out.println("Latest start time: " + task.latestStart);
            }
            //Skriver ut tasks som er depending
            String depends = "";

            for(Task task2: task.outerEdges){
                depends += task2.id + " ";
            }
            if(depends.isEmpty()){
                System.out.println("There are no depending tasks!");
            
            }else{
                System.out.println("Depending task(s): " + depends);
            }
        }
    }
}