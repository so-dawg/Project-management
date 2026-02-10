import java.util.Scanner;
import logic.Task;
import java.time.localDate;
public class Owner{ 

    private int Id;
    private int day;
    private int month;
    private int year;
    private String task;
    private String membername;
    private String email;
    private boolean isowner = true;
    
    Owner(int Id, String task, String membername, String email) {
        this.Id = Id;
        this.task = task;;
        this.membername = membername;
        this.email = email;
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
    public String get_email(){
        return email;
    }
    //for deadline section 

    public int set_deadline(String deadline) {
        System.out.print("Enter deadline (Ex: DD-MM-YYYY):");
        String deadline = sc.nextLine();
        String pattern = "^\\d{2}-\\d{2}-\\d{4}$";
        if (deadline.matches(pattern)) {
          String[] tokens = deadline.split("-");
          for (String token : tokens){
            int[] values = Integer.parseInt(token.trim());
          }
          int days = values[0];
          int months = values[1];
          int years = values[2];
          if (!Task.isPastDeadline(years, months, days)){
            
          }
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

    // request join section
    boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");

        if (atIndex <= 0) {
            return false;
        }

        if (dotIndex <= atIndex + 1) {
            return false;
        }

        if (dotIndex >= email.length() - 1) {
            return false;
        }
        return true;
    }
    public void request_join(Owner member){
        boolean ver_email =  member.isValidEmail(member.get_email());
        if(ver_email){
            System.out.println("name:" + member.get_name() + "\nID:" + member.get_Id() + "\nEmail:" + member.get_email());
            System.out.println("this member should approve or not?(yes/no)");
            String answer = sc.nextLine();
            if (answer.equalsIgnoreCase("yes")) {
                System.out.println("Request approved.");
            } else if (answer.equalsIgnoreCase("no")) {
                System.out.println("Request denied.");
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
        else{
            System.out.println("This invalid email.");
        }
        
    } 

    void sc_close() {
        sc.close();
    }
    
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
// tring() {
    //         return "Owner setting completed.";
    //     }
    @Override
    public String toString() {
        return "Owner setting completed.";
    }

public static void main(String[] args) {
        Owner o = new Owner("", 0, "add instructions", "heng", "heng@example.com");
        String deadline = o.inputDeadline();
        int search_ID = o.input_Id();
        o.set_deadline(deadline);
        System.out.println("Deadline set to: " + o.set_deadline(deadline));
        o.Find_member(o, search_ID);
    }


}
