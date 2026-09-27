package sn.gainde2000.backenmfpai.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author G2k R&D
 */


@Service
public class Sms {
    private Sms(){

    }
    private static final String URL_BEGIN = "http://192.168.1.164/sms/traitersms.php?username=kannel&password=kannel&to=";
    private static final String URL_END = "&sms_platform=4&signature=GAINDE2000";
    public static final String URL_CENTER = "&text=";
    private static final Logger logger = LoggerFactory.getLogger(Sms.class);

    public static boolean sendSms(String telephoneDestinataire, String sms) {
        boolean isSmsSend = false;
        String url;
        String telephoneDestinataireFormate;
        String telephoneDestinataireWithoutSpace = telephoneDestinataire.trim().replace(" ", "");
        try {
            if (telephoneDestinataireWithoutSpace.length() == 9) {
                telephoneDestinataireFormate = "221" + telephoneDestinataireWithoutSpace;

                if (sms.length() < 160) {
                    url = URL_BEGIN + telephoneDestinataireFormate + URL_CENTER + URLEncoder.encode(sms, StandardCharsets.UTF_8) + URL_END;
                    Client client = Client.create();
                    WebResource webResource = client.resource(url);
                    ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

                    if (response.getStatus() == 200) {
                        String output = response.getEntity(String.class);

                        if (output.equalsIgnoreCase("1")) {
                            isSmsSend = true;
                            //Réponse différent de 1 donc erreur
                        }

                    } else {
                        logger.info("Response status = {}", response.getStatus());

                    }
                }
            } else {

                logger.info("Numero à traiter.");
            }
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("Une erreur s'est produite.", e);
        }
        return isSmsSend;
    }
}
