package Sign.sergio.esigns.service;

import Sign.sergio.esigns.model.DocumentECategory;
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
public class DocumentCategoryEService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentCategoryEService.class);

	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/category";

	private static DocumentCategoryEService service = null;

	public static DocumentCategoryEService getService() {
		if (service == null) {
			service = new DocumentCategoryEService();
		}
		return service;
	}

	private RestTemplate restTemplate = null;

	private RestTemplate getRestTemplate() {
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

	public DocumentECategory get(Integer id) {
		String url = endpointUrl + "/" + id;
		logger.info("get: " + url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> request = new HttpEntity<>(null, headers);

		ResponseEntity<DocumentECategory> response =
				getRestTemplate().exchange(url, HttpMethod.GET, request, DocumentECategory.class);

		return response.getBody();
	}

	public DocumentECategory[] getAll() {
		String url = endpointUrl;
		logger.info("getAll: " + url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> request = new HttpEntity<>(null, headers);

		ResponseEntity<DocumentECategory[]> response =
				getRestTemplate().exchange(url, HttpMethod.GET, request, DocumentECategory[].class);

		return response.getBody();
	}

	public DocumentECategory create(DocumentECategory documentECategory) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<DocumentECategory> request = new HttpEntity<>(documentECategory, headers);

		ResponseEntity<DocumentECategory> response =
				getRestTemplate().exchange(url, HttpMethod.PUT, request, DocumentECategory.class);

		return response.getBody();
	}

	public DocumentECategory update(DocumentECategory documentECategory) {
		logger.info("update: " + documentECategory.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<DocumentECategory> request = new HttpEntity<>(documentECategory, headers);

		ResponseEntity<DocumentECategory> response =
				getRestTemplate().exchange(url, HttpMethod.POST, request, DocumentECategory.class);

		return response.getBody();
	}

	public void delete(Integer id) {
		logger.info("delete: " + id);
		String url = endpointUrl + "/" + id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> request = new HttpEntity<>(null, headers);

		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Void.class);
	}
}
