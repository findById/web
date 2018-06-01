//package org.cn.web.rbac;
//
//import com.cn.web.rbac.dao.UserDao;
//import com.cn.web.rbac.domain.User;
//import com.cn.web.rbac.util.AuthUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UserTest {
//
//    @Autowired
//    UserDao userDao;
//
//    @Test
//    public void save() {
//        String password = AuthUtils.initPassword(6);
//        System.out.println("password: " + password);
//        User item = new User();
//        item.setEmail("admin@admin.com");
//        item.setPassword(password);
//        userDao.save(item);
//    }
//
//}
