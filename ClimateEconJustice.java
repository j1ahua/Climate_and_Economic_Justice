package climate;

import java.util.ArrayList;

import javax.swing.plaf.nimbus.State;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
       

        String[] data= inputLine.split((","));
        String state = data[2];
        if (this.firstState == null){
            this.firstState = new StateNode(state,null,null);
        }
        else{
            StateNode ptr = firstState;
            while (ptr != null && !ptr.getName().equals(state)){
                if (ptr.next == null){
                    StateNode newState = new StateNode(state, null, null);
                    ptr.next = newState;
                    break;
                }
                ptr = ptr.next;
            }
        }
    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
        String[] data= inputLine.split((","));
        String county = data[1];
        String state = data[2];
        StateNode ptr = firstState;
        
        while (ptr != null){
            if (ptr.getName().equals(state)){
                CountyNode countyNode = new CountyNode(county, null, null);
            
            
                if (ptr.getDown() == null){
                    ptr.setDown(countyNode);
                }
                else{
                    CountyNode countyPTR = ptr.getDown();
                    CountyNode prevCounty = null;
                    while ( countyPTR != null){
                        
                        if (countyPTR.getName().equals(countyNode.getName())){
                            break;
                        }
                        prevCounty = countyPTR;
                        countyPTR = countyPTR.getNext();
                    }
                    if (countyPTR == null){
                        prevCounty.setNext(countyNode);
                    }
                    
                }
                
            }
                
            ptr = ptr.getNext();
        }
                
            
    }
        
        
    

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
        String[] data = inputLine.split(",");
        String community = data[0];
        double pAA = Double.parseDouble(data[3]);
        double pN = Double.parseDouble(data[4]);
        double pA = Double.parseDouble(data[5]);
        double pW = Double.parseDouble(data[8]);
        double pH = Double.parseDouble(data[9]);
        String dA = data[19];
        double PML = Double.parseDouble(data[49]);
        double cOF = Double.parseDouble(data[37]);
        double pPL = Double.parseDouble(data[121]);

        Data communityData = new Data(pAA, pN, pA, pW, pH, dA, PML, cOF, pPL);
        String county = data[1];
        String state = data[2];
        StateNode statePTR = firstState;

        while (statePTR != null){
            if (statePTR.getName().equals(state)){

                CountyNode countyPTR = statePTR.getDown();

                while (countyPTR != null){
                    if (countyPTR.getName().equals(county)){
                        CommunityNode newComm = new CommunityNode(community, null, communityData);
                        if (countyPTR.getDown() ==null){
                            countyPTR.setDown(newComm);
                        }else{
                            CommunityNode commPTR = countyPTR.getDown();
                            while (commPTR.getNext() != null){
                                if (commPTR.getName().equals(community)){
                                    break;
                                }
                                commPTR = commPTR.getNext();
                            }
                            if (!commPTR.getName().equals(community)){
                                commPTR.setNext(newComm);
                            }
                        }
                        break;
                    }
                    countyPTR = countyPTR.getNext();
                }
                break;
            }
            statePTR = statePTR.getNext();
        }

    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {

        
        int count = 0;
        StateNode statePTR = firstState;

        while (statePTR != null){
            CountyNode countyPTR = statePTR.getDown();

            while (countyPTR != null){
                CommunityNode commPTR = countyPTR.getDown();

                while (commPTR != null){
                   if (commPTR.getInfo().getAdvantageStatus().equals("True")){
                    double racePerc = 0.0;
                    switch (race){
                        case "African American":
                            racePerc = commPTR.getInfo().prcntAfricanAmerican * 100;
                            break; 
                        case "Native American":
                            racePerc = commPTR.getInfo().prcntNative * 100;
                            break;
                        case "Asian American":
                            racePerc = commPTR.getInfo().prcntAsian * 100;
                            break;
                        case "White American":
                            racePerc = commPTR.getInfo().prcntWhite * 100;
                            break;
                        default:
                            racePerc = commPTR.getInfo().prcntHispanic * 100;
                    }
                    if (userPrcntage <= racePerc){
                        count++;
                    }
                   }
                    commPTR = commPTR.getNext();
                }
                countyPTR = countyPTR.getNext();
            }
            statePTR = statePTR.getNext();
        }
        return count; 
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        int count = 0;
        StateNode statePTR = firstState;

        while (statePTR != null){
            CountyNode countyPTR = statePTR.getDown();

            while (countyPTR != null){
                CommunityNode commPTR = countyPTR.getDown();

                while (commPTR != null){
                   if (commPTR.getInfo().getAdvantageStatus().equals("False")){
                    double racePerc = 0.0;
                    switch (race){
                        case "African American":
                            racePerc = commPTR.getInfo().prcntAfricanAmerican * 100;
                            break; 
                        case "Native American":
                            racePerc = commPTR.getInfo().prcntNative * 100;
                            break;
                        case "Asian American":
                            racePerc = commPTR.getInfo().prcntAsian * 100;
                            break;
                        case "White American":
                            racePerc = commPTR.getInfo().prcntWhite * 100;
                            break;
                        default:
                            racePerc = commPTR.getInfo().prcntHispanic * 100;
                    }
                    if (userPrcntage <= racePerc){
                        count++;
                    }
                   }
                    commPTR = commPTR.getNext();
                }
                countyPTR = countyPTR.getNext();
            }
            statePTR = statePTR.getNext();
        }
        return count; 
    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {
        ArrayList <StateNode> targetStates = new ArrayList<>();
        StateNode statePTR = firstState;

        while (statePTR != null){
            CountyNode countyPTR = statePTR.getDown();

            while (countyPTR != null){
                CommunityNode commPTR = countyPTR.getDown();

                while (commPTR != null){
                   if (commPTR.getInfo().PMlevel >= PMlevel){
                        if (!targetStates.contains(statePTR)){
                            targetStates.add(statePTR);
                        }
                   }
                   
                    commPTR = commPTR.getNext();
                }
                countyPTR = countyPTR.getNext();
            }
            statePTR = statePTR.getNext();
        }
      
        return targetStates; 
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {
        int count = 0;
        StateNode statePTR = firstState;

        while (statePTR != null){
            CountyNode countyPTR = statePTR.getDown();

            while (countyPTR != null){
                CommunityNode commPTR = countyPTR.getDown();

                while (commPTR != null){
                  if (commPTR.getInfo().chanceOfFlood >= userPercntage){
                    count++;
                  }
                    commPTR = commPTR.getNext();
                }
                countyPTR = countyPTR.getNext();
            }
            statePTR = statePTR.getNext();
        }
        return count; 
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {
            ArrayList<CommunityNode> highestPovertyCommunities = new ArrayList<>(10);
            StateNode statePTR = firstState;
            while (statePTR != null) {
                if (statePTR.getName().equals(stateName)) {
                    CountyNode countyPTR = statePTR.getDown();
                    while (countyPTR != null) {
                        CommunityNode commPTR = countyPTR.getDown();
                        while (commPTR != null) {
                            if (highestPovertyCommunities.size() < 10) {
                                highestPovertyCommunities.add(commPTR);
                            } else {
                                int lowestIndex = 0;
                                double lowestPovertyLine = highestPovertyCommunities.get(0).getInfo().getPercentPovertyLine();
                                // Find the community with the lowest percent poverty line in the list
                                for (int i = 1; i < highestPovertyCommunities.size(); i++) {
                                    double currentPovertyLine = highestPovertyCommunities.get(i).getInfo().getPercentPovertyLine();
                                    if (currentPovertyLine < lowestPovertyLine) {
                                        lowestPovertyLine = currentPovertyLine;
                                        lowestIndex = i;
                                    }
                                }
                                // Replace the community with the lowest percent poverty line if current is higher
                                if (commPTR.getInfo().getPercentPovertyLine() > lowestPovertyLine) {
                                    highestPovertyCommunities.set(lowestIndex, commPTR);
                                }
                            }
                            commPTR = commPTR.getNext();
                        }
                        countyPTR = countyPTR.getNext();
                    }
                }
                statePTR = statePTR.getNext();
            }
            return highestPovertyCommunities;
        }
        
}
    
