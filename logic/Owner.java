import java.util.Scanner;
import logic.Task;
public class Owner{ 

    private String deadline;
    private int Id;
    private String task;
    private String membername;
    private final String role = "Owner";
    private boolean isSet = false;
    
    Owner(String deadline, int Id, String task, String membername) {
        this.deadline = deadline;
        this.Id = Id;
        this.task = task;;
        this.membername = membername;
    }
    private Scanner sc = new Scanner(System.in);
    // this task is for owner giving task to member. it need to give deadline ,id member and we need to get task from Task.java 
    // getter section
    public String get_Deadline() {
        return deadline;
    }
    public int get_Id(){
        return Id;
    }
    public String get_task(){
        return task;
    }
    public String get_name(){
        return membername;
    }
    
    //for deadline section 

    private String inputDeadline(){
        System.out.print("Enter deadline (Ex: DD-MM-YYYY):");
        String deadline = sc.nextLine();
        return deadline;
    }
    private String set_deadline(String deadline) {
        String pattern = "^\\d{2}-\\d{2}-\\d{4}$";
        if (deadline.matches(pattern)) {
            return this.deadline = deadline;
        }
        System.out.println("Invalid deadline format! Please use DD-MM-YYYY.");
        return this.deadline;

    }

    
    //for id section
    private int input_Id(){
        System.out.print("Enter member ID:");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline character left by nextInt()
        return id;
    }
    private void Find_member(Owner mem1 , int Id){ 
        if(mem1.get_Id() == Id){
            System.out.println("name" + mem1.get_name() + "\nID:" + mem1.get_Id() + "\nTask:" + mem1.get_task() + "\nRole" + mem1.role);
        }
        else{
            System.out.println("Invalid member");
        }
        
    }

    // // request join 
    // void request_join(){
        
    // } 

    
    // //for task section
    // void taskby_owner(){ 

    // }
//     void idinput(Owner owner){
//         System.out.println("Give an id of member to provide task:");
//         owner.id_ow_set = sc.nextInt();
//         sc.nextLine(); // Consume the newline character left by nextInt()
//     }
//     void closeScanner() {sc.close();}
//     @Override
//     public String toSprivate Scanner sc = new Scanner(System.in);
//     void deadlineinput(){
//         System.out.print("Enter deadline in DD:");
//         this.deadline = sc.nextLine();
//     }
    
//     void taskinput(Owner owner){ 
//         System.out.println("Give an title of task:");
//         owner.memfor_owner.task_title = sc.nextLine();
//     }
    
//     void idinput(Owner owner){
//         System.out.println("Give an id of member to provide task:");
//         owner.id_ow_set = sc.nextInt();
//         sc.nextLine(); // Consume the newline character left by nextInt()
//     }
//     void closeScanner() {sc.close();}
//     @Override
//     public String toString() {
//         return "Owner setting completed.";
//     }
// tring() {
//         return "Owner setting completed.";
//     }

public static void main(String[] args) {
        Owner o = new Owner("", 0, "add instructions", "heng");
        String deadline = o.inputDeadline();
        int search_ID = o.input_Id();
        o.set_deadline(deadline);
        System.out.println("Deadline set to: " + o.set_deadline(deadline));
        o.Find_member(o, search_ID);
    }


}
