package app.model;

public class Team {

    private String name;
    private int manager;
    private int analyst;
    private int designer;
    private int programmer;
    private int cyber_security;

    public Team(String name, int manager, int analyst, int designer, int programmer, int cyber_security) {
        this.name = name;
        this.manager = manager;
        this.analyst = analyst;
        this.designer = designer;
        this.programmer = programmer;
        this.cyber_security = cyber_security;
    }

    public Team(String name) {
        this.name = name;
    }

    public Team() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public int getAnalyst() {
        return analyst;
    }

    public void setAnalyst(int analyst) {
        this.analyst = analyst;
    }

    public int getDesigner() {
        return designer;
    }

    public void setDesigner(int designer) {
        this.designer = designer;
    }

    public int getProgrammer() {
        return programmer;
    }

    public void setProgrammer(int programmer) {
        this.programmer = programmer;
    }

    public int getCyber_security() {
        return cyber_security;
    }

    public void setCyber_security(int cyber_security) {
        this.cyber_security = cyber_security;
    }
}
