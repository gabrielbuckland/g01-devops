package ch.hslu.devops.g01.backend.repository;

import ch.hslu.devops.g01.backend.entity.Form;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
public interface FormRepository extends CrudRepository<Form, String> {

}
