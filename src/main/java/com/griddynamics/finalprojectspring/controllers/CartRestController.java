package com.griddynamics.finalprojectspring.controllers;

import com.griddynamics.finalprojectspring.controllers.Exceptions.ValidateOrder;
import com.griddynamics.finalprojectspring.controllers.Exceptions.ValidateQuantityException;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.griddynamics.finalprojectspring.dto.CartDTO;
import com.griddynamics.finalprojectspring.services.CartService;
import com.griddynamics.finalprojectspring.services.SessionData;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/carts")
public class  CartRestController {

    private final CartService service;
    private final SessionData sessionData;
    private static final Logger LOGGER = Logger.getLogger(CartRestController.class);

    @GetMapping(path = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDTO viewCart(@NotNull Principal principal) throws Exception {
        CartDTO cartDTO = service.getCartByUser(principal.getName());
        validateQuantity();
        return cartDTO;
    }

    @GetMapping(path = "/cart/session", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDTO viewSessionCart(Principal principal, HttpSession httpSession) throws Exception {
        if (!httpSession.isNew()) {
            sessionData.setPrincipal(principal);
            var resultPrincipal = sessionData.getPrincipal();
            LOGGER.info("Principal result: " + resultPrincipal);
        }
        if (principal == null || sessionData.getPrincipal() == null) {
             var validateOrder = new ValidateOrder();
             LOGGER.error("Wrong order", validateOrder);
             throw validateOrder;
        } else {
            CartDTO cartDTO = service.getCartByUser(principal.getName());
            httpSession.invalidate();
            validateQuantity();
            return cartDTO;
        }
    }

    private void validateQuantity() throws ValidateQuantityException {
        if (service.isNotQuantity()) {
            var validateQuantityException = new ValidateQuantityException();
            LOGGER.error("Don't available quantity in store", validateQuantityException);
            throw validateQuantityException;
        }
    }

    @ExceptionHandler
    public ResponseEntity<String> ExistExceptionHandler(ValidateQuantityException e) {
        return new ResponseEntity<>("\"Don't available quantity in store\"", HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<String> ExistExceptionHandler(ValidateOrder e) {
        return new ResponseEntity<>("Wrong order", HttpStatus.CONFLICT);
    }
}

