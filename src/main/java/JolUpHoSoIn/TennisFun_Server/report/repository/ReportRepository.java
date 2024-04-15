package JolUpHoSoIn.TennisFun_Server.report.repository;

import JolUpHoSoIn.TennisFun_Server.report.data.entity.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report,String> {
}
