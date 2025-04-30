package test.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit teszt osztály a Fungorium játék termináljának tesztelésére.
 * A tesztek bemeneti parancsokat olvasnak fájlból, lefuttatják azokat
 * a Terminal objektumon, majd összehasonlítják az elvárt és a kapott játékmenetet.
 */

public class FungoriumTest
{
	 /** A fájlból beolvasott bemeneti parancsok listája. */
    List<String> inputCommands;
    /** A ténylegesen létrejött játékmenet állapot sorai. */
    List<String> actualGameState;
    /** A fájlból beolvasott, elvárt játékmenet állapot sorai. */
    List<String> expectedGameState;
    /** A tesztelt Terminal példány. */
    Terminal terminal;

    String testLocation = System.getProperty("user.dir") + "/src/test/java/Tesztek";
    /**
     * Teszt inicializálás. Minden teszteset előtt létrehoz egy új Terminal objektumot,
     * valamint inicializálja a listákat.
     *
     * @throws IOException ha a terminál inicializálása közben hiba történik.
     */
    @BeforeEach
    public void init() throws IOException {
        terminal = new Terminal(true, 3);
        inputCommands = new ArrayList<>();
        actualGameState = new ArrayList<>();
        expectedGameState = new ArrayList<>();
    }

    /**
     * A teszt futása után összehasonlítja a létrejött és az elvárt állapotot.
     * Ha eltérés van, a teszt megbukik.
     */
    @AfterEach
    public void compareStates() {
        assertEquals(expectedGameState, actualGameState);
    }

    /**
     * Próba
     *
     * @throws IOException ha a fájlok olvasása közben hiba történik.
     */
    /*@Test
    public void Test1() throws IOException {
        runTestWith(testLocation + "\\test1\\input.txt"
        		, testLocation + "\\test1\\expected.txt");
    }*/
    
    /**
     * Gyorsító spóra elhelyezése szomszédos tektonra
     *
     * @throws IOException ha a fájlok olvasása közben hiba történik.
     */
    @Test
    public void Test2() throws IOException {
        runTestWith(testLocation + "\\test2\\input.txt"
        		, testLocation + "\\test2\\expected.txt");
    }
    
    /**
     * Lassító spóra elhelyezése szomszédos tektonra
     *
     * @throws IOException ha a fájlok olvasása közben hiba történik.
     */
    @Test
    public void Test3() throws IOException {
        runTestWith(testLocation + "\\test3\\input.txt"
        		, testLocation + "\\test3\\expected.txt");
    }
    
    /**
     * Bénító spóra elhelyezése szomszédos tektonra
     *
     * @throws IOException ha a fájlok olvasása közben hiba történik.
     */
    @Test
    public void Test4() throws IOException {
        runTestWith(testLocation + "\\test4\\input.txt"
        		, testLocation + "\\test4\\expected.txt");
    }
    
    /**
     * Szelíd spóra elhelyezése szomszédos tektonra
     *
     * @throws IOException ha a fájlok olvasása közben hiba történik.
     */
    @Test
    public void Test5() throws IOException {
        runTestWith(testLocation + "\\test5\\input.txt"
        		, testLocation + "\\test5\\expected.txt");
    }
    
    /**
     * Sokszorozó spóra elhelyezése szomszédos tektonra
     *
     * @throws IOException ha a fájlok olvasása közben hiba történik.
     */
    @Test
    public void Test6() throws IOException {
        runTestWith(testLocation + "\\test6\\input.txt"
        		, testLocation + "\\test6\\expected.txt");
    }
    
    /**
     * Teljesen kifejlett gomba letétele a játék elején
     *
     * @throws IOException ha a fájlok olvasása közben hiba történik.
     */
    @Test
    public void Test7() throws IOException {
        runTestWith(testLocation + "\\test7\\input.txt"
        		, testLocation + "\\test7\\expected.txt");
    }

