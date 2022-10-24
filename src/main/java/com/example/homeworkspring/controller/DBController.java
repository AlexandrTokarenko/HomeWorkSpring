package com.example.homeworkspring.controller;

import com.example.homeworkspring.entities.*;
import com.example.homeworkspring.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class DBController {

    private CarRepository carRepository;
    private CarTypeRepository carTypeRepository;
    private FuelRepository fuelRepository;
    private TransmissionRepository transmissionRepository;
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String clickRegistration(Model model) {
        System.out.println();
        return "registration";
    }

    @PostMapping("/showMainMenu")
    public String checkUser(@RequestParam String email, @RequestParam String password, Model model) {

        List<User> user = userRepository.findUserByEmailAndPassword(email, password);
        if (user.size() != 0) {
            List<Car> cars = carRepository.findAll();
            model.addAttribute("cars", cars);
            model.addAttribute("user", userRepository.findUserByEmail(email).get(0));
            return "mainMenu";
        } else return "redirect:/authorization";
    }

    @GetMapping("/authorization")
    public String clickAuthorization() {

        return "authorization";
    }



    @PostMapping( "/addUser")
    public String addUser(@RequestParam String firstName, @RequestParam String lastName,
                          @RequestParam String email,@RequestParam String phone,
                          @RequestParam String password) {
        if (userRepository.findUserByEmail(email).size() == 0) {
            if (phone.length() == 10 && email.contains("@") && checkPassword(password)) {
                User user = new User();
                user.setPassword(password);
                user.setEmail(email);
                user.setLastname(lastName);
                user.setFirstname(firstName);
                user.setPhone(phone);
                userRepository.save(user);
                return "index";
            }
        }
        return "redirect:/registration";
    }

    private boolean checkPassword(String password) {

        if (password.length() < 8) {
            return false;
        }
        int a = 0;
        int b = 0;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isAlphabetic(password.charAt(i))) a++;
            else if(Character.isDigit(password.charAt(i))) b++;
            else if (Character.isWhitespace(password.charAt(i))) return false;
        }
        if (a > 0 && b > 0 ) return true;
        return false;
    }

    @GetMapping("/car_information/{id}")
    public String allCarInformation(@PathVariable("id") int id, Model model) {
        Optional<Car> cars = carRepository.findById(id);
        if (cars.isEmpty()) {
            model.addAttribute("msg","Объявление не найдено");
            return "error";
        } else {
            model.addAttribute("car",cars.get());
            System.out.println(cars.get().getAdditionalInformation());
            return "car_information";
        }
    }

    @GetMapping("/edit_ad/{userId}/{adId}")
    public String showUpdateGroupPage(@PathVariable("userId") int userId,@PathVariable("adId") int adId, Model model) {
        Optional<Car> car = carRepository.findById(adId);
        if (car.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            return "error";
        } else {
            model.addAttribute("car", car.get());
            model.addAttribute("user", userRepository.findById(userId).get());
            model.addAttribute("car_type", carTypeRepository.findAll());
            model.addAttribute("fuel", fuelRepository.findAll());
            model.addAttribute("transmission", transmissionRepository.findAll());
            return "edit_ad";
        }
    }

    @PostMapping("/update_ad/{userId}/{adId}")
    public String updateGroup(@PathVariable("userId") int userId,@PathVariable("adId") int adId, Car car, @RequestParam String carBrand,  @RequestParam String carModel,
                              @RequestParam int year,  @RequestParam int price,  @RequestParam int mileage,
                              @RequestParam double engine_volume, @RequestParam String car_type,
                              @RequestParam String transmission, @RequestParam String fuel, @RequestParam String add_information, Model model) {
        Optional<Car> ca = carRepository.findById(adId);
        Car car1 = ca.get();
        car1.setBrand(carBrand);
        car1.setModel(carModel);
        car1.setYear(year);
        car1.setPrice(price);
        car1.setMileage(mileage);
        car1.setEngineVolume(engine_volume);
        car1.setType(carTypeRepository.findByTitle(car_type));
        car1.setFuel(fuelRepository.findByTitle(fuel));
        car1.setTransmission(transmissionRepository.findByTitle(transmission));
        car1.setAdditionalInformation(add_information);
        carRepository.save(car1);
        model.addAttribute("cars",carRepository.findByUserId(userId));
        model.addAttribute("user",userRepository.findById(userId).get());
        return "my_ads";
    }

    @RequestMapping ("/exit_profile")
    public String exitProfile() {

        return "index";
    }

    @GetMapping("/search_by_filter/{id}")
    public String searchByFilter(@PathVariable("id") int id, @RequestParam String carBrand, @RequestParam String carModel,
                                 @RequestParam int firstYear, @RequestParam int lastYear, @RequestParam int firstPrice,
                                 @RequestParam int lastPrice, @RequestParam int firstMileage, @RequestParam int lastMileage,
                                 @RequestParam double firstEngineVolume, @RequestParam double lastEngineVolume,
                                 @RequestParam String car_type, @RequestParam String fuel,
                                 @RequestParam String transmission, Model model) {
        if ((firstPrice > lastPrice) || (firstMileage > lastMileage) || (firstYear > lastYear) || (firstEngineVolume > lastMileage)) {
            model.addAttribute("user", userRepository.findById(id).get());
            model.addAttribute("car_type", carTypeRepository.findAll());
            model.addAttribute("fuel", fuelRepository.findAll());
            model.addAttribute("transmission", transmissionRepository.findAll());
            return "filter";
        }
        List<Car> cars = carRepository.findByBrandLikeAndModelLikeAndPriceBetweenAndYearBetweenAndMileageBetweenAndEngineVolumeBetween(
                carBrand + "%",carModel + "%",firstPrice,lastPrice+1,firstYear,lastYear+1,firstMileage,lastMileage+1,
                firstEngineVolume,lastEngineVolume+0.1);

        if (!car_type.equals("Не выбрано")) {
            cars =  cars.stream().filter(x -> x.getType().getTitle().equals(car_type)).toList();
        } if (!fuel.equals("Не выбрано")) {
            cars =  cars.stream().filter(x -> x.getFuel().getTitle().equals(fuel)).toList();
        } if (!transmission.equals("Не выбрано")) {
            cars =  cars.stream().filter(x -> x.getTransmission().getTitle().equals(transmission)).toList();
        }
        model.addAttribute("cars",cars);
        model.addAttribute("user", userRepository.findById(id).get());
        return "mainShowWithFilter";
    }

    @RequestMapping("/click_add_ad/{id}")
    public String ckickAddAd(@PathVariable("id") int id, Model model) {

        model.addAttribute("transmission", transmissionRepository.findAll());
        model.addAttribute("user",userRepository.findById(id).get());
        model.addAttribute("car_type",carTypeRepository.findAll());
        model.addAttribute("fuel",fuelRepository.findAll());
        return "add_ad";
    }

    @PostMapping("/add_ad/{id}")
    public String addAd(@PathVariable("id") int id, @RequestParam String carBrand,  @RequestParam String carModel,
                        @RequestParam int year,  @RequestParam int price,  @RequestParam int mileage,
                        @RequestParam double engine_volume, @RequestParam String car_type,
                        @RequestParam String transmission, @RequestParam String fuel, @RequestParam String add_information, Model model) {
        if ((year < 1885) || (year > 2022)) {
            model.addAttribute("transmission", transmissionRepository.findAll());
            model.addAttribute("user",userRepository.findById(id).get());
            model.addAttribute("car_type",carTypeRepository.findAll());
            model.addAttribute("fuel",fuelRepository.findAll());
            return "add_ad";
        }
        Car car = new Car();
        car.setBrand(carBrand);
        car.setModel(carModel);
        car.setPrice(price);
        car.setYear(year);
        car.setMileage(mileage);
        car.setEngineVolume(engine_volume);
        car.setType(carTypeRepository.findByTitle(car_type));
        car.setFuel(fuelRepository.findByTitle(fuel));
        car.setUser(userRepository.findById(id).get());
        car.setTransmission(transmissionRepository.findByTitle(transmission));
        car.setAdditionalInformation(add_information);
        carRepository.save(car);
        model.addAttribute("user",userRepository.findById(id).get());
        model.addAttribute("cars", carRepository.findByUserId(id));
        return "my_ads";
    }

    @GetMapping ("/goMainMenu/{id}")
    public String showMainMenu(@PathVariable("id") int id, Model model) {
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("user", userRepository.findById(id).get());
        return "mainMenu";
    }

    @RequestMapping("/filter/{id}")
    public String showFilter(@PathVariable("id") int id, Model model) {

        model.addAttribute("user", userRepository.findById(id).get());
        model.addAttribute("car_type", carTypeRepository.findAll());
        model.addAttribute("fuel", fuelRepository.findAll());
        model.addAttribute("transmission", transmissionRepository.findAll());
        return "filter";
    }

    @RequestMapping("/delete_filter/{id}")
    public String deleteFilter(@PathVariable("id") int id, Model model) {

        model.addAttribute("user", userRepository.findById(id).get());
        model.addAttribute("cars",carRepository.findAll());
        return "mainMenu";
    }

    @RequestMapping("/my_ads/{id}")
    public String viewMyAds(@PathVariable("id") int id, Model model) {

        model.addAttribute("cars",carRepository.findByUserId(id));
        model.addAttribute("user",userRepository.findById(id).get());
        return "my_ads";
    }

    @GetMapping("/delete_ad/{userId}/{adId}")
    public String deleleAd(@PathVariable("userId") int userId, @PathVariable("adId") int adId, Model model) {

        carRepository.deleteById(adId);
        model.addAttribute("cars",carRepository.findByUserId(userId));
        model.addAttribute("user",userRepository.findById(userId).get());
        return "my_ads";
    }
}
