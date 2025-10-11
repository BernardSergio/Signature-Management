package Sign.sergio.esigns.transform;

import Sign.sergio.esigns.entity.SignatureRequestData;
import Sign.sergio.esigns.model.SignatureRequest;
import org.springframework.stereotype.Service;

@Service
public class TransformSignatureRequestServiceImpl implements TransformSignatureRequestService {

	@Override
	public SignatureRequest transform(SignatureRequestData data) {
		if (data == null) return null;

		SignatureRequest model = new SignatureRequest();

		model.setId(data.getId());
		model.setRequestName(data.getRequestTitle());
		model.setDescription(data.getRequestDescription());

		model.setDocumentId(data.getDocumentId());
		model.setDocumentName(data.getDocumentName());

		model.setCategoryId(data.getCategoryId());
		model.setCategoryName(data.getCategoryName());

		model.setStatusId(data.getStatusId());
		model.setStatusName(data.getStatusLabel());

		model.setStorageId(data.getStorageId());
		model.setStorageName(data.getStorageName());

		model.setLastUpdated(data.getLastUpdated());
		model.setCreated(data.getCreated());

		return model;
	}

	@Override
	public SignatureRequestData transform(SignatureRequest model) {
		if (model == null) return null;

		SignatureRequestData data = new SignatureRequestData();

		data.setId(model.getId());
		data.setRequestTitle(model.getRequestName());
		data.setRequestDescription(model.getDescription());

		data.setDocumentId(model.getDocumentId());
		data.setDocumentName(model.getDocumentName());

		data.setCategoryId(model.getCategoryId());
		data.setCategoryName(model.getCategoryName());

		data.setStatusId(model.getStatusId());
		data.setStatusLabel(model.getStatusName());

		data.setStorageId(model.getStorageId());
		data.setStorageName(model.getStorageName());

		// created and lastUpdated are managed by Hibernate annotations,
		// so you generally don't set them manually here.

		return data;
	}
}
