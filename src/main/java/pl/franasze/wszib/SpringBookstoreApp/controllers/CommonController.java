package pl.franasze.wszib.SpringBookstoreApp.controllers;


import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.franasze.wszib.SpringBookstoreApp.model.Book;
import pl.franasze.wszib.SpringBookstoreApp.services.IBookService;
import pl.franasze.wszib.SpringBookstoreApp.session.SessionObject;

@Controller
public class CommonController {

    @Autowired
    private IBookService bookService;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(path = {"/", "/main", "index"}, method = RequestMethod.GET)
    public String main(Model model) {
        model.addAttribute("books", this.bookService.getAll());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "index";
    }


    @RequestMapping(path = "/booksSearch", method = RequestMethod.GET)
    public String booksSearch(Model model) {
        model.addAttribute("books", this.bookService.getAll());
        model.addAttribute("pattern", "");
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "booksSearch";
    }

    @RequestMapping(path = "/booksSearch", method = RequestMethod.POST)
    public String booksSearch(@RequestParam("pattern") String pattern, Model model){
        model.addAttribute("isLogged",
                this.sessionObject.isLogged());
        model.addAttribute("books", bookService.getByPattern(pattern));
        return "booksSearch";
    }



    @RequestMapping(path = "/borrowedBooksList", method = RequestMethod.GET)
    public String listBooks(Model model) {
        model.addAttribute("borrowedBooks", this.bookService.getAll());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "borrowedBooksList";
    }

    @RequestMapping(path = "/afterDeadlineList", method = RequestMethod.GET)
    public String listBooksAfterDeadline(Model model) {
        model.addAttribute("books", this.bookService.getAll());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "afterDeadlineList";
    }

    @RequestMapping(path ="/addBook", method = RequestMethod.GET)
    public String addBook(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "addBook";
    }

    @RequestMapping(path ="/addBook", method = RequestMethod.POST)
    public String addBook(@ModelAttribute Book book, Model model){
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        bookService.persist(book);
        return "redirect:/main";
    }




}
