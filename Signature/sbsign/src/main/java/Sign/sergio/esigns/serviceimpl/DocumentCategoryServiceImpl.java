package Sign.sergio.esigns.serviceimpl;

import Sign.sergio.esigns.entity.DocumentCategoryData;
import Sign.sergio.esigns.model.DocumentCategory;
import Sign.sergio.esigns.repository.DocumentCategoryRepository;
import Sign.sergio.esigns.service.DocumentCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DocumentCategoryServiceImpl implements DocumentCategoryService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentCategoryServiceImpl.class);

	@Autowired
	private DocumentCategoryRepository documentCategoryRepository;

	@Override
	public List<DocumentCategory> getAll() {
		List<DocumentCategoryData> categoryDataList = new ArrayList<>();
		documentCategoryRepository.findAll().forEach(categoryDataList::add);

		List<DocumentCategory> documentCategories = new ArrayList<>();
		for (DocumentCategoryData data : categoryDataList) {
			DocumentCategory category = mapToModel(data);
			documentCategories.add(category);
		}

		return documentCategories;
	}

	@Override
	public DocumentCategory create(DocumentCategory documentCategory) {
		logger.info("Creating new category: {}", documentCategory);

		DocumentCategoryData data = new DocumentCategoryData();
		data.setCategoryName(documentCategory.getCategoryName());
		data = documentCategoryRepository.save(data);

		DocumentCategory createdCategory = mapToModel(data);
		logger.info("Created category: {}", createdCategory);

		return createdCategory;
	}


	@Override
	public DocumentCategory update(DocumentCategory documentCategory) {
		Optional<DocumentCategoryData> optionalData = documentCategoryRepository.findById(documentCategory.getId());
		if (optionalData.isPresent()) {
			DocumentCategoryData existingData = optionalData.get();
			existingData.setCategoryName(documentCategory.getCategoryName());
			existingData = documentCategoryRepository.save(existingData);
			return mapToModel(existingData);
		} else {
			logger.error("Category with id {} does not exist", documentCategory.getId());
			return null;
		}
	}


	@Override
	public DocumentCategory get(Integer id) {
		Optional<DocumentCategoryData> optionalData = documentCategoryRepository.findById(id);
		if (optionalData.isPresent()) {
			return mapToModel(optionalData.get());
		} else {
			logger.warn("Category with id {} not found", id);
			return null;
		}
	}

	@Override
	public void delete(Integer id) {
		Optional<DocumentCategoryData> optionalData = documentCategoryRepository.findById(id);
		if (optionalData.isPresent()) {
			documentCategoryRepository.delete(optionalData.get());
			logger.info("Deleted category with id {}", id);
		} else {
			logger.error("Category with id {} not found for deletion", id);
		}
	}

	private DocumentCategory mapToModel(DocumentCategoryData data) {
		DocumentCategory model = new DocumentCategory();
		model.setId(data.getId());
		model.setCategoryName(data.getCategoryName());
		model.setCreated(data.getCreated());
		model.setLastUpdated(data.getLastUpdated());
		return model;
	}
}
