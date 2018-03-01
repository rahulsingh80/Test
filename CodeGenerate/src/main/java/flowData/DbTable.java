package flowData;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class DbTable {

	private String name;
	private String action;
	private String voClassName;
	private String serviceClassName;
	private String dbClassName;
	private List<Column> columns;

	@XmlAttribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	@XmlAttribute
	public String getVoClassName() {
		return voClassName;
	}
	public void setVoClassName(String voClassName) {
		this.voClassName = voClassName;
	}
	@XmlAttribute
	public String getServiceClassName() {
		return serviceClassName;
	}
	public void setServiceClassName(String serviceClassName) {
		this.serviceClassName = serviceClassName;
	}
	@XmlAttribute
	public String getDbClassName() {
		return dbClassName;
	}
	public void setDbClassName(String dbClassName) {
		this.dbClassName = dbClassName;
	}
	@XmlElement(name = "column")
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "DbTable [name=" + name + ", action=" + action
				+ ", voClassName=" + voClassName + ", serviceClassName="
				+ serviceClassName + ", dbClassName=" + dbClassName
				+ ", columns=" + columns + "]";
	}

}
