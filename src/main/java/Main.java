import java.util.Scanner;
import java.util.Random;

public class Main {
    public static final int SIZE = 10;
    public static final int MAX_COUNT = 10;
    public static final int MAX_SHOT = 30;
    public static final char WATER = '*';
    public static final char SHIP = '1';
    public static final char DEAD = '2';
    public static final char MISS = '0';
    public static Random random = new Random();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) {
        int shotCount = MAX_SHOT; // счетчик выстрелов
        int deadCount = 0; // счетчик уничтоженых кораблей
        char[][] warField = createField(); // создание поля боя
        char[][] shipField = createField(); // создание поля для кораблей
        shipField = createShip(shipField); // размещение кораблей на поле
        rulesGame();
        printArray(warField);
        System.out.println();
        while (true) {
            try {
                boolean motions; // ход игрока
                System.out.println("Количество выстрелов: " + shotCount + "\n" +
                        "Количество уничтоженных кораблей: " + deadCount + "\n" +
                        "Введите координаты для атаки (\"q\" - выход):");
                String input = scanner.nextLine();
                if ("q".equals(input)) {
                    System.out.println("Количество уничтоженных кораблей: " + deadCount);
                    break;
                }
                String[] inputCoordinate = input.split(" ");
                int coordinateX = Integer.parseInt(inputCoordinate[0]);
                int coordinateY = Integer.parseInt(inputCoordinate[1]);
                motions = checkCoordinates(warField, coordinateX, coordinateY);
                if (motions) continue;
                motions = checkHit(shipField, coordinateX, coordinateY);
                shotCount--;
                if (motions) {
                    System.out.println("Корабль уничтожен!\n");
                    warField[coordinateX][coordinateY] = DEAD;
                    deadCount++;
                } else {
                    System.out.println("Промах!\n");
                    warField[coordinateX][coordinateY] = MISS;
                }
                printArray(warField);
                System.out.println();

                if (deadCount == MAX_COUNT) {
                    System.out.println("Победа! Все корабли уничтожены!");
                    break;
                }
                if (shotCount == 0) {
                    System.out.println("Выстрелы кончились! Вы проиграли!");
                    System.out.println("Количество уничтоженных кораблей: " + deadCount);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введены некорректные данные");
            }
        }
        warField = uniteField(warField, shipField);
        printArray(warField);
    }

    public static char[][] createField() {

        char[][] field = new char[SIZE][SIZE];
        char x = '0';
        char y = '0';
        // координаты x
        for (int i = 0; i < SIZE; i++) {
            field[i][0] = x;
            x++;
        }
        // координаты y
        for (int j = 0; j < SIZE; j++) {
            field[0][j] = y;
            y++;
        }
        // заполнение поля xy
        for (int i = 1; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                field[i][j] = WATER;
            }
        }
        return field;
    }

    public static char[][] createShip(char[][] shipField) {
        int shipCount = 0; // счетчик кораблей
        while (true) {
            int coordinateX = random.nextInt(SIZE);
            int coordinateY = random.nextInt(SIZE);
            if (coordinateX == 0 || coordinateY == 0 ||
                    shipField[coordinateX][coordinateY] == SHIP) continue;
            shipCount++;
            shipField[coordinateX][coordinateY] = SHIP;
            if (shipCount == MAX_COUNT) break;
        }
        return shipField;
    }

    public static boolean checkCoordinates(char[][] warField, int coordinateX, int coordinateY) {
        if (coordinateX < 1 || coordinateX > 9 || coordinateY < 1 || coordinateY > 9) {
            System.out.println("Координаты за пределами поля боя!");
            return true;
        }
        if (warField[coordinateX][coordinateY] == DEAD ||
                warField[coordinateX][coordinateY] == MISS) {
            System.out.println("Атака по этим координатам проводилась!");
            return true;
        }
        return false;
    }

    public static boolean checkHit(char[][] shipField, int coordinateX, int coordinateY) {
        return shipField[coordinateX][coordinateY] == SHIP ? true : false;
    }

    public static void printArray(char[][] field) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }

    public static char[][] uniteField(char[][] warField, char[][] shipField) {
        for (int i = 1; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                if (warField[i][j] == WATER)
                    warField[i][j] = shipField[i][j];
            }
        }
        return warField;
    }

    public static void rulesGame() {
        System.out.println("\nигра \"Морской бой\"\n" +
                "Введите координаты атаки в формате: \"x y\", где\n" +
                "х - координата строки, у - координата столбца\n" +
                "0 - промах (пустое поле) \n" +
                "1 - месторасположение корабля\n" +
                "2 - корабль уничтожен\n" +
                "\"q\" - выход\n");
    }
}
