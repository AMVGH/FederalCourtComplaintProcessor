public class Complaint {
    private String causeOfAction;
    private String plaintiffCitizenship;
    private String defendantCitizenship;
    private String originalStateOfFiling;
    private double amountInControversy;
    private int id;
    private static int nextID;
    Complaint(String causeOfAction, String plaintiffCitizenship, String defendantCitizenship, String originalStateOfFiling, double amountInControversy){
        this.causeOfAction = causeOfAction;
        this.plaintiffCitizenship = plaintiffCitizenship;
        this.originalStateOfFiling = originalStateOfFiling;
        this.defendantCitizenship = defendantCitizenship;
        this.amountInControversy = amountInControversy;

        id = nextID;
        nextID++;
    }



    public String getCauseOfAction(){
        return causeOfAction;
    }
    public String getPlaintiffCitizenship(){
        return plaintiffCitizenship;
    }
    public String getDefendantCitizenship(){
        return defendantCitizenship;
    }
    public String getOriginalStateOfFiling(){
        return originalStateOfFiling;
    }

    public double getAmountInControversy() {
        return amountInControversy;
    }

    public int getId(){
        return id;
    }
}
