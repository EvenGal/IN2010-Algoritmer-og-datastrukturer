import java.util.ArrayList;


class Task{
    public int id;
    public int time; //Tiden for hver task
    public int staff; //Antall staff for hver task
    public String name; //Task name
    public int earliestStart;
    public int latestStart; 
    public int slack;
    public ArrayList<Task> outerEdges = new ArrayList<Task>(); //Node edges
    public ArrayList<Task> InnerEdges = new ArrayList<Task>(); //Node predecessors
    public ArrayList<Integer> dependencies = new ArrayList<Integer>(); //Node dependencies, (Nodes that are dependent for the task)
    public int cntPredecessors = 0;
    public int numberOfOutEdges = 0;
    public int numberOutEdges = 0;
    public String currentStatus;


    //Konstruktoer
    public Task(int id, String name, int time, int staff, int cntPredecessors, ArrayList<Integer> dependencies){
        //Pointers
        this.id = id;
        this.time = time;
        this.name = name;   
        this.staff = staff;
        this.cntPredecessors = cntPredecessors;
        this.dependencies = dependencies;
    }
}

   