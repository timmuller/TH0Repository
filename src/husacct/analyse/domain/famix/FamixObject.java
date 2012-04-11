package husacct.analyse.domain.famix;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * The Class Object.
 */
public abstract class FamixObject
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder string = new StringBuilder();

		string.append("(" + this.getClass().getSimpleName() + "\r\n");

		ArrayList<Field> fields = getFields(this.getClass());

		for (Field field : fields)
		{
			string.append(" (" + field.getName() + " \"" + getFieldValue(field, this) + "\")\r\n");

		}

		string.append(')');

		return string.toString();
	}

	/**
	 * Gets the fields.
	 * 
	 * @param theClass the the class
	 * @return the fields
	 */
	private ArrayList<Field> getFields(java.lang.Class<? extends java.lang.Object> theClass)
	{
		ArrayList<Field> fields = new ArrayList<Field>();

		if (theClass.getSuperclass() != null)
			fields.addAll(getFields(theClass.getSuperclass()));

		Field[] myFields = theClass.getDeclaredFields();

		for (Field field : myFields)
		{
			fields.add(field);
		}

		return fields;
	}

	/**
	 * Gets the field value.
	 * 
	 * @param field the field
	 * @param object the object
	 * @return the field value
	 */
	private String getFieldValue(Field field, Object object)
	{
		String fieldName = field.getName();
		String methodName = getFieldMethodName(fieldName);

		try
		{
			Method method = this.getClass().getMethod(methodName, null);

			java.lang.Object response = (java.lang.Object) method.invoke(this, null);

			if (response == null)
			{
				return "null";
			}
			return response.toString();
		}
		catch (Exception e)
		{
			return "-";
		}
	}

	/**
	 * Gets the methodname for a field.
	 * 
	 * @param fieldName the field name
	 * @return the field method name
	 */
	private String getFieldMethodName(String fieldName)
	{
		if (fieldName.startsWith("is"))
		{
			return fieldName;
		}
		else if (fieldName.startsWith("has"))
		{
			return "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		}
		return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
