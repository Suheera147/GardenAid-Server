package com.gardenaid.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

@Controller // This means that this class is a Controller
@RequestMapping(path="/") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired // This means to get the bean called userRepository
            // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @Autowired 
    private PlantRepository plantRepository;

    @Autowired 
    private BoughtRepository boughtRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path="/user/signup") // Map ONLY POST Requests
    public ResponseEntity<String> addNewUser (@RequestParam("name") String name
        , @RequestParam String email, @RequestParam String password, @RequestParam String number) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        if (userRepository.findByEmail(email) == null) {
            User temp = new User();
            temp.setName(name);
            temp.setEmail(email);
            temp.setNumber(number);
            temp.setPassword(password);
            userRepository.save(temp);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Responded", "MyController");
            return ResponseEntity.accepted().headers(headers).body(name);
        }
        else {

            HttpHeaders headers = new HttpHeaders();
            headers.add("Responded", "MyController");
            return ResponseEntity.status(403).headers(headers).body("User Already Exists");
        }

    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path="/user/signin")
    public ResponseEntity<String> loginUser (@RequestParam String email, @RequestParam String password) {

        User temp = userRepository.findByEmail(email);
        if (temp != null) {
            System.out.println(temp.getPassword());
            if (temp.getPassword().equals(password)) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Responded", "MyController");
                return ResponseEntity.accepted().headers(headers).body((temp.getId()).toString());
            }
            else {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Responded", "MyController");
                return ResponseEntity.status(403).headers(headers).body("Wrong Password or User Email");
            }
        }
        else {

            HttpHeaders headers = new HttpHeaders();
            headers.add("Responded", "MyController");
            return ResponseEntity.status(403).headers(headers).body("Wrong Password or User Email");
        }
        
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path="/user/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path="/user/removeAll")
    public ResponseEntity<String> removeAllUsers() {
        // This returns a JSON or XML with the users
        userRepository.deleteAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "MyController");
        return ResponseEntity.accepted().headers(headers).body("Deleted All");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path="/plant/all")
    public @ResponseBody Iterable<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path="/bought/all")
    public @ResponseBody Iterable<Bought> getAllBought() {
        return boughtRepository.findAll();
    }

    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path="/bought/byUser")
    public @ResponseBody Iterable<Bought> getBoughtByUser(@RequestParam Integer id) {
        return boughtRepository.findByUserId(id);
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path="/plant/add") // Map ONLY POST Requests
    public ResponseEntity<String> addNewPlant (@RequestParam String name
    , @RequestParam String type, @RequestParam Integer price) {
        
        if (plantRepository.findByName(name) == null) {
            Plant temp = new Plant();
            temp.setName(name);
            temp.setPlantType(type);
            temp.setPrice(price);
            plantRepository.save(temp);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Responded", "MyController");
            return ResponseEntity.accepted().headers(headers).body("Added");
        }
        else {

            HttpHeaders headers = new HttpHeaders();
            headers.add("Responded", "MyController");
            return ResponseEntity.status(403).headers(headers).body("Plant Already Exists");
        }

    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path="/plant/buy") // Map ONLY POST Requests
    public ResponseEntity<String> buy (@RequestParam Integer userId
    , @RequestParam Integer plantId) {
        if (plantRepository.findById(plantId) != null) {
            Bought temp = new Bought();
            temp.setPlantId(plantId);
            temp.setUserId(userId);
            boughtRepository.save(temp);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Responded", "MyController");
            return ResponseEntity.accepted().headers(headers).body("Bought");
        }
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Responded", "MyController");
            return ResponseEntity.status(403).headers(headers).body("Plant Doesn't Exists");
        }

    }

    

}