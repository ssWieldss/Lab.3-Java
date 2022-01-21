package Lab3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Controller {

    private static String USER = "";
    private static String PASSWORD = "";
    private static boolean ROOT = false;
    private static boolean DEBUG = false;
    private static boolean TEST = false;
    static Logger LOGGER;

    static {
        try (FileInputStream ins = new FileInputStream("C:\\Users\\egor1\\IdeaProjects\\Lab.3\\" +
                "src\\config\\log.config")) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Controller.class.getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void Start() throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(new File("C:\\Users\\egor1\\IdeaProjects\\Lab.3\\src\\" +
                "config\\properties.ini")));
        USER = props.getProperty("USER");
        PASSWORD = props.getProperty("PASSWORD");
        ROOT = Boolean.parseBoolean(props.getProperty("ROOT"));
        DEBUG = Boolean.parseBoolean(props.getProperty("DEBUG"));
        TEST = Boolean.parseBoolean(props.getProperty("TEST"));
    }


    public static void Enter() {
        String log_pass = View.Input_Start_Data();
        while (!(USER + " " + PASSWORD).equals(log_pass)) {
            View.out("Ошибка входа! Проверьте правильность набранных данных");
            if (DEBUG)
                LOGGER.log(Level.WARNING, "Попытка входа в аккаунт " + USER);
            log_pass = View.Input_Start_Data();
        }
    }


    public static void Process() {

        ArrayList<ArrayList> Base = new ArrayList<>(4);
        Base = Model.Create_Null_Base(Base);

        //Reading for configuration file

        try {
            Start();
            if (DEBUG)
                LOGGER.log(Level.INFO, "Запуск программы");
        } catch (IOException ex) {
            View.out("Не удалось загрузить файл конфигурации");
            if (DEBUG)
                LOGGER.log(Level.WARNING, "Не удалось загрузить файл конфигурации");
        }

        //Enter into the account

        Enter();

        if (DEBUG)
            LOGGER.log(Level.INFO, "Выполнен вход в аккаунт " + USER);
        if (ROOT && TEST) {
            View.out("AUTO_TESTS");
        } else if (ROOT) {
            View.out("Здравствуйте, Хозяин!");
        } else {
            View.out("Добро пожаловать " + USER);
        }

        int n = 0;

        do {
            View.Menu_Out();
            n = View.Number();

            switch (n) {
                case 1 -> {
                    try {
                        Base = Model.ReadFile();
                        View.out("Файл успешно загружен");

                        if (DEBUG)
                            LOGGER.log(Level.INFO, "Файл успешно загружен");
                    } catch (IOException ex) {
                        View.out("Возникли ошибки при чтении файла");
                        if (DEBUG)
                            LOGGER.log(Level.WARNING, "Возникли ошибки при чтении файла");
                    }
                    catch (NoSuchElementException e) {
                        View.out("В файле нет элементов!");
                        if (DEBUG)
                            LOGGER.log(Level.WARNING, "Возникли ошибки при чтении файла");
                    }
                }
                case 2 -> {
                    try {
                        String TextFile;
                        TextFile = Model.ToFile(Base.get(0), Base.get(1), Base.get(2), Base.get(3));

                        boolean res = Model.SaveFile("C:\\Users\\egor1\\IdeaProjects" +
                                "\\Lab.3\\src\\files\\Base.txt", TextFile);

                        if (res) {
                            View.out("Файл успешно сохранен");
                            if (DEBUG)
                                LOGGER.log(Level.INFO, "Файл успешно сохранен");
                        } else {
                            View.out("Возникли ошибки при сохранении");
                            if (DEBUG)
                                LOGGER.log(Level.WARNING, "Возникли ошибки при сохранении");
                        }
                    } catch (IOException ex) {
                        View.out("Возникли ошибки при сохранении");
                        if (DEBUG)
                            LOGGER.log(Level.WARNING, "Возникли ошибки при сохранении");
                    }
                }

                case 3 -> View.Print_Base(Base);

                case 4 -> {

                    int number;
                    number = View.Menu_Add();
                    Base = Model.Add_New_Element(Base, number);

                    if (DEBUG)
                        LOGGER.log(Level.INFO, "В базу был добавлен новый элемент");

                }

                case 5 -> {

                    int number;
                    number = View.Menu_Delete();
                    Base = Model.Delete_Element(Base, number);
                    if (DEBUG)
                        LOGGER.log(Level.INFO, "Из базы был удален элемент");
                }
            }

        }
        while (n != 6);
    }
}

