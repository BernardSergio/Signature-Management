package Sign.sergio.esigns.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "document_storage")
public class DocumentStorageData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String fileName;         // e.g., "contract_2024.pdf"
	private String fileType;         // e.g., "PDF", "DOCX"
	private long fileSize;           // size in bytes
	private String storagePath;      // where it’s stored (local or cloud path)
	private String uploadedBy;       // user who uploaded

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date lastUpdated;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date created;
}
