package Sign.sergio.esigns.serviceimpl;

import Sign.sergio.esigns.entity.SignatureStatusData;
import Sign.sergio.esigns.model.SignatureStatus;
import Sign.sergio.esigns.repository.SignatureStatusRepository;
import Sign.sergio.esigns.service.SignatureStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SignatureStatusServiceImpl implements SignatureStatusService {
	Logger logger = LoggerFactory.getLogger(SignatureStatusServiceImpl.class);

	@Autowired
	SignatureStatusRepository signatureStatusRepository;

	@Override
	public List<SignatureStatus> getAll() {
		List<SignatureStatusData> dataList = new ArrayList<>();
		List<SignatureStatus> statusList = new ArrayList<>();
		signatureStatusRepository.findAll().forEach(dataList::add);

		for (SignatureStatusData data : dataList) {
			SignatureStatus status = new SignatureStatus();
			status.setId(data.getId());
			status.setStatusName(data.getStatusLabel());
			status.setCreated(data.getCreated());
			status.setLastUpdated(data.getLastUpdated());
			statusList.add(status);
		}

		return statusList;
	}

	@Override
	public SignatureStatus create(SignatureStatus signatureStatus) {
		logger.info("Add: Input " + signatureStatus.toString());

		SignatureStatusData data = new SignatureStatusData();
		data.setStatusLabel(signatureStatus.getStatusName());
		data = signatureStatusRepository.save(data);

		SignatureStatus newStatus = new SignatureStatus();
		newStatus.setId(data.getId());
		newStatus.setStatusName(data.getStatusLabel());
		newStatus.setCreated(data.getCreated());
		newStatus.setLastUpdated(data.getLastUpdated());

		return newStatus;
	}

	@Override
	public SignatureStatus update(SignatureStatus signatureStatus) {
		SignatureStatus updatedStatus = null;
		int id = signatureStatus.getId();

		Optional<SignatureStatusData> optional = signatureStatusRepository.findById(id);
		if (optional.isPresent()) {
			SignatureStatusData existingData = optional.get();

			existingData.setStatusLabel(signatureStatus.getStatusName());
			// Preserve created date is automatic, no need to reset

			SignatureStatusData updatedData = signatureStatusRepository.save(existingData);

			updatedStatus = new SignatureStatus();
			updatedStatus.setId(updatedData.getId());
			updatedStatus.setStatusName(updatedData.getStatusLabel());
			updatedStatus.setCreated(updatedData.getCreated());
			updatedStatus.setLastUpdated(updatedData.getLastUpdated());
		} else {
			logger.error("Status record with id: " + id + " does not exist");
		}
		return updatedStatus;
	}

	@Override
	public SignatureStatus get(Integer id) {
		logger.info("Input id >> " + id);
		SignatureStatus status = null;

		Optional<SignatureStatusData> optional = signatureStatusRepository.findById(id);
		if (optional.isPresent()) {
			SignatureStatusData data = optional.get();

			status = new SignatureStatus();
			status.setId(data.getId());
			status.setStatusName(data.getStatusLabel());
			status.setCreated(data.getCreated());
			status.setLastUpdated(data.getLastUpdated());
		} else {
			logger.info("Failed >> unable to locate id: " + id);
		}

		return status;
	}

	@Override
	public void delete(Integer id) {
		logger.info("Input >> " + id);

		Optional<SignatureStatusData> optional = signatureStatusRepository.findById(id);
		if (optional.isPresent()) {
			signatureStatusRepository.delete(optional.get());
			logger.info("Successfully deleted Status record with id: " + id);
		} else {
			logger.error("Unable to locate status with id: " + id);
		}
	}
}
