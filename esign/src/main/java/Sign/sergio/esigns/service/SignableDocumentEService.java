package Sign.sergio.esigns.service;
import Sign.sergio.esigns.model.SignableEDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SignableDocumentEService {
	Logger logger = LoggerFactory.getLogger(SignableDocumentEService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/signabledcs";

	protected static SignableDocumentEService service= null;
	public static SignableDocumentEService getService(){
		if(service == null){
			service = new SignableDocumentEService();
		}
		return service;
	}

	RestTemplate restTemplate = null;
	public RestTemplate getRestTemplate() {
		if(restTemplate == null) {
		restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
			converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
			messageConverters.add(converter);
			restTemplate.setMessageConverters(messageConverters);
		}
		return restTemplate;
	}

	public SignableEDocument get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<SignableEDocument> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, SignableEDocument.class);
		return response.getBody();
	}

	public SignableEDocument[] getAll() {
		String url = endpointUrl;
		logger.info("getProducts: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<SignableEDocument[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, SignableEDocument[].class);
		SignableEDocument[] signableEDocuments = response.getBody();
		return signableEDocuments;
	}

	public SignableEDocument create(SignableEDocument signableEDocument) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<SignableEDocument> request = new HttpEntity<>(signableEDocument, headers);
		final ResponseEntity<SignableEDocument> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, SignableEDocument.class);
		return response.getBody();
	}
	public SignableEDocument update(SignableEDocument signableEDocument) {
		logger.info("update: " + signableEDocument.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<SignableEDocument> request = new HttpEntity<>(signableEDocument, headers);
		final ResponseEntity<SignableEDocument> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, SignableEDocument.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<SignableEDocument> request = new HttpEntity<>(null, headers);
		final ResponseEntity<SignableEDocument> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, SignableEDocument.class);
	}
}
