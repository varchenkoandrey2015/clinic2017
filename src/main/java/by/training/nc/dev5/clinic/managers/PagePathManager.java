package by.training.nc.dev5.clinic.managers;

import by.training.nc.dev5.clinic.constants.ConfigConstants;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

/**
 * Created by user on 04.04.2017.
 */

@Component
public class PagePathManager {
    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigConstants.CONFIGS_SOURCE);

    public String getProperty(String key){
        return bundle.getString(key);
    }
}
