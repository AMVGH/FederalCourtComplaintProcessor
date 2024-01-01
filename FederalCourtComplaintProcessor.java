import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class FederalCourtComplaintProcessor {
    public static void processComplaint(Complaint c) throws StateComplaintException{
        if (c.getCauseOfAction().equals("Equal Protection Challenge") || c.getCauseOfAction().equals("Title IX Workplace Discrimination") ||
                c.getCauseOfAction().equals("Prisoner Civil Rights Claim") || c.getCauseOfAction().equals("Fair Labor Standard Act Claim")){
            return;
        } else if (c.getPlaintiffCitizenship().equals(c.getDefendantCitizenship())) {
            throw new StateComplaintException("Lack of Diversity");
        } else if (c.getAmountInControversy() <= 75000) {
            throw new StateComplaintException("Amount in controversy less than or equal to $75000");
        } else if (c.getDefendantCitizenship().equals(c.getOriginalStateOfFiling())){
            throw new StateComplaintException("No prejudice through diversity");
        }else {
            return;
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);


        //Reads in the file name
        System.out.print("[Federal Court Complaint Processor]" +
                "\nEnter file name to process: ");
        String fileName = sc.nextLine();

        try{
            //New file Object; File Path can be updated, used direct path for simplicity
            File processFile = new File("C:\\Users\\Alex\\IdeaProjects\\CSE1322L\\Assignment6\\src\\"+fileName);
            //File object passed to scanner object
            Scanner processFileScan = new Scanner(processFile);

            int numberOfAcceptedCases = 0;
            int numberOfRemandedCases = 0;

            //Iterates over the complaints file
            while (processFileScan.hasNextLine()){

                //Stores the information on the line in an array, delimiter being ","
                String[] complaintInformation = processFileScan.nextLine().split(",",5);

                //Values in complaintInformation Array are passed to objects to store the information
                String causeOfAction = complaintInformation[0];
                double amountInControversy = Double.parseDouble(complaintInformation[1]);
                String plaintiffCitizenship = complaintInformation[2];
                String defendantCitizenship = complaintInformation[3];
                String originalStateOfFiling = complaintInformation[4];

                //New Complaint Object, values being passed in from what was stored in this iteration of the complaint
                Complaint newComplaint = new Complaint(causeOfAction, plaintiffCitizenship, defendantCitizenship, originalStateOfFiling, amountInControversy);

                /*New try catch inside the while loop. Processes newComplaint in the try block, if
                the complaint is valid no exception will be thrown and the information will be written to
                accepted (toString method in Complaint?)

                If the method throws an exception, it will go to the catch block and write the information in that complaint
                to remanded
                 */

                try{
                    processComplaint(newComplaint);
                    numberOfAcceptedCases++;
                    //Write to accepted
                    try{
                        File acceptedFile = new File("C:\\Users\\Alex\\IdeaProjects\\CSE1322L\\Assignment6\\src\\accepted.txt");
                        FileOutputStream fos = new FileOutputStream(acceptedFile, true);
                        PrintWriter pw = new PrintWriter(fos);
                        pw.println("Case ID: " +newComplaint.getId()
                                +"\nCause of action: " + newComplaint.getCauseOfAction()
                                +"\nAmount in Controversy: $" + newComplaint.getAmountInControversy()
                                +"\nPlaintiff's Citizenship: " + newComplaint.getPlaintiffCitizenship()
                                +"\nDefendant's Citizenship: " + newComplaint.getDefendantCitizenship()
                                +"\nOriginally filled in: " + newComplaint.getOriginalStateOfFiling()
                                +"\n==============================");
                        pw.close();
                    }catch (Exception e){
                        System.out.println("Error occurred when writing information to accepted.txt");
                    }
                }catch (StateComplaintException e){
                    numberOfRemandedCases++;
                    //Write to remanded
                    try{
                        File remandedFile = new File("C:\\Users\\Alex\\IdeaProjects\\CSE1322L\\Assignment6\\src\\remanded.txt");
                        FileOutputStream fos = new FileOutputStream(remandedFile, true);
                        PrintWriter pw = new PrintWriter(fos);
                        pw.println("Case ID: " +newComplaint.getId()
                                +"\nCause of action: " + newComplaint.getCauseOfAction()
                                +"\nAmount in Controversy: $" + newComplaint.getAmountInControversy()
                                +"\nPlaintiff's Citizenship: " + newComplaint.getPlaintiffCitizenship()
                                +"\nDefendant's Citizenship: " + newComplaint.getDefendantCitizenship()
                                +"\nOriginally filled in: " + newComplaint.getOriginalStateOfFiling()
                                +"\n" +
                                "\nReason for remand: " + e.getMessage() +
                                "\n==============================");
                        pw.close();
                    } catch (Exception s){
                        System.out.println("Error occurred when writing information to remanded.txt");
                    }
                }
            }
            System.out.println("Processing complete. Accepted cases written to accepted.txt and remanded cases written to remanded.txt");
            System.out.println("Number of remanded cases: " + numberOfRemandedCases);
            System.out.println("Number of accepted cases: " + numberOfAcceptedCases);
        }catch (FileNotFoundException e){
            System.out.println("No file with name \"" + fileName+"\"");
        }catch (Exception e){
            System.out.println("Error please try again");
        } finally {
            System.out.println("Shutting down...");
        }


    }
}
