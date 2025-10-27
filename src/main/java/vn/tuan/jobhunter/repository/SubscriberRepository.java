package vn.tuan.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.tuan.jobhunter.domain.Skill;
import vn.tuan.jobhunter.domain.Subscriber;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long>, JpaSpecificationExecutor<Subscriber> {
    Boolean existsByEmail(String email);

}
