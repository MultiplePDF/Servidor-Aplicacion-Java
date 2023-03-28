package co.edu.upb.bucaramanga.java.soapserver;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletRegistration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.MessageDispatcher;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WsConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean messageDispatcherServelet(ApplicationContext applicationContext){
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/service/*");
    }

    @Bean(name="multiplePDFwsdl")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema pdfSchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("multiplePdfDetailsPort");
        wsdl11Definition.setLocationUri("/service/multiplepdf");
        wsdl11Definition.setTargetNamespace("http://java.bucaramanga.upb.edu.co/multiplepdf.xsd");
        wsdl11Definition.setSchema(pdfSchema);
        return wsdl11Definition;
    }


    @Bean
    public XsdSchema multiplePdfSchema(){
        return new SimpleXsdSchema(new ClassPathResource("schemas.xsd"));
    }
}
