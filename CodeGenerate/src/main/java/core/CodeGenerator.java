package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import flowData.Column;
import flowData.DbTable;
import flowData.FlowData;

public class CodeGenerator {

	private static String basePackage = "src/com/mkyong/generatedCode/src/com/mkyong/";
	private static String basePackageWithPeriodSeparator = basePackage.replace('/', '.');
	
	public static void main(String[] args) {
		FlowData data = Parser.parse();
		generateCodeUsingIO(data);
	}

	private static void generateCodeUsingIO(FlowData data) {
		//Set default values where values are not provided
		CodeGeneratorHelper.setDefaultValuesWhereNotProvided(data);
		
		for (DbTable dbTable : data.getDbTables()) {
			//Create VO class
			List<String> voClass = createBasicJavaClassLines("vo", dbTable.getVoClassName());
			//Loop over columns, send name and data type.
			List<FieldData> fieldDataList = new ArrayList<FieldData>();
			for (Column column : dbTable.getColumns()) {
				FieldData fieldData = new FieldData();
				fieldData.setName(column.getName());
				fieldData.setType(column.getDataType());

				fieldDataList.add(fieldData);
			}
			int lastLine = addFieldsToJavaClass(voClass, fieldDataList);
			createGetterSetters(voClass, fieldDataList, lastLine);
			
			createJavaFile(voClass, "vo", dbTable.getVoClassName());
			
			//CreateDaoClass
			List<String> daoClass = createDaoClass(dbTable);
			createJavaFile(daoClass, "dao", dbTable.getDbClassName() + "Impl");
			
			
			//Create Service class
			List<String> serviceClass = createServiceClass(dbTable); 
					//createBasicJavaClassLines("service", dbTable.getServiceClassName());
			//Call DAO class?
			createJavaFile(serviceClass, "service", dbTable.getServiceClassName() + "Impl");
			
			
			//Create Hibernate mapping xml
			List<String> hibernateMappingFile = createHibernateMappingXml(dbTable);
			createFile(hibernateMappingFile, "hibernate", dbTable.getName() + ".hbm.xml");
			
			//Create DB script
			List<String> dbScriptFile = createDbScript(dbTable);
			createFile(dbScriptFile, "hibernate", dbTable.getName() + ".sql");

			//Create controller class
			List<String> controllerClass = createControllerClass(dbTable);
			createJavaFile(controllerClass, "Controller", 
					CodeGeneratorHelper.approximatePascalCase(dbTable.getName() + "Controller"));
		}
	}

