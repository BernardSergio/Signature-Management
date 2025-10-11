package Sign.sergio.esigns.model;

import lombok.Data;
import java.util.Date;

@Data
public class DocumentStorage {
	private int id;
	private String storageName;  // clearer field name

	private Date lastUpdated;
	private Date created;
}
