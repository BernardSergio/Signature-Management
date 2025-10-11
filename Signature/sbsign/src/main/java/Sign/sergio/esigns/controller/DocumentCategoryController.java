package Sign.sergio.esigns.controller;

import Sign.sergio.esigns.model.DocumentCategory;
import Sign.sergio.esigns.service.DocumentCategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class DocumentCategoryController {

	private final Logger logger = LoggerFactory.getLogger(DocumentCategoryController.class);

	private final DocumentCategoryService documentCategoryService;

	public DocumentCategoryController(DocumentCategoryService documentCategoryService) {
		this.documentCategoryService = documentCategoryService;
	}

	@GetMapping
	public ResponseEntity<List<DocumentCategory>> getAllCategories() {
		List<DocumentCategory> categories = documentCategoryService.getAll();
		return ResponseEntity.ok(categories);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DocumentCategory> getCategoryById(@PathVariable int id) {
		DocumentCategory category = documentCategoryService.get(id);
		if (category == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(category);
	}

	@PostMapping
	public ResponseEntity<DocumentCategory> addCategory(@RequestBody DocumentCategory documentCategory) {
		DocumentCategory savedCategory = documentCategoryService.create(documentCategory);
		return ResponseEntity.ok(savedCategory);
	}

	@PutMapping("/{id}")
	public ResponseEntity<DocumentCategory> updateCategory(@PathVariable int id, @RequestBody DocumentCategory documentCategory) {
		logger.info("Update Input >> {}", documentCategory);
		documentCategory.setId(id); // Make sure the ID is set in the object
		DocumentCategory updatedCategory = documentCategoryService.update(documentCategory);
		if (updatedCategory == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(updatedCategory);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
		DocumentCategory category = documentCategoryService.get(id);
		if (category == null) {
			return ResponseEntity.notFound().build();
		}
		documentCategoryService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