	private static PrintWriter createPrintWriter(String fileName) {
		PrintWriter writer = null;
		ensurePackageExists(fileName);
		try {
			writer = new PrintWriter(fileName, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return writer;
	}

	private static void ensurePackageExists(String fileName) {
		fileName = fileName.substring(0, fileName.lastIndexOf('/'));
		File files = new File(fileName);
		if (!files.exists()) {
			if (files.mkdirs()) {
				System.out.println("Multiple directories are created!");
			} else {
				System.out.println("Failed to create multiple directories!");
			}
		}
	}

	private static List<String> createBasicJavaClassLines(String packageName, String className) {
		List<String> lines = new ArrayList<String>();
		String fullyQualifiedClassName = basePackage + "/" + packageName
				+ "/" + className + ".java";
//		PrintWriter writer = createPrintWriter(basePackage + "/" + packageName
//				+ "/" + className + ".java");
		
		lines.add("package com.mkyong." + packageName + ";\n");
		lines.add("public class " + className + "{");
		lines.add("}");
		
		return lines;
	}

	private static void createJavaFile(List<String> lines, String packageName, String className) {
		createFile(lines, packageName, className + ".java");
	}

	private static void createFile(List<String> lines, String packageName, String fileName) {
		PrintWriter writer = createPrintWriter(basePackage + "/" + packageName
				+ "/" + fileName);

		for (String line : lines) {
			writer.println(line);
		}

		writer.close();
	}

	private static int addFieldsToJavaClass(List<String> classLines, List<FieldData> fieldDataList) {
		//It seems there is no easy way to insert in an already created file
		//There is something called FileChannels that can be explored 
		//Another solution is to create a new file altogether.

		System.out.println("lines in file : " + classLines);
		//Search for line starting "class " or "public class ". Insert fields after that.
		int i;
		for (i=0; i<classLines.size(); i++) {
			if (classLines.get(i).startsWith("class ") 
					|| classLines.get(i).startsWith("public class ")) {
				//insert fields here
				for (FieldData data : fieldDataList) {
					StringBuffer lineToAdd = new StringBuffer(CodeGeneratorHelper.addSpaces(4));
					switch (data.getType()) {
					case "Int" :
						lineToAdd.append("int ");
						break;
					case "Varchar" :
					case "Varchar2" :
						lineToAdd.append("String ");
						break;
					case "Date" :
						lineToAdd.append("Date ");
						break;
					}
					lineToAdd.append(CodeGeneratorHelper.approximateCamelCase(data.getName()) + ";");
					classLines.add(++i, lineToAdd.toString());
				}
				break;
			}
		}
		System.out.println("Modified lines in file : " + classLines);
		
		return ++i;
	}

	private static String getJavaDataType(String type) {
		switch (type) {
		case "Int" :
			return("int");
		case "Varchar" :
		case "Varchar2" :
			return("String");
		case "Date" :
			return("Date");
		}

		return type;
	}

	private static List<String> createGetterSetters(List<String> classLines, 
			List<FieldData> fieldDataList, int lastLine) {
		List<String> lines = new ArrayList<String>();
		for (FieldData data : fieldDataList) {
			lines.addAll(createGetter(data));
			lines.addAll(createSetter(data));
		}
		
		classLines.addAll(lastLine, lines);
		return lines;
	}

	private static List<String> createGetter(FieldData fieldData) {
		List<String> lines = new ArrayList<String>();
		//lines.add("\n");
		
		String methodName = "get" + CodeGeneratorHelper.firstCharacterUpperCase(CodeGeneratorHelper.approximateCamelCase(fieldData.getName()));
		lines.add(CodeGeneratorHelper.addSpaces(4) + "public " + getJavaDataType(fieldData.getType())
				+ " " + methodName + "() {");
		lines.add(CodeGeneratorHelper.addSpaces(8) + "return " + CodeGeneratorHelper.approximateCamelCase(fieldData.getName()) + ";");
		lines.add(CodeGeneratorHelper.addSpaces(4) + "}");
		return lines;
	}

	private static List<String> createSetter(FieldData fieldData) {
		List<String> lines = new ArrayList<String>();
		//lines.add("\n");
		
		String fieldName = CodeGeneratorHelper.approximateCamelCase(fieldData.getName());
		String methodName = "set" + CodeGeneratorHelper.approximatePascalCase(fieldData.getName());
		lines.add(CodeGeneratorHelper.addSpaces(4) + "public void " + methodName + "(" 
				+ getJavaDataType(fieldData.getType()) + " " + fieldName + ") {");
		lines.add(CodeGeneratorHelper.addSpaces(8) + "this." + fieldName + " = " + fieldName + ";");
		lines.add(CodeGeneratorHelper.addSpaces(4) + "}");
		return lines;
	}

	private static List<String> createDbScript(DbTable dbTable) {
		/*
		 * 	   CREATE TABLE tutorials_tbl(
			   -> tutorial_id INT NOT NULL AUTO_INCREMENT,
			   -> tutorial_title VARCHAR(100) NOT NULL,
			   -> tutorial_author VARCHAR(40) NOT NULL,
			   -> submission_date DATE,
			   -> PRIMARY KEY ( tutorial_id )
			   -> );
		 */
		List<String> fileLines = new ArrayList<String>();
		
		fileLines.add("CREATE TABLE " + dbTable.getName() + "(");
		Column primaryKey = null;
		for (Column column : dbTable.getColumns()) {
			if(null != column.getPrimaryKey() && column.getPrimaryKey().equalsIgnoreCase("Yes")) {
				primaryKey = column;
			}
				
			StringBuffer buffer = new StringBuffer(spaces(4) + column.getName() 
					+ " " + column.getDataType());
			
			if (column.getDataType().contains("Varchar")) {
				buffer.append("(" + column.getMaxLength() + ")");
			}
			//buffer.append(")");
			
			if(null != column.isNotNull() && column.isNotNull().equalsIgnoreCase("Yes")) {
				buffer.append(" NOT NULL");
			}
			
			buffer.append(",");
			fileLines.add(buffer.toString());
		}
		if (null != primaryKey)
			fileLines.add(spaces(4) + "PRIMARY KEY (" + primaryKey.getName() + ")");
		fileLines.add(");");
		
		return fileLines;
	}

	private static List<String> createHibernateMappingXml(DbTable dbTable) {
		//Create basic hibernate mapping file
		List<String> fileLines = new ArrayList<String>();
		String docTypeDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!DOCTYPE hibernate-mapping PUBLIC"
				+ "\"-//Hibernate/Hibernate Mapping DTD 3.0//EN\""
				+ "\"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\n";
		fileLines.add(docTypeDeclaration);
		fileLines.add("<hibernate-mapping>");
		fileLines.add("</hibernate-mapping>");
		
		//Add mappings
		int insertPosition = fileLines.indexOf("</hibernate-mapping>");
		List<String> mappingLines = new ArrayList<String>();
		mappingLines.add(CodeGeneratorHelper.addSpaces(4) + "<class name=\"" + basePackageWithPeriodSeparator 
				+ "vo." + dbTable.getVoClassName() + "\" table=\"" + dbTable.getName() + "\">");
		//<property name="deptName" column="dept_name"/>
		for (Column column : dbTable.getColumns()) {
			if (null != column.getPrimaryKey() && column.getPrimaryKey().equals("yes")) {
				mappingLines.add(CodeGeneratorHelper.addSpaces(8) + "<id name=\"" + CodeGeneratorHelper.approximateCamelCase(column.getName())
						+ "\" type=\"" + column.getDataType() + "\" column=\"" + column.getName() + "\">");
				mappingLines.add(CodeGeneratorHelper.addSpaces(12) + "<generator class=\"native\"></generator>");
				mappingLines.add(CodeGeneratorHelper.addSpaces(8) + "</id>");
			}
			else {
				mappingLines.add(CodeGeneratorHelper.addSpaces(8) + "<property name=\"" + CodeGeneratorHelper.approximateCamelCase(column.getName())
						+ "\" column=\"" + column.getName() + "\"/>");
			}
		}
		mappingLines.add(CodeGeneratorHelper.addSpaces(4) + "</class>");
		fileLines.addAll(insertPosition, mappingLines);

		return fileLines;
	}

	private static List<String> createControllerClass(DbTable dbTable) {
		List<String> controllerClass = createBasicJavaClassLines("controller", 
				CodeGeneratorHelper.approximatePascalCase(dbTable.getName() + "Controller"));
		
		List<String> classLines = new ArrayList<String>();
		int insertPosition = 0;
		String tblName = dbTable.getName();
		String tblNameCamelCase = CodeGeneratorHelper.approximateCamelCase(tblName);
		String capsTblName = capital(tblNameCamelCase);
		
		//Add @Controller and @RequestMapping annotations
		classLines.add("@Controller");
		classLines.add("@RequestMapping(\"/" + tblNameCamelCase + "\")");
		
		for (int i=0; i<controllerClass.size(); i++) {
			if (controllerClass.get(i).startsWith("class ") 
					|| controllerClass.get(i).startsWith("public class ")) {
				insertPosition = i;
			}
		}
		controllerClass.addAll(insertPosition, classLines);
		
		classLines = new ArrayList<String>();
		//Add serviceClass instance
		classLines.add("\n");
		classLines.add(spaces(4) + "@Autowired");
		classLines.add(spaces(4) + "private " + dbTable.getServiceClassName() 
				+ " " + lowerCase(dbTable.getServiceClassName()) + ";\n");
		
		//Create getAll method
		classLines.add(spaces(4) + "@RequestMapping(\"/" + tblNameCamelCase + "List\")");
		classLines.add(spaces(4) + "public @ResponseBody List<String> get" 
				+ capsTblName + "List() {");
		classLines.add(spaces(4) 
				+ "return " + lowerCase(dbTable.getServiceClassName()) + ".getAll" + capsTblName + "s();");
		classLines.add(spaces(4) + "}");
		
		//Create add method
		classLines.add("\n");
		classLines.add(spaces(4) + "@RequestMapping(value = \"/add" + capsTblName
				+ "/{" + lowerCase(tblNameCamelCase) + "}\", method = RequestMethod.POST)");
		classLines.add(spaces(4) + "public @ResponseBody void add" + capsTblName
				+ "(@PathVariable(\"" + lowerCase(tblNameCamelCase) + "\"" + ") String " 
				+ lowerCase(tblNameCamelCase) + ") {");
		classLines.add(spaces(8) + lowerCase(dbTable.getServiceClassName()) + ".add"
				+ capsTblName + "(" + lowerCase(tblNameCamelCase) + ");");
		classLines.add(spaces(4) + "}");

		//Create remove method
	    classLines.add("\n");
		classLines.add(spaces(4) + "@RequestMapping(value = \"/remove" + capsTblName + "/{"
				+ lowerCase(tblNameCamelCase) + "}\", method = RequestMethod.POST)");
		classLines.add(spaces(4) + "public @ResponseBody void remove" + capsTblName
				+ "(@PathVariable(\"" + lowerCase(tblNameCamelCase) + "\") String " + lowerCase(tblNameCamelCase)
				+ ") {");
		classLines.add(spaces(8) + lowerCase(dbTable.getServiceClassName()) + ".delete"
				+ capsTblName + "(" + lowerCase(tblNameCamelCase) + ");");
		classLines.add(spaces(4) + "}");

		//Create remove All method
		classLines.add("\n");
		classLines.add(spaces(4) + "@RequestMapping(value = \"/removeAll" 
				+ capsTblName + "s\", method = RequestMethod.POST)");
		classLines.add(spaces(4) + "public @ResponseBody void removeAll" + capsTblName + "s() {");
		classLines.add(spaces(8) + lowerCase(dbTable.getServiceClassName()) + ".deleteAll();");
		classLines.add(spaces(4) + "}");
		
		/*FieldData fieldData = new FieldData();
		fieldData.setName(CodeGeneratorHelper.firstCharacterLowerCase(dbTable.getServiceClassName()));
		fieldData.setType(dbTable.getServiceClassName());
		
		classLines.addAll(createSetter(fieldData));*/
		
		insertPosition = controllerClass.lastIndexOf("}");
		controllerClass.addAll(insertPosition, classLines);
		return controllerClass;
	}

	private static List<String> createServiceClass(DbTable dbTable) {
		List<String> controllerClass = createBasicJavaClassLines("service", 
				CodeGeneratorHelper.approximatePascalCase(lowerCase(dbTable.getServiceClassName()) + "Impl"));
		
		List<String> classLines = new ArrayList<String>();
		int insertPosition = 0;
		String tblName = dbTable.getName();
		String tblNameCamelCase = CodeGeneratorHelper.approximateCamelCase(tblName);
		String capsTblName = capital(tblNameCamelCase);
		
		//Add @Service annotations
		classLines.add("@Service(\"" + tblNameCamelCase + "Service\")");
		
		for (int i=0; i<controllerClass.size(); i++) {
			if (controllerClass.get(i).startsWith("class ") 
					|| controllerClass.get(i).startsWith("public class ")) {
				insertPosition = i;
			}
		}
		String classDef = controllerClass.get(insertPosition);
		classDef = classDef.replace("{", " implements " 
				+ capital(dbTable.getServiceClassName()) + " {");
		controllerClass.add(insertPosition, classDef);
		controllerClass.remove(insertPosition+1);
		controllerClass.addAll(insertPosition, classLines);
		
		classLines = new ArrayList<String>();
		//Add dao Class instance
		classLines.add("\n");
		classLines.add(spaces(4) + "@Autowired");
		classLines.add(spaces(4) + "private " + dbTable.getDbClassName() 
				+ " " + lowerCase(dbTable.getDbClassName()) + ";\n");
		
		//Create get all method
		classLines.add("\n");
		classLines.add(spaces(4) + "@Override");
		classLines.add(spaces(4) + "public List<" + dbTable.getVoClassName() + ">" 
				+ " getAll" + capsTblName + "s() {");
		classLines.add(spaces(4) + "}");
		
		//Create add method
		classLines.add("\n");
		classLines.add(spaces(4) + "@Override");
		classLines.add(spaces(4) + "public void add" + capsTblName
				+ "(" + dbTable.getVoClassName() + " " + lowerCase(tblNameCamelCase)
				+ ") {");
		/*classLines.add(spaces(8) + lowerCase(tblName) + "Service.delete"
				+ capsTblName + "(" + lowerCase(tblName) + ");");*/
		classLines.add(spaces(4) + "}");

		//Create remove All method
		classLines.add("\n");
		classLines.add(spaces(4) + "@Override");
		classLines.add(spaces(4) + "public void removeAll" + capsTblName + "s() {");
		classLines.add(spaces(8) + lowerCase(tblNameCamelCase) + "List.clear();");
		classLines.add(spaces(4) + "}");
		
		//Create remove method
		classLines.add("\n");
		classLines.add(spaces(4) + "@Override");
		classLines.add(spaces(4) + "public void remove" + capsTblName + "(" 
				+ dbTable.getVoClassName() + " " + tblNameCamelCase + ") {");
		//classLines.add(spaces(8) + lowerCase(tblName) + "List.clear();");
		classLines.add(spaces(4) + "}");
		
		insertPosition = controllerClass.lastIndexOf("}");
		controllerClass.addAll(insertPosition, classLines);
		return controllerClass;
	}

	private static List<String> createDaoClass (DbTable dbTable) {
		List<String> controllerClass = createBasicJavaClassLines("dao", 
				CodeGeneratorHelper.approximatePascalCase(dbTable.getName() + "DaoImpl"));
		
		List<String> classLines = new ArrayList<String>();
		int insertPosition = 0;
		String tblName = dbTable.getName();
		String tblNameCamelCase = CodeGeneratorHelper.approximateCamelCase(tblName);
		String capsTblName = capital(tblNameCamelCase);
				
		//Add @Repository annotation
		classLines.add("@Repository(value = \"" + tblNameCamelCase + "Dao\")");
		
		for (int i=0; i<controllerClass.size(); i++) {
			if (controllerClass.get(i).startsWith("class ") 
					|| controllerClass.get(i).startsWith("public class ")) {
				insertPosition = i;
			}
		}
		String classDef = controllerClass.get(insertPosition);
		classDef = classDef.replace("{", " implements " 
				+ capsTblName + "Dao {");
		controllerClass.add(insertPosition, classDef);
		controllerClass.remove(insertPosition+1);
		controllerClass.addAll(insertPosition, classLines);
		
		classLines = new ArrayList<String>();
		//Add session factory instance
		classLines.add("\n");
		classLines.add(spaces(4) + "@Autowired");
		classLines.add(spaces(4) + "private SessionFactory sessionFactory;");

		//Create add method
		classLines.add("\n");
		classLines.add(spaces(4) + "@Override");
		classLines.add(spaces(4) + "@Transactional");
		classLines.add(spaces(4) + "public void add" + capsTblName
				+ "(" + dbTable.getVoClassName() + " " + lowerCase(tblNameCamelCase)
				+ ") {");
		classLines.add(spaces(8) + "this.sessionFactory.getCurrentSession().save(" 
				+ lowerCase(tblNameCamelCase) + ");");
		classLines.add(spaces(4) + "}");

		//Create get all method
		classLines.add("\n");
		classLines.add(spaces(4) + "@SuppressWarnings(\"unchecked\")");
		classLines.add(spaces(4) + "@Override");
		classLines.add(spaces(4) + "@Transactional");
		classLines.add(spaces(4) + "public List<" + dbTable.getVoClassName() + ">" 
				+ " getAll" + capsTblName + "s() {");
		classLines.add(spaces(8) + "this.sessionFactory.getCurrentSession().createQuery(\"from " 
				+ capital(tblNameCamelCase) + "\").list();");
		classLines.add(spaces(4) + "}");
		
		insertPosition = controllerClass.lastIndexOf("}");
		controllerClass.addAll(insertPosition, classLines);
		return controllerClass;
	}

	private static String capital(String string) {
		return CodeGeneratorHelper.firstCharacterUpperCase(string);
	}

	private static String lowerCase(String string) {
		return CodeGeneratorHelper.firstCharacterLowerCase(string);
	}
	
	private static String spaces(int num) {
		return CodeGeneratorHelper.addSpaces(num);
	}
}
