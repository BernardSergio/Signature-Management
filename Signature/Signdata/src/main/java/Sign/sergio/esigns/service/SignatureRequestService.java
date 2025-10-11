package Sign.sergio.esigns.service;

import Sign.sergio.esigns.model.SignatureRequest;
import java.util.List;

public interface SignatureRequestService {

	List<SignatureRequest> getAll();

	SignatureRequest get(Integer id);

	SignatureRequest create(SignatureRequest signatureRequest);

	SignatureRequest update(SignatureRequest signatureRequest);

	boolean delete(Integer id);
}
