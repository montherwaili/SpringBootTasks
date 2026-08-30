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
    }
}
