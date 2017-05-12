package com.acooly.epei.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.ImageService;
import com.acooly.epei.dao.ImageDao;
import com.acooly.epei.domain.Image;

@Service("imageService")
public class ImageServiceImpl extends EntityServiceImpl<Image, ImageDao> implements ImageService {

}
