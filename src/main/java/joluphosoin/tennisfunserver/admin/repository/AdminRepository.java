package joluphosoin.tennisfunserver.admin.repository;

import joluphosoin.tennisfunserver.admin.data.entity.Admin;
import joluphosoin.tennisfunserver.user.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin,String> {
    Optional<Admin> findByEmailId(String email);
}
