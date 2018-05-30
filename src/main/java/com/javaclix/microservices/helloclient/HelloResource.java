package com.javaclix.microservices.helloclient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/rest/hello/client")
public class HelloResource {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback", groupKey = "Hello",
            commandKey = "hello",
            threadPoolKey = "helloThread"
    )
    @GetMapping
    public String getHello(){
        String url = "http://hello-server/rest/hello/server";
        return restTemplate.getForObject(url, String.class);
    }

    //The return type of this method must be the same as the getHello() method.
    public String fallback(Throwable hystrixCommand) {
        return "Fall Back Hello world";
    }
}
