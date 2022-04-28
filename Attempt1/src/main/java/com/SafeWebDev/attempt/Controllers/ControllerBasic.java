package com.SafeWebDev.attempt.Controllers;

import java.util.List;
import java.util.regex.Pattern;
import com.SafeWebDev.attempt.Models.*;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import com.SafeWebDev.attempt.Models.Comment;
import com.SafeWebDev.attempt.Models.Cupon;
import com.SafeWebDev.attempt.Models.Item;
import com.SafeWebDev.attempt.Models.User;
import com.SafeWebDev.attempt.Models.Services.CommentService;
import com.SafeWebDev.attempt.Models.Services.CuponService;
import com.SafeWebDev.attempt.Models.Services.ItemService;
import com.SafeWebDev.attempt.Models.Services.UserService;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
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

    User currentUser;


    @PostConstruct
    public void init(){
        currentUser=new User("webo","webotes","webazos","webitos"/*,"weboncio"*/);
    }


    @GetMapping("")     //redirect to StartPage.html, the main page
    public String homePage(Model model) {
        return "StartPage";
    }

    @GetMapping("/item/edit/{id}")  //edit an item
    public String updateItem(Model model,@PathVariable long id){

        model.addAttribute("item", itemService.findById(id));
        return "ItemEdit";
    }

    @PostMapping("/editting/{id}")  //edit an item
    public String updatingItem(Model model,@PathVariable long id, Item item){

        Item aux = itemService.findById(id);
        aux.update(item);
        itemService.add(aux);

        return "ItemEdited";
    }

    @GetMapping("/item/{id}")   //redirect to ItemPage.html, where you can see the info of one item
    public String itemPage(Model model, @PathVariable long id) {

        model.addAttribute("item", itemService.findById(id));
        return "ItemPage";
    }

    @GetMapping("/usr") //redirect to UsrPage.html with your usr info (right now just the admin user)
    public String usrPage(Model model) {

        model.addAttribute("user", currentUser);
        return "UsrPage";

    }

    @PostMapping("/item/new")   //redirect to ItemAdded.html after adding an item to our general List
    public String addItem(Model model,Item item){
        itemService.add(item);
        return "ItemAdded";
    }

    @GetMapping("/items")   //redirect to ItemsList.html, where you can see every product aviable
    public String listaItems(Model model){
        model.addAttribute("items", itemService.getAll());
        return "ItemsList";
    }

    @GetMapping("/item/del/{id}")   //delete an item
    public String delFromList(@PathVariable int id){
        itemService.delete(itemService.findById(id));
        return "ItemDeletedCompletely";

    }

    @GetMapping("/cart")    //redirect to Cart.html, with your cart info
    public String carrito(Model model){
        model.addAttribute("cart", currentUser.getCart());
        return "Cart";
    }

    @GetMapping("/cart/{id}")   //redirect to CartAdded.html or CartAlreadyContains.html
    public String addCarrito(Model model, @PathVariable long id){
        if(currentUser.getCart().contains(itemService.findById(id))){
            return "CartAlreadyContains";
        }else{
            currentUser.addCart(itemService.findById(id));
            return "CartAdded";
        }
    }

    @GetMapping("/cart/del/{id}")   //redirect to ItemDeleted.html, to confirm the item was deleted
    public String deleteItem(@PathVariable int id){
        currentUser.delCart(id-1);
        return "ItemDeleted";

    }

    @GetMapping("/login")   //redirect to LogIn.html, where you'll be able to log in
    public String logIn(Model model) {
        model.addAttribute("aviso", "");
        return "LogIn";

    }
    @PostMapping("/login")   //logs the user in and displays the user page with the user already swapped
    public String logInPost(Model model,@RequestParam String userName,@RequestParam String password) {
        if(userService.findByName(userName,password)!=null){
            currentUser=userService.findByName(userName,password);
            model.addAttribute("user",currentUser);
            return "UsrPage";
        }else{
            model.addAttribute("aviso", "El nombre de usuario no correponde con ninguno registrado en nuestra base de datos");
            return "LogIn";
        }
    }

    @GetMapping("/loggedIn")
    public String loggedIn(){
        return "hoola";
    }

   /* @GetMapping("/createAccount")   //redirect to CreateAccount.html, to sign up
    public String createAccount(){
        return "CreateAccount";

    }*/

    @PostMapping("/account/created")
    public String createdAccount(Model model, User user){
        if(userService.findByOnlyName(user.getUserName())==null){
            userService.saveUser(user);
            currentUser=user;
            return "AccountCreated";
        }else{
            model.addAttribute("aviso", "Este usuario ya esta registrado, inicie sesión con sus credenciales");
            return "LogIn";
        }
        
    }

    @GetMapping("/comments")    //see every comment in our database
    public String comments(Model model){
        model.addAttribute("comment",commentService.getAll());
        return "comments";
    }

    @GetMapping("/NewComment")     //add a comment to our database
    public String addComment(Model model){
        return "NewComment";
    }

    @PostMapping("/createComment")
    public String createComment(@RequestParam String content,@RequestParam String user){
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
        String sanUser=sanitizer.sanitize(user);
        Comment comment=new Comment();
        comment.setContent(sanitized);
        comment.setUser(sanUser);

        commentService.addComment(comment);
        return "CommentAdded";
    }

    @GetMapping("/payments")    //see what you have to pay
    public String payment(Model model){
        model.addAttribute("cart",currentUser.getCart());
        return "Payments";
    }

    @GetMapping("/pay") //pay
    public String pay(Model model){
        model.addAttribute("precio",currentUser.getPrice());
        return "PayForm";
    }

    @PostMapping("/price/final")        //final price
    public String finalPrice(Model model, @RequestParam long cupon){
        /*if(cupon==null){
            model.addAttribute("precioFinal", currentUser.getPrice());
            return "SuccessfulPurchase";
        }
        else */if(!cuponService.exists(cupon)){
            model.addAttribute("precioFinal", currentUser.getPrice());
            currentUser.emptyCart();

            return "SuccessfulPurchase";
        } else {
            Cupon cupone = cuponService.findById(cupon);
            model.addAttribute("precioFinal", currentUser.priceCupon(cupone));
            currentUser.emptyCart();

            return "SuccessfulPurchase";
        }

    }



    @GetMapping("/coupons") //see the created coupuns
    public String coupons(Model model){
        model.addAttribute("coupons",cuponService.getAll());
        return "CouponList";
    }

    @PostMapping("/coupon/new") //create a new coupon
    public String createCoupon(Cupon coupon){
        cuponService.addCupon(coupon);
        return "CouponAdded";
    }

    @PostMapping("/search") //search an item by it's name
    public String searchByName(Model model, @RequestParam String name){

        String webo = name.replaceAll(".*([';]+|(--)+).*", " ");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Item> q1=entityManager.createQuery("SELECT c FROM Item c WHERE c.productName like concat('%',:webo,'%')",Item.class).setParameter("webo",webo);
        List<Item> items=q1.getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        model.addAttribute("items",items);
        return "ItemsList";
    }
}
