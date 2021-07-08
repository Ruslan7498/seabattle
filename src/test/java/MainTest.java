import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Random;


class MainTest {
    static final int SIZE = 10;
    static final char WATER = '*';
    static final char SHIP = '1';
    static final char DEAD = '2';
    static final char MISS = '0';
    static int coordinateX;
    static int coordinateY;
    static Random random = new Random();

    @BeforeAll
    static void beafoAll() {
        System.out.println("BeforeAll");
        coordinateX = random.nextInt(SIZE);
        coordinateY = random.nextInt(SIZE);
    }

    @Test
    void createFieldTeast() {
        char[][] excepted = {
                {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'},
                {'1', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
                {'2', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
                {'3', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
                {'4', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
                {'5', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
                {'6', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
                {'7', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
                {'8', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
                {'9', '*', '*', '*', '*', '*', '*', '*', '*', '*'}
        };
        //Создание поля
        char[][] actual = new char[SIZE][SIZE];
        char x = '0';
        char y = '0';
        // координаты x
        for (int i = 0; i < SIZE; i++) {
            actual[i][0] = x;
            x++;
        }
        // координаты y
        for (int j = 0; j < SIZE; j++) {
            actual[0][j] = y;
            y++;
        }
        // заполнение поля xy
        for (int i = 1; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                actual[i][j] = WATER;
            }
        }
        //Тестирование
        Assertions.assertArrayEquals(excepted, actual);
    }

    @Test
    void checkCoordinates() {
        if (coordinateX < 1 || coordinateX > 9 || coordinateY < 1 || coordinateY > 9) {
            Assertions.assertTrue(false, "Координаты за пределами поля боя!");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"ops", "error"})
    void mainExceptionTest(String argument) {
        Exception actual = Assertions.assertThrows(Exception.class,
                () -> Integer.parseInt(argument)
        );
        Assertions.assertEquals(NumberFormatException.class, actual.getClass());
        /*
        Assertions.assertDoesNotThrow(
                () ->  Integer.parseInt(inputCoordinate[1])
        );
         */
    }
/*
    @BeforeEach
    void  beafoEach() {
            System.out.println("BeforeEach");
    }

    @AfterEach
    void  afterEach() {
        System.out.println("AfterEach");
    }
 */
}