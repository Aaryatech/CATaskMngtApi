package com.ats.cataskapi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.model.DailyWorkLog;
import com.ats.cataskapi.model.TaskCountByStatus;
import com.ats.cataskapi.repositories.TaskCountByStatusRepo;

@RestController
public class DashboardRestController {

	@Autowired
	TaskCountByStatusRepo taskCountByStatusRepo;

	@RequestMapping(value = { "/getTaskCountByStatus" }, method = RequestMethod.POST)
	public @ResponseBody List<TaskCountByStatus> getTaskCountByStatus(@RequestParam("empId") int empId) {

		List<TaskCountByStatus> list = new ArrayList<TaskCountByStatus>();

		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			list = taskCountByStatusRepo.getTaskCountByStatus(sf.format(date), empId);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;

	}

}
