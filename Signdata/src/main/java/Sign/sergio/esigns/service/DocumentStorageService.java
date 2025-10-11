package Sign.sergio.esigns.service;

import Sign.sergio.esigns.model.DocumentStorage;
import java.util.List;

public interface DocumentStorageService {

	List<DocumentStorage> getAll();

	DocumentStorage get(Integer id);

	DocumentStorage create(DocumentStorage documentStorage);

	DocumentStorage update(DocumentStorage documentStorage);

	void delete(Integer id);

}
