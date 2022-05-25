package dtapcs.springframework.Formee.configuration;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import dtapcs.springframework.Formee.entities.EmailTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;

@Configuration
public class EmailTemplateConfig {
    public static HashMap<String, EmailTemplate> EMAIL_TEMPLATES;
    @Value(value = "classpath:email_template.json")
    private Resource EmailResource;
    @Bean
    public void CreateEmailTemplateConfig() throws IOException
    {
            InputStream emailTemplate_stream = EmailResource.getInputStream();
            Reader reader = new InputStreamReader(emailTemplate_stream, "UTF-8");
            Type type = new TypeToken<HashMap<String, EmailTemplate>>(){}.getType();
            EmailTemplateConfig.EMAIL_TEMPLATES = new Gson().fromJson(reader,type);
            EmailTemplate a = EmailTemplateConfig.EMAIL_TEMPLATES.get("Order-Notification"); //to get template
        System.out.println("Email template setup DONE\n"+a.toString());
    }


}
