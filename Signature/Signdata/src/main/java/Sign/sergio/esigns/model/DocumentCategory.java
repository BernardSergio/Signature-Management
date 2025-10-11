package Sign.sergio.esigns.model;

import lombok.Data;
import java.util.Date;

@Data
public class DocumentCategory {
	private int id;
	private String categoryName;  // clearer field name

	private Date lastUpdated;
	private Date created;
}
