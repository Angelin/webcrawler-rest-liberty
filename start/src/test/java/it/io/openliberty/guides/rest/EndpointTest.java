package it.io.openliberty.guides.rest;

import static org.junit.Assert.assertEquals;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Iterator;

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.junit.Test;

public class EndpointTest {

     // @Test
     public void testGetProperties() {
        String port = System.getProperty("liberty.test.port");
        String war  = System.getProperty("war.name");
        String url = "http://localhost:" + port + "/" + war + "/";

        Client client = ClientBuilder.newClient();
        client.register(JsrJsonpProvider.class);

        WebTarget target = client.target(url + "webcrawler/sitemap");
        Response response = target.request().get();

        assertEquals("Incorrect response code from " + url, Response.Status.OK.getStatusCode(), response.getStatus());

        JsonObject obj = response.readEntity(JsonObject.class);
        Iterator iterator = obj.keySet().iterator();

         int count =0;
         while(iterator.hasNext()) {
             String key = (String) iterator.next();
             count++;
         }
        assertEquals("result match", 92, count);
        response.close();
    }
}