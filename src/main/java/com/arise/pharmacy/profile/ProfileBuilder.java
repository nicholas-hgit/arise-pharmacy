package com.arise.pharmacy.profile;

import com.arise.pharmacy.exceptions.InvalidIdentityNumberException;
import com.arise.pharmacy.security.user.User;

public final class ProfileBuilder {

    private Long id;
    private String identityNumber;
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String image;

    private User user;

    public ProfileBuilder id(Long id){
        this.id = id;
        return this;
    }

    public ProfileBuilder user(User user){
        this.user = user;
        return this;
    }

    public ProfileBuilder identityNumber(String identityNumber){
        this.identityNumber = identityNumber;
        return this;
    }

    public ProfileBuilder firstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public ProfileBuilder lastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public ProfileBuilder phoneNumber(Long phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ProfileBuilder image(String image){
        this.image = image;
        return this;
    }

    public Profile build(){
        if(!isValidIdentityNumber(identityNumber)){
            throw new InvalidIdentityNumberException("invalid ID number");
        }
        return new Profile(id,identityNumber,firstName,lastName,phoneNumber,image,user);
    }

    private boolean isValidIdentityNumber(String id){
        if(id.length() != 13){
            return false;
        }

        int checkDigit = Character.getNumericValue(id.charAt(12));
        int totalDigitsSum = 0;
        for(int index = id.length() - 2; index >= 0; --index){

            int currDigit = Character.getNumericValue(id.charAt(index));
            if(index % 2 != 0){
                currDigit *= 2;
                if(currDigit > 9){
                    currDigit -= 9;
                }
            }

            totalDigitsSum += currDigit;
        }
        totalDigitsSum += checkDigit;

        return totalDigitsSum % 10 == 0;
    }


}
