package co.edu.upb.bucaramanga.java.soapserver;

import co.edu.upb.bucaramanga.java.multiplepdf.FileResponse;
import co.edu.upb.bucaramanga.java.multiplepdf.GetFileRequest;
import co.edu.upb.bucaramanga.java.multiplepdf.GetFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.File;

@Endpoint
public class ClientEndpoint {
    // Referencia hacia el dominio publicado
    //http://java.bucaramanga.upb.edu.co/multiplepdf.xsd

    //referencia hacia la operacion a realizar

    //getFileResponse


    @PayloadRoot(namespace = "http://java.bucaramanga.upb.edu.co/multiplepdf.xsd", localPart = "getFileResponse")
    @ResponsePayload
    public GetFileResponse getFile(@RequestPayload GetFileRequest request){
        GetFileResponse response = new GetFileResponse();
        FileResponse fr = new FileResponse();
        fr.setStatus("OK");
        response.setFile(fr);
        return response;
    }
}
