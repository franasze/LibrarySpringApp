package pl.franasze.wszib.SpringBookstoreApp.controllers;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.franasze.wszib.SpringBookstoreApp.model.Book;
import pl.franasze.wszib.SpringBookstoreApp.model.BorrowBook;
import pl.franasze.wszib.SpringBookstoreApp.services.ICartService;
import pl.franasze.wszib.SpringBookstoreApp.session.SessionObject;

@Controller
public class CartController {

    @Autowired
    ICartService cartService;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(path = "/book/add/{bookId}", method = RequestMethod.GET)
    public String addBook(@PathVariable final int bookId) {
        this.cartService.addBookToCart(bookId);
        return "redirect:/my";
    }

    @RequestMapping(path = "/my", method = RequestMethod.GET)
    public String cart(Model model) {
        model.addAttribute("order", new BorrowBook());
        model.addAttribute("my", this.sessionObject.getCart());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "my";
    }


    @RequestMapping(path ="/my", method = RequestMethod.POST)
    public String submitForm(@ModelAttribute BorrowBook order) {
        if (this.cartService.order(order.getFirstName(), order.getLastName())){
            return "redirect:/main";
        }
        return "redirect:/my";
    }



}
