package Sign.sergio.esigns.service;

import Sign.sergio.esigns.model.SignableDocument;
import java.util.List;

public interface SignableDocumentService {

	List<SignableDocument> getAll();

	SignableDocument get(Integer id);

	SignableDocument create(SignableDocument signableDocument);

	SignableDocument update(SignableDocument signableDocument);

	boolean delete(Integer id);
}
