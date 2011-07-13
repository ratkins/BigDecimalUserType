import java.io.*;
import java.math.*;
import java.sql.*;

import org.hibernate.*;
import org.hibernate.usertype.*;

/**
 * This software is public domain and carries NO WARRANTY.
 *
 * Patches, bug reports and feature requests welcome:
 *
 * https://bitbucket.org/ratkins/bigdecimalusertype/
 */
public class BigDecimalUserType implements UserType {

	private static final int[] SQL_TYPES = new int[] {Types.DECIMAL, Types.INTEGER};

	@Override
	public Object assemble(Serializable arg0, Object arg1) throws HibernateException {
		return deepCopy(arg0);
	}

	@Override
	public Object deepCopy(Object arg0) throws HibernateException {
		return arg0;
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		return (Serializable) arg0;
	}

	@Override
	public boolean equals(Object arg0, Object arg1) throws HibernateException {
		return arg0.equals(arg1);
	}

	@Override
	public int hashCode(Object arg0) throws HibernateException {
		return arg0.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		BigDecimal bigDecimal = rs.getBigDecimal(names[0]);
		if (bigDecimal == null) {
			return null;
		}
		return bigDecimal.setScale(rs.getInt(names[1]), BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.DECIMAL);
			st.setNull(index + 1, Types.INTEGER);
		} else {
			st.setBigDecimal(index, (BigDecimal) value);
			st.setInt(index + 1, ((BigDecimal) value).scale());
		}
	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2) throws HibernateException {
		return arg0;
	}

	@Override
	public Class<?> returnedClass() {
		return BigDecimal.class;
	}

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

}
