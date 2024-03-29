package com.SafeWebDev.attempt.Controllers;

import java.security.SecureRandom;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.SafeWebDev.attempt.Models.*;
import com.SafeWebDev.attempt.Models.Services.CommentService;
import com.SafeWebDev.attempt.Models.Services.CuponService;
import com.SafeWebDev.attempt.Models.Services.ItemService;
import com.SafeWebDev.attempt.Models.Services.UserDetailsServiceImpl;
import com.SafeWebDev.attempt.Models.Services.UserService;

import org.checkerframework.checker.units.qual.A;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ControllerBasic {

    @Autowired
    private CuponService cuponService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    PasswordEncoder encoder;


    User currentUser;


    @PostConstruct
    public void init(){


        userDetailsService.saveAdmin(new User("admin", "admin@admin", encoder.encode("1234"), "admin", "admin"));
    }


    @GetMapping("")     //redirect to StartPage.html, the main page
    public String homePage(Model model, HttpServletRequest request) {

        if(request.getUserPrincipal() == null){
            model.addAttribute("user", false);
            model.addAttribute("not", true);
            log.info("No iniciado sesion");
        }else{

            User user = userService.findByOnlyName(request.getUserPrincipal().getName());
            if(user.getRole() == RoleName.ADMIN){
                model.addAttribute("user", true);
                model.addAttribute("not", false);
            }else{
                model.addAttribute("user", true);
                model.addAttribute("not", false);
            }
            log.info("User: {}",  request.getUserPrincipal().getName());
        }

        return "StartPage";
    }

    @GetMapping("/item/edit/{id}")  //edit an item
    public String updateItem(Model model,@PathVariable long id,HttpServletRequest request){
        if(itemService.findById(id).canEdit(request.getUserPrincipal().getName())){
            model.addAttribute("item", itemService.findById(id));
            log.info("Item found, loading page");
            return "ItemEdit";
        }else{
            log.info("User did not have permission to edit the item");
            return "LogIn";
        }

    }

    @PostMapping("/editting/{id}")  //edit an item
    public String updatingItem(Model model,@PathVariable long id, Item item,HttpServletRequest request){
        if(itemService.findById(id).canEdit(request.getUserPrincipal().getName())){
            Item aux = itemService.findById(id);
            aux.update(item);
            itemService.add(aux);
            return "ItemEdited";
        }else{
            return "LogIn";
        }
    }

    @GetMapping("/item/{id}")   //redirect to ItemPage.html, where you can see the info of one item
    public String itemPage(Model model, @PathVariable long id, HttpServletRequest request) {
        model.addAttribute("item", itemService.findById(id));
        if(request.getUserPrincipal()==null){
            model.addAttribute("not",false);
        }else{
            model.addAttribute("not",true);
            model.addAttribute("edit", itemService.findById(id).canEdit(request.getUserPrincipal().getName())||userService.findByOnlyName(request.getUserPrincipal().getName()).getRole()==RoleName.ADMIN);
        }


        return "ItemPage";
    }

    @GetMapping("/usr") //redirect to UsrPage.html with your usr info (right now just the admin user)
    public String usrPage(Model model, HttpServletRequest request) {
        log.info("User: {}", request.getUserPrincipal().getName());
        String name = request.getUserPrincipal().getName();
        User user = userService.findByOnlyName(name);
        model.addAttribute("user", user);
        return "UsrPage";

    }

    @GetMapping("/register_form")
    public String registerForm(){
        return "CreateAccount";
    }

    @GetMapping("/coupon_form")
    public String couponForm(){
        return "NewCoupon";
    }

    @GetMapping("/item_form")
    public String itemForm(){
        return "NewItem";
    }

    @PostMapping("/item/new")   //redirect to ItemAdded.html after adding an item to our general List
    public String addItem(Model model,Item item, HttpServletRequest request){
        item.setOwner(request.getUserPrincipal().getName());
        itemService.add(item);
        return "ItemAdded";
    }

    @GetMapping("/items")   //redirect to ItemsList.html, where you can see every product aviable
    public String listaItems(Model model, HttpServletRequest request){
        model.addAttribute("items", itemService.getAll());
        if(request.getUserPrincipal() == null){
            model.addAttribute("normal", false);
            model.addAttribute("user", false);
            model.addAttribute("not", true);
        }else{
            model.addAttribute("normal", true);
            User user = userService.findByOnlyName(request.getUserPrincipal().getName());
            if(user.getRole() == RoleName.ADMIN){
                model.addAttribute("admin", true);
                model.addAttribute("user", true);
                model.addAttribute("not", false);
            }else{
                model.addAttribute("admin", false);
                model.addAttribute("user", true);
                model.addAttribute("not", false);
            }
        }
        return "ItemsList";
    }

    @GetMapping("/item/del/{id}")   //delete an item
    public String delFromList(@PathVariable int id,HttpServletRequest request){
        if(itemService.findById(id).canEdit(request.getUserPrincipal().getName()) ||  userService.findByOnlyName(request.getUserPrincipal().getName()).getRole()==RoleName.ADMIN){
            itemService.delete(itemService.findById(id));
            return "ItemDeletedCompletely";
        }else{
            return "LogIn";
        }


    }

    @GetMapping("/cart")    //redirect to Cart.html, with your cart info
    public String carrito(Model model,HttpServletRequest request){
        model.addAttribute("aviso", userService.findByOnlyName(request.getUserPrincipal().getName()).getCart().size()==0);
        model.addAttribute("cart", userService.findByOnlyName(request.getUserPrincipal().getName()).getCart());
        return "Cart";
    }

    @GetMapping("/cart/{id}")   //redirect to CartAdded.html or CartAlreadyContains.html
    public String addCarrito(Model model, @PathVariable long id,HttpServletRequest request){
        if(userService.findByOnlyName(request.getUserPrincipal().getName()).getCart().contains(itemService.findById(id))){
            return "CartAlreadyContains";
        }else{
            User user=userService.findByOnlyName(request.getUserPrincipal().getName());
            user.addCart(itemService.findById(id));
            userService.saveUser(user);
            return "CartAdded";
        }
    }

    @GetMapping("/cart/del/{id}")   //redirect to ItemDeleted.html, to confirm the item was deleted
    public String deleteItem(@PathVariable int id,HttpServletRequest request){
        User user=userService.findByOnlyName(request.getUserPrincipal().getName());
        user.delCart(id-1);
        userService.saveUser(user);
        return "ItemDeleted";
    }

    @GetMapping("/login")   //redirect to LogIn.html, where you'll be able to log in
    public String logIn(Model model) {
        model.addAttribute("aviso", "");
        return "LogIn";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "StartPage";
    }

    @PostMapping("/account/created")
    public String createdAccount(Model model, User user){
        if(userService.findByOnlyName(user.getUserName().replaceAll(".*([';]+|(--)+).*", " ")) ==  null){
            user.setUserPass(encoder.encode(user.getUserPass()));
            user.addRole(RoleName.USER);
            userService.saveUser(user);
            return "AccountCreated";
        }else{
            model.addAttribute("aviso", "Este usuario ya esta registrado, inicie sesión con sus credenciales");
            return "LogIn";
        }

    }

    @GetMapping("/comments")    //see every comment in our database
    public String comments(Model model, HttpServletRequest request){

        if(request.getUserPrincipal() == null){
            model.addAttribute("user", false);
            model.addAttribute("not", true);
            log.info("No iniciado sesion");
        }else{

            User user = userService.findByOnlyName(request.getUserPrincipal().getName());
            if(user.getRole() == RoleName.ADMIN){
                model.addAttribute("user", true);
                model.addAttribute("not", false);
            }else{
                model.addAttribute("user", true);
                model.addAttribute("not", false);
            }
            log.info("User: {}",  request.getUserPrincipal().getName());
        }


        model.addAttribute("comment",commentService.getAll());
        return "comments";
    }

    @GetMapping("/NewComment")     //add a comment to our database
    public String addComment(Model model){
        return "NewComment";
    }

    @PostMapping("/createComment")
    public String createComment(@RequestParam String content,HttpServletRequest request){
        PolicyFactory sanitizer=new HtmlPolicyBuilder()
                .allowStandardUrlProtocols()
                .allowAttributes("title").globally() //We allow the title attribute wherever it is
                .allowAttributes("href").onElements("a") //We allow the use of link in elements of type <a>
                .requireRelNofollowOnLinks() //Because we allow link, we use this option t avoid spamming
                .allowAttributes("lang").matching(Pattern.compile("[a-zA-Z]{2,20}")).globally() //Allow alphabetic values in lang attributes wherever it is
                .allowAttributes("align").matching(true,"left","rigth","center",
                        "justify","char").onElements("p") //Allow alignment options in <p> elements
                .allowAttributes("style").onElements("span")
                .allowElements("a","p","div","i","b","em","blockquote","tt","strong",
                        "br","ul","ol","li", "span") //List of all the elements that are allowed in our "String"
                .toFactory(); //Make it be a factory so that the Policy Builder matches the PolicyFactory type
        String sanitized=sanitizer.sanitize(content);
        Comment comment=new Comment();
        comment.setContent(sanitized);
        comment.setOwner(request.getUserPrincipal().getName());
        commentService.addComment(comment);
        return "CommentAdded";
    }

    @GetMapping("/payments")    //see what you have to pay
    public String payment(Model model,HttpServletRequest request){
        model.addAttribute("cart",userService.findByOnlyName(request.getUserPrincipal().getName()).getCart());
        return "Payments";
    }

    @GetMapping("/pay") //pay
    public String pay(Model model,HttpServletRequest request){
        model.addAttribute("precio",userService.findByOnlyName(request.getUserPrincipal().getName()).getPrice());
        return "PayForm";
    }

    @PostMapping("/price/final")        //final price
    public String finalPrice(Model model, @RequestParam long cupon,HttpServletRequest request){
        /*if(cupon==null){
            model.addAttribute("precioFinal", currentUser.getPrice());
            return "SuccessfulPurchase";
        }
        else */if(!cuponService.exists(cupon)){
            model.addAttribute("precioFinal", userService.findByOnlyName(request.getUserPrincipal().getName()).getPrice());
            User user=userService.findByOnlyName(request.getUserPrincipal().getName());
            user.emptyCart();
            userService.saveUser(user);
            return "SuccessfulPurchase";
        } else {
            Cupon cupone = cuponService.findById(cupon);
            model.addAttribute("precioFinal", userService.findByOnlyName(request.getUserPrincipal().getName()).priceCupon(cupone));
            userService.findByOnlyName(request.getUserPrincipal().getName()).emptyCart();

            return "SuccessfulPurchase";
        }

    }



    @GetMapping("/coupons") //see the created coupuns
    public String coupons(Model model, HttpServletRequest request){
        model.addAttribute("coupons",cuponService.getAll());

        if(request.getUserPrincipal() == null){
            model.addAttribute("user", false);
            model.addAttribute("not", true);
        }else{

            User user = userService.findByOnlyName(request.getUserPrincipal().getName());
            if(user.getRole() == RoleName.ADMIN){
                model.addAttribute("user", true);
                model.addAttribute("not", false);
                model.addAttribute("admin", true);
            }else{
                model.addAttribute("user", true);
                model.addAttribute("not", false);
                model.addAttribute("admin", false);
            }
        }
        return "CouponList";
    }

    @PostMapping("/coupon/new") //create a new coupon
    public String createCoupon(Cupon coupon){
        cuponService.addCupon(coupon);
        return "CouponAdded";
    }

    @GetMapping("/coupon/del/{id}")
    public String deleteCoupon(Model model,@PathVariable long id,HttpServletRequest request){
        cuponService.obliterateCoupon(id);
        model.addAttribute("coupons",cuponService.getAll());

        if(request.getUserPrincipal() == null){
            model.addAttribute("user", false);
            model.addAttribute("not", true);
        }else{

            User user = userService.findByOnlyName(request.getUserPrincipal().getName());
            if(user.getRole() == RoleName.ADMIN){
                model.addAttribute("user", true);
                model.addAttribute("not", false);
                model.addAttribute("admin", true);
            }else{
                model.addAttribute("user", true);
                model.addAttribute("not", false);
                model.addAttribute("admin", false);
            }
        }
        return "CouponDeleted";
    }

    @PostMapping("/search") //search an item by it's name
    public String searchByName(Model model, @RequestParam String name, HttpServletRequest request){

        String webo = name.replaceAll(".*([';]+|(--)+).*", " ");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Item> q1=entityManager.createQuery("SELECT c FROM Item c WHERE c.productName like concat('%',:webo,'%')",Item.class).setParameter("webo",webo);
        List<Item> items=q1.getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        model.addAttribute("items",items);
        if(request.getUserPrincipal() == null){
            model.addAttribute("user", false);
        }else{
            model.addAttribute("user", true);
            User user = userService.findByOnlyName(request.getUserPrincipal().getName());
            if(user.getRole() == RoleName.ADMIN){
                model.addAttribute("admin", true);
            }else{
                model.addAttribute("admin", false);
            }
        }
        return "ItemsList";
    }

    @GetMapping("/adminpage")
    public String adminPage(Model model, HttpServletRequest request){

        model.addAttribute("users", userService.getAll());



        return "AdminPage";
    }

    @GetMapping("/user/del/{id}")   //delete an item
    public String delUser(@PathVariable int id){

        userService.delete(userService.findById(id));

        return "UserDeleted";

    }

    /*@PostMapping("/searchUser") //search an item by it's name
    public String searchUser(Model model, @RequestParam String name, HttpServletRequest request){

        String webo = name.replaceAll(".*([';]+|(--)+).*", " ");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<User> q1=entityManager.createQuery("SELECT c FROM User c WHERE c.userName like concat('%',:webo,'%')",User.class).setParameter("webo",webo);
        List<User> users=q1.getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        model.addAttribute("users",users);

        return "AdminPage";
    }*/

}
