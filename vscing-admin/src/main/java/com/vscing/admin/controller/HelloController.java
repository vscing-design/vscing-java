package com.vscing.admin.controller;

import com.vscing.admin.dto.HelloListDto;
import com.vscing.common.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/v1/hello")
public class HelloController {

	@GetMapping("/")
	public String index() {
		return "hello word";
	}

	@RequestMapping(value = "/queryList", method = RequestMethod.GET)
	public CommonResult<Object> queryList(Map<String, Object> params) {
		HelloListDto item = new HelloListDto();
//		item.setId(1);
//		item.setTitle("测试");
//		item.setCreateBy("创建人");

//		String dateString = "2024-05-20T12:00:00"; // 假设时间点为12:00:00
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//		LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
//		item.setCreateAt(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));

//		List<HelloListDto> list = new ArrayList<>();
//		list.add(item);

		Map<String, Object> result = new HashMap<>();
//		result.put("list", list);
		result.put("total", 100);
		return new CommonResult<>(200, "成功", result);
	}

	@RequestMapping(value = "/queryInfo", method = RequestMethod.GET)
	public CommonResult<Object> queryInfo(Integer id) {
		Map<String, Object> result = new HashMap<>();
		result.put("id", id);
		return new CommonResult<>(200, "成功", result);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/delInfo/{id}/{name}", method = RequestMethod.GET)
	public CommonResult<Map<String, Object>> delInfo(@PathVariable("id") Integer id, @PathVariable("name") String name) {
		Map<String, Object> result = new HashMap<>();
		result.put("id", id);
		result.put("name", name);
		return new CommonResult<>(404, "删除成功", result);
	}
}
