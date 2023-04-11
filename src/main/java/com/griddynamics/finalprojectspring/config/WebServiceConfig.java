package com.griddynamics.finalprojectspring.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {

    public static final String NAMESPACE_GREETING = "http://griddynamics.com/finalprojectspring/ws/greeting";
    public static final String NAMESPACE_PRODUCTS = "http://griddynamics.com/finalprojectspring/ws/products";

    // реєструєм точку в контексті, де будуть находитись вес сервіси
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "greeting")
    public DefaultWsdl11Definition defaultWsdl11Definition() {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("GreetingPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(NAMESPACE_GREETING);
        wsdl11Definition.setSchema(xdsGreetingSchema());
        return wsdl11Definition;
    }

    @Bean("greetingSchema")
    public XsdSchema xdsGreetingSchema() {
        return new SimpleXsdSchema(new ClassPathResource("ws/greeting.xsd"));
    }

//    @Bean(name = "products")
//    public DefaultWsdl11Definition productsWsdlDefinition() {
//        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
//        wsdl11Definition.setPortTypeName("ProductsPort");
//        wsdl11Definition.setLocationUri("/ws");
//        wsdl11Definition.setTargetNamespace(NAMESPACE_PRODUCTS);
//        wsdl11Definition.setSchema(xdsProductsSchema());
//        return wsdl11Definition;
//    }
//
//    @Bean
//    public XsdSchema xdsProductsSchema() {
//        return new SimpleXsdSchema(new ClassPathResource("products.xsd"));
//    }
}
