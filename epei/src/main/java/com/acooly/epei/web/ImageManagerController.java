package com.acooly.epei.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.epei.domain.Image;
import com.acooly.epei.service.ImageService;

@Controller
@RequestMapping(value = "/manage/epei/image")
public class ImageManagerController extends AbstractJQueryEntityController<Image, ImageService> {


	@Autowired
	private ImageService imageService;

	

}
