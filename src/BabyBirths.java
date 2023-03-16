import edu.duke.*;
import org.apache.commons.csv.*;

public class BabyBirths {
    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name: " + rec.get(0)
                        + " Gender: " + rec.get(1)
                        + " Num Born: " + rec.get(2));
            }
        }
    }
    public void totalBirths (FileResource fr) {
        int totalNamesCounter = 0, totalNamesCounterMale = 0, totalNamesCounterFemale = 0;
        int totalBirths = 0;
        int totalBirthsMale = 0, totalBirthsFemale = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            totalNamesCounter ++;
            if (rec.get(1).equals("M")) {
                totalBirthsMale += numBorn;
                totalNamesCounterMale ++;
            }
            if (rec.get(1).equals("F")) {
                totalBirthsFemale += numBorn;
                totalNamesCounterFemale ++;
            }
        }
        System.out.println("Total Births: " + totalBirths);
        System.out.println("Total names: " + totalNamesCounter);
        System.out.println("Total Births Male: " + totalBirthsMale);
        System.out.println("Total male names: " + totalNamesCounterMale);
        System.out.println("Total Births Female: " + totalBirthsFemale);
        System.out.println("Total names Female: " + totalNamesCounterFemale);
    }

    public int getRank (int year, String name, String gender){
        // Part 1: prepare CSV parsing formatting
        String parsingFormat = "media/us_babynames_test/yob" + year + "short.csv";
        FileResource fr = new FileResource(parsingFormat);

        // Part 2: Data analysis variable declaration
        int rankCounter = 1;
        int nameCount = 1;
        ///// Part 2.2: get number of total names filtred by the gender
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) nameCount ++;
        }
        System.out.println(nameCount);


        // Part 3: data analysis
        for (CSVRecord rec : fr.getCSVParser(false)){
            //Filter by gender
            if (!rec.get(1).equals(gender)) continue;

            if (rec.get(0).equals(name)) break;
            rankCounter ++;
        }
        // Part 4: return rank
        if (rankCounter == nameCount) return -1;
        else return rankCounter;
    }

    public String getName (int year, int rank, String gender) {
        //Part 1. Parsing string formatting
        String parsingFormat = "media/us_babynames_test/yob" + year + "short.csv";
        FileResource fr = new FileResource(parsingFormat);

        //Part 2. Data variables
        int rankCounter = 0;
        String name = null;

        for (CSVRecord rec : fr.getCSVParser(false)){
            //Filter by gender
            if (!rec.get(1).equals(gender)) continue;

            rankCounter ++;
            if (rankCounter == rank) {
                name = rec.get(0);
                break;
            }
        }
        if (name == null) return "NO NAME";
        else return name;
    }

    public void whatIsNameInYear (String name, int year, int newYear, String gender) {
        int rank = 0;
        String newName = null;
        String genderGenerator;
        rank = getRank(year, name, gender);
        newName = getName(year, rank, gender);
        if (gender.equals("F")) genderGenerator = "she";
        else genderGenerator = "he";
        System.out.println(name + ", born in " + year
                + ", would be " + newName + " if " + genderGenerator
                + " was born in " + newYear);
    }


    //**************************************************************//
    //Test methods
    public void testTotalBirths () {
        FileResource fr = new FileResource("media/us_babynames_by_year/yob2014.csv");
        totalBirths(fr);
    }
    public void testGetRank () {
        int year = 2012;
        String name = "Mason";
        String gender = "F";
        System.out.println("For " + name + " " + gender + " in " + year
                + ", the rank is " + getRank(year, name, gender));
    }
    public void testGetName () {
        int year = 2012;
        int rank = 4;
        String gender = "F";
        System.out.println("For rank " + rank + " " + gender + " in " + year
                + ", the name is " + getName(year, rank, gender));
    }
    public void testwhatIsNameInYear () {
        String name = "Mary";
        int year = 1880, newYear = 1959;
        String gender = "F";
        whatIsNameInYear();
    }
}
