package com.aynu.redis.typehandler;

import com.aynu.redis.pojo.ColorEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//声明JdbcType为整数类型
@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(value = ColorEnum.class)
public class ColorTypeHandler extends BaseTypeHandler {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {

    }
    //通过列名读取
    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int sex=resultSet.getInt(s);
        if (sex!=1&&sex!=2&&sex!=3&&sex!=4){
            return null;
        }
        return ColorEnum.getColor(sex);
    }
    //通过下标读取性别
    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int sex=resultSet.getInt(i);
        if (sex!=1&&sex!=2&&sex!=3&&sex!=4){
            return null;
        }
        return ColorEnum.getColor(sex);
    }
    //通过存储过程读取性别
    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int sex=callableStatement.getInt(i);
        if (sex!=1&&sex!=2&&sex!=3&&sex!=4){
            return null;
        }
        return ColorEnum.getColor(sex);
    }
}
