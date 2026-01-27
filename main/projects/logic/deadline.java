public class deadline {

    //Basic attributes
    private int id;
    private String title;
    private String duedate;
    private boolean completed;
    // Constructor
    public deadline(int id, String title, String dueDate) {
        this.id = id;
        this.title = title;
        this.duedate = dueDate;
        this.completed = false;
    }
    //Mark as completed
    public void markCompleted(){
        completed = true;
    }
    //check if complete
    public boolean isCompleted(){
        return completed;
    }
    // Get information
    public String getInfo() {
        return "ID: " + id +
               "\nTitle: " + title +
               "\nDue Date: " + duedate +
               "\nCompleted: " + completed;
    }

    // Simple setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDueDate(String dueDate) {
        this.duedate  = dueDate;
    }
  }
