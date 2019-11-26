package training.busboard.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import training.busboard.PostcodeException;

@Controller
@EnableAutoConfiguration
public class Website {

    @RequestMapping("/")
    ModelAndView home() {
        return new ModelAndView("index", "hasError", false);
    }

    @RequestMapping("/busInfo")
    ModelAndView busInfo(@RequestParam("postcode") String postcode) {
        try {
            return new ModelAndView("info", "busInfo", new BusDisplay(postcode)) ;
        } catch (PostcodeException err) {
            return new ModelAndView("index", "hasError", true);
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Website.class, args);
    }

}