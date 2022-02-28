package com.sidabw.jpadbmysql.jpa;

import com.sidabw.jpadbmysql.jpa.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author shaogz
 */
@Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    /**
     *  Map ONLY POST Requests
     * e.. @ResponseBody means the returned String is the response, not a view name
     * e.. @RequestParam means it is a parameter from the GET or POST request
     * @param name name
     * @param email email
     * @return application json body
     */
    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(path = "/transactionTest")
    public @ResponseBody String transactionTest() throws InterruptedException {
        int curRandom = new Random().nextInt(10000);
        log.info("req come in, random: {}", curRandom);
        userService.queryAndUpdate(curRandom);
        return "success_random_" + curRandom;
    }

    @GetMapping(path = "/trTest2")
    public @ResponseBody String trTest2() throws Exception {
        log.info("req come in");
        userService.batchInsertWitchOneException();
        return "aa";
    }
}