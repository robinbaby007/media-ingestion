package com.mi.event_ingestion_service.repository;

import com.mi.event_ingestion_service.models.MediaEventRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaInjestionRepository extends JpaRepository<MediaEventRequest,Long> {
}
