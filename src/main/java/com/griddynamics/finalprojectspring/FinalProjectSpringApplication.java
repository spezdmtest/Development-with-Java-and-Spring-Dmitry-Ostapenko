package com.griddynamics.finalprojectspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * // for SOAP - go to http://localhost:8080/ws/greeting.wsdl
 * //    Request:
 * //    <x:Envelope xmlns:x="http://schemas.xmlsoap.org/soap/envelope/" xmlns:gre="http://griddynamics.com/finalprojectspring/ws/greeting">
 * //    <x:Header/>
 * //    <x:Body>
 * //        <gre:getGreetingRequest>
 * //            <gre:name>Dmytro</gre:name>
 * //        </gre:getGreetingRequest>
 * //    </x:Body>
 * //</x:Envelope>
 * <p>
 * // add to Header
 * Content-Type: text/xml
 **/

@SpringBootApplication
public class FinalProjectSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinalProjectSpringApplication.class, args);
    }
}
