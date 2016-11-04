package logic;

public class User {
    
    private long telegramId;
    private UserState state;
    private String argument;
    private int rating;
    
    public User(){}
    
    public User(String argument, int rating, UserState state)
    {
        this.rating = rating;
        this.argument = argument;
        this.state = state;
    }
    
    public User(long telegramId, String argument, int rating,UserState state)
    {
        this.telegramId = telegramId;
        this.rating = rating;
        this.argument = argument;
        this.state = state;
    }
    
    public void setTelegramId(long telegramId )
    {
        this.telegramId = telegramId;
    }
    
    public long getTelegramId()
    {
        return telegramId;
    }
    
    public void setArgument(String argument)
    {
        this.argument = argument;
    }
    
    public String getArgument()
    {
        return argument;
    }
    
    public void setRating(int rating)
    {
        this.rating = rating;
    }
    
    public int getRating()
    {
        return rating;
    }
    
    public void setState(UserState state)
    {
        this.state = state;
    }
    
    public UserState getState()
    {
        return state;
    }
}
