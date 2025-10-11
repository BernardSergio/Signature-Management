package Sign.sergio.esigns.controller;

import Sign.sergio.esigns.model.DocumentStorage;
import Sign.sergio.esigns.service.DocumentStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class DocumentStorageController {

	private static final Logger logger = LoggerFactory.getLogger(DocumentStorageController.class);

	private final DocumentStorageService documentStorageService;

	public DocumentStorageController(DocumentStorageService documentStorageService) {
		this.documentStorageService = documentStorageService;
	}

	@GetMapping
	public ResponseEntity<List<DocumentStorage>> listInventory() {
		try {
			List<DocumentStorage> inventoryList = documentStorageService.getAll();
			return ResponseEntity.ok(inventoryList);
		} catch (Exception ex) {
			logger.error("Error fetching inventory list", ex);
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<DocumentStorage> getInventoryById(@PathVariable Integer id) {
		try {
			DocumentStorage documentStorage = documentStorageService.get(id);
			if (documentStorage == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(documentStorage);
		} catch (Exception ex) {
			logger.error("Error fetching inventory with id: {}", id, ex);
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping
	public ResponseEntity<DocumentStorage> addInventory(@RequestBody DocumentStorage documentStorage) {
		try {
			logger.info("Adding inventory: {}", documentStorage);
			DocumentStorage created = documentStorageService.create(documentStorage);
			return ResponseEntity.ok(created);
		} catch (Exception ex) {
			logger.error("Error creating inventory", ex);
			return ResponseEntity.internalServerError().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<DocumentStorage> updateInventory(@PathVariable Integer id, @RequestBody DocumentStorage documentStorage) {
		try {
			logger.info("Updating inventory id {} with data: {}", id, documentStorage);
			documentStorage.setId(id);  // set ID manually before update
			DocumentStorage updated = documentStorageService.update(documentStorage);
			if (updated == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(updated);
		} catch (Exception ex) {
			logger.error("Error updating inventory with id: {}", id, ex);
			return ResponseEntity.internalServerError().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteInventory(@PathVariable Integer id) {
		try {
			documentStorageService.delete(id); // no boolean check
			return ResponseEntity.noContent().build();
		} catch (Exception ex) {
			logger.error("Error deleting inventory with id: {}", id, ex);
			return ResponseEntity.internalServerError().build();
		}
	}
}
