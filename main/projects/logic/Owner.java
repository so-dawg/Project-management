package logic;
import java.util.Scanner;
class owner_set{
    String deadline;
    String task_title;
    int id_ow_set;
    owner_set(String deadline,int id_owner_set,String task_title){
        this.deadline = deadline;
        this.id_ow_set = id_owner_set;
        this.task_title = task_title;
    }
}
public class Owner{ 
    void ownerinput(owner_set owner){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter deadline:");
        owner.deadline = sc.nextLine(); 
        System.out.println("Give an title of task:");
        owner.task_title = sc.nextLine();
        System.out.println("Give an id of member to provide task:");
        owner.id_ow_set = sc.nextInt();
    }     
    @Override
    public String toString() {
        return "Owner setting completed.";
    }
    public static void main(String[] args) {
       owner_set owner1 = new owner_set("",0," ");
       Owner ow = new Owner();
       ow.ownerinput(owner1);
       System.out.println(owner1.deadline+"\n"+owner1.id_ow_set+"\n"+owner1.task_title);
       System.out.println(ow.toString());
    }
}
