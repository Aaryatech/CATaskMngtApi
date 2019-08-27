package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.model.ActivityMaster;
import com.ats.cataskapi.model.Info;
import com.ats.cataskapi.repositories.ActivityMasterRepo;
import com.ats.cataskapi.repositories.ServiceMasterRepo;
import com.ats.cataskapi.task.model.Task;
import com.ats.cataskapi.task.repo.TaskRepo;

@RestController
public class ActiveDeactivateRestController {

	@Autowired
	ServiceMasterRepo srvMstrRepo;

	@Autowired
	ActivityMasterRepo actvtMstrRepo;

	@Autowired
	TaskRepo taskRepo;

	@RequestMapping(value = { "/getTaskListForisactive" }, method = RequestMethod.POST)
	public @ResponseBody List<Task> getTaskListForisactive(@RequestParam("servId") int servId) {

		List<Task> taskList = new ArrayList<Task>();

		try {

			taskList = taskRepo.getTaskListForisactive(servId);

		} catch (Exception e) {
			System.err.println("Exce in getActivityById" + e.getMessage());
		}
		return taskList;
	}

	@RequestMapping(value = { "/updateServiceIsActiveStatus" }, method = RequestMethod.POST)
	public @ResponseBody Info updateServiceIsActiveStatus(@RequestParam("servId") int servId,
			@RequestParam("isActiveStatus") int isActiveStatus, @RequestParam("taskIds") List<Integer> taskIds) {

		Info info = new Info();

		try {

			int update = srvMstrRepo.updateIsActiveStatus(servId,isActiveStatus);
			
			update = actvtMstrRepo.updateIsActiveStatus(servId,isActiveStatus);
			
			update = taskRepo.updateIsActiveStatus(taskIds,isActiveStatus);
			
			info.setError(false);
			info.setMessage("successfully");
			
		} catch (Exception e) {
			e.printStackTrace();
			
			info.setError(true);
			info.setMessage("failed");
		}
		return info;
	}
}
