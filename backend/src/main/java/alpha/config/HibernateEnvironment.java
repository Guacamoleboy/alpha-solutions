package alpha.config;

public class HibernateEnvironment {

    // Attributes
    private static Boolean isTest = false;

    // _________________________________________________________________________________________________________________

    public static void setTest(Boolean test) {
        isTest = test;
    }

    // _________________________________________________________________________________________________________________

    public static Boolean getTest() {
        return isTest;
    }

}