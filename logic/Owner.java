package logic;
import java.util.Scanner;
public class Owner{ 
    String deadline;
    String task_title;
    int id_ow_set;
    Owner(String deadline, String task_title, int id_ow_set){
        this.deadline = deadline;
        this.task_title = task_title;
        this.id_ow_set = id_ow_set;
    }
    private Scanner sc = new Scanner(System.in);
    void deadlineinput(Owner owner){
        System.out.print("Enter deadline:");
        owner.deadline = sc.nextLine();
    }
    
    void taskinput(Owner owner){ 
        System.out.println("Give an title of task:");
        owner.task_title = sc.nextLine();
    }
    
    void idinput(Owner owner){
        System.out.println("Give an id of member to provide task:");
        owner.id_ow_set = sc.nextInt();
        sc.nextLine(); // Consume the newline character left by nextInt()
    }
    void closeScanner() {sc.close();}
    @Override
    public String toString() {
        return "Owner setting completed.";
    }

}
