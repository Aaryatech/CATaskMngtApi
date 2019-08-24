package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.communication.Repo.CommunicationRepo;
import com.ats.cataskapi.communication.Repo.GetAllCommunicationByTaskIdRepo;
import com.ats.cataskapi.communication.model.Communication;
import com.ats.cataskapi.communication.model.GetAllCommunicationByTaskId;
  
@RestController
public class CommunicationApiController {
	
	@Autowired
	GetAllCommunicationByTaskIdRepo getAllCommunicationByTaskIdRepo;
	
	@RequestMapping(value = { "/getCommunicationByTaskId" }, method = RequestMethod.POST)
	public @ResponseBody List<GetAllCommunicationByTaskId> getCommunicationByTaskId(@RequestParam("taskId") int taskId) {

		 List<GetAllCommunicationByTaskId>  commmunicate = new ArrayList<GetAllCommunicationByTaskId>();
		try {

			commmunicate = getAllCommunicationByTaskIdRepo.getCommunicationListByTaskId(taskId);
			 
System.out.println("data is "+commmunicate.toString());
		} catch (Exception e) {

			e.printStackTrace();
		}

		return commmunicate;

	}
	
	@Autowired
	CommunicationRepo communicationRepo;
	
	@RequestMapping(value = { "/saveCommunication" }, method = RequestMethod.POST)
	public @ResponseBody Communication saveCommunication(@RequestBody Communication com) {

		Communication save = new Communication();
		try {

			save = communicationRepo.saveAndFlush(com);
 		} catch (Exception e) {
			 
			e.printStackTrace();
		}

		return save;

	}
	
	

}
