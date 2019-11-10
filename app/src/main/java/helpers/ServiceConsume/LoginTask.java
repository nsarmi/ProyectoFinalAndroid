package helpers.ServiceConsume;

import android.os.AsyncTask;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class LoginTask extends AsyncTask<String, Void, Void> {

    public String Result;

    @Override
    public Void doInBackground(String... params) {
        HttpHeaders requestHeaders = new HttpHeaders();
        //requestHeaders.setContentType(MediaType.TEXT_PLAIN_VALUE);
        HttpEntity<String> _entity = new HttpEntity<String>(requestHeaders);
        RestTemplate templ = new RestTemplate();
        //null here in order there wasn't http converter errors because response type String and [text/html] for JSON are not compatible;
        String response = templ.getForObject(params[0], String.class);

        Result = response;

        return null;
    }
}
