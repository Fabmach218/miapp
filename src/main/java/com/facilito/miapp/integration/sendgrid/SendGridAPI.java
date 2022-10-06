package com.facilito.miapp.integration.sendgrid;

import java.io.IOException;

import org.springframework.stereotype.Service;
import com.sendgrid.*;

@Service
public class SendGridAPI {
    
    public int send(String desde, String asunto, String hasta, String body){
        Email from = new Email(desde);
        String subject = asunto;
        Email to = new Email(hasta);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        int statusCode = 0;
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            statusCode = response.getStatusCode();
        } catch (IOException ex) {
            ex.printStackTrace();
            statusCode = 500;
        }
        return statusCode;
    }


}
