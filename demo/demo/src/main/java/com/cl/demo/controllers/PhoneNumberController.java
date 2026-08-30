package com.cl.demo.controllers;

import com.cl.demo.entities.PhoneNumber;
import com.cl.demo.requestobjects.PhoneNumberCreateRequest;
import com.cl.demo.requestobjects.PhoneNumberUpdateRequest;
import com.cl.demo.responseobjects.PhoneNumberCreateResponse;
import com.cl.demo.responseobjects.PhoneNumberUpdateResponse;
import com.cl.demo.services.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("phoneNumber")
public class PhoneNumberController {

    @Autowired
    private PhoneNumberService phoneNumberService;


    @PostMapping("/add")
    public PhoneNumberCreateResponse addPhoneNumber(@RequestBody PhoneNumberCreateRequest req) {
        PhoneNumber pn = phoneNumberService.addPhoneNumber(req);
        return PhoneNumberCreateResponse.convert(pn);
    }


    @GetMapping("/getById")
    public PhoneNumberCreateResponse getPhoneNumberById(@RequestParam UUID uuid) {
        PhoneNumber pn = phoneNumberService.getPhoneNumberById(uuid);
        return PhoneNumberCreateResponse.convert(pn);
    }


