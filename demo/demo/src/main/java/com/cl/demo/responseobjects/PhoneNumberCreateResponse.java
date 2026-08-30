package com.cl.demo.responseobjects;

import com.cl.demo.entities.PhoneNumber;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PhoneNumberCreateResponse {
    private String phoneNumberId;
    private String countryCode;
    private Long phoneNumber;


    public static PhoneNumberCreateResponse convert(PhoneNumber pn) {
        if (pn == null || pn.getId() == null) {
            return null;
        }
        PhoneNumberCreateResponse res = new PhoneNumberCreateResponse();
        res.setPhoneNumberId(pn.getId().toString());
        res.setCountryCode(pn.getCountryCode());
        res.setPhoneNumber(pn.getPhoneNumber());
        return res;
    }


    public static List<PhoneNumberCreateResponse> convert(List<PhoneNumber> pns) {
        List<PhoneNumberCreateResponse> list = new ArrayList<>();
        if (pns == null) return list;

        for (PhoneNumber p : pns) {
            PhoneNumberCreateResponse res = convert(p);
            if (res != null) {
                list.add(res);
            }
        }
        return list;
    }
}
