package com.epam.test.dao;

import static java.util.Collections.singletonMap;
import static java.util.Optional.ofNullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.test.entity.Entity;
import com.epam.test.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class UserDao implements Dao<User, Integer> {

    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<Map<String, Object>>() {
    };
    private static final String SQL_DELETE = String.format("delete from User where %s=:%s", Entity.COLUMN_ID, Entity.COLUMN_ID);
    private final ObjectMapper objectMapper;
    private UserSelectAll selectAll;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private UserGetById getById;
    private UserGetByEmail getByEmail;
    private UpdateUser update;
    private CreateUser create;

    @Autowired
    public UserDao(@Qualifier("upperCamelCase") ObjectMapper objectMapper, DataSource dataSource) {
        this.objectMapper = objectMapper;
        selectAll = new UserSelectAll(dataSource);
        getById = new UserGetById(dataSource);
        getByEmail = new UserGetByEmail(dataSource);
        update = new UpdateUser(dataSource);
        create = new CreateUser(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> get(Integer id) {
        return ofNullable(getById.findObjectByNamedParam(singletonMap(Entity.COLUMN_ID, id)));
    }

    @Override
    public List<User> getAll() {
        return selectAll.execute();
    }

    @Override
    public Integer update(User user) {
        if (user.getId() != null) {
            update.updateByNamedParam(objectMapper.convertValue(user, MAP_TYPE));
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            Map<String, Object> properties = objectMapper.convertValue(user, MAP_TYPE);
            properties.remove(Entity.COLUMN_ID);
            create.updateByNamedParam(properties, keyHolder);
            user.setId(keyHolder.getKey().intValue());
        }
        return user.getId();
    }

    @Override
    public boolean delete(User user) {
        return namedParameterJdbcTemplate.update(SQL_DELETE, singletonMap(Entity.COLUMN_ID, user.getId())) == 1;
    }

    public Optional<User> getByEmail(String email) {
        return ofNullable(getByEmail.findObjectByNamedParam(singletonMap(User.COLUMN_EMAIL, email)));
    }

//    private Map<String, Object> merge(Product product) {
//        Product repository = get(product.getId()).orElseThrow(RuntimeException::new);
//
//
//    }

    private static class UserSelectAll extends UserMapping {

        private static final String SQL = "Select * from USER";

        UserSelectAll(DataSource ds) {
            super(ds, SQL);
        }
    }

    private static abstract class UserMapping extends MappingSqlQuery<User> {

        UserMapping(DataSource ds, String sql) {
            super(ds, sql);
        }

        @Override
        protected User mapRow(ResultSet set, int i) throws SQLException {
            User user = new User();
            user.setId(set.getInt(Entity.COLUMN_ID));
            user.setFirstName(set.getString(User.COLUMN_FIRST_NAME));
            user.setLastName(set.getString(User.COLUMN_LAST_MAME));
            user.setEmail(set.getString(User.COLUMN_EMAIL));
            user.setPassword(set.getString(User.COLUMN_PASSWORD));
            user.setRole(set.getString(User.COLUMN_ROLE));
            return user;
        }
    }

    private static final class UserGetByEmail extends UserMapping {

        private static final String SQL = String.format("Select * from USER where %s = :%s", User.COLUMN_EMAIL, User.COLUMN_EMAIL);

        UserGetByEmail(DataSource ds) {
            super(ds, SQL);
            super.declareParameter(new SqlParameter(User.COLUMN_EMAIL, Types.VARCHAR));
        }
    }

    private static final class UserGetById extends UserMapping {

        private static final String SQL = String.format("Select * from USER where %s = :%s", Entity.COLUMN_ID, Entity.COLUMN_ID);

        UserGetById(DataSource ds) {
            super(ds, SQL);
            super.declareParameter(new SqlParameter(Entity.COLUMN_ID, Types.INTEGER));
        }
    }

    private static abstract class UpdateUserConfiguration extends SqlUpdate {

        UpdateUserConfiguration(DataSource ds, String sql) {
            super(ds, sql);
            super.declareParameter(new SqlParameter(User.COLUMN_FIRST_NAME, Types.VARCHAR));
            super.declareParameter(new SqlParameter(User.COLUMN_LAST_MAME, Types.VARCHAR));
            super.declareParameter(new SqlParameter(User.COLUMN_EMAIL, Types.VARCHAR));
            super.declareParameter(new SqlParameter(User.COLUMN_PASSWORD, Types.VARCHAR));
            super.declareParameter(new SqlParameter(User.COLUMN_ROLE, Types.VARCHAR));
        }
    }

    private static final class CreateUser extends UpdateUserConfiguration {

        private static final String SQL = String.format("insert into USER (%s, %s, %s, %s, %s) values (:%s, :%s, :%s, :%s, :%s)",
                User.COLUMN_FIRST_NAME, User.COLUMN_LAST_MAME, User.COLUMN_EMAIL, User.COLUMN_PASSWORD, User.COLUMN_ROLE,
                User.COLUMN_FIRST_NAME, User.COLUMN_LAST_MAME, User.COLUMN_EMAIL, User.COLUMN_PASSWORD, User.COLUMN_ROLE);

        CreateUser(DataSource ds) {
            super(ds, SQL);
            super.setGeneratedKeysColumnNames(Entity.COLUMN_ID);
        }
    }

    private static final class UpdateUser extends UpdateUserConfiguration {

        private static final String SQL = String.format("update User set %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s where %s = :%s",
                User.COLUMN_FIRST_NAME, User.COLUMN_FIRST_NAME,
                User.COLUMN_LAST_MAME, User.COLUMN_LAST_MAME,
                User.COLUMN_EMAIL, User.COLUMN_EMAIL,
                User.COLUMN_PASSWORD, User.COLUMN_PASSWORD,
                User.COLUMN_ROLE, User.COLUMN_ROLE,
                Entity.COLUMN_ID, Entity.COLUMN_ID);

        UpdateUser(DataSource ds) {
            super(ds, SQL);
            super.declareParameter(new SqlParameter(Entity.COLUMN_ID, Types.INTEGER));
        }
    }
}
