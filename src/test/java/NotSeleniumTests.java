import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NotSeleniumTests {

    public static final boolean TC2B = true;
    public static final boolean TC3 = true;

    @Test(enabled = TC2B)
    public void verifyToppingsAlphabetFromAssignment() {
        String[] expectedValues =  getValuesFromList("DataFiles/" + Data.fileFlavors + ".csv");
        for (int i = 0; i < expectedValues.length-1; i++) {
            // current String is > than the next one (if there are equal list is still sorted)
            if (expectedValues[i].compareToIgnoreCase(expectedValues[i + 1]) > 0) {
                Assert.fail("List of toppings from assignment is not listed alphabetically");
            }
        }

    }


    @Test(enabled = TC3)
    public void verifySaucesAlphabetFromAssignment() {
        String[] expectedValues =  getValuesFromList("DataFiles/"  + Data.fileSauces + ".csv");
        for (int i = 0; i < expectedValues.length-1; i++) {
            // current String is > than the next one (if there are equal list is still sorted)
            if (expectedValues[i].compareToIgnoreCase(expectedValues[i + 1]) > 0) {
                Assert.fail("List of sauces from assignment is not listed alphabetically");
            }
        }

    }




    public String[] getValuesFromList(String nameOfFile){
        String[] expectedValues = null;
        try {
            expectedValues = Files.readAllLines(Paths.get(nameOfFile)).stream().toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expectedValues;
    }

}
