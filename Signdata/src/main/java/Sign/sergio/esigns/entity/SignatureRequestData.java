package Sign.sergio.esigns.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "signature_requests")
public class SignatureRequestData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String requestTitle;         // e.g., "HR Onboarding Form Signature"
	private String requestDescription;   // e.g., "Please sign this document for onboarding."

	private int documentId;              // FK to SignableDocumentData
	private String documentName;

	private int categoryId;              // FK to DocumentCategoryData
	private String categoryName;

	private int statusId;                // FK to StatusData
	private String statusLabel;          // e.g., "Pending", "Completed"

	private int storageId;               // FK to DocumentStorageData
	private String storageName;          // optional reference to where it's stored

	private int totalSigners;
	private int completedSigners;
	private boolean isCompleted;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date lastUpdated;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date created;
}
