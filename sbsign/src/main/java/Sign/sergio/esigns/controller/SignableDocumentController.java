package Sign.sergio.esigns.controller;

import Sign.sergio.esigns.model.SignableDocument;
import Sign.sergio.esigns.service.SignableDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/signabledcs")
public class SignableDocumentController {

	private static final Logger logger = LoggerFactory.getLogger(SignableDocumentController.class);

	private final SignableDocumentService signableDocumentService;

	public SignableDocumentController(SignableDocumentService signableDocumentService) {
		this.signableDocumentService = signableDocumentService;
	}

	@GetMapping
	public ResponseEntity<List<SignableDocument>> listProducts() {
		try {
			List<SignableDocument> products = signableDocumentService.getAll();
			return ResponseEntity.ok(products);
		} catch (Exception ex) {
			logger.error("Error retrieving products", ex);
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<SignableDocument> getProduct(@PathVariable Integer id) {
		try {
			SignableDocument product = signableDocumentService.get(id);
			if (product == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(product);
		} catch (Exception ex) {
			logger.error("Error retrieving product with id {}", id, ex);
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping
	public ResponseEntity<SignableDocument> addProduct(@RequestBody SignableDocument signableDocument) {
		try {
			logger.info("Adding product: {}", signableDocument);
			SignableDocument created = signableDocumentService.create(signableDocument);
			return ResponseEntity.ok(created);
		} catch (Exception ex) {
			logger.error("Error creating product", ex);
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<SignableDocument> updateProduct(@PathVariable Integer id, @RequestBody SignableDocument signableDocument) {
		try {
			logger.info("Updating product with id {}: {}", id, signableDocument);

			// 🔧 Set the ID before passing to service
			signableDocument.setId(id);

			SignableDocument updated = signableDocumentService.update(signableDocument);
			if (updated == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(updated);
		} catch (Exception ex) {
			logger.error("Error updating product with id {}", id, ex);
			return ResponseEntity.status(500).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
		try {
			boolean deleted = signableDocumentService.delete(id);
			if (!deleted) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.noContent().build();
		} catch (Exception ex) {
			logger.error("Error deleting product with id {}", id, ex);
			return ResponseEntity.status(500).build();
		}
	}
}


