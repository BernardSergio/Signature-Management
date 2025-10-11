package Sign.sergio.esigns.service;

import Sign.sergio.esigns.model.DocumentEStorage;
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
public class DocumentStorageEService {
	Logger logger = LoggerFactory.getLogger(DocumentStorageEService.class);

	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/signreq"; // Updated for Signature Management context

	private static DocumentStorageEService service = null;

	public static DocumentStorageEService getService() {
		if (service == null) {
			service = new DocumentStorageEService();
		}
		return service;
	}

	private RestTemplate restTemplate = null;

	public RestTemplate getRestTemplate() {
		if (restTemplate == null) {
			restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
			converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
			messageConverters.add(converter);
			restTemplate.setMessageConverters(messageConverters);
		}
		return restTemplate;
	}

	public DocumentEStorage get(Integer id) {
		String url = endpointUrl + "/" + id;
		logger.info("GET DocumentStorage: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Void> request = new HttpEntity<>(null, headers);
		ResponseEntity<DocumentEStorage> response = getRestTemplate().exchange(
				url, HttpMethod.GET, request, DocumentEStorage.class);
		return response.getBody();
	}

	public DocumentEStorage[] getAll() {
		String url = endpointUrl;
		logger.info("GET All DocumentStorages: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Void> request = new HttpEntity<>(null, headers);
		ResponseEntity<DocumentEStorage[]> response = getRestTemplate().exchange(
				url, HttpMethod.GET, request, DocumentEStorage[].class);
		return response.getBody();
	}

	public DocumentEStorage create(DocumentEStorage documentEStorage) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<DocumentEStorage> request = new HttpEntity<>(documentEStorage, headers);
		ResponseEntity<DocumentEStorage> response = getRestTemplate().exchange(
				url, HttpMethod.PUT, request, DocumentEStorage.class);
		return response.getBody();
	}

	public DocumentEStorage update(DocumentEStorage documentEStorage) {
		logger.info("UPDATE DocumentStorage: " + documentEStorage);
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<DocumentEStorage> request = new HttpEntity<>(documentEStorage, headers);
		ResponseEntity<DocumentEStorage> response = getRestTemplate().exchange(
				url, HttpMethod.POST, request, DocumentEStorage.class);
		return response.getBody();
	}

	public void delete(Integer id) {
		logger.info("DELETE DocumentStorage ID: " + id);
		String url = endpointUrl + "/" + id; // fixed wrong spacing with slash
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Void> request = new HttpEntity<>(null, headers);
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Void.class);
	}
}
