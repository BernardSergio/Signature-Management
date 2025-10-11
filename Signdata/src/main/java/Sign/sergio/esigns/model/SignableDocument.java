package Sign.sergio.esigns.model;

import lombok.Data;
import java.util.Date;

@Data
public class SignableDocument {
	private int id;
	private String documentName;  // clearer field name

	private Date lastUpdated;
	private Date created;
}
