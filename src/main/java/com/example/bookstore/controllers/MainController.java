package com.example.bookstore.controllers;


import com.example.bookstore.DAO.impl.*;
import com.example.bookstore.models.Users;
import com.example.bookstore.models.Books;
import com.example.bookstore.models.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import com.example.bookstore.DAO.UsersDAO;
import com.example.bookstore.DAO.BooksDAO;
import com.example.bookstore.DAO.OrdersDAO;

//import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Objects;

@Controller
@SessionAttributes({
        "nickname",
        "password"
})
public class MainController {
    @Autowired
    private OrdersDAO OrdersDAO = new OrdersDAOImpl();
    @Autowired
    private UsersDAO UsersDAO = new UsersDAOImpl();
    @Autowired
    private BooksDAO BooksDAO = new BooksDAOImpl();
    @RequestMapping(value = { "/", "/index"})
    public String index (ModelMap model) {
        model.remove("userStatus");
        if (!model.containsAttribute("nickname") || !model.containsAttribute("password") ||
                !UsersDAO.checkHashPassword(
                        model.getAttribute("nickname").toString(),
                        model.getAttribute("password").toString())) {
            model.addAttribute("userStatus", "unknown");
        } else {
            if (UsersDAO.checkStatus(model.getAttribute("nickname").toString())) {
                model.addAttribute("userStatus", "admin");
            } else {
                model.addAttribute("userStatus", "user");
            }
        }
        return "index";
    }
    @RequestMapping(value = "/login")
    public String login(ModelMap model) {
        return "login";
    }
    @PostMapping(value = "/login")
    public String loginPost(@RequestParam String nickname,
                               @RequestParam String password,
                               ModelMap model) {
        if (UsersDAO.checkHashPassword(nickname, password)) {
            model.addAttribute("nickname", nickname);
            model.addAttribute("password", password);
            return "redirect:/";
        }
        else {
            model.addAttribute("error_msg", "Неверное имя пользователя или пароль.");
            return "errorPage";
        }
    }

    @PostMapping(value = "/logout")
    public String logoutPost(ModelMap model) {
        model.remove("userStatus");
        model.remove("nickname");
        model.remove("password");
        return "index";
    }


    @GetMapping("/books")
    public String booksListPage(ModelMap model, String keyword) {
        model.remove("userStatus");
        if (!model.containsAttribute("nickname") || !model.containsAttribute("password") ||
                !UsersDAO.checkHashPassword(
                        model.getAttribute("nickname").toString(),
                        model.getAttribute("password").toString())) {
            model.addAttribute("userStatus", "unknown");
        } else {
            if (UsersDAO.checkStatus(model.getAttribute("nickname").toString())) {
                model.addAttribute("userStatus", "admin");
            } else {
                model.addAttribute("userStatus", "user");
            }
        }
        if (keyword != null) {
            List<Books> books =
                    new ArrayList<Books> ();
            if (BooksDAO.getAllBooksByName(keyword) != null) {
                books.addAll(BooksDAO.getAllBooksByName(keyword));
            }
            if (BooksDAO.getAllBooksByAuthor(keyword) != null) {
                books.addAll((ArrayList<Books>) BooksDAO.getAllBooksByAuthor(keyword));
            }

            List<Books> uniqueObjects = new ArrayList<Books>();
            if (books != null) {
                for (Books i : books) {
                    boolean to_add = true;
                    for (Books j : uniqueObjects) {
                        if (j != null && i.getId() == j.getId()) {
                            to_add = false;
                        }
                    }
                    if (to_add) {
                        uniqueObjects.add(i);
                    }
                }
            }

            model.addAttribute("books", uniqueObjects);
        } else {
            List<Books> books = (List<Books>) BooksDAO.getAll();
            model.addAttribute("books", books);
        }
        return "books";
    }
    @PostMapping(value = "/addBooks")
    public String addBooksPost(@RequestParam(name = "bookid") Long bookId, @RequestParam Integer numberToAdd,
                            ModelMap model) {
        if (!model.containsAttribute("nickname") || !model.containsAttribute("password") ||
                !UsersDAO.checkHashPassword(
                        model.getAttribute("nickname").toString(),
                        model.getAttribute("password").toString())) {
            model.addAttribute("error_msg", "Не хватает прав");
            return "errorPage";
        } else {
            if (BooksDAO.addBookCopies(UsersDAO.getUserByNickname(model.getAttribute("nickname").toString()),
                    BooksDAO.getById(bookId), numberToAdd)) {
                return "redirect:/books";
            } else {
                model.addAttribute("error_msg", "Не хватает прав");
                return "errorPage";
            }
        }
    }
}