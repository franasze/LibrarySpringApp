package pl.franasze.wszib.SpringBookstoreApp.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.franasze.wszib.SpringBookstoreApp.dao.IOrderDAO;
import pl.franasze.wszib.SpringBookstoreApp.dao.IUserDAO;
import pl.franasze.wszib.SpringBookstoreApp.model.BorrowBook;
import pl.franasze.wszib.SpringBookstoreApp.model.User;


import java.util.Optional;

@Controller
public class TestController {

    @Autowired
    IUserDAO userDAO;

    @Autowired
    IOrderDAO orderDAO;

    @RequestMapping(path = "/test/addUser", method = RequestMethod.GET)
    public String addUser() {
        User user = new User();
        user.setLogin("admin");
        user.setPassword(DigestUtils.md5Hex("admin123"));

        this.userDAO.persist(user);
        return "redirect:/main";
    }

    @RequestMapping(path = "/test/getOrder", method = RequestMethod.GET)
    public String getOrder() {
        Optional<BorrowBook> order = orderDAO.getOrderById(6);
        System.out.println(order.get().getUser());

        return "redirect:/main";
    }

}
