package com.cl.demo.services;

import com.cl.demo.DemoApplication;
import com.cl.demo.entities.PhoneNumber;
import com.cl.demo.requestobjects.PhoneNumberCreateRequest;
import com.cl.demo.requestobjects.PhoneNumberUpdateRequest;
import com.cl.demo.HelperUtils;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PhoneNumberService {


    public PhoneNumber addPhoneNumber(PhoneNumberCreateRequest req) {

        if (req.getPhoneNumber() == null) {
            return null;
        }

        PhoneNumber pn = new PhoneNumber();
        pn.setId(UUID.randomUUID());
        pn.setIsActive(Boolean.TRUE);
        pn.setCreatedDate(new Date());
        pn.setCountryCode(req.getCountryCode());
        pn.setPhoneNumber(req.getPhoneNumber());


        DemoApplication.PhoneNumber_List.add(pn);
        return pn;
    }


    public PhoneNumber getPhoneNumberById(UUID uuid) {
        if (uuid == null) return null;
        for (PhoneNumber p : DemoApplication.PhoneNumber_List) {

            if (p.getId().equals(uuid) && Boolean.TRUE.equals(p.getIsActive())) {
                return p;
            }
        }
        return null;
    }
    }
}
