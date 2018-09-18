package com.eimapi.starter.kong.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class RestControllerTestApplication {

    @RequestMapping(value = "/basic", method = RequestMethod.GET)
    public String basic() {
        return "This is the simple GET method!";
    }

    @RequestMapping(value = "/method/ignored")
    public String methodIgnored() {
        return "This is the test that had method ignored";
    }

    @RequestMapping(value = {"/array/of/value", "/arrayofvalue", "/array/value"}, method = RequestMethod.GET)
    public String arrayOfValue() {
        return "This is the test that have multiples paths";
    }

    @GetMapping("/getmapping")
    public String getMapping() {
        return "This is the test that has mapping through GetMapping!";
    }
}