    /**
     * Gombafonal növesztése 2 tekton között
     *
     * @throws IOException ha a fájlok olvasása közben hiba történik.
     */
    @Test
    public void Test8() throws IOException {
        runTestWith(testLocation + "\\test8\\input.txt"
                , testLocation + "\\test8\\expected.txt");
    }

    /**
     * Gombatest növesztése nem száraz tektonon lassító spórákból
     *
     * @throws IOException ha a fájlok olvasása közben hiba történik.
     */
    @Test
    public void Test9() throws IOException {
        runTestWith(testLocation + "\\test9\\input.txt"
                , testLocation + "\\test9\\expected.txt");
    }

    /**
     * Gombatest növesztése nem száraz tektonon gyorsító spórákból
     *
     * @throws IOException ha a fájlok olvasása közben hiba történik.
     */
    @Test
    public void Test10() throws IOException {
        runTestWith(testLocation + "\\test10\\input.txt"
                , testLocation + "\\test10\\expected.txt");
    }

    /**
     * Gombatest növesztése nem száraz tektonon szelíd spórákból
     *
     * @throws IOException ha a fájlok olvasása közben hiba történik.
     */
    @Test
    public void Test11() throws IOException {
        runTestWith(testLocation + "\\test11\\input.txt"
                , testLocation + "\\test11\\expected.txt");
    }

    /**
     * Gombatest növesztése nem száraz tektonon bénító spórákból
     *
     * @throws IOException ha a fájlok olvasása közben hiba történik.
     */
    @Test
    public void Test12() throws IOException {
        runTestWith(testLocation + "\\test12\\input.txt"
                , testLocation + "\\test12\\expected.txt");
    }
    
    /**
     * Az insect átlép egy gombafonalon keresztül egy másik tectonra.
     * 
     * @throws IOException ha a fileok olvasása közben hiba történik
     */
    @Test
    public void Test13() throws IOException {
    	runTestWith(testLocation + "\\test13\\input.txt",
    			testLocation + "\\test13\\expected.txt");
    }
    
    /**
     * Az insect elvág egy gombafonalat két tekton között
     * 
     * @throws IOException ha a fileok olvasása közben hiba történik
     */
    @Test
    public void Test14() throws IOException {
    	runTestWith(testLocation + "\\test14\\input.txt",
    			testLocation + "\\test14\\expected.txt");
    }
    
    /**
     * Az insect elfogyaszt egy gyorsító spórát
     * 
     * @throws IOException ha a fileok olvasása közben hiba történik
     */
    @Test
    public void Test15() throws IOException {
    	runTestWith(testLocation + "\\test15\\input.txt",
    			testLocation + "\\test15\\expected.txt");
    }
    
    /**
     * Az insect elfogyaszt egy lassító spórát
     * 
     * @throws IOException ha a fileok olvasása közben hiba történik
     */
    @Test
    public void Test16() throws IOException {
    	runTestWith(testLocation + "\\test16\\input.txt",
    			testLocation + "\\test16\\expected.txt");
    }

    /**
     * Az insect elfogyaszt egy bénító spórát
     *
     * @throws IOException ha a fileok olvasása közben hiba történik
     */
    @Test
    public void Test17() throws IOException {
        runTestWith(testLocation + "\\test17\\input.txt",
                testLocation + "\\test17\\expected.txt");
    }

    /**
     * Az insect elfogyaszt egy szelíd spórát
     *
     * @throws IOException ha a fileok olvasása közben hiba történik
     */
    @Test
    public void Test18() throws IOException {
        runTestWith(testLocation + "\\test18\\input.txt",
                testLocation + "\\test18\\expected.txt");
    }
    
    @Test
    public void Test19() throws IOException {
        runTestWith(testLocation + "\\test19\\input.txt",
                testLocation + "\\test19\\expected.txt");
    }

    @Test
    public void Test20() throws IOException {
        runTestWith(testLocation + "\\test20\\input.txt",
                testLocation + "\\test20\\expected.txt");
    }
    
