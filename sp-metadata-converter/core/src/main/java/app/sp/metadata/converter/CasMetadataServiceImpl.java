package app.sp.metadata.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import app.sp.metadata.converter.config.Config;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Collections;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Service
public class CasMetadataServiceImpl implements CasMetadataService {

    private static final Logger LOG = LoggerFactory.getLogger(CasMetadataServiceImpl.class);

    private static final Config CONFIG = new Config();

    private static final String SIGNATURE_TAG = "ds:SignatureValue";

    private static final String URL_CONTEXT = "saml2sp";

    private String encodedAuthToken;

    @Override
    public CasMetadataResponseTO attributes(final String entityId) {
        CasMetadataResponseTO casMetadataResponseTO = new CasMetadataResponseTO();
        if (StringUtils.isBlank(entityId)) {
            LOG.warn("No 'entityId' specified");
        } else {
            try {
                Response response = getSPWebclient(entityId).get();
                String responseData = response.readEntity(String.class);
                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    casMetadataResponseTO.setId(10000003);
                    casMetadataResponseTO.setName("SAMLService");
                    casMetadataResponseTO.setValue(responseData);
                    casMetadataResponseTO.setSignature(extractMetadataSignature(responseData));
                } else {
                    LOG.warn("Response after fetching SP metadata - status: {}", response.getStatus());
                    LOG.warn("Response after fetching SP metadata - entity: {}", responseData);
                }
            } catch (Exception e) {
                LOG.error("While getting SP metadata", e);
            }
        }

        return casMetadataResponseTO;
    }

    private WebClient getSPWebclient(final String entityId) {
        if (StringUtils.isBlank(encodedAuthToken)) {
            String authString = CONFIG.getUsername() + ":" + CONFIG.getPassword();
            try {
                encodedAuthToken = Base64.getEncoder().encodeToString((authString).getBytes("UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                LOG.error("While encoding authorization data", ex);
            }
        }
        WebClient webClient = WebClient.create(CONFIG.getSpMetadataUrl(),
                Collections.singletonList(new JacksonJsonProvider()));
        webClient.header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuthToken);
        webClient.header("X-Syncope-Domain", "Master");
        webClient.accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML);
        webClient.query("spEntityID", StringUtils.appendIfMissing(entityId, "/"));
        webClient.query("urlContext", URL_CONTEXT);

        return webClient;
    }

    private String extractMetadataSignature(final String metadataContent) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        String value = null;
        try {
            Document doc = dbFactory.newDocumentBuilder().parse(new InputSource(new StringReader(metadataContent)));
            doc.getDocumentElement().normalize();
            value = doc.getElementsByTagName(SIGNATURE_TAG).getLength() > 0
                    ? doc.getElementsByTagName(SIGNATURE_TAG).item(0).getTextContent() : null;
        } catch (IOException | ParserConfigurationException | DOMException | SAXException ex) {
            LOG.error("While parsing metadata xml", ex);
        }

        return value;
    }

}
