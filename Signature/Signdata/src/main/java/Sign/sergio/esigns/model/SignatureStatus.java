package Sign.sergio.esigns.model;

import lombok.Data;
import java.util.Date;

@Data
public class SignatureStatus {
	private int id;
	private String statusName;
	private Date lastUpdated;
	private Date created;
}
