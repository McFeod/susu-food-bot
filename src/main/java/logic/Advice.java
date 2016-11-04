package logic;

public class Advice {
    private long id;
    private String advice;

    public Advice() {
    }
    
    public Advice(String advice)
    {
        this.advice = advice;
    }
    
    public Advice (long id, String advice)
    {
        this.id = id;
        this.advice = advice;
    }
    
    
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getAdvice() {
        return advice;
    }
}
