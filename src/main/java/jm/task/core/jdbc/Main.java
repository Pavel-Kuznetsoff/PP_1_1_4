package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

//        Создание таблицы User(ов)
        userService.createUsersTable();

//        Добавление 4 User(ов) в таблицу с данными на свой выбор.
//        После каждого добавления должен быть вывод в консоль
//        ( User с именем – name добавлен в базу данных )
        userService.saveUser("Иван", "Иванов", (byte) 18);
        userService.saveUser("Сергей", "Сергеев", (byte) 30);
        userService.saveUser("Алексей", "Алексеев", (byte) 25);
        userService.saveUser("Семен", "Семенов", (byte) 26);

//        Получение всех User из базы и вывод в консоль (должен быть переопределен toString в классе User)
        userService.getAllUsers().forEach(System.out::println);

//        Очистка таблицы User(ов)
        userService.cleanUsersTable();

//        Удаление таблицы
        userService.dropUsersTable();

        Util.closeConnection();
    }
}
