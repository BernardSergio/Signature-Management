package Sign.sergio.esigns.serviceimpl;

import Sign.sergio.esigns.entity.DocumentStorageData;
import Sign.sergio.esigns.model.DocumentStorage;
import Sign.sergio.esigns.repository.DocumentStorageRepository;
import Sign.sergio.esigns.service.DocumentStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DocumentStorageServiceImpl implements DocumentStorageService {
	Logger logger = LoggerFactory.getLogger(DocumentStorageServiceImpl.class);

	@Autowired
	DocumentStorageRepository documentStorageRepository;

	@Override
	public List<DocumentStorage> getAll() {
		List<DocumentStorageData> storageDataList = new ArrayList<>();
		List<DocumentStorage> documentStorages = new ArrayList<>();
		documentStorageRepository.findAll().forEach(storageDataList::add);

		for (DocumentStorageData data : storageDataList) {
			DocumentStorage storage = new DocumentStorage();
			storage.setId(data.getId());
			storage.setStorageName(data.getFileName()); // FIXED
			storage.setCreated(data.getCreated());
			storage.setLastUpdated(data.getLastUpdated());
			documentStorages.add(storage);
		}

		return documentStorages;
	}

	@Override
	public DocumentStorage create(DocumentStorage documentStorage) {
		logger.info("Add: Input " + documentStorage.toString());

		DocumentStorageData data = new DocumentStorageData();
		data.setFileName(documentStorage.getStorageName()); // FIXED

		data = documentStorageRepository.save(data);

		DocumentStorage newStorage = new DocumentStorage();
		newStorage.setId(data.getId());
		newStorage.setStorageName(data.getFileName()); // FIXED
		newStorage.setCreated(data.getCreated());
		newStorage.setLastUpdated(data.getLastUpdated());

		return newStorage;
	}

	@Override
	public DocumentStorage update(DocumentStorage documentStorage) {
		DocumentStorage updatedStorage = null;
		int id = documentStorage.getId();

		Optional<DocumentStorageData> optional = documentStorageRepository.findById(id);

		if (optional.isPresent()) {
			DocumentStorageData existingData = optional.get();

			existingData.setFileName(documentStorage.getStorageName()); // FIXED

			existingData.setCreated(existingData.getCreated());

			DocumentStorageData savedData = documentStorageRepository.save(existingData);

			updatedStorage = new DocumentStorage();
			updatedStorage.setId(savedData.getId());
			updatedStorage.setStorageName(savedData.getFileName()); // FIXED
			updatedStorage.setCreated(savedData.getCreated());
			updatedStorage.setLastUpdated(savedData.getLastUpdated());
		} else {
			logger.error("Inventory record with id: " + id + " does not exist");
		}

		return updatedStorage;
	}

	@Override
	public DocumentStorage get(Integer id) {
		logger.info("Input id >> " + id);
		DocumentStorage documentStorage = null;

		Optional<DocumentStorageData> optional = documentStorageRepository.findById(id);

		if (optional.isPresent()) {
			DocumentStorageData data = optional.get();

			documentStorage = new DocumentStorage();
			documentStorage.setId(data.getId());
			documentStorage.setStorageName(data.getFileName()); // FIXED
			documentStorage.setCreated(data.getCreated());
			documentStorage.setLastUpdated(data.getLastUpdated());
		} else {
			logger.info("Failed >> unable to locate id: " + id);
		}

		return documentStorage;
	}

	@Override
	public void delete(Integer id) {
		logger.info("Input >> " + id);

		Optional<DocumentStorageData> optional = documentStorageRepository.findById(id);

		if (optional.isPresent()) {
			documentStorageRepository.delete(optional.get());
			logger.info("Successfully deleted Inventory record with id: " + id);
		} else {
			logger.error("Unable to locate inventory with id: " + id);
		}
	}
}
