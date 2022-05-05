package com.SafeWebDev.attempt.Controllers;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import com.SafeWebDev.attempt.Controllers.Security.JwtUtils;
import com.SafeWebDev.attempt.Models.Comment;
import com.SafeWebDev.attempt.Models.Cupon;
import com.SafeWebDev.attempt.Models.Item;
import com.SafeWebDev.attempt.Models.RoleName;
import com.SafeWebDev.attempt.Models.User;
import com.SafeWebDev.attempt.Models.Services.CommentService;
import com.SafeWebDev.attempt.Models.Services.CuponService;
import com.SafeWebDev.attempt.Models.Services.ItemService;
import com.SafeWebDev.attempt.Models.Services.UserDetailsServiceImpl;
import com.SafeWebDev.attempt.Models.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class ItemRESTController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CuponService cuponService;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private PasswordEncoder encoder;



    @GetMapping("/see") //to see every item on stock
    public List<Item> getItems(){

        return itemService.getAll();
    }

    @GetMapping("/see/{id}")    //see a specified item with id
    public ResponseEntity<Item> getById(@PathVariable long id){

        if(itemService.exists(id)){

            return new ResponseEntity<>(itemService.findById(id), HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/del/{id}")    //delete an item
    public ResponseEntity<?> deleteItem(@PathVariable long id, HttpServletRequest request){


        if(itemService.exists(id)){
            Item item = itemService.findById(id);
            if(item.getOwner().equals(request.getUserPrincipal().getName()) ||
                    userService.findByOnlyName(request.getUserPrincipal().getName()).getRole().toString().equals("ADMIN")){

                itemService.delete(itemService.findById(id));
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }



    }

    @PostMapping("/addItem")    //add item to stock
    public ResponseEntity<Item> add(@RequestBody Item item, HttpServletRequest request){

        String owner = request.getUserPrincipal().getName();
        item.setOwner(owner);
        itemService.add(item);


        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping("/editItem/{id}")  //edit item info
    public ResponseEntity<Item> edit(@RequestBody Item item, @PathVariable long id, HttpServletRequest request){

        if(itemService.exists(id)){

            if(itemService.findById(id).getOwner().equals(request.getUserPrincipal().getName())
            || userService.findByOnlyName(request.getUserPrincipal().getName()).getRole().toString().equals("ADMIN")){

                itemService.updateItem(id, item);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user){

        user.addRole(RoleName.USER);
        log.info("{}", user.getUserPass());
        user.setUserPass(encoder.encode(user.getUserPass()));
        userService.saveUser(user);
        return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
    }
    /*@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        if(userService.findByOnlyName(signupRequest.getUsername()) != null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: nombre de usuario ya usado"));
        }

        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword(), signupRequest.getAddress(), signupRequest.getPersonalName());
        user.setUserPass(encoder.encode(user.getUserPass()));
        userDetailsService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("Usuario registrado"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginRe(@Valid @RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsEntityImpl userDetails = UserDetailsEntityImpl.build(userService.findByOnlyName(authentication.getName()));
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        String role = userDetails.getAuthorities().toString();
        RoleName roleName;
        if(role == RoleName.ADMIN.toString()){
            roleName = RoleName.ADMIN;
        }else {
            roleName = RoleName.USER;
        }
        //RoleName roleName = userDetails.getRole();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roleName));


    }*/

    @GetMapping("/addCart/{id}")    //add item to cart
    public ResponseEntity<List<Item>> addCart(@PathVariable long id, HttpServletRequest request){

        if(itemService.exists(id)){
            User user = userService.findByOnlyName(request.getUserPrincipal().getName());
            if(user.getCart().contains(itemService.findById(id))){
                return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
            }else{
                //currentUser.addCart(itemService.findById(id));
                user.addCart(itemService.findById(id));
                return new ResponseEntity<>(user.getCart(), HttpStatus.ACCEPTED);
            }
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }




    }

    @GetMapping("/seeCart") //see the cart
    public List<Item> seeCart(HttpServletRequest request){

        return userService.findByOnlyName(request.getUserPrincipal().getName()).getCart();

    }

    @GetMapping("/removeCart/{id}") //remove item from cart
    public ResponseEntity<List<Item>> removeCart(@PathVariable int id, HttpServletRequest request){

        User user = userService.findByOnlyName(request.getUserPrincipal().getName());
        user.getCart().remove(itemService.findById(id));
        return new ResponseEntity<>(user.getCart(),HttpStatus.ACCEPTED);
    }

    @GetMapping("/usr") //see your user
    public User seeUser(HttpServletRequest request){

        return userService.findByOnlyName(request.getUserPrincipal().getName());
    }
    @GetMapping("/comments")    //see every comment in our database
    public ResponseEntity<List<Comment>> comments(Model model){
        return new ResponseEntity<>(commentService.getAll(),HttpStatus.ACCEPTED);
    }

    @PostMapping("/NewComment")     //add a comment to our database
    public ResponseEntity<Comment> addComment(Model model, @RequestBody Comment comment, HttpServletRequest request){
        String user = request.getUserPrincipal().getName();
        comment.setOwner(user);
        commentService.addComment(comment);
        return new ResponseEntity<>(comment,HttpStatus.ACCEPTED);
    }

    @PostMapping("/coupon/new") //create a coupon
    public ResponseEntity<Cupon> createCoupon(@RequestBody Cupon coupon){
        cuponService.addCupon(coupon);
        return new ResponseEntity<>(coupon, HttpStatus.CREATED);
    }

    @GetMapping("/coupons") //see the coupons
    public ResponseEntity<List<Cupon>> getCupons(){

        return new ResponseEntity<>(cuponService.getAll(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/pay") //pay
    public ResponseEntity<Float> pay(HttpServletRequest request){

        return new ResponseEntity<>(userService.findByOnlyName(request.getUserPrincipal().getName()).getPrice(), HttpStatus.ACCEPTED);
    }

    @GetMapping("pay/cupon/{id}")   //pay with coupons
    public ResponseEntity<Float> payCupons(@PathVariable long id,HttpServletRequest request){

        return new ResponseEntity<>(userService.findByOnlyName(request.getUserPrincipal().getName()).priceCupon(cuponService.findById(id)), HttpStatus.ACCEPTED);
    }

    @GetMapping("/search/{name}")   //search by name
    public ResponseEntity<List<Item>> searchByName(@PathVariable String name){

        String webo = name.replaceAll(".*([';]+|(--)+).*", " ");
        System.out.println(webo);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Item> q1=entityManager.createQuery("SELECT c FROM Item c WHERE c.productName like concat('%',:webo,'%')",Item.class).setParameter("webo",webo);
        List<Item> items=q1.getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return new ResponseEntity<>(items, HttpStatus.ACCEPTED);
    }

}