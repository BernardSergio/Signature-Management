package Sign.sergio.esigns.service;
import Sign.sergio.esigns.model.SignatureEStatus;
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
public class SignatureStatusEService {
	Logger logger = LoggerFactory.getLogger(SignatureStatusEService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/status";

	protected static SignatureStatusEService service= null;
	public static SignatureStatusEService getService(){
		if(service == null){
			service = new SignatureStatusEService();
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

	public SignatureEStatus get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<SignatureEStatus> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, SignatureEStatus.class);
		return response.getBody();
	}

	public SignatureEStatus[] getAll() {
		String url = endpointUrl;
		logger.info("getStatuss: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<SignatureEStatus[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, SignatureEStatus[].class);
		SignatureEStatus[] statusses = response.getBody();
		return statusses;
	}

	public SignatureEStatus create(SignatureEStatus signatureEStatus) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<SignatureEStatus> request = new HttpEntity<>(signatureEStatus, headers);
		final ResponseEntity<SignatureEStatus> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, SignatureEStatus.class);
		return response.getBody();
	}
	public SignatureEStatus update(SignatureEStatus signatureEStatus) {
		logger.info("update: " + signatureEStatus.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<SignatureEStatus> request = new HttpEntity<>(signatureEStatus, headers);
		final ResponseEntity<SignatureEStatus> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, SignatureEStatus.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<SignatureEStatus> request = new HttpEntity<>(null, headers);
		final ResponseEntity<SignatureEStatus> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, SignatureEStatus.class);
	}
}
