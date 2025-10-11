package Sign.sergio.esigns.model;

import lombok.Data;
import java.util.Date;

@Data
public class SignatureRequest {
	private int id;
	private String requestName;
	private String description;

	private int documentId;
	private String documentName;

	private int categoryId;
	private String categoryName;

	private int statusId;
	private String statusName;

	private int storageId;
	private String storageName;

	private Date lastUpdated;
	private Date created;

	@Override
	public String toString(){
		return requestName;
	}
}
