public class Main {
    public static void main(String[] args) {

        Member member1 = Member.register(
            "kun",
             "pisal",
              "pisalkun@gmail.com",
               "Password@1");
        if (member1 != null) {
            System.out.println(member1.toString());
        }

        System.out.println("Total member: " + member1.getTotalMembers());
    }
}