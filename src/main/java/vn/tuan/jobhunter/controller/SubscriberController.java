package vn.tuan.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.tuan.jobhunter.domain.Subscriber;
import vn.tuan.jobhunter.domain.response.ApiResponse;
import vn.tuan.jobhunter.repository.SubscriberRepository;
import vn.tuan.jobhunter.service.impl.SubscriberServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class SubscriberController {
    private SubscriberServiceImpl subscriberService;
    public SubscriberController(SubscriberServiceImpl subscriberService) {
        this.subscriberService = subscriberService;
    }
    @PostMapping("/subscribers")
    public ResponseEntity<ApiResponse<Subscriber>> subscribe(@RequestBody Subscriber subscriber) {
        Subscriber sub=subscriberService.createSubscriber(subscriber);
        ApiResponse<Subscriber> apiResponse=new ApiResponse<Subscriber>(HttpStatus.CREATED,"created subscrib",sub,null);
        return  ResponseEntity.ok().body(apiResponse);
    }
    @PutMapping("subscribers")
    public ResponseEntity<ApiResponse<Subscriber>> update(@RequestBody Subscriber subscriber) {
        Subscriber updated=subscriberService.updateSubscriber(subscriber);
        ApiResponse<Subscriber> apiResponse=new ApiResponse<Subscriber>(HttpStatus.OK,"updated subscrib",updated,null);
        return  ResponseEntity.ok().body(apiResponse);
    }
}
