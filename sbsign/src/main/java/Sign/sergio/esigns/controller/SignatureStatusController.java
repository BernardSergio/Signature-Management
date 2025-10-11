package Sign.sergio.esigns.controller;

import Sign.sergio.esigns.model.SignatureStatus;
import Sign.sergio.esigns.service.SignatureStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class SignatureStatusController {

	private static final Logger logger = LoggerFactory.getLogger(SignatureStatusController.class);

	private final SignatureStatusService signatureStatusService;

	public SignatureStatusController(SignatureStatusService signatureStatusService) {
		this.signatureStatusService = signatureStatusService;
	}

	@GetMapping
	public ResponseEntity<List<SignatureStatus>> listStatus() {
		try {
			List<SignatureStatus> signatureStatuses = signatureStatusService.getAll();
			return ResponseEntity.ok(signatureStatuses);
		} catch (Exception ex) {
			logger.error("Error retrieving signature statuses", ex);
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<SignatureStatus> getStatus(@PathVariable Integer id) {
		try {
			SignatureStatus signatureStatus = signatureStatusService.get(id);
			if (signatureStatus == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(signatureStatus);
		} catch (Exception ex) {
			logger.error("Error retrieving status with id {}", id, ex);
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping
	public ResponseEntity<SignatureStatus> addStatus(@RequestBody SignatureStatus signatureStatus) {
		try {
			logger.info("Creating status: {}", signatureStatus);
			SignatureStatus created = signatureStatusService.create(signatureStatus);
			return ResponseEntity.ok(created);
		} catch (Exception ex) {
			logger.error("Error creating status", ex);
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<SignatureStatus> updateStatus(@PathVariable Integer id, @RequestBody SignatureStatus signatureStatus) {
		try {
			logger.info("Updating status with id {}: {}", id, signatureStatus);
			// Option 1: Use only signatureStatus
			SignatureStatus updated = signatureStatusService.update(signatureStatus);

			// Option 2: If you want to match id, do it inside the service manually
			// signatureStatus.setId(id);

			if (updated == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(updated);
		} catch (Exception ex) {
			logger.error("Error updating status with id {}", id, ex);
			return ResponseEntity.status(500).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStatus(@PathVariable Integer id) {
		try {
			// Simply call delete without expecting a return value
			signatureStatusService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (Exception ex) {
			logger.error("Error deleting status with id {}", id, ex);
			return ResponseEntity.status(500).build();
		}
	}
}
