package io.openliberty.guides.rest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("sitemap")
public class SitemapResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getProperties() {

        JsonObjectBuilder builder = Json.createObjectBuilder();
        try {
            URL oracle = new URL("https://www.wiprodigital.com/");
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));

            String patternStr = "href=\"(https://wiprodigital.com.*?)\"";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = null;

            String inputLine;
            int count = 0;
            while ((inputLine = in.readLine()) != null) {
                matcher = pattern.matcher(inputLine);

                while (matcher.find()) {
                    builder.add(Integer.toString(count),(String)matcher.group(1));
                    count++;
                }
                matcher.reset(inputLine);
            }
            in.close();

        } catch (Exception e) {

        }
        return builder.build();
    }
}