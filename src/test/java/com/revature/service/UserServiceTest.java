package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.FailedLoginException;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  @Mock
  private UserDao mockDao;

  @InjectMocks
  private UserService systemUnderTest;

  @Test
  public void testUserLogin_Positive() throws FailedLoginException, SQLException {
    when(mockDao.getUserByLogin(eq("test"), eq("test")))
        .thenReturn(new User(99, "test", "test", "Jane", "Doe", "janedoe@test.com", "finance manager"));

    User actual = this.systemUnderTest.login("test", "test");
    User expected = new User(99, "test", "test", "Jane", "Doe", "janedoe@test.com", "finance manager");
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testUserLogin_NegativeLogin() throws FailedLoginException, SQLException {
  Assertions.assertThrows(FailedLoginException.class, () ->{
    this.systemUnderTest.login("test", "test");
  });
  }
}
