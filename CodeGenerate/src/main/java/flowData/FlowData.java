package flowData;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
public class FlowData {

	private String folderName;
	private String appName;
	private List<DbTable> dbTables;

	@XmlAttribute
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	@XmlAttribute
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	@XmlElement(name = "Database-Table")
	public List<DbTable> getDbTables() {
		return dbTables;
	}
	public void setDbTables(List<DbTable> dbTables) {
		this.dbTables = dbTables;
	}

	@Override
	public String toString() {
		return "FlowData [folderName=" + folderName + ", appName=" + appName
				+ ", dbTables=" + dbTables + "]";
	}

}
