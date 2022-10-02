package com.expert.ExpertAssignment.controller;

import com.expert.ExpertAssignment.model.UrlModel;
import com.expert.ExpertAssignment.model.UrlResponseModel;
import com.expert.ExpertAssignment.service.UrlService;
import com.expert.ExpertAssignment.util.Constant;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * This is UrlController class.
 * @author Karan.
 */
@RestController
@RequestMapping("/url")
public class UrlController {

    /**
     * urlService field.
     */
    @Autowired
    private UrlService urlService;

    /**
     * This is getDetails() method.
     * @param urlModel field.
     * @return url response.
     * @throws IOException exception.
     */
    @ApiOperation(value = "URL details fetch successfully.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constant.URL_DETAILS_FETCH_SUCCESS),
            @ApiResponse(code = 403, message = Constant.FORBIDDEN),
            @ApiResponse(code = 400, message = Constant.BAD_REQUEST)
    }
    )
    @PostMapping(value = "/details", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UrlResponseModel> getDetails(@RequestBody final UrlModel urlModel) throws IOException {
        final UrlResponseModel urlResponseModel = urlService.fetchDetailsFromURL(urlModel);
        return new ResponseEntity<>(urlResponseModel, HttpStatus.OK);
    }
}
