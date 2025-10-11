package Sign.sergio.esigns.service;
import Sign.sergio.esigns.model.SignatureRequest;
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
public class SignatureRequestEService {
	Logger logger = LoggerFactory.getLogger(SignatureRequestEService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/signreq";

	protected static SignatureRequestEService service= null;
	public static SignatureRequestEService getService(){
		if(service == null){
			service = new SignatureRequestEService();
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

	public SignatureRequest get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // 👈 ADD THIS
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<SignatureRequest> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, SignatureRequest.class);
		return response.getBody();
	}

	public SignatureRequest[] getAll() {
		String url = endpointUrl;
		logger.info("getEcommerces: " + url);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // 👈 ADD THIS
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<SignatureRequest[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, SignatureRequest[].class);
		SignatureRequest[] signatureRequests = response.getBody();
		return signatureRequests;
	}

	public SignatureRequest create(SignatureRequest signatureRequest) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); // 👈 ADD THIS
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // 👈 ADD THIS
		HttpEntity<SignatureRequest> request = new HttpEntity<>(signatureRequest, headers);
		final ResponseEntity<SignatureRequest> response =
				getRestTemplate().exchange(url, HttpMethod.POST, request, SignatureRequest.class); // FIXED: PUT → POST
		return response.getBody();
	}

	public SignatureRequest update(SignatureRequest signatureRequest) {
		Integer id = signatureRequest.getId();
		if (id == null) {
			throw new IllegalArgumentException("Cannot update: Signature ID is null");
		}

		String url = endpointUrl + "/" + id;
		logger.info("Updating SignatureRequest at URL: " + url);
		logger.info("Payload: " + signatureRequest.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // 👈 ADD THIS
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<SignatureRequest> request = new HttpEntity<>(signatureRequest, headers);

		final ResponseEntity<SignatureRequest> response =
				getRestTemplate().exchange(url, HttpMethod.PUT, request, SignatureRequest.class);

		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + id);
		String url = endpointUrl + "/" + id; // FIXED: removed space
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // 👈 ADD THIS
		HttpEntity<SignatureRequest> request = new HttpEntity<>(null, headers);
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Void.class); // no need to parse response body
	}

}
