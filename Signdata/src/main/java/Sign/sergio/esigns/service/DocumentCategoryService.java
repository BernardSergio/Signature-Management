package Sign.sergio.esigns.service;

import Sign.sergio.esigns.model.DocumentCategory;
import java.util.List;

public interface DocumentCategoryService {

	List<DocumentCategory> getAll();

	DocumentCategory get(Integer id);

	DocumentCategory create(DocumentCategory documentCategory);

	DocumentCategory update(DocumentCategory documentCategory);

	void delete(Integer id);
}
