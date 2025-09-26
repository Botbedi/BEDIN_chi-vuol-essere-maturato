public class PlayerStatics {
    public String name;
    public int correctAnswer;
    public boolean used5050;
    public boolean usedAudience;
    public PlayerStatics(String name) {
        this.name = name;
        this.correctAnswer = 0;
        this.used5050 = false;
        this.usedAudience = false;
    }
}
