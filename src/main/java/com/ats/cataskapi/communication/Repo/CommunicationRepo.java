package com.ats.cataskapi.communication.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.cataskapi.communication.model.Communication;

public interface CommunicationRepo extends JpaRepository<Communication, Integer>{

}
