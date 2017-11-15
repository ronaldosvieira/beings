package mind.goal;

public class Task {
    private String name;
    private int perc = 0;
    private boolean completed = false;

    public Task(String name) {
        this.name = name;
    }

    public void execute(int perc) {
        this.perc += perc;

        completed = this.perc >= 100;
    }

    public String getName() {return name;}
    public boolean isCompleted() {return completed;}
}
