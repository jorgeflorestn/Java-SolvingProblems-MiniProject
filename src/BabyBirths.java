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
        newName = getName(newYear, rank, gender);
        if (gender.equals("F")) genderGenerator = "she";
        else genderGenerator = "he";
        System.out.println(name + ", born in " + year
                + ", would be " + newName + " if " + genderGenerator
                + " was born in " + newYear);
    }

    public int yearOfHighestRank (String name, String gender) {
        int rankYear = 2012;
        int rank1 = 500000;

        for (int i = 2012; i < 2015; i++) {
            int a = getRank(i, name, gender);
            if (a == -1) continue;
            if (a < rank1) rank1 = a;
        }
        if (rank1 == 500000) return -1;
        else return rankYear;
    }

    public double getAverageRank (String name, String gender) {
        double sum = 0, pivot;
        double numValidYear = 0;

        for (int i = 2012; i < 2015; i++) {
            pivot = (double) getRank(i, name, gender);
            if (pivot != -1) {
                sum += pivot;
                numValidYear ++;
            }
        }
        if (sum == 0) return -1.0;
        else return ((double) sum) / ((double) numValidYear);
    }
    public int getTotalBirthsRankedHigher (int year, String name, String gender) {
        int rank = getRank(year, name, gender);
        int totalBirths = 0;
        if (rank == -1) return -1;
        String parsingFormat = "media/us_babynames_test/yob" + year + "short.csv";
        FileResource fr = new FileResource(parsingFormat);

        for (CSVRecord rec : fr.getCSVParser(false)) {
            //Filter by gender
            if (!rec.get(1).equals(gender)) continue;

            //Rank
            String nName = rec.get(0), nGender = rec.get(1);
            if (getRank(year, nName, nGender) < rank) totalBirths += Integer.parseInt(rec.get(2));
        }
        return totalBirths;
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
    public void testWhatIsNameInYear () {
        String name = "Mary";
        int year = 1880, newYear = 1962;
        String gender = "F";
        whatIsNameInYear(name, year, newYear, gender);
    }
    public void testYearOfHighestRank () {
        String name = "Mason";
        String gender = "M";
        System.out.println(yearOfHighestRank(name, gender));
    }
    public void testGetAverageRank () {
        String name = "Jacob";
        String gender = "M";
        System.out.println(getAverageRank(name, gender));
    }
    public void testGetTotalBirthsRankedHigher () {
        int year = 2012;
        String name = "Ethan";
        String gender = "M";
        System.out.println(getTotalBirthsRankedHigher(year, name, gender));
    }
}
