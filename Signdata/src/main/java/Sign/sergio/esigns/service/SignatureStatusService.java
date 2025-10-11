package Sign.sergio.esigns.service;

import Sign.sergio.esigns.model.SignatureStatus;
import java.util.List;

public interface SignatureStatusService {

	List<SignatureStatus> getAll();

	SignatureStatus get(Integer id);

	SignatureStatus create(SignatureStatus signatureStatus);

	SignatureStatus update(SignatureStatus signatureStatus);

	void delete(Integer id);
}
