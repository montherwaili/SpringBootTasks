package com.cl.demo.services;

import com.cl.demo.DemoApplication;
import com.cl.demo.entities.Person;
import com.cl.demo.entities.PhoneNumber;
import com.cl.demo.entities.UserName;
import com.cl.demo.requestobjects.PersonCreateRequest;
import com.cl.demo.requestobjects.PersonUpdateRequest;
import com.cl.demo.requestobjects.PhoneNumberCreateRequest;
import com.cl.demo.utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService {

    public static final String PERSON_USERNAME_OR_EMAIL_ALREADY_TAKEN = "Given username or email is already taken";
    public static final String PERSON_SAVED = "Person saved";

    @Autowired
    private PhoneNumberService phoneNumberService;

    public Map<String, String> addPerson(PersonCreateRequest requestObj) {
        Map<String, String> response = new HashMap<>();

        if (requestObj.getPersonFirstName() == null || requestObj.getPersonFirstName().trim().isEmpty() ||
                requestObj.getPersonLastName() == null || requestObj.getPersonLastName().trim().isEmpty() ||
                requestObj.getPersonUserName() == null || requestObj.getPersonUserName().trim().isEmpty() ||
                requestObj.getPersonEmail() == null || requestObj.getPersonEmail().trim().isEmpty()) {
            response.put("error", "Important fields (First Name, Last Name, Username, Email) cannot be empty");
            return response;
        }

        if (!checkIfUserNameOrEmailExists(requestObj.getPersonUserName(), requestObj.getPersonEmail())) {
            response.put("error", PERSON_USERNAME_OR_EMAIL_ALREADY_TAKEN);
            return response;
        }

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setIsActive(Boolean.TRUE);
        person.setCreatedDate(new Date());

        UserName userName = new UserName();
        userName.setActiveUserName(requestObj.getPersonUserName());

        person.setUserName(userName);
        person.setName(getFullName(requestObj));
        person.setEmail(requestObj.getPersonEmail());

        if (requestObj.getPersonPhoneNumber() != null) {
            PhoneNumberCreateRequest phoneReq = new PhoneNumberCreateRequest();
            phoneReq.setCountryCode(requestObj.getPersonCountryCode());
            phoneReq.setPhoneNumber(requestObj.getPersonPhoneNumber());

            PhoneNumber newPhone = phoneNumberService.addPhoneNumber(phoneReq);
            person.setPhoneNumber(newPhone);
        }

        Boolean result = DemoApplication.Person_List.add(person);

        if (result) {
            DemoApplication.emails.add(requestObj.getPersonEmail());
            DemoApplication.userNames.add(requestObj.getPersonUserName());
            response.put("response", PERSON_SAVED);
        }
        return response;
    }

    public Person getPersonById(String uuid) {
        if (uuid == null) return null;
        for (Person p : DemoApplication.Person_List) {
            if (p.getId().toString().equals(uuid) && Boolean.TRUE.equals(p.getIsActive())) {
                return p;
            }
        }
        return null;
    }

    public Person updatePerson(PersonUpdateRequest updateObj) {
        Person person = getPersonById(updateObj.getUuid());

        if (person == null || person.getId() == null || !Boolean.TRUE.equals(person.getIsActive())) {
            return null;
        }
        DemoApplication.Person_List.remove(person);

        person.setUserName(getUserNameByCompare(person.getUserName(), updateObj));
        person.setEmail(HelperUtils.compare(person.getEmail(), updateObj.getEmailToUpdate()));

        person.setUpdatedDate(new Date());

        DemoApplication.Person_List.add(person);
        return person;
    }

    public List<Person> getAllPersons() {
        List<Person> resultList = new ArrayList<>();
        for (Person p : DemoApplication.Person_List) {
            if (Boolean.TRUE.equals(p.getIsActive())) {
                resultList.add(p);
            }
        }
        return resultList;
    }

    private Boolean checkIfUserNameOrEmailExists(String userName, String email) {
        return !DemoApplication.emails.contains(email) && !DemoApplication.userNames.contains(userName);
    }

    public String getFullName(PersonCreateRequest request) {
        return request.getPersonFirstName() + " " +
                request.getPersonMiddleName() + " " +
                request.getPersonLastName();
    }

    private UserName getUserNameByCompare(UserName currentUserNameObj, PersonUpdateRequest updateRequest) {
        if (currentUserNameObj == null || updateRequest.getUserNameToUpdate() == null) {
            return currentUserNameObj;
        }

        String finalCheckedName = HelperUtils.compare(currentUserNameObj.getActiveUserName(), updateRequest.getUserNameToUpdate());

        if (!currentUserNameObj.getActiveUserName().equals(finalCheckedName)) {
            if (!DemoApplication.userNames.contains(finalCheckedName)) {
                List<String> userNameHistory = currentUserNameObj.getPrevUserNames();
                if (userNameHistory == null) {
                    userNameHistory = new ArrayList<>();
                }
                userNameHistory.add(currentUserNameObj.getActiveUserName());
                currentUserNameObj.setPrevUserNames(userNameHistory);

                DemoApplication.userNames.add(finalCheckedName);
                currentUserNameObj.setActiveUserName(finalCheckedName);
            }
        } else {
            currentUserNameObj.setActiveUserName(finalCheckedName);
        }

        return currentUserNameObj;
    }

    public Boolean deleteById(String uuid) {
        Person person = getPersonById(uuid);
        if (person == null || person.getId() == null || !Boolean.TRUE.equals(person.getIsActive())) {
            return false;
        } else {
            DemoApplication.Person_List.remove(person);
            person.setIsActive(false);
            DemoApplication.Person_List.add(person);
            return true;
        }
    }
}
