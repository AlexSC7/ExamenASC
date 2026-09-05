package com.exam.asc.infrastructure.adapter.out.persistence.repository;

import com.exam.asc.infrastructure.adapter.out.persistence.document.ClienteDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClienteMongoRepository extends MongoRepository<ClienteDocument, String> {
}