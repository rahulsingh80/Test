package flowData;

import javax.xml.bind.annotation.XmlAttribute;

public class Column {

	private String name;
	private String dataType;
	private String primaryKey;
	private String maxLength;
	private String  isNotNull;
	private String parentTable;
	private String parentColumn;

	@XmlAttribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	@XmlAttribute
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey.toLowerCase();
	}
	@XmlAttribute
	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	@XmlAttribute
	public String isNotNull() {
		return isNotNull;
	}
	public void setNotNull(String isNotNull) {
		this.isNotNull = isNotNull;
	}
	@XmlAttribute
	public String getParentTable() {
		return parentTable;
	}
	public void setParentTable(String parentTable) {
		this.parentTable = parentTable;
	}
	@XmlAttribute
	public String getParentColumn() {
		return parentColumn;
	}
	public void setParentColumn(String parentColumn) {
		this.parentColumn = parentColumn;
	}
	
	@Override
	public String toString() {
		return "Column [name=" + name + ", dataType=" + dataType
				+ ", isPrimaryKay=" + primaryKey + ", maxLength=" + maxLength
				+ ", isNotNull=" + isNotNull + ", parentTable=" + parentTable
				+ ", parentColumn=" + parentColumn + "]";
	}
}
