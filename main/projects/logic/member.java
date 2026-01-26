package logic;
public class member {
    String name;
    int age;
    member(String name,int age){
        this.name = name;
        if(age != 10){
            this.age = 10;
        } 
    }
}