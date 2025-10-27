package vn.tuan.jobhunter.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.controller.errors.CustomException;
import vn.tuan.jobhunter.domain.*;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.JobDTO.ResCreateJob;
import vn.tuan.jobhunter.repository.CompanyRepository;
import vn.tuan.jobhunter.repository.JobRepository;
import vn.tuan.jobhunter.repository.SkillRepository;
import vn.tuan.jobhunter.repository.SubscriberRepository;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriberServiceImpl {
    private final SubscriberRepository subscriberRepository;
    private final SkillRepository skillRepository;
    private SubscriberRepository SubscriberRepository;
    public SubscriberServiceImpl(SubscriberRepository SubscriberRepository, SubscriberRepository subscriberRepository, SkillRepository skillRepository) {
        this.SubscriberRepository = SubscriberRepository;
        this.subscriberRepository = subscriberRepository;
        this.skillRepository = skillRepository;
    }
    public Subscriber createSubscriber(Subscriber subscriber) {
        //check email
        if (subscriberRepository.existsByEmail(subscriber.getEmail())) {
            throw new CustomException("Email already in use");
        }
        //check Skill

        if (subscriber.getSkills()!=null && subscriber.getSkills().size()>0) {
            List<Long> reqSkills=subscriber.getSkills()
                    .stream()
                    .map(x->Long.valueOf(x.getId()))
                    .collect(Collectors.toList());
            List<Skill> skills=skillRepository.findByIdIn(reqSkills);
            subscriber.setSkills(skills);
        }
        return subscriberRepository.save(subscriber);

    }
    public Subscriber updateSubscriber(Subscriber update) {
        //checkid
        if (!subscriberRepository.existsById(Long.valueOf(update.getId()))) {
            throw new CustomException("Subscriber not found");
        }
        if (update.getSkills()!=null && update.getSkills().size()>0) {
            List<Long> reqSkills=update.getSkills()
                    .stream()
                    .map(x->Long.valueOf(x.getId()))
                    .collect(Collectors.toList());
            List<Skill> skills=skillRepository.findByIdIn(reqSkills);
            update.setSkills(skills);
        }
        Subscriber updated=subscriberRepository.findById(Long.valueOf(update.getId())).get();
        updated.setSkills(update.getSkills());
        return subscriberRepository.save(updated);
    }

}
