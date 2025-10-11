package Sign.sergio.esigns.transform;

import Sign.sergio.esigns.entity.SignatureRequestData;
import Sign.sergio.esigns.model.SignatureRequest;

public interface TransformSignatureRequestService {
	SignatureRequestData transform(SignatureRequest signatureRequest);
	SignatureRequest transform(SignatureRequestData signatureRequestData);
}
