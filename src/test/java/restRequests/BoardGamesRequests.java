package restRequests;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pages.BoardGamePage;

import java.util.HashMap;
import java.util.Map;

public class BoardGamesRequests {

    private RequestSpecification httpRequest;
    private Map<String, String> requestHeader = new HashMap<String, String>();
    private Response response;
    private String responseBody;
    public static String languageDependence;

    public void createAndSendRequest() {
        //URL where we send our request
        RestAssured.baseURI = BoardGamePage.currentUrl; //BoardGamePage.currentUrl;
        // Creating of request
        httpRequest = RestAssured.given();
        requestHeader.put("Upgrade-Insecure-Requests", "1");
        requestHeader.put("DNT", "1");
        requestHeader.put("Sec-Fetch-User", "1");
        requestHeader.put("Accept-", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");

        for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
            httpRequest.header(entry.getKey(), entry.getValue());
        }

        response = httpRequest.post();
        responseBody = response.asString();

        //We can change it for a more beautiful view or if we can communicate with developers we can change it for a more correct way
        String[] splittedResponseBody = responseBody.split("=");
        for (int i = 0; i < splittedResponseBody.length; i++) {
            if (splittedResponseBody[i].contains("geekitemPreload")) {
                int el = i;
                String[] pre = splittedResponseBody[i + 1].split(";");
                String json = pre[0].trim() + ">\"}}";
                languageDependence = JsonPath.read(json, "$.item.polls.languagedependence");
            }
        }
    }
}
