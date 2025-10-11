package Sign.sergio.esigns.controller;

import Sign.sergio.esigns.model.SignatureRequest;
import Sign.sergio.esigns.service.SignatureRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping("/api/signreq")
public class 	SignatureRequestController {

	private static final Logger logger = LoggerFactory.getLogger(SignatureRequestController.class);

	private final SignatureRequestService signatureRequestService;

	public SignatureRequestController(SignatureRequestService signatureRequestService) {
		this.signatureRequestService = signatureRequestService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SignatureRequest>> listSignatureRequests() {
		try {
			List<SignatureRequest> signatureRequests = signatureRequestService.getAll();
			return ResponseEntity.ok(signatureRequests);
		} catch (Exception ex) {
			logger.error("Error retrieving signature requests", ex);
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SignatureRequest> getSignatureRequest(@PathVariable Integer id) {
		try {
			SignatureRequest request = signatureRequestService.get(id);
			if (request == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(request);
		} catch (Exception ex) {
			logger.error("Error retrieving signature request with id {}", id, ex);
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping
	public ResponseEntity<SignatureRequest> addSignatureRequest(@RequestBody SignatureRequest signatureRequest) {
		try {
			logger.info("Adding signature request: {}", signatureRequest);
			SignatureRequest created = signatureRequestService.create(signatureRequest);
			return ResponseEntity.ok(created);
		} catch (Exception ex) {
			logger.error("Error creating signature request", ex);
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<SignatureRequest> updateSignatureRequest(@PathVariable Integer id, @RequestBody SignatureRequest signatureRequest) {
		try {
			logger.info("Updating signature request with id {}: {}", id, signatureRequest);

			// Set the ID here
			signatureRequest.setId(id);

			// Call update with just the object
			SignatureRequest updated = signatureRequestService.update(signatureRequest);

			if (updated == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(updated);
		} catch (Exception ex) {
			logger.error("Error updating signature request with id {}", id, ex);
			return ResponseEntity.status(500).build();
		}
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSignatureRequest(@PathVariable Integer id) {
		try {
			boolean deleted = signatureRequestService.delete(id);
			if (!deleted) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.noContent().build();
		} catch (Exception ex) {
			logger.error("Error deleting signature request with id {}", id, ex);
			return ResponseEntity.status(500).build();
		}
	}
}
