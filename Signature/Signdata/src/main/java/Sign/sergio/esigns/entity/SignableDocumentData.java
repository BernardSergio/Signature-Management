package Sign.sergio.esigns.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "signable_documents")
public class SignableDocumentData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String documentTitle;     // title of the document, e.g., "Employee NDA"
	private String filePath;          // path to the actual uploaded file
	private String fileType;          // e.g., PDF, DOCX
	private String uploadedBy;        // user who submitted the document

	private boolean isSigned;         // true if completed
	private int totalSigners;         // number of required signers
	private int completedSigners;     // how many have signed

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date lastUpdated;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date created;
}
