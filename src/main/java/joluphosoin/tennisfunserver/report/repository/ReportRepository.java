package joluphosoin.tennisfunserver.report.repository;

import joluphosoin.tennisfunserver.report.data.entity.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report,String> {
}
