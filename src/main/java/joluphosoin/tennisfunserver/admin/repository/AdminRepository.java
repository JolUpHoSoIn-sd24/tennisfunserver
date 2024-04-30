package joluphosoin.tennisfunserver.admin.repository;

import joluphosoin.tennisfunserver.admin.data.entity.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin,String> {
}
