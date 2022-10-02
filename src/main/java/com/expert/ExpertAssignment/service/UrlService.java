package com.expert.ExpertAssignment.service;

import com.expert.ExpertAssignment.model.UrlModel;
import com.expert.ExpertAssignment.model.UrlResponseModel;

import java.io.IOException;

public interface UrlService {

    UrlResponseModel fetchDetailsFromURL(UrlModel urlModel) throws IOException;

}