    @Test
    public void Test21() throws IOException {
        runTestWith(testLocation + "\\test21\\input.txt",
                testLocation + "\\test21\\expected.txt");
    }
    
    @Test
    public void Test22() throws IOException {
        runTestWith(testLocation + "\\test22\\input.txt",
                testLocation + "\\test22\\expected.txt");
    }
    
    @Test
    public void Test23() throws IOException {
        runTestWith(testLocation + "\\test23\\input.txt",
                testLocation + "\\test23\\expected.txt");
    }
    
    @Test
    public void Test24() throws IOException {
        runTestWith(testLocation + "\\test24\\input.txt",
                testLocation + "\\test24\\expected.txt");
    }
    /**
     * Gyógyító tektonon gombatesthez nem tartozó gombafonal életben marad
     *
     * @throws IOException ha a fileok olvasása közben hiba történik
     */
    @Test
    public void Test25() throws IOException {
        runTestWith(testLocation + "\\test25\\input.txt",
                testLocation + "\\test25\\expected.txt");
    }
    /**
     * Gombafonal bekebelez egy bénult rovart és gombatestet növeszt
     *
     * @throws IOException ha a fileok olvasása közben hiba történik
     */
    @Test
    public void Test26() throws IOException {
        runTestWith(testLocation + "\\test26\\input.txt",
                testLocation + "\\test26\\expected.txt");
    }
    /**
     * Sokszorozó spóra lehelyezése szomszédos tektonra
     *
     * @throws IOException ha a fileok olvasása közben hiba történik
     */
    @Test
    public void Test27() throws IOException {
        runTestWith(testLocation + "\\test27\\input.txt",
                testLocation + "\\test27\\expected.txt");
    }
    /**
     * A gombász egy nem száraz tektonon új gombatest növesztését kezdeményezi
     * sokszorozó spórákból
     *
     * @throws IOException ha a fileok olvasása közben hiba történik
     */
    @Test
    public void Test28() throws IOException {
        runTestWith(testLocation + "\\test28\\input.txt",
                testLocation + "\\test28\\expected.txt");
    }
    /**
     * Az insect elfogyaszt egy sokszorozó spórát
     *
     * @throws IOException ha a fileok olvasása közben hiba történik
     */
    @Test
    public void Test29() throws IOException {
        runTestWith(testLocation + "\\test29\\input.txt",
                testLocation + "\\test29\\expected.txt");
    }
    /**
     * Gombafonal elhalása mérgező tektonon
     *
     * @throws IOException ha a fileok olvasása közben hiba történik
     */
    @Test
    public void Test30() throws IOException {
        runTestWith(testLocation + "\\test30\\input.txt",
                testLocation + "\\test30\\expected.txt");
    }

    /**
     * Teszt végrehajtása megadott bemeneti és elvárt állapot fájl alapján.
     * A metódus beolvassa a fájlokat, feldolgozza a parancsokat, majd lekéri az aktuális állapotot.
     *
     * @param input a bemeneti parancsokat tartalmazó fájl elérési útja
     * @param output az elvárt játékmenetet tartalmazó fájl elérési útja
     * @throws IOException ha bármelyik fájl olvasása közben hiba történik
     */
    public void runTestWith(String input, String output) throws IOException {
        inputCommands = readLinesFromFile(input);
        expectedGameState = readLinesFromFile(output);
        for (String command : inputCommands) {
            terminal.processCommand(command, terminal.gc.getPlanet());
        }
        actualGameState = terminal.getStateLines().stream().map(String::trim).toList();
    }

    /**
     * Segédfüggvény egy fájl sorainak beolvasására.
     * Minden sort levág a szóközökről és listába gyűjt.
     *
     * @param filePath a fájl elérési útja
     * @return a beolvasott sorok listája
     * @throws IOException ha a fájl olvasása közben hiba történik
     */
    private List<String> readLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine().trim());
            }
        }
        return lines;
    }
}