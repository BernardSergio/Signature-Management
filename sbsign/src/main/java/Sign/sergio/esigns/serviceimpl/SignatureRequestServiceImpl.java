package Sign.sergio.esigns.serviceimpl;

import Sign.sergio.esigns.entity.SignatureRequestData;
import Sign.sergio.esigns.model.SignatureRequest;
import Sign.sergio.esigns.repository.SignatureRequestRepository;
import Sign.sergio.esigns.service.SignatureRequestService;
import Sign.sergio.esigns.transform.TransformSignatureRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SignatureRequestServiceImpl implements SignatureRequestService {
	Logger logger = LoggerFactory.getLogger(SignatureRequestServiceImpl.class);

	@Autowired
	SignatureRequestRepository signatureRequestRepository;

	@Autowired
	TransformSignatureRequestService transformSignatureRequestService;

	@Override
	public List<SignatureRequest> getAll() {
		List<SignatureRequestData> dataList = new ArrayList<>();
		List<SignatureRequest> signatureRequests = new ArrayList<>();
		signatureRequestRepository.findAll().forEach(dataList::add);

		for (SignatureRequestData data : dataList) {
			SignatureRequest request = transformSignatureRequestService.transform(data);
			signatureRequests.add(request);
		}

		return signatureRequests; // Return List directly, NOT array
	}

	@Override
	public SignatureRequest create(SignatureRequest signatureRequest) {
		logger.info("Add: Input " + signatureRequest.toString());

		SignatureRequestData data = transformSignatureRequestService.transform(signatureRequest);
		data = signatureRequestRepository.save(data);

		SignatureRequest newRequest = transformSignatureRequestService.transform(data);
		return newRequest;
	}

	@Override
	public SignatureRequest update(SignatureRequest signatureRequest) {
		SignatureRequest updatedRequest = null;
		int id = signatureRequest.getId();

		Optional<SignatureRequestData> optional = signatureRequestRepository.findById(id);

		if (optional.isPresent()) {
			SignatureRequestData existingData = optional.get();
			SignatureRequestData updatedData = transformSignatureRequestService.transform(signatureRequest);

			// Preserve created timestamp from existing record
			updatedData.setCreated(existingData.getCreated());

			updatedData = signatureRequestRepository.save(updatedData);
			updatedRequest = transformSignatureRequestService.transform(updatedData);
		} else {
			logger.error("SignatureRequest record with id: " + id + " does not exist");
		}

		return updatedRequest;
	}

	@Override
	public SignatureRequest get(Integer id) {
		logger.info("Input id >> " + id);
		SignatureRequest request = null;

		Optional<SignatureRequestData> optional = signatureRequestRepository.findById(id);
		if (optional.isPresent()) {
			request = transformSignatureRequestService.transform(optional.get());
		} else {
			logger.info("Failed >> unable to locate id: " + id);
		}

		return request;
	}

	@Override
	public boolean delete(Integer id) {
		Optional<SignatureRequestData> optional = signatureRequestRepository.findById(id);
		if (optional.isPresent()) {
			signatureRequestRepository.delete(optional.get());
			logger.info("Successfully deleted SignatureRequest record with id: " + id);
			return true;
		} else {
			logger.error("Unable to locate SignatureRequest with id: " + id);
			return false;
		}
	}
}
