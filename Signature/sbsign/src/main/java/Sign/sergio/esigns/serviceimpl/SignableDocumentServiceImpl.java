package Sign.sergio.esigns.serviceimpl;

import Sign.sergio.esigns.entity.SignableDocumentData;
import Sign.sergio.esigns.model.SignableDocument;
import Sign.sergio.esigns.repository.SignableDocumentRepository;
import Sign.sergio.esigns.service.SignableDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SignableDocumentServiceImpl implements SignableDocumentService {
	Logger logger = LoggerFactory.getLogger(SignableDocumentServiceImpl.class);

	@Autowired
	SignableDocumentRepository signableDocumentRepository;

	@Override
	public List<SignableDocument> getAll() {
		List<SignableDocumentData> dataList = new ArrayList<>();
		List<SignableDocument> signableDocuments = new ArrayList<>();
		signableDocumentRepository.findAll().forEach(dataList::add);

		for (SignableDocumentData data : dataList) {
			SignableDocument doc = new SignableDocument();
			doc.setId(data.getId());
			doc.setDocumentName(data.getDocumentTitle());
			doc.setCreated(data.getCreated());
			doc.setLastUpdated(data.getLastUpdated());
			signableDocuments.add(doc);
		}

		return signableDocuments;
	}

	@Override
	public SignableDocument create(SignableDocument signableDocument) {
		logger.info("Add: Input " + signableDocument.toString());

		SignableDocumentData data = new SignableDocumentData();
		data.setDocumentTitle(signableDocument.getDocumentName());

		data = signableDocumentRepository.save(data);

		SignableDocument newDoc = new SignableDocument();
		newDoc.setId(data.getId());
		newDoc.setDocumentName(data.getDocumentTitle());
		newDoc.setCreated(data.getCreated());
		newDoc.setLastUpdated(data.getLastUpdated());

		return newDoc;
	}

	@Override
	public SignableDocument update(SignableDocument signableDocument) {
		SignableDocument updatedDoc = null;
		int id = signableDocument.getId();

		Optional<SignableDocumentData> optional = signableDocumentRepository.findById(id);

		if (optional.isPresent()) {
			SignableDocumentData existingData = optional.get();

			// Update only mutable fields
			existingData.setDocumentTitle(signableDocument.getDocumentName());

			SignableDocumentData savedData = signableDocumentRepository.save(existingData);

			updatedDoc = new SignableDocument();
			updatedDoc.setId(savedData.getId());
			updatedDoc.setDocumentName(savedData.getDocumentTitle());
			updatedDoc.setCreated(savedData.getCreated());
			updatedDoc.setLastUpdated(savedData.getLastUpdated());
		} else {
			logger.error("SignableDocument record with id: " + id + " does not exist");
		}

		return updatedDoc;
	}

	@Override
	public SignableDocument get(Integer id) {
		logger.info("Input id >> " + id);
		SignableDocument doc = null;

		Optional<SignableDocumentData> optional = signableDocumentRepository.findById(id);

		if (optional.isPresent()) {
			SignableDocumentData data = optional.get();

			doc = new SignableDocument();
			doc.setId(data.getId());
			doc.setDocumentName(data.getDocumentTitle());
			doc.setCreated(data.getCreated());
			doc.setLastUpdated(data.getLastUpdated());
		} else {
			logger.info("Failed >> unable to locate id: " + id);
		}

		return doc;
	}

	@Override
	public boolean delete(Integer id) {
		logger.info("Input >> " + id);

		Optional<SignableDocumentData> optional = signableDocumentRepository.findById(id);

		if (optional.isPresent()) {
			signableDocumentRepository.delete(optional.get());
			logger.info("Successfully deleted SignableDocument record with id: " + id);
			return true;
		} else {
			logger.error("Unable to locate SignableDocument with id: " + id);
			return false;
		}
	}
}
